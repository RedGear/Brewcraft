package redgear.brewcraft.plugins.compat;

import net.minecraft.item.ItemStack;
import redgear.brewcraft.common.Brewcraft;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState.ModState;

public class SWTPlugin implements IPlugin {

	@Override
	public String getName() {
		return "SWTPlugin";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return true;
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public void preInit(ModUtils mod) {

	}

	@Override
	public void Init(ModUtils mod) {
		if (Brewcraft.inst.getBoolean("Plugins", "Stuff Worth Throwing Plugin", "Toggle Stuff Worth Throwing Plugin",
				true)) {
			if (Loader.isModLoaded("renkin42swt")) {
				try {
					Class items = Class.forName("renkin42.stuffWorthThrowing.items.StuffWorthThrowingItems");
					if (items != null) {
						SimpleItem dynamiteSnowball = new SimpleItem((ItemStack) items.getField("dynamiteSnowball")
								.get(null));
						SimpleItem dynamite = new SimpleItem((ItemStack) items.getField("dynamite").get(null));
						SimpleItem rockSnowball = new SimpleItem((ItemStack) items.getField("snowballRock").get(null));
						SimpleItem ectoplasm = new SimpleItem((ItemStack) items.getField("ectoplasm").get(null));
						SimpleItem pureSand = new SimpleItem((ItemStack) items.getField("purifiedSoul").get(null));
						SimpleItem fungus = new SimpleItem((ItemStack) items.getField("gungusSpore").get(null));
						SimpleItem corruptEctoplasm = new SimpleItem((ItemStack) items.getField("corruptedEctoplasm")
								.get(null));
					}
				} catch (Exception e) {
					mod.logDebug("STW reflection failed.", e);
				}
			}
		}
	}

	@Override
	public void postInit(ModUtils mod) {

	}

}
