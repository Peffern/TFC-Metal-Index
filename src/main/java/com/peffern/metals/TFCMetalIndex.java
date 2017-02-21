package com.peffern.metals;


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
import mcp.mobius.waila.api.IWailaDataAccessor;
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
@Mod(modid = TFCMetalIndex.MODID, name = TFCMetalIndex.MODNAME, version = TFCMetalIndex.VERSION, dependencies = "required-before:" + "terrafirmacraft" + ";" + "required-after:" + "Waila" + ";")
public class TFCMetalIndex 
{
	
	private static boolean anvilInit = false;
	
	/** Mod ID String */
	public static final String MODID = "tfcmetals";
	
	/** Mod Name */
	public static final String MODNAME = "TFC Metal Index";
	
	/** Mod Version */
	public static final String VERSION = "1.1";
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		TerraFirmaCraft.PACKET_PIPELINE.registerPacket(InitClientWorldPacket.class);
		
		FMLCommonHandler.instance().bus().register(new PlayerTracker());
		
		FMLCommonHandler.instance().bus().register(new ServerTickHandler());
		
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
				//pewter sheet trapdoords
				Method addTrapDoor = Recipes.class.getDeclaredMethod("addTrapDoor", Item.class, int.class);
				addTrapDoor.setAccessible(true);
				addTrapDoor.invoke(Recipes.class, m.sheet, i);
			}
			/*catch(InvocationTargetException ex)
			{
				ex.getTargetException().printStackTrace();
			}*/
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		anvilInit = true;
	}
	
	public static void addTrapDoors(AnvilManager manager, Item sheet, int index)
	{
		for(Map.Entry<Integer,MetalData> e : MetalsRegistry.enumerate())
		{
			int id = e.getKey();
			MetalData m = e.getValue();
			manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(m.ingot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.metalTrapDoor, 1, index + ((id) << 5))));

		}
	}
	
	/**
	 * Get the ItemStack to show in Waila when you look at a Crop
	 * @param accessor Waila block accessor
	 * @param config Waila config
	 * @return the display stack
	 */
	/*public static List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		JOptionPane.showMessageDialog(null, "WAILA'D");
		NBTTagCompound tag = accessor.getNBTData();
		ItemStack sheetStack = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("sheetType"));
		int v = sheetStack.getItemDamage() & 31;
		String metalType;
		if(v < BlockMetalTrapDoor.metalNames.length)
			metalType = BlockMetalTrapDoor.metalNames[v];
		else
		{
			IMetal metalObj = MetalsRegistry.getMetal(v);
			metalType = metalObj.getMetalName();
		}
		
		currenttip.add(TFC_Core.translate("gui.metal." + metalType.replaceAll("\\s", "")));
		return currenttip;
	}*/

	public static String s(IWailaDataAccessor a)
	{
		NBTTagCompound tag = a.getNBTData();
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
	
	
}
