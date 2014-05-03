package redgear.brewcraft.village;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import redgear.brewcraft.core.Brewcraft;

public class ComponentWitchHut extends StructureVillagePieces.House1 {

	private static final String __OBFID = "CL_00000517";

	public boolean hasCauldron;
	public boolean hasTable;
	public boolean hasFlowerPot;
	public boolean hasFences;

	public ComponentWitchHut() {
	}
	
    @Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("Cauldron", this.hasCauldron);
		par1NBTTagCompound.setBoolean("Crafting Table", this.hasTable);
		par1NBTTagCompound.setBoolean("Flower Pots", this.hasFlowerPot);
		par1NBTTagCompound.setBoolean("Fences", this.hasFences);
	}

    @Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
		this.hasCauldron = par1NBTTagCompound.getBoolean("Cauldron");
		this.hasTable = par1NBTTagCompound.getBoolean("Crafting Table");
		this.hasFlowerPot = par1NBTTagCompound.getBoolean("Flower Pots");
		this.hasFences = par1NBTTagCompound.getBoolean("Fences");
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
	}

	public static ComponentWitchHut func_74898_a(StructureVillagePieces.Start par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5,
				0, 0, 0, 7, 5, 9, par6);
		return canVillageGoDeeper(structureboundingbox)
				&& StructureComponent.findIntersecting(par1List, structureboundingbox) == null ? new ComponentWitchHut(
				par0ComponentVillageStartPiece, par7, par2Random, structureboundingbox, par6) : null;
	}

	@Override
	public boolean addComponentParts(World w, Random par2Random, StructureBoundingBox sbb) {

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

		//Rails on front of house.
		if (this.hasFences) {
			this.placeBlockAtCurrentPosition(w, Blocks.fence, 0, 2, 3, 2, sbb);
			this.placeBlockAtCurrentPosition(w, Blocks.fence, 0, 3, 3, 7, sbb);
			this.placeBlockAtCurrentPosition(w, Blocks.fence, 0, 1, 2, 1, sbb);
			this.placeBlockAtCurrentPosition(w, Blocks.fence, 0, 5, 2, 1, sbb);
		}
		
		//Clearing out space somewhere?
		this.placeBlockAtCurrentPosition(w, Blocks.air, 0, 1, 3, 4, sbb);
		this.placeBlockAtCurrentPosition(w, Blocks.air, 0, 5, 3, 4, sbb);
		this.placeBlockAtCurrentPosition(w, Blocks.air, 0, 5, 3, 5, sbb);

		//Miscellaneous things that are randomly included/excluded.
		if (this.hasFlowerPot)
			this.placeBlockAtCurrentPosition(w, Blocks.flower_pot, 7, 1, 3, 5, sbb);

		if (this.hasTable)
			this.placeBlockAtCurrentPosition(w, Blocks.crafting_table, 0, 3, 2, 6, sbb);

		if (this.hasCauldron)
			this.placeBlockAtCurrentPosition(w, Blocks.cauldron, 0, 4, 2, 6, sbb);

		//The roof.
		this.fillWithBlocks(w, sbb, 0, 4, 1, 6, 4, 1, Blocks.oak_stairs, Blocks.oak_stairs, false);
		this.fillWithBlocks(w, sbb, 0, 4, 2, 0, 4, 7, Blocks.oak_stairs, Blocks.oak_stairs, false);
		this.fillWithBlocks(w, sbb, 6, 4, 2, 6, 4, 7, Blocks.oak_stairs, Blocks.oak_stairs, false);
		this.fillWithBlocks(w, sbb, 0, 4, 8, 6, 4, 8, Blocks.oak_stairs, Blocks.oak_stairs, false);
		int i1;
		int j1;

		for (i1 = 2; i1 <= 7; i1 += 5) {
			for (j1 = 1; j1 <= 5; j1 += 4) {
				this.func_151554_b(w, Blocks.log, 0, j1, -1, i1, sbb);
			}
		}

		this.spawnVillagers(w, sbb, 2, 1, 2, 1);
		return true;
	}

	@Override
	protected int getVillagerType(int par1) {
		return Brewcraft.inst.getInt("General", "Villager Profession ID", 15);
	}
}
