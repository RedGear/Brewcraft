package redgear.brewcraft.village;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import redgear.brewcraft.core.Brewcraft;

public class ComponentWitchHut extends StructureVillagePieces.House1 {

	private static final String __OBFID = "CL_00000517";

	public boolean hasCauldron;
	public boolean hasTable;
	public boolean hasFlowerPot;
	public boolean hasFences;
	public boolean isInDesert;
	public boolean hasGlass;

	public ComponentWitchHut() {
	}

	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("Cauldron", this.hasCauldron);
		par1NBTTagCompound.setBoolean("Crafting Table", this.hasTable);
		par1NBTTagCompound.setBoolean("Flower Pots", this.hasFlowerPot);
		par1NBTTagCompound.setBoolean("Fences", this.hasFences);
		par1NBTTagCompound.setBoolean("Desert", this.isInDesert);
		par1NBTTagCompound.setBoolean("Glass", this.hasGlass);
	}

	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
		this.hasCauldron = par1NBTTagCompound.getBoolean("Cauldron");
		this.hasTable = par1NBTTagCompound.getBoolean("Crafting Table");
		this.hasFlowerPot = par1NBTTagCompound.getBoolean("Flower Pots");
		this.hasFences = par1NBTTagCompound.getBoolean("Fences");
		this.isInDesert = par1NBTTagCompound.getBoolean("Desert");
		this.hasGlass = par1NBTTagCompound.getBoolean("Glass");
	}

	public ComponentWitchHut(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random,
			StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2, par3Random, par4StructureBoundingBox, par5);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
		this.hasCauldron = par3Random.nextBoolean();
		this.hasTable = par3Random.nextBoolean();
		this.hasFlowerPot = par3Random.nextBoolean();
		this.hasFences = par3Random.nextBoolean();
		this.isInDesert = par1ComponentVillageStartPiece.inDesert;
		this.hasGlass = par3Random.nextBoolean();
	}

	public static ComponentWitchHut buildComponent(Start start, List list, Random rand, int p3, int p4, int p5, int p6,
			int p7) {
		StructureBoundingBox sbb = StructureBoundingBox.getComponentToAddBoundingBox(p3, p4, p5, 0, 0, 0, 7, 5, 9, p6);
		return canVillageGoDeeper(sbb) && StructureComponent.findIntersecting(list, sbb) == null ? new ComponentWitchHut(
				start, p7, rand, sbb, p6) : null;
	}

	@Override
	public boolean addComponentParts(World w, Random par2Random, StructureBoundingBox sbb) {

		if (this.field_143015_k < 0) {
			this.field_143015_k = this.getAverageGroundLevel(w, sbb);

			if (this.field_143015_k < 0) {
				return true;
			}

			this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4, 0);
		}

		//The walls, floor, posts that hold it upright.
		this.fillWithBlocks(w, sbb, 1, 1, 1, 5, 1, 7, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(w, sbb, 1, 4, 2, 5, 4, 7, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(w, sbb, 2, 1, 0, 4, 1, 0, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(w, sbb, 2, 2, 2, 3, 3, 2, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(w, sbb, 1, 2, 3, 1, 3, 6, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(w, sbb, 5, 2, 3, 5, 3, 6, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(w, sbb, 2, 2, 7, 4, 3, 7, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(w, sbb, 1, 0, 2, 1, 3, 2, Blocks.log, Blocks.log, false);
		this.fillWithBlocks(w, sbb, 5, 0, 2, 5, 3, 2, Blocks.log, Blocks.log, false);
		this.fillWithBlocks(w, sbb, 1, 0, 7, 1, 3, 7, Blocks.log, Blocks.log, false);
		this.fillWithBlocks(w, sbb, 5, 0, 7, 5, 3, 7, Blocks.log, Blocks.log, false);

		//Rails on front of house and in the windows.
		if (this.hasFences) {
			this.placeBlockAtCurrentPosition(w, Blocks.fence, 0, 2, 3, 2, sbb);
			this.placeBlockAtCurrentPosition(w, Blocks.fence, 0, 3, 3, 7, sbb);
			this.placeBlockAtCurrentPosition(w, Blocks.fence, 0, 1, 2, 1, sbb);
			this.placeBlockAtCurrentPosition(w, Blocks.fence, 0, 5, 2, 1, sbb);
		}
		
		if (this.hasFlowerPot && !this.hasGlass)
			this.placeBlockAtCurrentPosition(w, Blocks.flower_pot, 7, 1, 3, 5, sbb);

		if (this.hasGlass) {
			this.placeBlockAtCurrentPosition(w, Blocks.glass_pane, 0, 2, 3, 2, sbb);
			this.placeBlockAtCurrentPosition(w, Blocks.glass_pane, 0, 3, 3, 7, sbb);
			this.placeBlockAtCurrentPosition(w, Blocks.glass_pane, 0, 1, 3, 4, sbb);
			this.placeBlockAtCurrentPosition(w, Blocks.glass_pane, 0, 5, 3, 4, sbb);
			this.placeBlockAtCurrentPosition(w, Blocks.glass_pane, 0, 5, 3, 5, sbb);
			this.placeBlockAtCurrentPosition(w, Blocks.glass_pane, 7, 1, 3, 5, sbb);
		}

		//Creating holes for the windows.
		if (!this.hasGlass) {
			this.placeBlockAtCurrentPosition(w, Blocks.air, 0, 1, 3, 4, sbb);
			this.placeBlockAtCurrentPosition(w, Blocks.air, 0, 5, 3, 4, sbb);
			this.placeBlockAtCurrentPosition(w, Blocks.air, 0, 5, 3, 5, sbb);
		}

		//Miscellaneous things that are randomly included/excluded.
		if (this.hasTable)
			this.placeBlockAtCurrentPosition(w, Blocks.crafting_table, 0, 3, 2, 6, sbb);

		if (this.hasCauldron)
			this.placeBlockAtCurrentPosition(w, Blocks.cauldron, 0, 4, 2, 6, sbb);

		if (!this.isInDesert) {
			this.fillWithBlocks(w, sbb, 0, 4, 1, 6, 4, 1, Blocks.wooden_slab, Blocks.wooden_slab, false);
			this.fillWithBlocks(w, sbb, 0, 4, 2, 0, 4, 7, Blocks.wooden_slab, Blocks.wooden_slab, false);
			this.fillWithBlocks(w, sbb, 6, 4, 2, 6, 4, 7, Blocks.wooden_slab, Blocks.wooden_slab, false);
			this.fillWithBlocks(w, sbb, 0, 4, 8, 6, 4, 8, Blocks.wooden_slab, Blocks.wooden_slab, false);
		} else {
			this.fillWithMetadataBlocks(w, sbb, 0, 4, 1, 6, 4, 1, Blocks.stone_slab, 1, Blocks.stone_slab, 1, false);
			this.fillWithMetadataBlocks(w, sbb, 0, 4, 2, 0, 4, 7, Blocks.stone_slab, 1, Blocks.stone_slab, 1, false);
			this.fillWithMetadataBlocks(w, sbb, 6, 4, 2, 6, 4, 7, Blocks.stone_slab, 1, Blocks.stone_slab, 1, false);
			this.fillWithMetadataBlocks(w, sbb, 0, 4, 8, 6, 4, 8, Blocks.stone_slab, 1, Blocks.stone_slab, 1, false);
		}
		int i1;
		int j1;

		for (i1 = 2; i1 <= 7; i1 += 5) {
			for (j1 = 1; j1 <= 5; j1 += 4) {
				this.func_151554_b(w, Blocks.log, 0, j1, -1, i1, sbb);
			}
		}

		this.spawnVillagers(w, sbb, 3, 1, 3, 1);
		return true;
	}

	@Override
	protected int getVillagerType(int par1) {
		return Brewcraft.inst.getInt("Village", "Witch Profession ID", 15);
	}
}
