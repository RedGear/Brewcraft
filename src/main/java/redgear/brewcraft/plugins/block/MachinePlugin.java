package redgear.brewcraft.plugins.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import redgear.brewcraft.blocks.brewery.BreweryFactory;
import redgear.brewcraft.blocks.sprayer.SprayerFactory;
import redgear.brewcraft.core.Brewcraft;
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
		brewing.setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setCreativeTab(Brewcraft.tab)
				.setHarvestLevel("pickaxe", 0);
		brewery = brewing.addMetaBlock(new SubTile("brewery", new BreweryFactory()));

		machine = new MetaTileSpecialRenderer(Material.iron, "RedGear.Brewcraft.Machine",
				RenderingRegistry.getNextAvailableRenderId());
		machine.setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setCreativeTab(Brewcraft.tab)
				.setHarvestLevel("pickaxe", 0);
		sprayer = machine.addMetaBlock(new SubTile("sprayer", new SprayerFactory()));

	}

	@Override
	public void Init(ModUtils mod) {

	}

	@Override
	public void postInit(ModUtils mod) {

	}
}
