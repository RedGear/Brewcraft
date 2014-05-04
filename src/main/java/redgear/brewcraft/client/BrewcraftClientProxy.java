package redgear.brewcraft.client;

import net.minecraft.util.ResourceLocation;
import redgear.brewcraft.blocks.TileEntityBrewery;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.entity.EntityBrewcraftPotion;
import redgear.brewcraft.entity.RenderBrewcraftPotion;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.VillagerRegistry;

public class BrewcraftClientProxy implements IPlugin {

	@Override
	public String getName() {
		return "BrewcraftClientProxy";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return true;
	}

	@Override
	public boolean isRequired() {
		return true;
	}

	@Override
	public void preInit(ModUtils mod) {
		RenderingRegistry.registerEntityRenderingHandler(EntityBrewcraftPotion.class, new RenderBrewcraftPotion());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBrewery.class, new TileRendererBrewery());
		new RenderItemBrewery(Brewcraft.brewing.renderId);

		VillagerRegistry.instance().registerVillagerSkin(
				Brewcraft.inst.getInt("Village", "Witch Profession ID", 15),
				new ResourceLocation("redgear_brewcraft", "textures/entity/villagerwitch.png"));
	}

	@Override
	public void Init(ModUtils mod) {

	}

	@Override
	public void postInit(ModUtils mod) {

	}
}
