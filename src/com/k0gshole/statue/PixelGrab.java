package com.k0gshole.statue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.GlobalRegionManager;

public class PixelGrab {

	private static Mod_statue plugin; // pointer to your main class, unrequired if you don't need methods from the main class

	public PixelGrab(Mod_statue plugin) {
		this.plugin = plugin;
	}

	public int[] range(int start, int length) {
		int[] result = new int[0];
		if(start <= length){
			int[] result1 = new int[length - start + 1];
			for (int i = start; i <= length; i++) {
				result1[i - start] = i;
				result = result1;
			}}
		else if(length <= start){
			int[] result2 = new int[start - length + 1];
			for (int i = length; i <= start; i++) {
				result2[i - length] = i;
				result = result2;
			}}
		return result;
	}





	public static boolean canBuildHere(Location loc, Player playerName){
		//Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

		//new WorldGuardPlugin();
		//new WorldGuardPlugin();
		try{
			GlobalRegionManager worldGuard = WorldGuardPlugin.inst().getGlobalRegionManager();
			}
			catch(NoClassDefFoundError e)
			{
				return true;
			}
			GlobalRegionManager worldGuard = WorldGuardPlugin.inst().getGlobalRegionManager();
		if (worldGuard!=null){ //Ensure we have access to the API
			boolean success = worldGuard.canBuild(playerName, loc);
			return success;
		}
		return false;
	}

	/*public static boolean isPlotAllowed(Player player, Block block){

		Block b = block;

		try{
			PlotManager.isPlotWorld(b);
		}
		catch(NoClassDefFoundError e){
			return true;
		}
		if(PlotManager.isPlotWorld(b))
		{
			Player p = player;
			boolean canbuild = PlotMe.cPerms(p, "plotme.admin.buildanywhere");
			String id = PlotManager.getPlotId(b.getLocation());

			if(id.equalsIgnoreCase(""))
			{
				if(!canbuild)
				{
					//p.sendMessage(PlotMe.caption("ErrCannotBuild"));
					//event.setCancelled(true);
					return false;
				}
				return true;
			}
			else
			{
				Plot plot = PlotManager.getPlotById(p,id);

				if (plot == null)
				{
					if(!canbuild)
					{
						//p.sendMessage(PlotMe.caption("ErrCannotBuild"));
						//event.setCancelled(true);
						return false;
					}
					return true;
				}
				else if(!plot.isAllowed(p.getName()))
				{
					if(!canbuild)
					{
						//p.sendMessage(PlotMe.caption("ErrCannotBuild"));
						//event.setCancelled(true);
						return false;
					}
					return true;
				}
				else
				{
					plot.resetExpire(PlotManager.getMap(b).DaysToExpiration);
				}

			}
			return true;
		}
		return true;
	}*/

	public static boolean generate(World par1World, Random par2Random, int par3,int par4, int par5, CommandSender sender, String skinName){

		File fileTemp = new File("./plugins/statue/" + skinName + ".png");
		Player player = (Player)sender;
		//player = CommandStatue.getPlayer();
		try {
			ImageIO.read(fileTemp);
		} catch (IOException e1) {

			player.sendMessage("You will need to download the skin first!");
			return false;
		}
		BufferedImage skin = null;
		try {
			skin = ImageIO.read(fileTemp);
		} catch (IOException e) {

			player.sendMessage("This might not be a valid skin file!");
			return false;
		}
		String thePath = "./plugins/statue/playerData/";
		String theFile = player.getUniqueId().toString() + ".dat";
		File myPath = new File(thePath);
		File myFile = new File(thePath + theFile);
		Boolean regionBuild = null;
		Boolean regionBlockBuild = null;
		Boolean plotBuild = null;
		if(myPath.exists()){
			//System.out.println("Directory Exists");
		}else{
			boolean wasDirecotyMade = myPath.mkdirs();
			//if(wasDirecotyMade)System.out.println("Direcoty Created");
			//else System.out.println("Sorry could not create directory");
		}

		//if(!(myFile.isFile())){
		try {
			//create a file named "testfile.txt" in the current working directory

			if ( myFile.createNewFile() ) {
				//System.out.println("Success!");
			} else {
				//System.out.println("Failure!");
			}
		} catch ( IOException ioe ) { ioe.printStackTrace(); }
		//}

		AlphaWriteFile data = new AlphaWriteFile(thePath + theFile, true);
		try {
			data.writeToFile("nnn");
			//data.writeToFile("  ");
			//data.writeToFile(word);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		int width = skin.getWidth();
		int height = skin.getHeight();
		regionBuild = true;
		regionBlockBuild = true;
		plotBuild = true;
		for(int x = width-1; x > -1; x-- ){

			int cnt = 0;
			for (int y = height- 1; y > - 1; y--){

				int cr = skin.getRGB(x, cnt);
				int[] d = new int[]{
						(cr >> 24) & 0xff, //red
						(cr >> 16) & 0xff, //red
						(cr >>  8) & 0xff, //green
						(cr      ) & 0xff  //blue

				};
				Color c = new Color(cr, true);
				//d = skin.getType();
				//d[cnt] = skin.getRGB(x, cnt, 1, 1, null, 0, 1);

				com.k0gshole.statue.ColorSetClass.col = c;
				int[] blockData =  com.k0gshole.statue.ColorSetClass.findClosestWoolColor();
				int blocks = blockData[0];				
				int datas = blockData[1];
				Material mats = Material.WOOL;
				byte meta = 0;
				//ModLoader.clientChat("" + (par3 + lx) + (par4 + ly) + (par5 + lz) + "");
				if(blocks == 35){
					mats = Material.WOOL;
				}else if(blocks == 159){
					mats = Material.STAINED_CLAY;
				}  
				else if(blocks == 5){
					mats = Material.WOOD;
				}  
				else if(blocks == 155){
					mats = Material.QUARTZ_BLOCK;
				} 
				else if(blocks == 174){
					mats = Material.PACKED_ICE;
				} 
				else if(blocks == 57){
					mats = Material.DIAMOND_BLOCK;
				} 
				else if(blocks == 22){
					mats = Material.LAPIS_BLOCK;
				} 
				else if(blocks == 103){
					mats = Material.MELON_BLOCK;
				} 
				else if(blocks == 133){
					mats = Material.EMERALD_BLOCK;
				} 
				else if(blocks == 152){
					mats = Material.REDSTONE_BLOCK;
				}
				meta = (byte)datas;





				if(d[0] == 255){
					final Block currentBlock = par1World.getBlockAt(par3 + x, par4 + y, par5 + 2);
					final byte finMeta = meta;
					final Material finMats = mats;

					if(canBuildHere(player.getLocation(), player)){
						if(canBuildHere(currentBlock.getLocation(), player)){
							//if(isPlotAllowed(player, currentBlock) == true){
								try {
									data.writeToFile(Integer.toString(par3 + x) 
											+ "," + Integer.toString(par4 + y) 
											+ "," + Integer.toString(par5 + 2) 
											+ "," + currentBlock.getType().toString());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
								//currentBlock.setType(mats);
								//currentBlock.setData(meta);
								Mod_statue.instance.getServer().getScheduler().scheduleSyncDelayedTask(Mod_statue.instance, new Runnable(){public void run(){currentBlock.setType(finMats); currentBlock.setData(finMeta);}});

								Mod_statue.instance.logBlock(player.getName().toString(), currentBlock.getLocation(), currentBlock.getTypeId(), meta );  
								//par1World.setBlock(par3 + lx, par4 + ly, par5 + lz, blocks, datas, 2);
							//}else{
								//player.sendMessage("You are not allowed to place blocks in this plot!");
							//	plotBuild = false;
							//}
						}else{
							//player.sendMessage("You are not allowed to place blocks in this region!");
							regionBlockBuild = false;
						}
					}else{
						//player.sendMessage("You are not allowed to build in this region!");
						regionBuild = false;
						//return false;
					}
				}    
				//par1World.setBlock(par3 + x, par4 + y, par5 + 2, blocks, datas, 2);
				// }
				cnt = cnt + 1;
			}


		}

		if (regionBuild == false){
			player.sendMessage("You are not allowed to build in this region!");
		}
		if (regionBlockBuild == false){
			player.sendMessage("You are not allowed to place blocks in this region!");
		}
		if (plotBuild == false){
			player.sendMessage("You are not allowed to place blocks outside your plot!");
		}
		return true;

	}



	/*
	 * Version 1.1 Bukkit.
	 * By K0Gs
	 */

}
