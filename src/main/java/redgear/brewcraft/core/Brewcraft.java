package redgear.brewcraft.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumChatFormatting;
import redgear.brewcraft.blocks.brewery.BreweryFactory;
import redgear.brewcraft.blocks.sprayer.SprayerFactory;
import redgear.brewcraft.client.BrewcraftClientProxy;
import redgear.brewcraft.entity.EntityBrewcraftPotion;
import redgear.brewcraft.event.CraftingHandler;
import redgear.brewcraft.event.DamageHandler;
import redgear.brewcraft.event.DropHandler;
import redgear.brewcraft.event.TipHandler;
import redgear.brewcraft.event.TradeHandler;
import redgear.brewcraft.packet.ParticleHandler;
import redgear.brewcraft.plugins.common.AchievementPlugin;
import redgear.brewcraft.plugins.common.CraftingPlugin;
import redgear.brewcraft.plugins.common.DamageSourcePlugin;
import redgear.brewcraft.plugins.common.EffectPlugin;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import redgear.brewcraft.plugins.common.KegPlugin;
import redgear.brewcraft.plugins.common.PotionPlugin;
import redgear.brewcraft.plugins.common.VillagePlugin;
import redgear.brewcraft.plugins.compat.BuildcraftPlugin;
import redgear.brewcraft.plugins.compat.ForestryPlugin;
import redgear.brewcraft.plugins.compat.VanillaPlugin;
import redgear.brewcraft.utils.BrewcraftTab;
import redgear.brewcraft.utils.PotionArrayExpander;
import redgear.core.asm.RedGearCore;
import redgear.core.block.MetaTileSpecialRenderer;
import redgear.core.block.SubTile;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(modid = "redgear_brewcraft", name = "Brewcraft", version = "@ModVersion@", dependencies = "required-after:redgear_core;after:TConstruct;")
public class Brewcraft extends ModUtils {

	@Instance("redgear_brewcraft")
	public static ModUtils inst;

	public static MetaTileSpecialRenderer brewing;
	public static SimpleItem brewery;

	public static MetaTileSpecialRenderer machine;
	public static SimpleItem sprayer;

	public static CreativeTabs tab;

	@Override
	protected void PreInit(FMLPreInitializationEvent event) {
		
		//event.getModMetadata().name = EnumChatFormatting.DARK_AQUA + "Brewcraft";

		PotionArrayExpander.init();

		addPlugin(new DamageSourcePlugin());
		addPlugin(new EffectPlugin());
		addPlugin(new PotionPlugin());
		addPlugin(new KegPlugin());
		addPlugin(new IngredientPlugin());
		addPlugin(new AchievementPlugin());
		addPlugin(new CraftingPlugin());
		addPlugin(new VillagePlugin());

		if (isClient())
			addPlugin(new BrewcraftClientProxy());

		addPlugin(new ForestryPlugin());
		addPlugin(new BuildcraftPlugin());
		addPlugin(new VanillaPlugin());

		tab = new BrewcraftTab("brewcraft", getBoolean("General", "Toggle Unconventional Creative Tab Overlay",
				"Toggle the cool background for the Brewcraft creative tab."));

		EntityRegistry.registerModEntity(EntityBrewcraftPotion.class, "Potion",
				EntityRegistry.findGlobalUniqueEntityId(), RedGearCore.inst, 128, 10, true);

		brewing = new MetaTileSpecialRenderer(Material.iron, "RedGear.Brewcraft.Brewery",
				RenderingRegistry.getNextAvailableRenderId());

		brewing.setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setCreativeTab(tab)
				.setHarvestLevel("pickaxe", 0);
		brewery = brewing.addMetaBlock(new SubTile("brewery", new BreweryFactory()));

		machine = new MetaTileSpecialRenderer(Material.iron, "RedGear.Brewcraft.Machine",
				RenderingRegistry.getNextAvailableRenderId());

		machine.setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setCreativeTab(tab)
				.setHarvestLevel("pickaxe", 0);
		sprayer = machine.addMetaBlock(new SubTile("sprayer", new SprayerFactory()));
	}

	@Override
	protected void Init(FMLInitializationEvent event) {
		CraftingHandler.register();
		DamageHandler.register();
		DropHandler.register();
		TradeHandler.register();
		ParticleHandler.register();// hi guys! Mind if I join you?
		TipHandler.register();

	}

	@Override
	protected void PostInit(FMLPostInitializationEvent event) {

	}

	@Override
	@Mod.EventHandler
	public void PreInitialization(FMLPreInitializationEvent event) {
		super.PreInitialization(event);
	}

	@Override
	@Mod.EventHandler
	public void Initialization(FMLInitializationEvent event) {
		super.Initialization(event);
	}

	@Override
	@Mod.EventHandler
	public void PostInitialization(FMLPostInitializationEvent event) {
		super.PostInitialization(event);
	}
}
