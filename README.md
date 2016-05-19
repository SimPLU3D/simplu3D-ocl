# Simplu3D-ocl

[![Build Status](https://travis-ci.org/IGNF/simplu3D-ocl.svg?branch=master)](https://travis-ci.org/IGNF/simplu3D-ocl)

Introduction
---------------------

This research library is developed as part of [COGIT laboratory](http://recherche.ign.fr/labos/cogit/accueilCOGIT.php) researches concerning the checking of local urban regulation. This regulation is modelled with OCL constraints. This project uses the model implemented in [simplu3d-rules project](https://github.com/IGNF/simplu3d-rules) and the checking processed can be plugged with building generation process of the [simplu3d project](https://github.com/IGNF/simplu3d).



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

Test class
---------------------


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

Contact for feedbacks
---------------------
[Mickaël Brasebin](http://recherche.ign.fr/labos/cogit/cv.php?nom=Brasebin) & [Julien Perret](http://recherche.ign.fr/labos/cogit/cv.php?prenom=Julien&nom=Perret)
[Cogit Laboratory](http://recherche.ign.fr/labos/cogit/accueilCOGIT.php)


Acknowledgments
---------------------

+ This research is supported by the French National Mapping Agency ([IGN](http://www.ign.fr))
+ It is partially funded by the FUI TerraMagna project and by Île-de-France
Région in the context of [e-PLU projet](www.e-PLU.fr)
