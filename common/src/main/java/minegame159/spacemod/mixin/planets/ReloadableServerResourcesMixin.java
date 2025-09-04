package minegame159.spacemod.mixin.planets;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import minegame159.spacemod.planets.Planets;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(ReloadableServerResources.class)
public abstract class ReloadableServerResourcesMixin {
    @ModifyReturnValue(method = "listeners", at = @At("RETURN"))
    private List<PreparableReloadListener> spacemod$listeners(List<PreparableReloadListener> listeners) {
        var list = new ArrayList<>(listeners);
        list.add(new Planets.Loader());

        return list;
    }
}
