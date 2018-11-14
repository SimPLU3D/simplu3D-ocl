package fr.ign.cogit.simplu3d.importer.ocl;

import java.io.FileInputStream;
import java.io.InputStream;

import fr.ign.cogit.geoxygene.api.feature.IFeature;
import fr.ign.cogit.geoxygene.api.feature.IFeatureCollection;
import fr.ign.cogit.geoxygene.sig3d.semantic.DTMArea;
import fr.ign.cogit.geoxygene.sig3d.util.ColorShade;
import fr.ign.cogit.geoxygene.util.conversion.ShapefileReader;
import fr.ign.cogit.simplu3d.io.nonStructDatabase.shp.LoaderSHP;
import fr.ign.cogit.simplu3d.model.EnvironnementOCL;


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
public class LoaderSHPOCL extends LoaderSHP {

	
	
	


	public EnvironnementOCL getEnvironnement(String folder) throws Exception {
		return LoaderSHPOCL.load(folder);
	}

	public static EnvironnementOCL load(String folder) throws Exception {

		return load(folder, new FileInputStream(folder + NOM_FICHIER_TERRAIN));

	}

	public static EnvironnementOCL loadNoDTM(String folder) throws Exception {
		EnvironnementOCL env = EnvironnementOCL.getInstance();
		env.setFolder( folder);

		// Chargement des fichiers
		IFeatureCollection<IFeature> pluColl = ShapefileReader.read(folder + NOM_FICHIER_PLU);
		
		IFeature featPLU = null;
		if(! pluColl.isEmpty()){
			featPLU = pluColl.get(0);
		}
		
		IFeatureCollection<IFeature> zoneColl = ShapefileReader.read(folder
				+ NOM_FICHIER_ZONAGE);
		IFeatureCollection<IFeature> parcelleColl = ShapefileReader.read(folder
				+ NOM_FICHIER_PARCELLE);
		IFeatureCollection<IFeature> voirieColl = ShapefileReader.read(folder
				+ NOM_FICHIER_VOIRIE);
		IFeatureCollection<IFeature> batiColl = ShapefileReader.read(folder
				+ NOM_FICHIER_BATIMENTS);
		IFeatureCollection<IFeature> prescriptions = ShapefileReader
				.read(folder + NOM_FICHIER_PRESC_LINEAIRE);

		return LoadFromCollectionOCL.load(featPLU, zoneColl, parcelleColl, voirieColl,
				batiColl, prescriptions, folder, null);
	}

	public static EnvironnementOCL load(String folder, InputStream dtmStream)
			throws Exception {

		EnvironnementOCL env = EnvironnementOCL.getInstance();
		env.setFolder(folder);

		// Chargement des fichiers

		// Chargement des fichiers
		IFeatureCollection<IFeature> pluColl = ShapefileReader.read(folder + NOM_FICHIER_PLU);
		
		IFeature featPLU = null;
		if(! pluColl.isEmpty()){
			featPLU = pluColl.get(0);
		}
		
		
		IFeatureCollection<IFeature> zoneColl = ShapefileReader.read(folder
				+ NOM_FICHIER_ZONAGE);
		IFeatureCollection<IFeature> parcelleColl = ShapefileReader.read(folder
				+ NOM_FICHIER_PARCELLE);
		IFeatureCollection<IFeature> voirieColl = ShapefileReader.read(folder
				+ NOM_FICHIER_VOIRIE);
		IFeatureCollection<IFeature> batiColl = ShapefileReader.read(folder
				+ NOM_FICHIER_BATIMENTS);
		IFeatureCollection<IFeature> prescriptions = ShapefileReader
				.read(folder + NOM_FICHIER_PRESC_LINEAIRE);

		// sous-parcelles route sans z, zonage, les bordures etc...
		DTMArea dtm = new DTMArea(dtmStream, "Terrain", true, 1,
				ColorShade.BLUE_CYAN_GREEN_YELLOW_WHITE);

		return LoadFromCollectionOCL.load(featPLU, zoneColl, parcelleColl, voirieColl,
				batiColl, prescriptions, folder, dtm);
	}

}
