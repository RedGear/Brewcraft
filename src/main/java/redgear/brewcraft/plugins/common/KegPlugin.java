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

		kegOak = kegs.addMetaBlock(new SubTile("kegOak", new KegFactory("Oak")));
		kegBirch = kegs.addMetaBlock(new SubTile("kegBirch", new KegFactory("Birch")));
		kegJungle = kegs.addMetaBlock(new SubTile("kegJungle", new KegFactory("Jungle")));
		kegSpruce = kegs.addMetaBlock(new SubTile("kegSpruce", new KegFactory("Spruce")));
		kegDark = kegs.addMetaBlock(new SubTile("kegDark", new KegFactory("Dark")));
		kegAcacia = kegs.addMetaBlock(new SubTile("kegAcacia", new KegFactory("Acacia")));
		kegIron = kegs.addMetaBlock(new SubTile("kegIron", new KegFactory("Iron")));
		kegSealed = kegs.addMetaBlock(new SubTile("kegSealed", new KegFactory("Sealed")));
		kegPlated = kegs.addMetaBlock(new SubTile("kegPlated", new KegFactory("Plated")));

		kegSteel = kegs.addMetaBlock(new SubTile("kegSteel", new KegFactory("Steel")));
		kegCopper = kegs.addMetaBlock(new SubTile("kegCopper", new KegFactory("Copper")));
		kegSilver = kegs.addMetaBlock(new SubTile("kegSilver", new KegFactory("Silver")));
		kegTungsten = kegs.addMetaBlock(new SubTile("kegTungsten", new KegFactory("Tungsten")));
		kegBrass = kegs.addMetaBlock(new SubTile("kegBrass", new KegFactory("Brass")));
		kegRubber = kegs.addMetaBlock(new SubTile("kegRubber", new KegFactory("Rubber")));

	}

	@Override
	public void Init(ModUtils mod) {

	}

	@Override
	public void postInit(ModUtils mod) {

	}
}
