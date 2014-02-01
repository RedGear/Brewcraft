package redgear.brewcraft.entity;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.SubItemPotion;

public class EntityBrewcraftPotion extends EntityThrowable {

	//private final SubItemPotion potion;

	public EntityBrewcraftPotion(World world, EntityLivingBase thrower) {
		super(world, thrower);
		
		//this.potion = potion;
	}
	
	public EntityBrewcraftPotion setPotion(ItemStack potion){
		getDataWatcher().addObject(10, potion);
		getDataWatcher().setObjectWatched(10);
		return this;
	}

	@Override
	protected void entityInit() {

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {

		AxisAlignedBB range = boundingBox.expand(4.0D, 2.0D, 4.0D);
		List<EntityLivingBase> entiesList = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, range);

		for (EntityLivingBase entity : entiesList) { //Use a for each whenever possible
			double distance = getDistanceSqToEntity(entity);

			if (distance < 16.0D)
				getPotion().effect(entity);

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
	
	public Icon getIcon(){
		return getPotion().getIcon();
	}

	public SubItemPotion getPotion() {
		ItemStack stack =  getDataWatcher().getWatchableObjectItemStack(10);
		Item item = Item.itemsList[stack.itemID];
		if(item instanceof MetaItemPotion){
			MetaItemPotion metaItem = (MetaItemPotion) item;
			return metaItem.getMetaItem(stack.getItemDamage());
		}
		else
			return null; //Should never happen. 
	}
}
