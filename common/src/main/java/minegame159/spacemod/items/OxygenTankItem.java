package minegame159.spacemod.items;

import minegame159.spacemod.SpaceMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class OxygenTankItem extends Item {
    public OxygenTankItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        var storage = stack.get(ModDataComponents.OXYGEN_STORAGE.get());

        if (storage != null) {
            tooltipComponents.add(SpaceMod
                .translatable("text", "oxygen_storage", (int) (storage.progress() * 100))
                .withStyle(ChatFormatting.GRAY)
            );
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.has(ModDataComponents.OXYGEN_STORAGE.get());
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        var storage = stack.get(ModDataComponents.OXYGEN_STORAGE.get());
        if (storage == null) return 0;

        return Math.clamp((int) (storage.progress() * 13), 0, 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return FastColor.ARGB32.color(50, 225, 225);
    }
}
