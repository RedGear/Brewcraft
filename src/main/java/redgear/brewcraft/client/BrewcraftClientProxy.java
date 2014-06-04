package redgear.brewcraft.client;

import net.minecraft.util.ResourceLocation;
import redgear.brewcraft.blocks.brewery.TileEntityBrewery;
import redgear.brewcraft.blocks.keg.TileEntityKeg;
import redgear.brewcraft.blocks.sprayer.TileEntitySprayer;
import redgear.brewcraft.client.render.item.RenderItemBrewery;
import redgear.brewcraft.client.render.item.RenderItemKeg;
import redgear.brewcraft.client.render.item.RenderItemSprayer;
import redgear.brewcraft.client.render.tile.TileRendererBrewery;
import redgear.brewcraft.client.render.tile.TileRendererKeg;
import redgear.brewcraft.client.render.tile.TileRendererSprayer;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.entity.EntityBrewcraftPotion;
import redgear.brewcraft.entity.RenderBrewcraftPotion;
import redgear.brewcraft.plugins.block.KegPlugin;
import redgear.brewcraft.plugins.block.MachinePlugin;
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
		return Brewcraft.inst.isClient();
	}

	@Override
	public boolean isRequired() {
		return true;
	}

	@Override
	public void preInit(ModUtils mod) {
		RenderingRegistry.registerEntityRenderingHandler(EntityBrewcraftPotion.class, new RenderBrewcraftPotion());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySprayer.class, new TileRendererSprayer());
		new RenderItemSprayer(MachinePlugin.machine.renderId);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBrewery.class, new TileRendererBrewery());
		new RenderItemBrewery(MachinePlugin.brewing.renderId);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKeg.class, new TileRendererKeg());
		new RenderItemKeg(KegPlugin.kegs.renderId);

		VillagerRegistry.instance().registerVillagerSkin(Brewcraft.inst.getInt("General", "Witch Profession ID", 15),
				new ResourceLocation("redgear_brewcraft", "textures/entity/villagerwitch.png"));
	}

	@Override
	public void Init(ModUtils mod) {

	}

	@Override
	public void postInit(ModUtils mod) {

	}
}
