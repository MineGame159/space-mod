package minegame159.spacemod;

import dev.architectury.event.EventResult;
import minegame159.spacemod.api.ResourceInteraction;
import minegame159.spacemod.api.ResourceView;
import minegame159.spacemod.api.fluid.FluidApi;
import minegame159.spacemod.api.fluid.FluidResource;
import minegame159.spacemod.blocks.MachineBlock;
import minegame159.spacemod.utils.ResourceUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public final class BucketHandler {
    private BucketHandler() {}

    public static EventResult onRightClickBlock(Player player, InteractionHand hand, BlockPos pos, Direction face) {
        // Check sneaking
        if (player.isShiftKeyDown()) return EventResult.pass();

        // Check bucket
        var stack = player.getItemInHand(hand);
        if (!(stack.getItem() instanceof BucketItem bucket)) return EventResult.pass();

        // Check machine block
        var state = player.level().getBlockState(pos);
        if (!(state.getBlock() instanceof MachineBlock)) return EventResult.pass();

        // Check fluid view
        var view = FluidApi.SIDED.find(player.level(), pos, face);
        if (view == null) return EventResult.pass();

        // Check for server
        if (player.level().isClientSide) return EventResult.interruptTrue();

        // Fill or empty bucket
        var fluid = bucket.arch$getFluid();

        if (fluid == Fluids.EMPTY) fillBucket(player, hand, pos, view);
        else emptyBucket(player, hand, pos, view, fluid);

        return EventResult.interruptTrue();
    }

    private static void fillBucket(Player player, InteractionHand hand, BlockPos pos, ResourceView<FluidResource> view) {
        // Check main output slot
        var slot = view.mainOutput();
        if (slot == null) return;

        // Check fluid amount
        if (slot.getAmount() < FluidApi.BUCKET_AMOUNT) return;

        // Check fluid bucket
        var bucket = slot.getResource().getFluid().getBucket();
        if (bucket == Items.AIR) return;

        // Extract fluid from slot
        if (!slot.canExtract(ResourceInteraction.Manual)) return;

        var resource = slot.getResource();

        var canExtract = slot.extract(resource, FluidApi.BUCKET_AMOUNT, ResourceInteraction.Simulation);
        if (canExtract < FluidApi.BUCKET_AMOUNT) return;

        var extracted = slot.extract(resource, FluidApi.BUCKET_AMOUNT, ResourceInteraction.Manual);

        // Check if a bucket worth of fluid was extracted
        if (extracted == FluidApi.BUCKET_AMOUNT) {
            player.setItemInHand(hand, bucket.getDefaultInstance());
            playFillSound(player, pos, resource.getFluid());

            return;
        }

        // Insert extracted fluid otherwise
        slot.insert(resource, extracted, ResourceInteraction.Manual);
    }

    private static void emptyBucket(Player player, InteractionHand hand, BlockPos pos, ResourceView<FluidResource> view, Fluid fluid) {
        var resource = FluidResource.of(fluid);

        // Insert fluid into view
        var canInsert = ResourceUtils.insert(view, resource, FluidApi.BUCKET_AMOUNT, ResourceInteraction.Simulation);
        if (canInsert < FluidApi.BUCKET_AMOUNT) return;

        var inserted = ResourceUtils.insert(view, resource, FluidApi.BUCKET_AMOUNT, ResourceInteraction.Manual);

        // Check if a bucket worth of fluid was inserted
        if (inserted == FluidApi.BUCKET_AMOUNT) {
            if (!player.isCreative()) {
                player.setItemInHand(hand, Items.BUCKET.getDefaultInstance());
            }

            playEmptySound(player, pos, fluid);

            return;
        }

        // Extract inserted fluid otherwise
        ResourceUtils.extract(view, resource, inserted, ResourceInteraction.Manual);
    }

    private static void playFillSound(Player player, BlockPos pos, Fluid fluid) {
        fluid.getPickupSound().ifPresent(soundEvent -> {
            player.level().playSound(player, pos, soundEvent, SoundSource.PLAYERS);
        });
    }

    private static void playEmptySound(Player player, BlockPos pos, Fluid fluid) {
        SoundEvent soundEvent = fluid.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
        player.level().playSound(player, pos, soundEvent, SoundSource.BLOCKS);
    }
}
