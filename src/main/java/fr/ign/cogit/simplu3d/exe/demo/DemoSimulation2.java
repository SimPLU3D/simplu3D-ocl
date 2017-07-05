package fr.ign.cogit.simplu3d.exe.demo;

import java.io.File;

import fr.ign.cogit.geoxygene.api.feature.IFeature;
import fr.ign.cogit.geoxygene.api.feature.IFeatureCollection;
import fr.ign.cogit.geoxygene.feature.FT_FeatureCollection;
import fr.ign.cogit.geoxygene.sig3d.representation.basic.Object2d;
import fr.ign.cogit.geoxygene.util.conversion.ShapefileWriter;
import fr.ign.cogit.simplu3d.experiments.thesis.predicate.UB16PredicateWithOtherBuildings;
import fr.ign.cogit.simplu3d.experiments.thesis.predicate.UXL3PredicateBuildingSeparation;
import fr.ign.cogit.simplu3d.importer.ocl.LoaderSHPOCL;
import fr.ign.cogit.simplu3d.io.nonStructDatabase.shp.LoaderSHP;
import fr.ign.cogit.simplu3d.model.BasicPropertyUnit;
import fr.ign.cogit.simplu3d.model.Environnement;
import fr.ign.cogit.simplu3d.model.EnvironnementOCL;
import fr.ign.cogit.simplu3d.rjmcmc.cuboid.geometry.impl.Cuboid;
import fr.ign.cogit.simplu3d.rjmcmc.paramshp.geometry.impl.LBuildingWithRoof;
import fr.ign.cogit.simplu3d.rjmcmc.paramshp.optimizer.OptimisedLShapeDirectRejection;
import fr.ign.cogit.simplu3d.util.AssignZ;
import fr.ign.cogit.simplu3d.util.convert.ExportAsFeatureCollection;
import fr.ign.mpp.configuration.BirthDeathModification;
import fr.ign.mpp.configuration.GraphConfiguration;
import fr.ign.parameters.Parameters;
import fr.ign.random.Random;

/**
 * 
 * This demo class allows to simulate the building configuration LOD2 as
 * described in CEUS paper (currently under submission).
 * 
 * 
 * @author Mickael Brasebin
 *
 */
public class DemoSimulation2 {

	public static void main(String[] args) throws Exception {

		// Output shapefile where generated simulations are stored
		String shapeFileOut = "/home/mickael/temp/shapeout.shp";

		// Relative path to input folder
		String folderIn = "src/main/resources/fr/ign/cogit/simplu3d/datademoLShape/";

		// Integration of geographic data and OCL into the model (described in
		// the section 4 of the article)
		AssignZ.DEFAULT_Z = 139;
		Environnement env = LoaderSHP.load(new File(folderIn));
		

		// File that determines parameters for the simulator (inputs of the
		// optimization algorithm - section 5 of the article)
		File f = new File(folderIn + "building_parameters_project_lshape.xml");
		Parameters p = Parameters.unmarshall(f);

		// Writing the output
		IFeatureCollection<IFeature> iFeatC = new FT_FeatureCollection<>();
		
		
		int nbParcels = env.getBpU().size();

		for (int i= 0; i < nbParcels;i++) {
			
		
			
			BasicPropertyUnit bpu = env.getBpU().get(i);

			// Definition of the predicate that checks the rule with the chosen
			// zone and rules parameters

			UB16PredicateWithOtherBuildings<LBuildingWithRoof, GraphConfiguration<LBuildingWithRoof>, BirthDeathModification<LBuildingWithRoof>> pred3 = null;
			pred3 = new UB16PredicateWithOtherBuildings<>(bpu, 5, 2);
			
			// L'optimizer
			OptimisedLShapeDirectRejection optimizer = new OptimisedLShapeDirectRejection();
			GraphConfiguration<LBuildingWithRoof> cc = optimizer.process(Random.random(), bpu, p, env, bpu.getId(),
					pred3, bpu.getGeom());

			ExportAsFeatureCollection export = new ExportAsFeatureCollection(cc);

			iFeatC.addAll(export.getFeatureCollection());

		}
		// Writing output shapefile
		ShapefileWriter.write(iFeatC, shapeFileOut);

	}

}
