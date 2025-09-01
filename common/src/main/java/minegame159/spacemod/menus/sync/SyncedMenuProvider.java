package minegame159.spacemod.menus.sync;

import net.minecraft.world.MenuProvider;

public interface SyncedMenuProvider<D extends MenuData<D>> extends MenuProvider {
    D getMenuData();
}
