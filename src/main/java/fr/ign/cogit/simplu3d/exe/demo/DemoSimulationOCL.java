package fr.ign.cogit.simplu3d.exe.demo;

import java.io.File;
import java.util.Collection;

import fr.ign.cogit.geoxygene.api.feature.IFeature;
import fr.ign.cogit.geoxygene.api.feature.IFeatureCollection;
import fr.ign.cogit.geoxygene.feature.DefaultFeature;
import fr.ign.cogit.geoxygene.feature.FT_FeatureCollection;
import fr.ign.cogit.geoxygene.util.attribute.AttributeManager;
import fr.ign.cogit.geoxygene.util.conversion.ShapefileWriter;
import fr.ign.cogit.simplu3d.importer.ocl.LoaderSHPOCL;
import fr.ign.cogit.simplu3d.model.BasicPropertyUnit;
import fr.ign.cogit.simplu3d.model.EnvironnementOCL;
import fr.ign.cogit.simplu3d.model.UrbaZoneOCL;
import fr.ign.cogit.simplu3d.optimizer.ocl.OCLBuildingsCuboidFinalDirectRejection;
import fr.ign.cogit.simplu3d.rjmcmc.cuboid.configuration.ModelInstanceGraphConfiguration;
import fr.ign.cogit.simplu3d.rjmcmc.cuboid.configuration.ModelInstanceGraphConfigurationModificationPredicate;
import fr.ign.cogit.simplu3d.rjmcmc.cuboid.geometry.impl.Cuboid;
import fr.ign.cogit.simplu3d.util.SimpluParameters;
import fr.ign.cogit.simplu3d.util.SimpluParametersJSON;
import fr.ign.mpp.configuration.GraphVertex;
import fr.ign.parameters.Parameters;

/**
 * 
 * This demo class allows to simulate the building configuration as described in
 * CEUS paper (currently under submission).
 * 
 * 
 * @author Mickael Brasebin
 *
 */
public class DemoSimulationOCL {

	public static void main(String[] args) throws Exception {

		// Output shapefile where generated simulations are stored
		String shapeFileOut = "/home/mickael/temp/shapeout.shp";

		// Relative path to input folder
		String folderIn = "src/main/resources/fr/ign/cogit/simplu3d/datademo/";

		// Integration of geographic data and OCL into the model (described in
		// the section 4 of the article)
		EnvironnementOCL env = LoaderSHPOCL.loadNoDTM(folderIn);

		// File that determines parameters for the simulator (inputs of the
		// optimization algorithm - section 5 of the article)
		File f = new File(folderIn + "simulation_parameters.xml");
		SimpluParameters p = new SimpluParametersJSON(f);

		int count = 0;
		// Writing the output
		IFeatureCollection<IFeature> iFeatC = new FT_FeatureCollection<>();

		for (BasicPropertyUnit bpu : env.getBpU()) {
			// Selectionning the zones that intersect the parcel
			Collection<UrbaZoneOCL> zoneColection = env.getUrbaZoneOCL().select(bpu.getGeom());

			// The first is the right one
			UrbaZoneOCL zone = (UrbaZoneOCL) zoneColection.toArray()[0];

			// Definition of the predicate that checks the rule with the chosen
			// zone and rules parameters
			ModelInstanceGraphConfigurationModificationPredicate<Cuboid> pred = new ModelInstanceGraphConfigurationModificationPredicate<Cuboid>(
					bpu, zone);

			// Insanciation of the optimizer
			OCLBuildingsCuboidFinalDirectRejection optimizer = new OCLBuildingsCuboidFinalDirectRejection();

			// Execution of the optimization process on the bpu, with selected
			// parameters, environnement, predicate
			ModelInstanceGraphConfiguration<Cuboid> modelInstance = optimizer.process(bpu, p, env, pred, count);

			// For all generated boxes
			for (GraphVertex<Cuboid> v : modelInstance.getGraph().vertexSet()) {

				// Output feature with generated geometry
				IFeature feat = new DefaultFeature(v.getValue().generated3DGeom());

				// We write some attributes
				AttributeManager.addAttribute(feat, "Longueur", Math.max(v.getValue().length, v.getValue().width),
						"Double");
				AttributeManager.addAttribute(feat, "Largeur", Math.min(v.getValue().length, v.getValue().width),
						"Double");
				AttributeManager.addAttribute(feat, "Hauteur", v.getValue().height, "Double");
				AttributeManager.addAttribute(feat, "Rotation", v.getValue().orientation, "Double");

				iFeatC.add(feat);

			}

			count++;
		}
		//Writing output shapefile
		ShapefileWriter.write(iFeatC, shapeFileOut);

	}

}
