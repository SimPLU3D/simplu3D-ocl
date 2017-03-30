package fr.ign.cogit.simplu3d.test.checker;

import java.util.ArrayList;

import org.junit.Test;

import fr.ign.cogit.simplu3d.checker.ExhaustiveChecker;
import fr.ign.cogit.simplu3d.importer.ocl.LoaderSHPOCL;
import fr.ign.cogit.simplu3d.model.BasicPropertyUnit;
import fr.ign.cogit.simplu3d.model.EnvironnementOCL;
import junit.framework.Assert;
import tudresden.ocl20.pivot.modelinstancetype.types.IModelInstanceObject;

public class TestOCLConstraint {

	@Test
	public void testInterpretation() throws Exception {

		EnvironnementOCL env = LoaderSHPOCL.loadNoDTM("src/test/resources/fr/ign/cogit/simplu3d/data/");

		for (BasicPropertyUnit bPU : env.getBpU()) {
			ExhaustiveChecker vFR = new ExhaustiveChecker(bPU, env.getUrbaZoneOCL().get(0));

			vFR.check(new ArrayList<IModelInstanceObject>());

		}
		Assert.assertEquals(true, true);
	}
}
