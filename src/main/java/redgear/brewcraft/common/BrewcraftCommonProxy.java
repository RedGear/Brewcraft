package redgear.brewcraft.common;

import net.minecraft.block.material.Material;
import redgear.core.block.MetaTile;
import redgear.core.block.MetaTileSpecialRenderer;
import cpw.mods.fml.client.registry.RenderingRegistry;


public class BrewcraftCommonProxy {
	
	public void registerRenders() {
		
		
		
	}
	
	public  MetaTile createBrewery(){
		return new MetaTileSpecialRenderer(Material.iron, "RedGear.Brewcraft.Brewery", RenderingRegistry.getNextAvailableRenderId());
		//new RenderItemBrewery(), new TileRendererBrewery()
	}

}
