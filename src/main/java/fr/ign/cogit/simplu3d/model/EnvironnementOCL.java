package fr.ign.cogit.simplu3d.model;

import java.net.URL;

import fr.ign.cogit.geoxygene.api.feature.IFeatureCollection;
import fr.ign.cogit.geoxygene.feature.FT_FeatureCollection;
import fr.ign.cogit.simplu3d.importer.model.ImportModelInstanceEnvironnement;
import fr.ign.cogit.simplu3d.importer.model.ModelProviderClass;
import tudresden.ocl20.pivot.model.IModel;

public class EnvironnementOCL extends Environnement {

	private static EnvironnementOCL env = null;

	private IFeatureCollection<UrbaZoneOCL> urbaZoneOCL = null;

	public IFeatureCollection<UrbaZoneOCL> getUrbaZoneOCL() {
		if (urbaZoneOCL == null) {
			urbaZoneOCL = new FT_FeatureCollection<>();
		}

		return urbaZoneOCL;
	}

	public static EnvironnementOCL getInstance() {
		if (env == null) {
			env = new EnvironnementOCL();
		}
		return env;
	}

	// On charge le modèle
	private static IModel model = null;

	public static IModel getModel() {
		if (model == null) {

			ClassLoader load = ModelProviderClass.class.getClassLoader();
			URL path = load.getResource("fr/ign/cogit/simplu3d/importer/model/ModelProviderClass.class");
			model = ImportModelInstanceEnvironnement.getModel(path.getFile());

			/*
			 * model = ImportModelInstanceEnvironnement .getModel(
			 * "target/classes/fr/ign/cogit/simplu3d/importer/model/ModelProviderClass.class"
			 * );
			 */
		}

		return model;
	}

}
