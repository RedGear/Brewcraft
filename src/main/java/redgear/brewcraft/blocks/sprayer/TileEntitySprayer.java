package redgear.brewcraft.blocks.sprayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.packet.ParticleHandler;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.SubItemPotion;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.tile.Faced;
import redgear.core.tile.TileEntityTank;
import cofh.api.tileentity.IRedstoneCache;

public class TileEntitySprayer extends TileEntityTank implements IRedstoneCache, Faced {

	public final AdvFluidTank tank;

	public boolean isPowered = false;
	public int delay = 1;

	public TileEntitySprayer() {
		super(20);

		tank = new SprayerTank(FluidContainerRegistry.BUCKET_VOLUME * 8);

		addTank(tank);
	}

	@Override
	public boolean doPreWork() {
		if (isPowered && tank.getAmount() >= 50) {

			setIdle(delay * 20);

			AxisAlignedBB range = worldObj.getBlock(xCoord, yCoord, zCoord).getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord).expand(3.0D, 2.0D, 3.0D);
			@SuppressWarnings("unchecked")
			List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, range);

			final FluidStack fluid = tank.getFluid();
			final ItemStack potion = FluidContainerRegistry.fillFluidContainer(new FluidStack(fluid, 1000),
					new ItemStack(Items.glass_bottle));
			Collection<PotionEffect> effects = null;

			if (potion != null && potion.getItem() instanceof MetaItemPotion) {
				final MetaItemPotion bottle = (MetaItemPotion) potion.getItem();
				final SubItemPotion bot = bottle.getMetaItem(bottle.getDamage(potion));

				ParticleHandler.send(worldObj, xCoord + 0.5, yCoord + 1, zCoord + 0.5, bot.getEffect(),
						ParticleHandler.MIST);
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.fizz", 0.5F,
						worldObj.rand.nextFloat() * 0.1F + 0.9F);

				effects = new ArrayList<PotionEffect>(1);
				effects.add(new PotionEffect(bot.getEffect().id, bot.getEffect().isInstant() ? 1 : bot.duration / 2,
						bot.strength));

			} else if (potion != null && potion.getItem() instanceof ItemPotion) {
				@SuppressWarnings("unchecked")
				final List<PotionEffect> list = PotionHelper.getPotionEffects(potion.getItemDamage(), false);

				if (list != null) {

					ParticleHandler.send(worldObj, xCoord + 0.5, yCoord + 1, zCoord + 0.5,
							PotionHelper.func_77915_a(potion.getItemDamage(), false), true, ParticleHandler.MIST);
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.fizz", 0.5F,
							worldObj.rand.nextFloat() * 0.1F + 0.9F);

					effects = list;
				}
			} else
				return false;

			if (effects != null)
				for (EntityLivingBase entity : entities)
					for (PotionEffect effect : effects)
						entity.addPotionEffect(effect);
			tank.drain(50, true);

			return true;
		} else
			return false;

	}

	@Override
	public int checkWork() {
		return 0;
	}

	@Override
	public boolean doWork() {
		return false;
	}

	@Override
	public boolean tryUseEnergy(int energy) {
		return true;
	}

	@Override
	public boolean doPostWork() {
		return false;
	}

	@Override
	public void setPowered(boolean isPowered) {
		setIdle(0);
		this.isPowered = isPowered;
	}

	@Override
	public boolean isPowered() {
		return isPowered;
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("delay", delay);
		tag.setBoolean("isPowered", isPowered);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		delay = tag.getInteger("delay");
		isPowered = tag.getBoolean("isPowered");
	}
}
