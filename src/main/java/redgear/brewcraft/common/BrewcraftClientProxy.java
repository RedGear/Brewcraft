package redgear.brewcraft.common;

import redgear.brewcraft.entity.EntityBrewcraftPotion;
import redgear.brewcraft.entity.RenderBrewcraftPotion;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BrewcraftClientProxy extends BrewcraftCommonProxy {
	
	@Override
	public void registerRenders() {
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBrewcraftPotion.class, new RenderBrewcraftPotion());
		
	}

}
