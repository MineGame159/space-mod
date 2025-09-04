package minegame159.spacemod.blocks;

import com.mojang.serialization.MapCodec;
import minegame159.spacemod.planets.Planets;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class UnlitTorchBlock extends BaseTorchBlock {
    protected UnlitTorchBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseTorchBlock> codec() {
        return simpleCodec(UnlitTorchBlock::new);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.getItem() instanceof FlintAndSteelItem && Planets.hasOxygen(level.dimension())) {
            if (state.hasProperty(UnlitWallTorchBlock.FACING)) {
                state = Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, state.getValue(UnlitWallTorchBlock.FACING));
            } else {
                state = Blocks.TORCH.defaultBlockState();
            }

            level.setBlock(pos, state, 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1, level.getRandom().nextFloat() * 0.4f + 0.8f);

            if (player != null) {
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
