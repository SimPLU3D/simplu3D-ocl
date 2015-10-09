package fr.ign.cogit.simplu3d.model;

import java.net.URL;


import tudresden.ocl20.pivot.model.IModel;

public class EnvironnementOCL  {

	
	  // On charge le modèle
	  private static IModel model = null;
	  
	  public static IModel getModel(){
	    if(model == null){
	      
	      ClassLoader load = ModelProviderClass.class.getClassLoader();
	      URL path = load.getResource("fr/ign/cogit/simplu3d/importer/model/ModelProviderClass.class");
	      model =  ImportModelInstanceEnvironnement
	          .getModel(path.getFile());
	      
	      
	      
	      /*
	      model =  ImportModelInstanceEnvironnement
	      .getModel("target/classes/fr/ign/cogit/simplu3d/importer/model/ModelProviderClass.class");*/
	    }
	    
	    return model;
	  }

}
