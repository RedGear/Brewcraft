package redgear.brewcraft.plugins.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import redgear.brewcraft.blocks.keg.KegFactory;
import redgear.brewcraft.blocks.keg.MetaTileKeg;
import redgear.brewcraft.core.Brewcraft;
import redgear.core.block.SubTile;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.ItemStackUtil;
import redgear.core.util.SimpleItem;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.LoaderState.ModState;

public class KegPlugin implements IPlugin {

	public static MetaTileKeg kegs;
	public static SimpleItem kegOak;
	public static SimpleItem kegBirch;
	public static SimpleItem kegJungle;
	public static SimpleItem kegSpruce;
	public static SimpleItem kegDark;
	public static SimpleItem kegAcacia;
	public static SimpleItem kegIron;
	public static SimpleItem kegSealed;
	public static SimpleItem kegPlated;
	public static SimpleItem kegSteel;
	public static SimpleItem kegCopper;
	public static SimpleItem kegSilver;
	public static SimpleItem kegTungsten;
	public static SimpleItem kegBrass;
	public static SimpleItem kegRubber;

	@Override
	public String getName() {
		return "KegPlugin";
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

		kegs = new MetaTileKeg(Material.wood, "RedGear.Brewcraft.Kegs", RenderingRegistry.getNextAvailableRenderId());

		kegs.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setCreativeTab(Brewcraft.tab)
				.setHarvestLevel("axe", 0);

		kegOak = kegs.addMetaBlock(new SubTile("kegOak", new KegFactory("oak")));
		kegBirch = kegs.addMetaBlock(new SubTile("kegBirch", new KegFactory("birch")));
		kegJungle = kegs.addMetaBlock(new SubTile("kegJungle", new KegFactory("jungle")));
		kegSpruce = kegs.addMetaBlock(new SubTile("kegSpruce", new KegFactory("spruce")));
		kegDark = kegs.addMetaBlock(new SubTile("kegDark", new KegFactory("dark")));
		kegAcacia = kegs.addMetaBlock(new SubTile("kegAcacia", new KegFactory("acacia")));
		kegIron = kegs.addMetaBlock(new SubTile("kegIron", new KegFactory("iron")));
		kegSealed = kegs.addMetaBlock(new SubTile("kegSealed", new KegFactory("sealed")));
		kegPlated = kegs.addMetaBlock(new SubTile("kegPlated", new KegFactory("plated")));
		//kegSteel = kegCheck("ingotSteel");
		//kegCopper = kegCheck("ingotCopper");
		//kegSilver = kegCheck("ingotSilver");
		//kegTungsten = kegCheck("ingotTungsten");
		//kegBrass = kegCheck("ingotBrass");

		//if (ItemStackUtil.getOreWithName("materialRubber") != null)
		//kegRubber = kegCheck("materialRubber");
		//else if (ItemStackUtil.getOreWithName("blockRubber") != null)
		//kegRubber = kegCheck("blockRubber");
	}

	@Override
	public void Init(ModUtils mod) {

	}

	@Override
	public void postInit(ModUtils mod) {

	}

	private SimpleItem kegCheck(String ingot) {
		ItemStack metal = ItemStackUtil.getOreWithName(ingot);
		if (metal != null) {
			SimpleItem barrel = kegs.addMetaBlock(new SubTile("barrel." + ingot, new KegFactory(ingot)));
			return barrel;
		} else {
			return null;
		}
	}
}
