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
import fr.ign.cogit.simplu3d.optimizer.ocl.OCLLShapeDirectRejection;
import fr.ign.cogit.simplu3d.rjmcmc.cuboid.configuration.ModelInstanceGraphConfiguration;
import fr.ign.cogit.simplu3d.rjmcmc.cuboid.configuration.ModelInstanceGraphConfigurationModificationPredicate;
import fr.ign.cogit.simplu3d.rjmcmc.generic.object.ISimPLU3DPrimitive;
import fr.ign.cogit.simplu3d.rjmcmc.paramshp.geometry.impl.LBuildingWithRoof;
import fr.ign.cogit.simplu3d.util.SimpluParameters;
import fr.ign.cogit.simplu3d.util.SimpluParametersJSON;
import fr.ign.mpp.configuration.GraphVertex;
import fr.ign.parameters.Parameters;
import fr.ign.random.Random;

/**
 * 
 * This demo class allows to simulate the building configuration LOD2 as described in
 * CEUS paper (currently under submission).
 * 
 * 
 * @author Mickael Brasebin
 *
 */
public class DemoSimulationOCLLOD2 {

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
		File f = new File(folderIn + "building_parameters_project_lshape.xml");
		SimpluParameters p = new SimpluParametersJSON(f);


		// Writing the output
		IFeatureCollection<IFeature> iFeatC = new FT_FeatureCollection<>();

		for (BasicPropertyUnit bpu : env.getBpU()) {
			// Selectionning the zones that intersect the parcel
			Collection<UrbaZoneOCL> zoneColection = env.getUrbaZoneOCL().select(bpu.getGeom());


			// The first is the right one
			UrbaZoneOCL zone = (UrbaZoneOCL) zoneColection.toArray()[0];

			// Definition of the predicate that checks the rule with the chosen
			// zone and rules parameters
			ModelInstanceGraphConfigurationModificationPredicate<LBuildingWithRoof> pred = new ModelInstanceGraphConfigurationModificationPredicate<LBuildingWithRoof>(
					bpu, zone);
			
			
		
			// L'optimizer
			OCLLShapeDirectRejection  optimizer = new OCLLShapeDirectRejection();
		

			// Execution of the optimization process on the bpu, with selected
			// parameters, environnement, predicate

			ModelInstanceGraphConfiguration<LBuildingWithRoof> modelInstance =  optimizer.process(Random.random(), bpu, p, env, pred,  bpu.getId(), bpu.getPol2D());
			
			iFeatC.addAll(exportResult(modelInstance, bpu));

		}
		//Writing output shapefile
		ShapefileWriter.write(iFeatC, shapeFileOut);

	}
	
	
	private static IFeatureCollection<IFeature>  exportResult(ModelInstanceGraphConfiguration<LBuildingWithRoof> modelInstance, BasicPropertyUnit bpu){
		IFeatureCollection<IFeature> iFeatC = new FT_FeatureCollection<>();
		// For all generated boxes
		for (GraphVertex<? extends ISimPLU3DPrimitive> v : modelInstance.getGraph().vertexSet()) {

			// Output feature with generated geometry
			IFeature feat = new DefaultFeature(v.getValue().generated3DGeom());
			AttributeManager.addAttribute(feat, "IDParc", bpu.getCadastralParcels().get(0).getCode(), "Double");
			AttributeManager.addAttribute(feat, "Hauteur", v.getValue().getHeight(), "Double");
			AttributeManager.addAttribute(feat, "Rotation", v.getValue().getArea(), "Double");

			iFeatC.add(feat);

		}
		return iFeatC;
	}

	
	
	/*
	public static void run(){

		// On instancie le prédicat (vérification des règles,
		// normalement,
		// rien à faire)
		SamplePredicate<LBuildingWithRoof, GraphConfiguration<LBuildingWithRoof>, BirthDeathModification<LBuildingWithRoof>> pred3 = null;

		// On indique le fichier de configuration (à créer ou utiliser
		// un
		// existant)
		String fileName2 = "building_parameters_project_lshape.xml";
		// On charge le fichier de configuration
		Parameters p = Parameters.unmarshall(new File("" + fileName2));
		// On instancie le prédicat (vérification des règles,
		// normalement,
		// rien à faire)

		// On génère l'optimizer et on le lance (à faire)
		OptimisedLShapeDirectRejection optimizer = new OptimisedLShapeDirectRejection();
		cc = ((OptimisedLShapeDirectRejection) optimizer).process(rng, bPU, p, env, bPU.getId(), pred3,
				bPU.getGeom());


	}*/
}
