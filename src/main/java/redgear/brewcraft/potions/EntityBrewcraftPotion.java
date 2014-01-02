package redgear.brewcraft.potions;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBrewcraftPotion extends EntityThrowable {
	
	private ItemStack potionDamage;
	
    public EntityBrewcraftPotion(World par1World, EntityPlayer par2EntityLiving, ItemStack par3ItemStack)
    {
        super(par1World, par2EntityLiving);
        this.potionDamage = par3ItemStack;
    }

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {
		
		 AxisAlignedBB distance = this.boundingBox.expand(4.0D, 2.0D, 4.0D);
		 List var4 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, distance);
		 Iterator var5 = var4.iterator();
		 
		 while (var5.hasNext())
         {
             EntityLiving var6 = (EntityLiving)var5.next();
             double var7 = this.getDistanceSqToEntity(var6);

             if (var7 < 16.0D)
             {
                 double var9 = 1.0D - Math.sqrt(var7) / 4.0D;

                 if (var6 == movingobjectposition.entityHit)
                 {
                     var9 = 1.0D;
                 }

                     //PotionEffect var12 = (PotionEffect)PotionEffect;
                     //int var13 = var12.getPotionID();
                     //var6.addPotionEffect(new PotionEffect(var13, duration, var12.getAmplifier()));
                 
                 	 //Need to somehow implement this. . .

             }
             
         }

	}
	
	   /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.05F;
    }

    protected float func_70182_d()
    {
        return 0.5F;
    }

    protected float func_70183_g()
    {
        return -20.0F;
    }
    
    public void setPotionDamage(int par1)
    {
        if (!(this.potionDamage == null))
        {
        this.potionDamage.setItemDamage(par1);
        }
    }
    
}
