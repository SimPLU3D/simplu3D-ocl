# Simplu3D-ocl

[![Build Status](https://travis-ci.org/SimPLU3D/simplu3D-ocl.svg?branch=master)](https://travis-ci.org/SimPLU3D/simplu3D-ocl)

Introduction
---------------------

This research library is developed as part of [COGIT laboratory](http://recherche.ign.fr/labos/cogit/accueilCOGIT.php) researches concerning the checking of local urban regulation. This regulation is modelled with OCL constraints. This project uses the model implemented in [simplu3d-rules project](https://github.com/SimPLU3D/simplu3d-rules) and the checking processed can be plugged with building generation process of the [simplu3d project](https://github.com/SimPLU3D/simplu3d).



Documentation and publications
---------------------

For more information about this code, the following article describes the regulation model and the use of OCL to represent urban regulation :

[Brasebin, M.; Perret, J.; Mustière, S.; Weber, C. A Generic Model to Exploit Urban Regulation Knowledge. ISPRS Int. J. Geo-Inf. 2016, 5, 14.](http://www.mdpi.com/2220-9964/5/2/14/htm)

It is also described more completely in the PhD of Mickael Brasebin (French document) :

[Brasebin, M. (2014) Les données géographiques 3D pour simuler l'impact de la réglementation urbaine sur la morphologie du bâti, Thèse de doctorat, spécialité Sciences et Technologies de l'Information Géographique, Université Paris-Est, apr 2014](http://recherche.ign.fr/labos/cogit/publiCOGITDetail.php?idpubli=5016)



Library installation
---------------------
The project is build with Maven and is coded in Java (JDK 1.7 or higher is required), it has been tested in most common OS. If you are not familiar with Maven, we suggest installing developer tools and versions as described in [GeOxygene install guide](http://oxygene-project.sourceforge.net/documentation/developer/install.html).

Conditions for use
---------------------
This software is free to use under CeCILL license. However, if you use this library in a research paper, you are kindly requested to acknowledge the use of this software.

Furthermore, we are interested in every feedbacks about this library if you find it useful, if you want to contribute or if you have some suggestions to improve it.

Test classes
---------------------

**For checking rules**

A test class TestOCLConstraint is implemented in the package : fr.ign.cogit.simplu3d.test.checker.

```Java
public class TestOCLConstraint {

  @Test
  public void testInterpretation() throws Exception {
    //Load an environnement with default data according to integration process described in Simplu3D-rules project
    EnvironnementOCL env = LoaderSHPOCL.load("src/test/resources/fr/ign/cogit/simplu3d/data/");

     //Rules are checked in each BasicPropertyUnit
    for (BasicPropertyUnit bPU : env.getBpU()) {
      //A checker is instanciated according to the relevant UrbanZone
      ExhaustiveChecker vFR = new ExhaustiveChecker(bPU, env.getUrbaZoneOCL().get(0));
      //The checking is processed, the results will be printed in the console
      vFR.check(new ArrayList<IModelInstanceObject>());

    }
    Assert.assertEquals(true, true);
  }
```
A large set of rules, from Strasbourg Local Urban Plan, is available in the file /simplu3d-ocl/src/main/resources/fr/ign/cogit/simplu3d/ocl_test

**For building simulation **

A test class (fr.ign.cogit.simplu3d.exe.demo.DemoSimulationOCL) is implemented according to a simulation presented in a paper (under submission). All the used resources, including geographic data, simulation parameters and OCL rules are available in the "src/main/resources/fr/ign/cogit/simplu3d/datademo/" folder.



```Java
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
		Parameters p = Parameters.unmarshall(f);

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
```


Contact for feedbacks
---------------------
[Mickaël Brasebin](http://recherche.ign.fr/labos/cogit/cv.php?nom=Brasebin) & [Julien Perret](http://recherche.ign.fr/labos/cogit/cv.php?prenom=Julien&nom=Perret)
[Cogit team](http://recherche.ign.fr/labos/cogit/accueilCOGIT.php)


Acknowledgments
---------------------

+ This research is supported by the French National Mapping Agency ([IGN](http://www.ign.fr))
+ It is partially funded by the FUI TerraMagna project and by Île-de-France
Région in the context of [e-PLU projet](www.e-PLU.fr)
