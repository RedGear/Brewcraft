package redgear.brewcraft.client;

import net.minecraft.block.material.Material;
import redgear.brewcraft.blocks.TileEntityBrewery;
import redgear.brewcraft.common.BrewcraftCommonProxy;
import redgear.brewcraft.entity.EntityBrewcraftPotion;
import redgear.brewcraft.entity.RenderBrewcraftPotion;
import redgear.core.block.MetaTile;
import redgear.core.block.MetaTileSpecialRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BrewcraftClientProxy extends BrewcraftCommonProxy {

	@Override
	public void registerRenders() {

		RenderingRegistry.registerEntityRenderingHandler(EntityBrewcraftPotion.class, new RenderBrewcraftPotion());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBrewery.class, new TileRendererBrewery());
	}

	@Override
	public MetaTile createBrewery() {
		return new MetaTileSpecialRenderer(Material.iron, "RedGear.Brewcraft.Brewery",
				new RenderItemBrewery().getRenderId());
	}
}
