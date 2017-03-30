package fr.ign.cogit.simplu3d.model;

import java.util.ArrayList;
import java.util.List;

import fr.ign.cogit.simplu3d.model.UrbaZone;

public class UrbaZoneOCL extends UrbaZone {

	public UrbaZoneOCL(UrbaZone u) {
		super();
		this.setGeom(u.getGeom());
		this.setSubParcels(u.getSubParcels());
		this.setLibelle(u.getLibelle());

	}

	private List<RuleOCL> rules = new ArrayList<RuleOCL>();

	// Pour les règles de la zone urba
	public void setRules(List<RuleOCL> rules) {
		this.rules = rules;
	}

	public List<RuleOCL> getRules() {
		return rules;
	}

}
