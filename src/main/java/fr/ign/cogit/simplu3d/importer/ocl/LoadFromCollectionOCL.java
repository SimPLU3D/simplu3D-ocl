package fr.ign.cogit.simplu3d.importer.ocl;

import fr.ign.cogit.geoxygene.api.feature.IFeature;
import fr.ign.cogit.geoxygene.api.feature.IFeatureCollection;
import fr.ign.cogit.geoxygene.sig3d.semantic.AbstractDTM;
import fr.ign.cogit.simplu3d.application.model.EnvironnementOCL;
import fr.ign.cogit.simplu3d.application.model.Rule;
import fr.ign.cogit.simplu3d.application.model.UrbaZoneOCL;
import fr.ign.cogit.simplu3d.importer.RulesImporter;
import fr.ign.cogit.simplu3d.io.load.application.LoadFromCollection;
import fr.ign.cogit.simplu3d.model.application.UrbaZone;

/**
 * 
 * This software is released under the licence CeCILL
 * 
 * see LICENSE.TXT
 * 
 * see <http://www.cecill.info/ http://www.cecill.info/
 * 
 * 
 * 
 * @copyright IGN
 * 
 * @author Brasebin Mickaël
 * 
 * @version 1.0
 **/
public class LoadFromCollectionOCL {

	public static EnvironnementOCL load(IFeature featPLU, IFeatureCollection<IFeature> zoneColl,
			IFeatureCollection<IFeature> parcelleColl, IFeatureCollection<IFeature> voirieColl,
			IFeatureCollection<IFeature> batiColl, IFeatureCollection<IFeature> prescriptions, String ruleFolder,
			AbstractDTM dtm) throws Exception {

		EnvironnementOCL envOCL = EnvironnementOCL.getInstance();

		LoadFromCollection.load(featPLU, zoneColl, parcelleColl, voirieColl, batiColl, prescriptions, ruleFolder, dtm,
				envOCL);

		if (ruleFolder != null) {
			for (UrbaZone z : envOCL.getUrbaZones()) {

				UrbaZoneOCL zOCL = new UrbaZoneOCL(z);
				envOCL.getUrbaZoneOCL().add(zOCL);

				RulesImporter.importer(ruleFolder, zOCL);
				System.out.println("Zone " + z.getLibelle());
				for (Rule rule : zOCL.getRules()) {
					System.out.println("rule " + rule.constraint + " with " + rule.text);
				}

			}
		}
		return envOCL;
	}

}
