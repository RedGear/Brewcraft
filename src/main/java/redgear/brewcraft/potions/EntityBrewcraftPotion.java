package redgear.brewcraft.potions;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBrewcraftPotion extends EntityThrowable {

	private final SubItemPotion potion;

	public EntityBrewcraftPotion(World world, EntityPlayer thrower, SubItemPotion potion) {
		super(world, thrower);
		this.potion = potion;
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {

		AxisAlignedBB range = boundingBox.expand(4.0D, 2.0D, 4.0D);
		List<EntityLivingBase> entiesList = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, range);

		for (EntityLivingBase entity : entiesList) { //Use a for each whenever possible
			double distance = getDistanceSqToEntity(entity);

			if (distance < 16.0D)
				potion.effect(worldObj, entity);

		}

	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity() {
		return 0.05F;
	}

	@Override
	protected float func_70182_d() {
		return 0.5F;
	}

	@Override
	protected float func_70183_g() {
		return -20.0F;
	}
}
