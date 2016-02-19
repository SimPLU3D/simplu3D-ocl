package fr.ign.cogit.simplu3d.test.checker;


import java.util.ArrayList;

import org.junit.Test;

import tudresden.ocl20.pivot.modelinstancetype.types.IModelInstanceObject;
import fr.ign.cogit.simplu3d.application.model.EnvironnementOCL;
import fr.ign.cogit.simplu3d.checker.ExhaustiveChecker;
import fr.ign.cogit.simplu3d.importer.ocl.LoaderSHPOCL;
import fr.ign.cogit.simplu3d.io.load.application.LoaderSHP;
import fr.ign.cogit.simplu3d.model.application.BasicPropertyUnit;
import junit.framework.Assert;

public class TestOCLConstraint {

  @Test
  public void testInterpretation() throws Exception {
	  
	    
	    // Rerouting towards the new files
	    LoaderSHP.NOM_FICHIER_PLU = "DOC_URBA.shp";
	    LoaderSHP.NOM_FICHIER_ZONAGE = "ZONE_URBA.shp";
	    LoaderSHP.NOM_FICHIER_PARCELLE = "parcelle.shp";
	    LoaderSHP.NOM_FICHIER_TERRAIN = "MNT_BD3D.asc";
	    LoaderSHP.NOM_FICHIER_VOIRIE = "route.shp";
	    
    EnvironnementOCL env = LoaderSHPOCL.load("src/test/resources/fr/ign/cogit/simplu3d/data/");
    

    


    for (BasicPropertyUnit bPU : env.getBpU()) {
      ExhaustiveChecker vFR = new ExhaustiveChecker(bPU, env.getUrbaZoneOCL().get(0));

      vFR.check(new ArrayList<IModelInstanceObject>());
      
    }
    Assert.assertEquals(true, true);
  }
}
