package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import redgear.brewcraft.plugins.core.AchievementPlugin;
import redgear.core.util.SimpleItem;
import redgear.core.world.WorldLocation;

public class EffectEternalFlame extends PotionExtension {

	public EffectEternalFlame(int id) {
		super(id, true, 0xFF9933);
		setPotionName("potion.brewcraft.flame");
		setIconIndex(7, 0);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		living.setFire(2);

		if (living.worldObj.rand.nextInt(100) == 0) {
			WorldLocation base = new WorldLocation((int) living.posX, (int) living.posY, (int) living.posZ,
					living.worldObj).translate(2, 2, 2);
			WorldLocation loc;

			SimpleItem fire = new SimpleItem(Blocks.fire);

			for (int x = 0; x < 3; x++)
				for (int y = 0; y < 3; y++)
					for (int z = 0; z < 3; z++) {
						loc = base.translate(x, y, z);
						if (loc.isAir())
							loc.placeBlock(fire);
					}
		}

		if (living instanceof EntityPlayer && AchievementPlugin.flame != null)
			((EntityPlayer) living).addStat(AchievementPlugin.flame, 1);
	}

}
