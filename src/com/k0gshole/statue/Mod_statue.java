package com.k0gshole.statue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Logger;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;



//@Mod(modid = mod_statue.modid, name = "K0Gs Statue Builder", version = "1.5")

//@NetworkMod(clientSideRequired = true, serverSideRequired = false)




public class Mod_statue extends JavaPlugin implements Listener
{
	public static Mod_statue instance;
	private Mod_statue JavaPlugin;
	public static CoreProtectAPI CoreProtect = null;
	public File png64 = new File("plugins/statue/64.png");
	public File flanagan = new File("plugins/statue/lib/flanagan.png");
	
	public Mod_statue(){
		
	}
	
	
    public void onEnable() {
    	instance = this;
    	instance.getServer().broadcastMessage("[Statue] Enableing...");
    	getCommand("statue").setExecutor(new CommandStatue());
        //save config
    	this.saveDefaultConfig();
    	String thePath = "./plugins/statue/lib/";
    	File myPath = new File(thePath);
    	
		if(myPath.exists()){
			System.out.println("Directory Exists");
		}else{
			boolean wasDirecotyMade = myPath.mkdirs();
			if(wasDirecotyMade)System.out.println("Direcoty Created");
			else System.out.println("Sorry could not create directory");
		}
    	
		if(!png64.exists()){
			getInstance().saveResource("64.png", false);
		}
		
		if(!flanagan.exists()){
			getInstance().saveResource("flanagan.jar", false);
		}
    	//download = this.getConfig().getBoolean("download_skins");
    	//URL = this.getConfig().getString("url");
    	

    	// ...
    	instance.getServer().getPluginManager().registerEvents(this, this);
    	// This will throw a NullPointerException if you don't have the command defined in your plugin.yml file!

     
    	// ...
        setupPermissions();
        //setupChat();
        

    		//boolean success = mod_statue.this.getConfig().getBoolean("canDownload");

    
    }
	

    public void onDisable() {
		System.out.println("Good Bye :( ");
        // TODO: Place any custom disable code here.
    	//log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
        
    }
    private static final Logger log = Logger.getLogger("Minecraft");
    //public static Economy econ = null;
    public static Permission perms = null;
    //public static Chat chat = null;




    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	Player player = event.getPlayer();
    	//event.getPlayer().sendMessage("Welcome, " + event.getPlayer().getDisplayName() + "!");
        String thePath = "./plugins/statue/playerData/";
		String theFile = player.getUniqueId().toString() + ".dat";
		//File myPath = new File(thePath);
		File myPath = new File(thePath);
        File myFile = new File(thePath + theFile);
		//String[] fileArray = new String[0];
		if(myPath.exists()){
			System.out.println("Directory Exists");
		}else{
			boolean wasDirecotyMade = myPath.mkdirs();
			if(wasDirecotyMade)System.out.println("Direcoty Created");
			else System.out.println("Sorry could not create directory");
		}
		

		myFile.delete();


		//if(!(myFile.isFile())){
		try {
			//create a file named "testfile.txt" in the current working directory
			
			if ( myFile.createNewFile() ) {
				System.out.println("Success!");
			} else {
				System.out.println("Failure!");
			}
		} catch ( IOException ioe ) { ioe.printStackTrace(); }
		
    }
    

    public Mod_statue getInstance(){
    	return instance;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    
	//@Instance("Statue")
	//public Statue instance;
	//@SideOnly(Side.CLIENT)
	//private List<ICommand> serverCommands = Lists.newArrayList();
	//public static final String modid = "statue";
	//public static Block sandstoneInjected;
	//public static Block quartzStained;
	//public static final Statue statueBlockGen = new Statue();
	//public static final CommandStatue CommandBlockGen = new CommandStatue();
	//public static final hats hatBlockGen = new hats();

	//public static final head headBlockGen = new head();

	//public static final pixel pixelBlockGen = new pixel();
	//public static final pixelGrab pixelGrabBlockGen = new pixelGrab();

	//public static ICommand statue = new CommandStatue();
	//public static ICommand 
	//public static final Block sandstoneInjected = new BlockSandstoneInjected(190, Material.rock).setUnlocalizedName("sandstoneinjected").setTextureName("sandstone_injected");
	//public static final Block quartzStained = new BlockQuartzStained(191, Material.rock).setUnlocalizedName("quartzstained").setTextureName("quartz_stained");

	//public static final ModMetadata meta = new ModMetadata();
	


	/*@EventHandler
	public void preInt(FMLPreInitializationEvent event){
		ModMetadata md = event.getModMetadata();
		md.autogenerated = false;
		md.name = "K0Gs Statue Builder";
		md.description = "The Statue Builder Mod. "+
				"Add Statues of you or others with one quick command. " +
				"Now create heads and pixel maps too! ";
		md.authorList = Arrays.asList(new String[] { "K0Gs" });
		md.url = "http://www.kogshole.com/statue";
		md.version = "1.5";
		md.credits = "Created by K0Gs";
		md.modId = modid;
	}*/


	//@Init
	/*
	@EventHandler
	public void load(FMLInitializationEvent event)

	{	


		GameRegistry.registerBlock(sandstoneInjected, "sandstoneInjected");
		GameRegistry.registerBlock(quartzStained, "quartzStained");



		LanguageRegistry.addName(sandstoneInjected, "Color Injected Sandstone");

		LanguageRegistry.addName(quartzStained, "Stained Quartz");
		//statue = new CommandStatue();

		//addServerCommand(statue);
		//ModLoader.addCommand(statue);







	}
	/*
	@SideOnly(Side.CLIENT)
	public static final StepSound soundSandFootstep = new StepSound("sand", 1.0F, 1.0F);
	public static final StepSound soundStoneFootstep = new StepSound("rock", 1.0F, 1.0F);
	public static final Block sandstoneInjected = new BlockSandstoneInjected(190, Material.rock)
	.setHardness(1.25F).setResistance(7.0F)/*.setStepSound(soundSandFootstep)*//*.setUnlocalizedName("sandstoneinjected").setTextureName("sandstone_injected");
	/*public static final Block quartzStained = new BlockQuartzStained(191, Material.rock)
	.setHardness(1.25F).setResistance(7.0F)/*.setStepSound(soundStoneFootstep)*//*.setUnlocalizedName("quartzstained").setTextureName("quartz_stained");*/


	/*
	public void load() {
		ModLoader.registerBlock(sandstoneInjected);
		ModLoader.registerBlock(quartzStained);
		ModLoader.addName(sandstoneInjected, "Color Injected Sandstone");
		ModLoader.addName(quartzStained, "Stained Sandstone");*/
	//ModLoader.addCommand(statue);
	//}


	//
	
	public String getVersion(){
		return "Version 1.1";
	}

	public static void generateStatue(World world, Random random, int par2,
			int par3, int par4, CommandSender sender, String skinName) {
		Statue.generate(world, random, par2, par3, par4, sender, skinName);		
	}

	public static void generateHat(World world, Random random, int par2,
			int par3, int par4, CommandSender sender, String skinName) {
		Hats.generate(world, random, par2, par3, par4, sender, skinName);		
	}

	public static void generatePixel(World world, Random random, int par2,
			int par3, int par4, CommandSender sender) {
		Pixel.generate(world, random, par2, par3, par4, sender);		
	}

	public static void generatePixelGrab(World world, Random random, int par2,
			int par3, int par4, CommandSender sender, String skinName) {
		PixelGrab.generate(world, random, par2, par3, par4, sender, skinName);		
	}

	public static void generateHead(World world, Random random, int par2,
			int par3, int par4, CommandSender sender, String skinName) {
		Head.generate(world, random, par2, par3, par4, sender, skinName);		
	}

	public static void undoStatue(Location loc, Random random, CommandSender sender) {
		StatueUndo.remove(loc, random, (Player)sender);		
	}
	
	public boolean registerCoreProtect(){
		try{
			CoreProtect = new CoreProtect().getAPI();
		}
		catch(NoClassDefFoundError e){
			return false;
		}
		 CoreProtect = new CoreProtect().getAPI();
		 return true;
	}

	public static boolean logBlock(String playerName, Location locate, int blockType, byte blockData ){

		if (CoreProtect != null){ //Ensure we have access to the API
			boolean success = CoreProtect.logPlacement(playerName, locate, blockType, blockData);
			return success;
			}
		return false;
	}
	
	
	/*
	 * Version 1.1 Bukkit.
	 * By K0Gs
	 */
}
