package fr.ign.cogit.simplu3d.exe.demo;

import fr.ign.cogit.simplu3d.rjmcmc.paramshp.geometry.impl.LBuildingWithRoof;

public class Script {
	
	
	
	public static void main(String[] args){
		
		double centreX = 0;
		double centreY = 0;
		double l1 = 3;
		double l2 = 3;
		double h1 = 3;
		double h2 = 3;
		double orientation = 0;
		double shift = 0;
		double hgutter = 6;
		double htop = 6;
		
		LBuildingWithRoof lb = new LBuildingWithRoof(centreX, centreY,l1,l2,h1,h2, htop, orientation, hgutter,shift);
		
		
		
		
		System.out.println(lb.getVolume());
		
		
		hgutter = 6;
		htop = 0;
		
		lb = new LBuildingWithRoof(centreX, centreY,l1,l2,h1,h2, htop, orientation, hgutter,shift);
		
		
			
		System.out.println(lb.getVolume());
		
	}

}
