package redgear.brewcraft.plugins.common;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.effects.EffectAngel;
import redgear.brewcraft.effects.EffectCreeper;
import redgear.brewcraft.effects.EffectEnternalFlame;
import redgear.brewcraft.effects.EffectFireEater;
import redgear.brewcraft.effects.EffectFireproof;
import redgear.brewcraft.effects.EffectFlight;
import redgear.brewcraft.effects.EffectFrozen;
import redgear.brewcraft.effects.EffectImmunity;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import cpw.mods.fml.common.LoaderState.ModState;

public class EffectPlugin implements IPlugin {

	public static Potion angel;
	public static Potion flight;
	public static Potion creeper;
	public static Potion immunity;
	public static Potion frozen;
	public static Potion fireproof;
	public static Potion flame;
	public static Potion fireEater;

	@Override
	public String getName() {
		return "EffectPlugin";
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

	}

	@Override
	public void Init(ModUtils mod) {

		angel = new EffectAngel(Brewcraft.inst.getInt("Potion Effect IDs", "'Angelic' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 40));

		flight = new EffectFlight(Brewcraft.inst.getInt("Potion Effect IDs", "'Flight' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 41));

		creeper = new EffectCreeper(Brewcraft.inst.getInt("Potion Effect IDs", "'Combustion' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 42));

		immunity = new EffectImmunity(Brewcraft.inst.getInt("Potion Effect IDs", "'Immunity' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 43));

		frozen = new EffectFrozen(Brewcraft.inst.getInt("Potion Effect IDs", "'Frozen' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 44))
				.func_111184_a(SharedMonsterAttributes.movementSpeed, "7107DE5E-7CE8-4030-940E-514C1F160891",
						-0.95000000596046448D, 2);

		fireproof = new EffectFireproof(Brewcraft.inst.getInt("Potion Effect IDs", "'Fireproof' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 45));
		
		flame = new EffectEnternalFlame(Brewcraft.inst.getInt("Potion Effect IDs", "'Eternal Flame' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 46));
		
		fireEater = new EffectFireEater(Brewcraft.inst.getInt("Potion Effect IDs", "'Fire Eater' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 47));
	}

	@Override
	public void postInit(ModUtils mod) {

	}
}
