package minegame159.spacemod.utils;

import minegame159.spacemod.api.fluid.FluidApi;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.material.Fluid;

import java.text.DecimalFormat;

public final class Components {
    private Components() {}

    // Energy

    private static final DecimalFormat ENERGY_M_FORMAT = new DecimalFormat("#.##mE");
    private static final DecimalFormat ENERGY_K_FORMAT = new DecimalFormat("#.##kE");

    public static MutableComponent energyAmount(int amount, int capacity) {
        return energyAmount(amount).append(" / ").append(energyAmount(capacity));
    }

    public static MutableComponent energyAmount(int amount) {
        if (amount > 1000000) {
            return Component.literal(ENERGY_M_FORMAT.format(amount / 1000000f));
        }

        if (amount > 1000) {
            return Component.literal(ENERGY_K_FORMAT.format(amount / 1000f));
        }

        return Component.literal(amount + "E");
    }

    public static MutableComponent energyUsage(int usage) {
        if (usage > 0) Component.literal("+").append(energyAmount(usage)).append("/t");
        return energyAmount(usage).append("/t");
    }

    // Fluid

    private static final DecimalFormat FLUID_BUCKET_FORMAT = new DecimalFormat("#.##B");

    public static MutableComponent fluidName(Fluid fluid) {
        var id = fluid.arch$registryName();
        return Component.translatable("fluid." + id.getNamespace() + "." + id.getPath());
    }

    public static MutableComponent fluidAmount(int amount, int capacity) {
        return fluidAmount(amount).append(" / ").append(fluidAmount(capacity));
    }

    public static MutableComponent fluidAmount(int amount) {
        var mb = FluidApi.platformUnitToMb(amount);

        if (mb > 1000) {
            return Component.literal(FLUID_BUCKET_FORMAT.format(mb / 1000f));
        }

        return Component.literal(mb + "mB");
    }

    // Progress

    public static MutableComponent progress(float progress) {
        return Component.literal((int) (progress * 100) + "%");
    }
}
