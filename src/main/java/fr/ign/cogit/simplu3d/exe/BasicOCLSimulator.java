package fr.ign.cogit.simplu3d.exe;

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

public class BasicOCLSimulator {

	public static void main(String[] args) throws Exception {

		String shapeFileOut = "/home/mickael/temp/shapeout.shp";

		SimpluParameters p = new SimpluParametersJSON(new File(
				"src/main/resources/fr/ign/cogit/simplu3d/scenario/building_parameters_project_expthese_1.xml"));

		EnvironnementOCL env = LoaderSHPOCL.loadNoDTM("src/main/resources/fr/ign/cogit/simplu3d/data/");

		int count = 0;

		// Writing the output
		IFeatureCollection<IFeature> iFeatC = new FT_FeatureCollection<>();

		for (BasicPropertyUnit bpu : env.getBpU()) {

			OCLBuildingsCuboidFinalDirectRejection optimizer = new OCLBuildingsCuboidFinalDirectRejection();

			Collection<UrbaZoneOCL> zoneColection = env.getUrbaZoneOCL().select(bpu.getGeom());
			UrbaZoneOCL zone = (UrbaZoneOCL) zoneColection.toArray()[0];

			ModelInstanceGraphConfigurationModificationPredicate<Cuboid> pred = new ModelInstanceGraphConfigurationModificationPredicate<Cuboid>(
					bpu, zone);

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

		ShapefileWriter.write(iFeatC, shapeFileOut);

	}

}
