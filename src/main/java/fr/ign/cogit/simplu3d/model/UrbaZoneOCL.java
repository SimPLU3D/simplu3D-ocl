package fr.ign.cogit.simplu3d.model;

import java.util.ArrayList;
import java.util.List;

public class UrbaZoneOCL extends UrbaZone{
	
	
	  private List<Rule> rules = new ArrayList<Rule>();
	  
	  // Pour les règles de la zone urba
	  public void setRules(List<Rule> rules) {
	    this.rules = rules;
	  }
	  
	  public List<Rule> getRules() {
	    return rules;
	  }
	  

}
