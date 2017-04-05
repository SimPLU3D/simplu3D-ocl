package fr.ign.cogit.simplu3d.importer.ocl;



import org.apache.log4j.Logger;

import fr.ign.cogit.geoxygene.api.feature.IFeature;
import fr.ign.cogit.geoxygene.api.feature.IFeatureCollection;
import fr.ign.cogit.geoxygene.sig3d.semantic.AbstractDTM;
import fr.ign.cogit.simplu3d.importer.RulesImporter;
import fr.ign.cogit.simplu3d.io.LoadFromCollection;
import fr.ign.cogit.simplu3d.model.EnvironnementOCL;
import fr.ign.cogit.simplu3d.model.RuleOCL;
import fr.ign.cogit.simplu3d.model.UrbaZone;
import fr.ign.cogit.simplu3d.model.UrbaZoneOCL;

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
	
	public static Logger logger = Logger.getLogger(LoadFromCollectionOCL.class);

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
				logger.info("Zone " + z.getLibelle());
				for (RuleOCL rule : zOCL.getRules()) {
					logger.info("rule " + rule.constraint + " with " + rule.text);
				}

			}
		}
		return envOCL;
	}

}
