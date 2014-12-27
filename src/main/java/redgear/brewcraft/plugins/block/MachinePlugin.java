package redgear.brewcraft.plugins.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import redgear.brewcraft.blocks.bottler.BottlerFactory;
import redgear.brewcraft.blocks.brewery.BreweryFactory;
import redgear.brewcraft.blocks.sprayer.SprayerFactory;
import redgear.brewcraft.blocks.unbottler.UnBottlerFactory;
import redgear.brewcraft.core.Brewcraft;
import redgear.core.block.MetaTile;
import redgear.core.block.MetaTileSpecialRenderer;
import redgear.core.block.SubTile;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.LoaderState.ModState;

public class MachinePlugin implements IPlugin {

	public static MetaTileSpecialRenderer brewing;
	public static SimpleItem brewery;

	public static MetaTileSpecialRenderer machine;
	public static SimpleItem sprayer;
	
	public static MetaTile bottlers;
	public static SimpleItem bottler;
	public static SimpleItem unbottler;

	@Override
	public String getName() {
		return "MachinePlugin";
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

		brewing = new MetaTileSpecialRenderer(Material.iron, "RedGear.Brewcraft.Brewery",
				RenderingRegistry.getNextAvailableRenderId());
		brewing.setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setCreativeTab(Brewcraft.tabMisc)
				.setHarvestLevel("pickaxe", 0);
		brewery = brewing.addMetaBlock(new SubTile("brewery", new BreweryFactory()));

		machine = new MetaTileSpecialRenderer(Material.iron, "RedGear.Brewcraft.Machine",
				RenderingRegistry.getNextAvailableRenderId());
		machine.setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setCreativeTab(Brewcraft.tabMisc)
				.setHarvestLevel("pickaxe", 0);
		sprayer = machine.addMetaBlock(new SubTile("sprayer", new SprayerFactory()));
		
		
		bottlers = new MetaTile(Material.piston, "RedGear.Brewcraft.Bottlers");
		bottlers.setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setCreativeTab(Brewcraft.tabMisc)
			.setHarvestLevel("pickaxe", 0);
		
		bottler   = bottlers.addMetaBlock(new SubTile("bottler",   new BottlerFactory()));
		unbottler = bottlers.addMetaBlock(new SubTile("unbottler", new UnBottlerFactory()));
	}

	@Override
	public void Init(ModUtils mod) {

	}

	@Override
	public void postInit(ModUtils mod) {

	}
}
