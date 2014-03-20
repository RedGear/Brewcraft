package redgear.brewcraft.common;

import net.minecraft.block.material.Material;
import redgear.brewcraft.blocks.RenderItemBrewery;
import redgear.brewcraft.blocks.TileEntityBrewery;
import redgear.brewcraft.blocks.TileRendererBrewery;
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
	


	public  MetaTile createBrewery(int blockId){
		return new MetaTileSpecialRenderer(blockId, Material.iron, "RedGear.Brewcraft.Brewery", new RenderItemBrewery().getRenderId());
	}	

}
