package redgear.brewcraft.blocks.sprayer;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.packet.ParticleHandler;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.SubItemPotion;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.tile.IBucketableTank;
import redgear.core.tile.TileEntityTank;

public class TileEntitySprayer extends TileEntityTank implements IBucketableTank {

	public final AdvFluidTank tank;

	public boolean isPowered = false;

	public TileEntitySprayer() {
		super(10);

		tank = new SprayerTank(FluidContainerRegistry.BUCKET_VOLUME * 12);

		addTank(tank);
	}

	@Override
	protected boolean doPreWork() {
		return true;
	}

	@Override
	protected int checkWork() {
		checkPower();
		if (isPowered) {
			if (!tank.isEmpty()) {
				return 1;
			}
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean doWork() {
		AxisAlignedBB range = this.getRenderBoundingBox().expand(3.0D, 2.0D, 3.0D);
		List<EntityLivingBase> entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, range);

		for (EntityLivingBase entity : entities) {
			final FluidStack fluid = tank.getFluid();
			final ItemStack potion = FluidContainerRegistry.fillFluidContainer(new FluidStack(fluid, 1000),
					new ItemStack(Items.glass_bottle));

			if (potion != null && potion.getItem() instanceof MetaItemPotion) {
				final MetaItemPotion bottle = (MetaItemPotion) potion.getItem();
				final SubItemPotion bot = (SubItemPotion) bottle.getMetaItem(bottle.getDamage(potion));

				ParticleHandler.send(xCoord + 0.5, yCoord + 1, zCoord + 0.5, bot.getEffect(), 1, 0.5D);
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.fizz", 0.5F,
						worldObj.rand.nextFloat() * 0.1F + 0.9F);

				if (bot != null)
					entity.addPotionEffect(new PotionEffect(bot.getEffect().id, bot.getEffect().isInstant() ? 1 : bot.duration / 2, bot.strength));

			} else if (potion != null && potion.getItem() instanceof ItemPotion) {
				final List<PotionEffect> list = PotionHelper.getPotionEffects(potion.getItemDamage(), false);

				if (list != null) {
					final Iterator<PotionEffect> iterator = list.iterator();
					final PotionEffect effect = iterator.next();

					if (effect != null) {
						ParticleHandler.send(xCoord + 0.5, yCoord + 1, zCoord + 0.5, Potion.potionTypes[effect.getPotionID()], 1, 0.5D);
						worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.fizz", 0.5F,
								worldObj.rand.nextFloat() * 0.1F + 0.9F);

						entity.addPotionEffect(new PotionEffect(effect.getPotionID(), effect.getDuration() / 2, effect
								.getAmplifier()));
					}
				}
			}
		}

		tank.drain(50, true);

		return false;
	}

	@Override
	protected boolean tryUseEnergy(int energy) {
		return true;
	}

	@Override
	protected boolean doPostWork() {
		checkPower();
		return false;
	}

	public void checkPower() {
		isPowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}
}
