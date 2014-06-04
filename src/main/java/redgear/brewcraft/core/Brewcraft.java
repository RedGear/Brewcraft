package redgear.brewcraft.core;

import net.minecraft.creativetab.CreativeTabs;
import redgear.brewcraft.client.BrewcraftClientProxy;
import redgear.brewcraft.entity.EntityBrewcraftPotion;
import redgear.brewcraft.event.CraftingHandler;
import redgear.brewcraft.event.DamageHandler;
import redgear.brewcraft.event.DropHandler;
import redgear.brewcraft.event.TipHandler;
import redgear.brewcraft.event.TradeHandler;
import redgear.brewcraft.packet.ParticleHandler;
import redgear.brewcraft.plugins.block.KegPlugin;
import redgear.brewcraft.plugins.block.MachinePlugin;
import redgear.brewcraft.plugins.compat.BuildcraftPlugin;
import redgear.brewcraft.plugins.compat.ForestryPlugin;
import redgear.brewcraft.plugins.compat.VanillaPlugin;
import redgear.brewcraft.plugins.core.AchievementPlugin;
import redgear.brewcraft.plugins.core.CraftingPlugin;
import redgear.brewcraft.plugins.core.DamageSourcePlugin;
import redgear.brewcraft.plugins.core.EffectPlugin;
import redgear.brewcraft.plugins.item.IngredientPlugin;
import redgear.brewcraft.plugins.item.PotionPlugin;
import redgear.brewcraft.plugins.world.VillagePlugin;
import redgear.brewcraft.utils.BrewcraftTab;
import redgear.brewcraft.utils.PotionArrayExpander;
import redgear.core.asm.RedGearCore;
import redgear.core.mod.ModUtils;
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

	public static CreativeTabs tab;

	@Override
	protected void PreInit(FMLPreInitializationEvent event) {
		
		PotionArrayExpander.init();

		addPlugin(new DamageSourcePlugin());
		addPlugin(new EffectPlugin());
		addPlugin(new PotionPlugin());
		addPlugin(new MachinePlugin());
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

		tab = new BrewcraftTab(getBoolean("General", "Toggle Unconventional Creative Tab Overlays"));

		EntityRegistry.registerModEntity(EntityBrewcraftPotion.class, "Brewcraft:Potion",
				EntityRegistry.findGlobalUniqueEntityId(), RedGearCore.inst, 128, 10, true);
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
