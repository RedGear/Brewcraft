package redgear.brewcraft.village;

import java.util.List;
import java.util.Random;

import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

public class VillageWitchHutHandler implements IVillageCreationHandler {

	@Override
	public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1,
			int p2, int p3, int p4, int p5) {

		return ComponentWitchHut.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
	}

	@Override
	public Class<?> getComponentClass() {

		return ComponentWitchHut.class;
	}

	@Override
	public PieceWeight getVillagePieceWeight(Random random, int i) {

		return new PieceWeight(ComponentWitchHut.class, 12, 1);
	}

}
