package fr.ign.cogit.simplu3d.importer.model;


import fr.ign.cogit.geoxygene.api.spatial.geomprim.IOrientableSurface;
import fr.ign.cogit.geoxygene.api.spatial.geomroot.IGeometry;
import fr.ign.cogit.geoxygene.spatial.coordgeom.GM_Polygon;
import fr.ign.cogit.simplu3d.model.AbstractBuilding;
import fr.ign.cogit.simplu3d.model.Alignement;
import fr.ign.cogit.simplu3d.model.BasicPropertyUnit;
import fr.ign.cogit.simplu3d.model.Building;
import fr.ign.cogit.simplu3d.model.BuildingPart;
import fr.ign.cogit.simplu3d.model.CadastralParcel;
import fr.ign.cogit.simplu3d.model.EnvironnementOCL;
import fr.ign.cogit.simplu3d.model.Prescription_SURF;
import fr.ign.cogit.simplu3d.model.PublicSpace;
import fr.ign.cogit.simplu3d.model.Recoil;
import fr.ign.cogit.simplu3d.model.Road;
import fr.ign.cogit.simplu3d.model.RoofSurface;
import fr.ign.cogit.simplu3d.model.Rule;
import fr.ign.cogit.simplu3d.model.SpecificCadastralBoundary;
import fr.ign.cogit.simplu3d.model.SpecificWallSurface;
import fr.ign.cogit.simplu3d.model.SubParcel;
import fr.ign.cogit.simplu3d.model.UrbaZone;
import fr.ign.cogit.simplu3d.model.UrbaZoneOCL;
/**
 * 
 *        This software is released under the licence CeCILL
 * 
 *        see LICENSE.TXT
 * 
 *        see <http://www.cecill.info/ http://www.cecill.info/
 * 
 * 
 * 
 * @copyright IGN
 * 
 * @author Brasebin Mickaël
 * 
 * @version 1.0
 **/
public class ModelProviderClass {

  protected CadastralParcel parcelle;
  protected Building sousParcelle;
  protected SpecificWallSurface facade;
  protected SpecificCadastralBoundary bordure;
  protected RoofSurface toit;
  protected Integer inti;
  protected AbstractBuilding ab;
  protected SubParcel sP;
  protected IGeometry gm;
  protected BasicPropertyUnit bUP;
  protected IOrientableSurface oS;
  protected Road r;
  protected GM_Polygon poly;
  protected Prescription_SURF ps;
  protected PublicSpace pss;
  protected BuildingPart bp;
  protected Recoil recoil;
  protected Alignement alignement;
  protected EnvironnementOCL env;
  protected Rule rule;
  protected UrbaZoneOCL u;

}
