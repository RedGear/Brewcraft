package redgear.brewcraft.common;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.material.Material;
import redgear.brewcraft.blocks.RenderItemBrewery;
import redgear.core.block.MetaTile;
import redgear.core.block.MetaTileSpecialRenderer;


public class BrewcraftCommonProxy {
	
	public void registerRenders() {
		
		
		
	}


	public  MetaTile createBrewery(){
		return new MetaTileSpecialRenderer(Material.iron, "RedGear.Brewcraft.Brewery", RenderingRegistry.getNextAvailableRenderId());
		//new RenderItemBrewery(), new TileRendererBrewery()
	}
	
	

}
