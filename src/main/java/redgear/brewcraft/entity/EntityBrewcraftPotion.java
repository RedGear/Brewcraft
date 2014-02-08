package redgear.brewcraft.entity;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.SubItemPotion;

public class EntityBrewcraftPotion extends EntityPotion {

	private final int stackIndex = 10;

	public EntityBrewcraftPotion(World par1World) {
		super(par1World);
	}

	public EntityBrewcraftPotion(World world, double x, double y, double z, ItemStack potion) {
		super(world, x, y, z, potion);
		setPotion(potion);
	}

	public EntityBrewcraftPotion(World world, EntityLivingBase thrower, ItemStack potion) {
		super(world, thrower, potion);
		setPotion(potion);
	}

	public void setPotion(ItemStack potion) {
		getDataWatcher().updateObject(stackIndex, potion);
		getDataWatcher().setObjectWatched(stackIndex);
	}

	@Override
	protected void entityInit() {
		getDataWatcher().addObjectByDataType(10, 5);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {
		SubItemPotion potion = getPotionItem();

		if (!worldObj.isRemote) {

			AxisAlignedBB range = boundingBox.expand(4.0D, 2.0D, 4.0D);
			List<EntityLivingBase> entiesList = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, range);

			for (EntityLivingBase entity : entiesList) { //Use a for each whenever possible
				double distance = getDistanceSqToEntity(entity);

				if (distance < 16.0D)
					potion.effect(entity);

			}
			
			//TODO: Replace this with the stuff that will create particles with the right color. 
			worldObj.playAuxSFX(2002, (int) Math.round(posX), (int) Math.round(posY), (int) Math.round(posZ), getPotionDamage());

			setDead();

		}

		
		//Save this crap for later. 
		//This was my attempt to make the particals myself so that they'd look right, 
		//but I gave up as it is REALLY complex and I think it'll require packets and 
		//stuff that will just change in 1.7 anyway. We'll fix it then. 
		
		
		/*Random random = worldObj.rand;
		String s;
		int j1;
		int k1;
		double d3;
		double d4;
		double d5;
		double d6;
		double d7;

		s = "iconcrack_" + Brewcraft.potions.itemID + "_" + getPotionStack().getItemDamage();

		for (j1 = 0; j1 < 8; ++j1)
			worldObj.spawnParticle(s, posX, posY, posZ, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D,
					random.nextGaussian() * 0.15D);

		if (worldObj.isRemote) { //TODO: Make particles work right on sever. It requires packets and stuff that I just don't feel like doing right now - Blackhole

			j1 = getPotion().getLiquidColor();
			float red = (j1 >> 16 & 255) / 255.0F;
			float green = (j1 >> 8 & 255) / 255.0F;
			float blue = (j1 >> 0 & 255) / 255.0F;

			boolean isInstant = getPotion().isInstant();

			String s1 = "spell";

			if (isInstant)
				s1 = "instantSpell";

			for (k1 = 0; k1 < 100; ++k1) {
				d7 = random.nextDouble() * 4.0D;
				d3 = random.nextDouble() * Math.PI * 2.0D;
				d4 = Math.cos(d3) * d7;
				d5 = 0.01D + random.nextDouble() * 0.5D;
				d6 = Math.sin(d3) * d7;

				EntityFX entityfx = Minecraft.getMinecraft().renderGlobal.doSpawnParticle(s1, posX + d4 * 0.1D,
						posY + 0.3D, posZ + d6 * 0.1D, d4, d5, d6);
				//new EntitySpellParticleFX(worldObj, posX + d4 * 0.1D, posY + 0.3D, posZ + d6 * 0.1D, d4, d5, d6);

				
				  if (isInstant)
				  ((EntitySpellParticleFX)
				  entityfx).setBaseSpellTextureIndex(144);
				 

				float f3 = 0.75F + random.nextFloat() * 0.25F;
				entityfx.setRBGColorF(red * f3, green * f3, blue * f3);
				entityfx.multiplyVelocity((float) d7);
				worldObj.spawnEntityInWorld(entityfx);

			}
		}
		worldObj.playSoundEffect(posX, posY, posZ, "random.glass", 1.0F, random.nextFloat() * 0.1F + 0.9F);*/

	}

	public Icon getIcon() {
		return getPotionItem().getIcon();
	}

	private ItemStack getPotionStack() {
		return getDataWatcher().getWatchableObjectItemStack(stackIndex);
	}

	private SubItemPotion getPotionItem() {
		ItemStack stack = getPotionStack();
		if (stack == null)
			return null;

		Item item = Item.itemsList[stack.itemID];
		if (item instanceof MetaItemPotion) {
			MetaItemPotion metaItem = (MetaItemPotion) item;
			return metaItem.getMetaItem(stack.getItemDamage());
		} else
			return null; //Should never happen. 
	}

	private Potion getPotion() {
		return Potion.potionTypes[getPotionItem().potionId];
	}
}
