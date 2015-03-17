package com.k0gshole.statue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class CommandStatue implements CommandExecutor{
	
	private Mod_statue plugin; // pointer to your main class, unrequired if you don't need methods from the main class
	//public Statue plugin;
	
	public CommandStatue() {

	}
	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	static boolean download = false;
	static String URL = null;
	public Player player = null;

	/*
	@Override
	public String getCommandName(){
		return "statue"; //set command name}
	}*/
	
	/*
	public int func_82362_a(){
		return 2;	
	}*/
	
	static Player Player = null;
	public void saveconf(){
		Mod_statue.instance.saveDefaultConfig();
	}
	
	public void reloadConfigs(){
		Mod_statue.instance.reloadConfig();
		
	}

	
	//
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    /*@Override
	public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr){
		*/
		player = (Player)sender;
		
	  
		/*
		 * types
		 * 0 = your statue
		 * 1 = others statue
		 * 2 = your statue hat
		 * 3 = other statue hat
		 * 4 = head
		 * 5 = others head
		 * 6 = pixel map
		 * 7 = pixel map other
		 * 9 = skip
		 * 10 = undo
		 */
		if (sender instanceof Player) {
			Player var3 = (Player) sender;
			String pname = var3.getName();
			//Player = var3;
			int type = 9;
			Location loc = ((Player) sender).getLocation();
			World world = loc.getWorld();
           	if(Mod_statue.perms.has(sender, "statue.build")) {
	           //Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("statue")){ // If the player typed /basic then do the following...

           	if (args.length > 3) {
                  sender.sendMessage("Too many arguments!");
                  return false;
               } 

		if(args.length > 0){
			
			
			if(args.length == 1){
				
				if(args[0].equals("pixel")){
					 //World world = var3.worldObj;
					 Mod_statue.generatePixel(world, null, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), sender); 
					 //type = 9;
					 //sender.sendMessage("Command Pixel");
					 return true;
				}
				else if(args[0].equals("reload")){
					 //World world = var3.worldObj;
					 //type = 4;
					 //sender.sendMessage("Reloading config");
					 reloadCustomConfig();
					 return true;
				}
				else if(args[0].equals("head")){
					 //World world = var3.worldObj;
					 type = 4;
					 //sender.sendMessage("Command Head");
				}
				else if(args[0].equals("undo")){
					 //World world = var3.worldObj;
					 type = 10;
					 //sender.sendMessage("Command Undo");
				}
				else if(args[0].equals("hat")){
					type = 2;
					//sender.sendMessage("Command hat");
				}
				else if(args[0].equals("help")){
					type = 9;
					sender.sendMessage("/statue <player> <hat> | /statue pixel <player_Name> | /statue head <player_Name>");

				}
				else if(args[0].equals("?")){
					type = 9;
					sender.sendMessage("/statue <player> <hat> | /statue pixel <player_Name> | /statue head <player_Name>");

				}
				else {
					type = 1;
				}
					
			}
			else if(args.length == 2){
			if(args[0].equals("head")){
				type =  5;
				//sender.sendMessage("Command head other");
			}
			else if(args[1].equals("hat")){
				type = 3;
				//sender.sendMessage("Command hat other");
			}
			else if(args[0].equals("pixel")){
				type = 6;
				//sender.sendMessage("Command pixel other");
			}
			else{
				type = 9;
				sender.sendMessage("Bad command");
			}
		}
		}
		else{
			type = 0;

		}
		if(type == 9){
			//sender.sendMessage("Fail");
			return false;
		}
		else{

			//sender.sendMessage("Start download");
			String tempFile = null;
			tempFile = "./plugins/statue/";
			String skinName = null;
			String path = null;
			UUID playerUUID = var3.getUniqueId();
			UUID OtherUUID0 = UUID.randomUUID();
			String[] OtherString0 = null;
			String EndString0 = "";
			if(args.length ==2){
			if(Mod_statue.instance.getServer().getOfflinePlayer(args[0]).getUniqueId() != null){
				OtherUUID0 = Mod_statue.instance.getServer().getOfflinePlayer(args[0]).getUniqueId();
				OtherString0 = OtherUUID0.toString().split("-");
				for(int a =0; a < OtherString0.length; a++){
				EndString0 = EndString0 + OtherString0[a];
				}
			}
			}
			UUID OtherUUID1 = UUID.randomUUID();
			String[] OtherString1 = null;
			String EndString1 = "";
			if(args.length ==2){
				if(Mod_statue.instance.getServer().getOfflinePlayer(args[1]).getUniqueId() != null){
					OtherUUID1 = Mod_statue.instance.getServer().getOfflinePlayer(args[1]).getUniqueId();
					OtherString1 = OtherUUID1.toString().split("-");
					for(int a =0; a < OtherString1.length; a++){
					EndString1 = EndString1 + OtherString1[a];
					}
				}
			}
			
			String[] PlayerString = player.getUniqueId().toString().split("-");
			String EndPlayerString = "";
			for(int a =0; a < PlayerString.length; a++){
			EndPlayerString = EndPlayerString + PlayerString[a];
			}
			
			
			if(type == 0){
				skinName = player.getName();
			}
			if(type == 1){
				skinName = args[0];
			}
			if(type == 2){
				skinName = player.getName();
			}
			if(type == 3){
				skinName = args[0];
			}
			if(type == 4){
				skinName = player.getName();
			}
			if(type == 5){
				skinName = args[1];
			}
			if(type == 6){
				skinName = args[1];
			}
			if(type == 10){
				Mod_statue.undoStatue(loc, null, sender);
				return true;
			}
			//if(type == 6){
				//skinName = "";
			//}
			path = tempFile + skinName;
			String istrue = "true";
			String isfalse = "false";
			getCustomConfig();
			String URL = new String(customConfig.getString("url"));
			/*
			if(customConfig.getBoolean("download_skins")){
				sender.sendMessage(istrue);
				
			}else{
				sender.sendMessage(isfalse);
			}*/
			
			if(customConfig.getBoolean("download_skins") == true){
				
			HttpURLConnection connection2 = null;
			try {
	      
	        URL url = new URL(URL + skinName + ".png");
	        
	        connection2 = (HttpURLConnection) url.openConnection();
	        connection2.connect();
	        connection2.getInputStream();

	        File fileTemp2 = new File(path);
	        if (fileTemp2.exists()){
	        fileTemp2.delete();
	        }
	        
	        BufferedImage skin2 = ImageIO.read(connection2.getInputStream());
	        fileTemp2 = new File(path + ".png");
	        ImageIO.write(skin2, "png", fileTemp2);
	        
	        //sender.sendMessage("Image buffered");

			} catch (MalformedURLException e1) {
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Error"+ e1.getMessage());
				//e1.printStackTrace();
				//type = 7;
				sender.sendMessage("Could not download the skin "+ skinName +" , check spelling!");
				//this.finalize();
				//return false;
			} catch (IOException e1) {
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Error"+ e1.getMessage());
				//e1.printStackTrace();
				//type = 7;
				sender.sendMessage("Error"+ e1.getMessage());
				//this.finalize();
				//return false;
			} finally {
				
				if(null != connection2) { connection2.disconnect(); }
			}
			
			}


                //sender.sendMessage("You are awesome!");
               	   //Do something
               	//WriteLetters.generate(loc, null, player, word, mat);
            
				if(type == 0){
					Mod_statue.generateStatue(world, null, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), sender, skinName);
				}
				
				if(type == 1){
					Mod_statue.generateStatue(world, null, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), sender, skinName);
				}
				if(type == 2){
					Mod_statue.generateStatue(world, null, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), sender, skinName);
					Mod_statue.generateHat(world, null, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), sender, skinName);
				}
				if(type == 3){
					Mod_statue.generateStatue(world, null, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), sender, skinName);
					Mod_statue.generateHat(world, null, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), sender, skinName);
				}
				if(type == 4){
					Mod_statue.generateHead(world, null, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), sender, skinName);
				}
				if(type == 5){
					Mod_statue.generateHead(world, null, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), sender, skinName);
				}
				if(type == 6){
					Mod_statue.generatePixelGrab(world, null, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), sender, skinName);
				}
				if(type == 7){
				
				}				
				
           	
		}
		}
			
			else {
		           
		           return false;
			}
		return true;
		} else {
            //sender.sendMessage("You suck!");
            

       	//if(player.hasPermission("alpha_bit.ab")) {
       	
       	   //Do something else

           	sender.sendMessage("I'm sorry " + sender.getName() + ", you dont have the alpha_bit.ab permission to use Alpha_Bit.");
           	return false;
       	}
	}else{
		sender.sendMessage("You must be a player!");
		return false;
	}
	}
	
	public void reloadCustomConfig() {
		customConfigFile = null;
	    if (customConfigFile == null) {
	    customConfigFile = new File("./plugins/statue", "config.yml");
	    }	    
	    if (customConfigFile == null) {
		    customConfigFile = new File("./statue", "config.yml");
		    }
	    customConfig = null;
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
	    /*
	    // Look for defaults in the jar
	    InputStream defConfigStream = this.getResource("config.yml");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        customConfig.setDefaults(defConfig);
	    }*/
	   // player.sendMessage("Reloaded Config");
	}
	
	public FileConfiguration getCustomConfig() {
	    if (customConfig == null) {
	        reloadCustomConfig();
	    }
	    //player.sendMessage("Got Config");
	    return customConfig;
	}
    
	/*
	 * Version 1.1 Bukkit.
	 * By K0Gs
	 */
}

