package fr.ign.cogit.simplu3d.test.checker;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import tudresden.ocl20.pivot.modelinstancetype.types.IModelInstanceObject;
import fr.ign.cogit.simplu3d.application.model.EnvironnementOCL;
import fr.ign.cogit.simplu3d.checker.ExhaustiveChecker;
import fr.ign.cogit.simplu3d.exe.LoadDefaultEnvironment;
import fr.ign.cogit.simplu3d.importer.ocl.LoaderSHPOCL;
import fr.ign.cogit.simplu3d.model.application.BasicPropertyUnit;
import fr.ign.cogit.simplu3d.model.application.Environnement;
import junit.framework.Assert;

public class TestOCLConstraint {

  @Test
  public void testInterpretation() throws Exception {
    EnvironnementOCL env = LoaderSHPOCL.load(TestOCLConstraint.class.getResource("fr/ign/cogit/simplu3d/data").toString());

    for (BasicPropertyUnit bPU : env.getBpU()) {
      ExhaustiveChecker vFR = new ExhaustiveChecker(bPU, env.getUrbaZoneOCL().get(0));

      vFR.check(new ArrayList<IModelInstanceObject>());

    }
    Assert.assertEquals(true, true);
  }
}
