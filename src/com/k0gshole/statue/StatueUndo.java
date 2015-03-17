package com.k0gshole.statue;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;



public class StatueUndo {
	
	public static boolean remove(Location loc, Random par2Random, Player player){
		
		
		//player.sendMessage(mat);
		//Material mats = Material.WOOD;
		String thePath = "./plugins/statue/playerData/";
		String theFile = player.getUniqueId().toString() + ".dat";
		//File myPath = new File(thePath);
		File myFile = new File(thePath + theFile);
		String[] fileArray = new String[0];
		//String[] fileArray2 = new String[0];
		int positionX;
		int positionY;
		int positionZ;
		Material mats;
		AlphaReadFile file = new AlphaReadFile(myFile.toString());
		try {
			fileArray = file.OpenFile();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		myFile.delete();
		
		//find bottom of array
		int bottom;
		int entry = 0;
		bottom = fileArray.length - 1;
		//find last entry
		for(int h = bottom; h > 0; h--){
			
			if(fileArray[h].equals("nnn")){
				entry = h;
				break;		
			}
				
			
			
		}
		if(bottom == -1){
			player.sendMessage("Nothing to Undo!");
		}else{
			player.sendMessage("Undo!");
		}
		//player.sendMessage(Integer.toString(bottom));
		//player.sendMessage(Integer.toString(entry));
		
		//int[] range1;
		//range1 = range(entry + 1, bottom);
		for(int i = bottom; i > entry; i--){
		//for(int i = 0; i < fileArray.length; i++){
			positionX = Integer.valueOf(fileArray[i].substring(0).split(",")[0]);
			positionY = Integer.valueOf(fileArray[i].substring(0).split(",")[1]);
			positionZ = Integer.valueOf(fileArray[i].substring(0).split(",")[2]);
			mats = Material.valueOf(fileArray[i].substring(0).split(",")[3]);
		
		
		World world = loc.getWorld();
		
	     Block currentBlock = world.getBlockAt(positionX, positionY, positionZ);
         

         currentBlock.setType(mats);
         //player.sendMessage("place block");
         //data = new AlphaWriteFile(theFile);
		}	
		
		try {
			   //create a file named "testfile.txt" in the current working directory
			
			   if ( myFile.createNewFile() ) {
			      System.out.println("Success!");
			   } else {
			      System.out.println("Failure!");
			   }
			} catch ( IOException ioe ) { ioe.printStackTrace(); }
		//}
		
		for(int i = 0; i < entry; i++){
			AlphaWriteFile data = new AlphaWriteFile(thePath + theFile, true);
			try {
				data.writeToFile(fileArray[i].toString());
				//data.writeToFile("  ");
				//data.writeToFile(word);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		
		
		return true;
	}
	

}

/*
 * Version 1.1 Bukkit.
 * By K0Gs
 */
