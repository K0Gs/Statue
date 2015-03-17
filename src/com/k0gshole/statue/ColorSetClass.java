package com.k0gshole.statue;

import java.awt.Color;
import java.util.ArrayList;

import ColorMine.ColorSpaces.Comparisons.CieDe2000Comparison;
import flanagan.complex.Complex;

public class ColorSetClass {

	public static Color col = null;
	
	public static CieDe2000Comparison CD200 = new CieDe2000Comparison();
	
	public static double colorDistance(Color c1, Color c2) {
	    long rmean = ( (long)c1.getRed() + (long)c2.getRed()) / 2;
	    long r = (long)c1.getRed() - (long)c2.getRed();
	    long g = (long)c1.getGreen() - (long)c2.getGreen();
	    long b = (long)c1.getBlue() - (long)c2.getBlue();
	    
	    return Math.sqrt((((512+rmean)*r*r)>>8) + 4*g*g + (((767-rmean)*b*b)>>8));
	    
	    //c1.

	};
	
	
	public double compareColors(int aR, int aG, int aB, int bR, int bG, int bB){
		
		ArrayList aRGB = new ArrayList();
		ArrayList aXYZ = new ArrayList();
		ArrayList aLAB = new ArrayList();
		ArrayList bRGB = new ArrayList();
		ArrayList bXYZ = new ArrayList();
		ArrayList bLAB = new ArrayList();
		
		aRGB.add(aR);
		aRGB.add(aG);
		aRGB.add(aB);
		bRGB.add(aR);
		bRGB.add(aG);
		bRGB.add(aB);
		
		aXYZ = rgbtoxyz(aRGB);
		bXYZ = rgbtoxyz(bRGB);
		
		aLAB = xyztolab(aXYZ);
		bXYZ = rgbtoxyz(bRGB);
		
		double result = deltae94(aLAB, bLAB);
		
		return result;
		//return Double.parseDouble("0");
	}
	
	
public static int[] findClosestWoolColor(/*Color col, int[][] clothColors, String type*/) {

	int closestId = 0;

	int closestBlock = 155;
	int closestData = 0;
	int[] blockdataout = {155,0};

	ArrayList pixelRGB = new ArrayList();
	pixelRGB.add(col.getRed());
	pixelRGB.add(col.getGreen());
	pixelRGB.add(col.getBlue());
	//Mod_statue.instance.getServer().broadcastMessage(Integer.toString(col.getRed()) + " " + Integer.toString(col.getGreen()) + " " + Integer.toString(col.getBlue()));
	ArrayList pixelXYZ = new ArrayList();
	pixelXYZ = rgbtoxyz(pixelRGB);
	ArrayList pixelLAB = new ArrayList();
	pixelLAB = xyztolab(pixelXYZ);
	
	
	double closestDistance = colorDistance(col, new Color(250, 250, 250));
	ArrayList matchDeltaE = new ArrayList();
	double tempClosest = 200.00;
	int tempIndex = -1;
	for(int i = 0; i < colorset.length; ++i ) {
		
		
		int r = colorset[i][0];
		int g = colorset[i][1];
		int b = colorset[i][2];
		//Mod_statue.instance.getServer().broadcastMessage(Integer.toString(colorset[i][0]) + " " + Integer.toString(colorset[i][1]) + " " + Integer.toString(colorset[i][2]));
		ArrayList colorsetRGB = new ArrayList();
		colorsetRGB.add(r);
		colorsetRGB.add(g);
		colorsetRGB.add(b);
		ArrayList colorsetXYZ = new ArrayList();
		colorsetXYZ = rgbtoxyz(colorsetRGB);
		ArrayList colorsetLAB = new ArrayList();
		colorsetLAB = xyztolab(colorsetXYZ);
		
		
		//tempClosest = 100.00;
		
		double deltaE = CD200.Compare(colorsetLAB, pixelLAB);
		
		if(deltaE <= tempClosest){
			
			tempClosest = deltaE;
			tempIndex = i;
			
		}
		//double dist = colorDistance(col,new Color(colorset[i][0],colorset[i][1], colorset[i][2]));
		
		
			//if(dist <= closestDistance){
			
			//closestId = i;
		//	closestDistance = dist;
			
		//}
		//blockdataout = new int[]{colorset[closestId][3],colorset[closestId][4]};
	}
	
		if(tempIndex != -1){
		blockdataout = new int[]{colorset[tempIndex][3],colorset[tempIndex][4]};
		}
		else if(tempIndex == -1){
			
		}

		// int[] result = new int[]{closestBlock,closestData};
	return blockdataout;
	
};

public static ArrayList rgbtoxyz(ArrayList rgb){ // a converter for converting rgb model to xyz model
	int red1 = (Integer) rgb.get(0);
	int green1 = (Integer) rgb.get(1);
	int blue1 = (Integer) rgb.get(2);
	//Mod_statue.instance.getServer().broadcastMessage(Integer.toString(red1) + " " + Integer.toString(green1) + " " + Integer.toString(blue1));
	double red2 = (double)(Integer)red1/255;
	double green2 = (double)(Integer)green1/255;
	double blue2 = (double)(Integer)blue1/255;
	//Mod_statue.instance.getServer().broadcastMessage(Double.toString(red2) + " " + Double.toString(green2) + " " + Double.toString(blue2));
	if(red2>0.04045){
		red2 = (red2+0.055)/1.055;
		red2 = Math.pow(red2,2.4);
	}
	else{
		red2 = red2/12.92;
	}
	if(green2>0.04045){
		green2 = (green2+0.055)/1.055;
		green2 = Math.pow(green2,2.4);    
	}
	else{
		green2 = green2/12.92;
	}
	if(blue2>0.04045){
		blue2 = (blue2+0.055)/1.055;
		blue2 = Math.pow(blue2,2.4);    
	}
	else{
		blue2 = blue2/12.92;
	}
	//Mod_statue.instance.getServer().broadcastMessage(Double.toString(red2) + " " + Double.toString(green2) + " " + Double.toString(blue2));
	
	red2 = (red2*100);
	green2 = (green2*100);
	blue2 = (blue2*100);
	//Mod_statue.instance.getServer().broadcastMessage(Double.toString(red2) + " " + Double.toString(green2) + " " + Double.toString(blue2));
	
	double x = (red2 * 0.4124) + (green2 * 0.3576) + (blue2 * 0.1805);
	double y = (red2 * 0.2126) + (green2 * 0.7152) + (blue2 * 0.0722);
	double z = (red2 * 0.0193) + (green2 * 0.1192) + (blue2 * 0.9505);
	//Mod_statue.instance.getServer().broadcastMessage(Double.toString(x) + " " + Double.toString(y) + " " + Double.toString(z));
	
	ArrayList xyzresult = new ArrayList();
	xyzresult.add(x);
	xyzresult.add(y);
	xyzresult.add(z);
	return(xyzresult);
} //end of rgb_to_xyz function

public static ArrayList xyztolab(ArrayList xyz){ //a convertor from xyz to lab model
	 double x = (Double) xyz.get(0);
	 double y = (Double)xyz.get(1);
	 double z = (Double) xyz.get(2);
	 double x2 = x/95.047;
	 double y2 = y/100;
	 double z2 = z/108.883;
	// Mod_statue.instance.getServer().broadcastMessage(Double.toString(x2) + " " + Double.toString(y2) + " " + Double.toString(z2));
		
	 if(x2>0.008856){
	      x2 = Math.pow(x2,1.0/3.0);
	 }
	 else{
	      x2 = (7.787*x2) + (16.0/116.0);
	 }
	 if(y2>0.008856){
	      y2 = Math.pow(y2,1.0/3.0);
	 }
	 else{
	      y2 = (7.787*y2) + (16.0/116.0);
	 }
	 if(z2>0.008856){
	      z2 = Math.pow(z2,1.0/3.0);
	 }
	 else{
	      z2 = (7.787*z2) + (16.0/116.0);
	 }
	 //Mod_statue.instance.getServer().broadcastMessage(Double.toString(x2) + " " + Double.toString(y2) + " " + Double.toString(z2));
		
	 double l= 116*y2 - 16;
	 double a= 500*(x2-y2);
	 double b= 200*(y2-z2);
	 //Mod_statue.instance.getServer().broadcastMessage(Double.toString(l) + " " + Double.toString(a) + " " + Double.toString(b));
		
	 ArrayList labresult = new ArrayList();
	 labresult.add(l);
	 labresult.add(a);
	 labresult.add(b);
	 return(labresult);
}

public static double deltae94(ArrayList lab1, ArrayList lab2){    //calculating Delta E 1994

    double c1 = Math.sqrt(((Double) lab1.get(1)*(Double) lab1.get(1))+((Double) lab1.get(2)*(Double) lab1.get(2)));
    double c2 =  Math.sqrt(((Double) lab2.get(1)*(Double) lab2.get(1))+((Double) lab2.get(2)*(Double) lab2.get(2)));
    Complex sqrt1 = Complex.sqrt(new Complex(((Double) lab1.get(1)*(Double) lab1.get(1))+((Double) lab1.get(2)*(Double) lab1.get(2))));
    Complex sqrt2 = Complex.sqrt(new Complex(((Double) lab2.get(1)*(Double) lab2.get(1))+((Double) lab2.get(2)*(Double) lab2.get(2))));
    // Mod_statue.instance.getServer().broadcastMessage(Double.toString(c1) + " " + Double.toString(c2));
	c1 = sqrt1.getReal();
	c2 = sqrt2.getReal();
    double dc = c1-c2;
    double dl = (Double) lab1.get(0)-(Double) lab2.get(0);
    double da = (Double) lab1.get(1)-(Double) lab2.get(1);
    double db = (Double) lab1.get(2)-(Double) lab2.get(2);
    double dh = Math.sqrt((da*da)+(db*db)-(dc*dc));
   // Mod_statue.instance.getServer().broadcastMessage(Double.toString(dl) + " " + Double.toString(da) + " " + Double.toString(db) + " " + Double.toString(dh));
	
    double first = dl;
    double second = dc/(1+(0.045*c1));
    double third = dh/(1+(0.015*c1));
    //Mod_statue.instance.getServer().broadcastMessage(Double.toString(first) + " " + Double.toString(second) + " " + Double.toString(third));
	
    double deresult = Math.sqrt((first*first)+(second*second)+(third*third));
    Complex cresults = Complex.sqrt(new Complex((first*first)+(second*second)+(third*third)));
    //Mod_statue.instance.getServer().broadcastMessage(Double.toString(deresult));
	deresult = cresults.getReal();
    return(deresult);
} // end of deltae94 function


//var deltae = deltae94(lab11,lab12);     
//                                   if (deltae <= 10) {

//                                   data[i*4] = 255;
//                                   data[(i*4)+1] = 0;
//                                   data[(i*4)+2] = 0;  
//                                   k2++;
//                                   } // end of if 

public static int[][] colorset	= new int[][]{
	

		
	{250,250,250,155,0},//Quartz_Block ****** 0
	{196,196,196,35,0},//Wool_White ****** 0
	{200,125,74,35,1},//Wool_Orange ****** 0
	{167,88,170,35,2},//Wool_Magenta ****** 0
	{106,132,185,35,3},//Wool_Light_Blue ****** 0
	{163,154,58,35,4},//Wool_Yellow ****** 0
	{79,161,72,35,5},//Wool_Lime ****** 0
	{190,126,144,35,6},//Wool_Pink ****** 0
	{78,78,78,35,7},//Wool_Grey ****** 0
	{151,151,151,35,8},//Wool_Light_Grey ****** 0
	{65,113,133,35,9},//Wool_Cyan ****** 0
	{125,74,168,35,10},//Wool_Purple ****** 0
	{64,72,136,35,11},//Wool_Blue ****** 0
	{89,118,53,35,12},//Wool_Brown ****** 0
	{69,83,50,35,13},//Wool_Green ****** 0
	{143,69,66,35,14},//Wool_Red ****** 0
	{48,48,48,35,15},//Wool_Black ****** 0
	
	{161,137,96,5,0},//Wood_Oak
	{119,97,70,5,1},//Wood_Oak
	{192,177,130,5,2},//Wood_Oak
	{155,120,90,5,3},//Wood_Oak
	{167,104,70,5,4},//Wood_Oak
	{80,52,44,5,5},//Wood_Oak
	
	{159,182,219,174,0},//Packed_Ice
	{127,207,204,57,0},//Diamond_Block
	{59,83,142,22,0},//Lapis_Block
	{140,144,58,103,0},//Melon
	{92,195,119,133,0},//Emerald_Block
	{150,48,36,152,0},//Redstone_Block
	
	{193,168,156,159,0},//Clay_White
	{155,95,59,159,1},//Clay_Orange
	{146,98,114,159,2},//Clay_Magenta
	{118,114,137,159,3},//Clay_Light_Blue
	{175,133,57,159,4},//Clay_Yellow
	{110,121,70,159,5},//Clay_Lime
	{156,91,91,159,6},//Clay_Pink
	{74,62,57,159,7},//Clay_Grey
	{135,113,106,159,8},//Clay_Light_Grey
	{97,100,101,159,9},//Clay_Cyan
	{122,84,97,159,10},//Clay_Purple
	{87,76,101,159,11},//Clay_Blue
	{90,69,57,159,12},//Clay_Brown
	{88,94,62,159,13},//Clay_Green
	{141,77,66,159,14},//Clay_Red
	{58,47,42,159,15},//Clay_Black
	
	//{0,0,0,35,15}//wool_black ****** 64

};
/*
 * Version 1.1 Bukkit.
 * By K0Gs
 */
}