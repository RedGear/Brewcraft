package redgear.brewcraft.potions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBrewcraftPotion extends EntityThrowable {
	
	private ItemStack potionDamage;
	public PotionEffect splash;

	public EntityBrewcraftPotion(World par1World, PotionEffect effect) {
		super(par1World);
		this.splash = effect;

	}
	
    public EntityBrewcraftPotion(World par1World, EntityPlayer par2EntityLiving, ItemStack par3ItemStack)
    {
        super(par1World, par2EntityLiving);
        this.potionDamage = par3ItemStack;
    }

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {
		
		//I'm missing something huge here.
		//The public PotionEffect 'splash' which is linked to the second parameter in the
		//contructor should be used here somehow.

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
