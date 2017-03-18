package com.peffern.metals;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockMetalSheet;
import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.AnvilRecipe;
import com.bioxx.tfc.api.Crafting.AnvilReq;
import com.peffern.metals.ChunkEventHandler;
import com.peffern.metals.InitClientWorldPacket;
import com.peffern.metals.PlayerTracker;
import com.peffern.metals.ServerTickHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

/**
 * Mod to make pewter a new alloy so you can make better bowls and jugs
 * @author peffern
 *
 */
@Mod(modid = TFCMetalIndex.MODID, name = TFCMetalIndex.MODNAME, version = TFCMetalIndex.VERSION, dependencies = "required-before:" + "terrafirmacraft" + ";" + "after:" + "Waila" + ";")
public class TFCMetalIndex 
{
	
	private static boolean anvilInit = false;
	
	/** Mod ID String */
	public static final String MODID = "tfcmetals";
	
	/** Mod Name */
	public static final String MODNAME = "TFC Metal Index";
	
	/** Mod Version */
	public static final String VERSION = "1.2";
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		//anvil setup things
		TerraFirmaCraft.PACKET_PIPELINE.registerPacket(InitClientWorldPacket.class);
		
		FMLCommonHandler.instance().bus().register(new PlayerTracker());
		
		FMLCommonHandler.instance().bus().register(new ServerTickHandler());
		
		//mold crafting thing
		FMLCommonHandler.instance().bus().register(new CraftingHandler());
		
		MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());
		
	}
	
	public static void anvilInit(World world)
	{
		if(anvilInit)
			return;
		
		for(Map.Entry<Integer,MetalData> e : MetalsRegistry.enumerate())
		{
			MetalData m = e.getValue();
			int i = e.getKey();
			IMetal metalObj = MetalsRegistry.getMetal(i);
			
			AnvilManager manager = AnvilManager.getInstance();
			AnvilManager.world = world;
			
			//manufacturing items
			manager.addRecipe(new AnvilRecipe(new ItemStack(m.ingot2X), null, "sheet", false, metalObj.getReq(), new ItemStack(m.sheet)));
			
			//double ingot/sheet
			manager.addWeldRecipe(new AnvilRecipe(new ItemStack(m.ingot),new ItemStack(m.ingot),metalObj.getReq(), new ItemStack(m.ingot2X, 1)));
			manager.addWeldRecipe(new AnvilRecipe(new ItemStack(m.sheet),new ItemStack(m.sheet),metalObj.getReq(), new ItemStack(m.sheet2X, 1)));
			
			try
			{
				//trapdoords
				Method addTrapDoor = Recipes.class.getDeclaredMethod("addTrapDoor", Item.class, int.class);
				addTrapDoor.setAccessible(true);
				addTrapDoor.invoke(Recipes.class, m.sheet, i);
			}
			catch(InvocationTargetException ex)
			{
				ex.getTargetException().printStackTrace();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		anvilInit = true;
	}
	
	//ASM recovery
	public static void addTrapDoors(AnvilManager manager, Item sheet, int index)
	{
		for(Map.Entry<Integer,MetalData> e : MetalsRegistry.enumerate())
		{
			int id = e.getKey();
			MetalData m = e.getValue();
			manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(m.ingot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.metalTrapDoor, 1, index + ((id) << 5))));

		}
	}

	//this is an extremely fragile ASM recovery - I think WAILA is swallowing exceptions so it's very hard to figure out when something breaks.
	public static String s(Object a)
	{
		try
		{
			Method getNBTData = a.getClass().getDeclaredMethod("getNBTData", new Class<?>[0]);
			NBTTagCompound tag = (NBTTagCompound)getNBTData.invoke(a,new Object[0]);
			ItemStack sheetStack = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("sheetType"));
			String[] names = ((BlockMetalSheet)TFCBlocks.metalSheet).metalNames;
			int v = sheetStack.getItemDamage() & 31;
			if(v < names.length)
				return names[v];
			else
			{
				IMetal metalObj = MetalsRegistry.getMetal(v);
				return metalObj.getMetalName();
			}
		}
		catch(Exception ex)
		{
			return "<ERROR>";
		}
	}
	
	
}
