package fr.ign.cogit.simplu3d.importer;

import java.io.File;
import java.util.List;

import fr.ign.cogit.simplu3d.model.EnvironnementOCL;
import fr.ign.cogit.simplu3d.model.RuleOCL;
import fr.ign.cogit.simplu3d.model.UrbaZoneOCL;
import tudresden.ocl20.pivot.pivotmodel.Constraint;
import tudresden.ocl20.pivot.standalone.facade.StandaloneFacade;

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
public class RulesImporter {

	public static void importer(String folder, UrbaZoneOCL z) {
		// on charge le fichier OCL du nom de la zone
		File f = new File(folder + z.getLibelle() + ".ocl");


		if (f.exists()) {
			try {
				List<Constraint> lC = StandaloneFacade.INSTANCE.parseOclConstraints(EnvironnementOCL.getModel(), f);

				int id = 0;

				for (Constraint c : lC) {

					RuleOCL r = new RuleOCL(c, (++id) + "");
					z.getRules().add(r);

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				System.exit(1);
			}

		}

	}

}
