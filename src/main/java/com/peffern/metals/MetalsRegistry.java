package com.peffern.metals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.bioxx.tfc.Blocks.BlockMetalSheet;
import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Core.Metal.Alloy;
import com.bioxx.tfc.Core.Metal.AlloyManager;
import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.Items.ItemIngot;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.Items.ItemMetalSheet;
import com.bioxx.tfc.Items.ItemMetalSheet2x;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRaw;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MetalsRegistry 
{
	private static final Map<Integer,IMetal> METALS_MAP;
	private static final Map<String, IMetal> METALS_DICT;
	private static final Map<Integer,MetalData> METAL_ITEMS;
	
	private static AtomicInteger METAL_ID_COUNTER;
	
	static
	{
		METALS_MAP = new HashMap<Integer,IMetal>();
		METALS_DICT = new HashMap<String,IMetal>();
		BlockMetalSheet delegate = new BlockMetalSheet();
		METAL_ID_COUNTER = new AtomicInteger(delegate.metalNames.length);
		METAL_ITEMS = new HashMap<Integer,MetalData>();
	}
	
	public static void registerIcons(IIconRegister register)
	{
		for(IMetal metal : METALS_MAP.values())
		{
			metal.registerIcons(register);
		}
	}
	
	public static MetalData addMetal(final IMetal metal)
	{
		int id = (METAL_ID_COUNTER.getAndIncrement());
		METALS_MAP.put(id, metal);
		METALS_DICT.put(metal.getMetalName(), metal);
		Item unshaped = new ItemMeltedMetal()
		{
			@Override
			public void registerIcons(IIconRegister register)
			{
				this.itemIcon = register.registerIcon(metal.getUnshapedIcon());
			}
		}.setUnlocalizedName(metal.getUnshapedUName());
		Item ingot = new ItemIngot()
		{
			@Override
			public void registerIcons(IIconRegister register)
			{
				this.itemIcon = register.registerIcon(metal.getIngotIcon());
			}
		}.setUnlocalizedName(metal.getIngotUName());
		Item doubleIngot = new ItemIngot()
		{
			@Override
			public void registerIcons(IIconRegister register)
			{
				this.itemIcon = register.registerIcon(metal.get2XIngotIcon());
			}
		}.setMetal(metal.getMetalName(), 200).setUnlocalizedName(metal.get2XIngotUName());
		Item sheet = new ItemMetalSheet(id)
		{
			@Override
			public void registerIcons(IIconRegister register)
			{
				this.itemIcon = register.registerIcon(metal.getSheetIcon());
			}
		}.setMetal(metal.getMetalName(), 200).setUnlocalizedName(metal.getSheetUName());
		Item doubleSheet = new ItemMetalSheet2x(id)
		{
			@Override
			public void registerIcons(IIconRegister register)
			{
				this.itemIcon = register.registerIcon(metal.get2XSheetIcon());
			}
		}.setMetal(metal.getMetalName(), 400).setUnlocalizedName(metal.get2XSheetUName());
		GameRegistry.registerItem(unshaped, unshaped.getUnlocalizedName());
		GameRegistry.registerItem(ingot, ingot.getUnlocalizedName());
		GameRegistry.registerItem(doubleIngot, doubleIngot.getUnlocalizedName());
		GameRegistry.registerItem(sheet, sheet.getUnlocalizedName());
		GameRegistry.registerItem(doubleSheet, doubleSheet.getUnlocalizedName());
		Metal METAL = new Metal(metal.getMetalName(), unshaped, ingot);
		MetalRegistry.instance.addMetal(METAL, metal.getTier());
		Alloy alloy = new Alloy(METAL, metal.getTier());
		for(Ingredient ingred : metal.getAlloyIngreds())
		{
			alloy.addIngred(ingred.metal, ingred.min, ingred.max);
		}
		AlloyManager.INSTANCE.addAlloy(alloy);
		
		HeatRegistry manager = HeatRegistry.getInstance();
		
		HeatRaw raw = new HeatRaw(metal.getSpecificHeat(), metal.getMeltingPoint());
		
		manager.addIndex(new HeatIndex(new ItemStack(unshaped,1), raw,new ItemStack(unshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(ingot,1), raw,new ItemStack(unshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(doubleIngot,1), raw,new ItemStack(unshaped,2)));
		manager.addIndex(new HeatIndex(new ItemStack(sheet,1), raw,new ItemStack(unshaped,2)));
		manager.addIndex(new HeatIndex(new ItemStack(doubleSheet,1),raw,new ItemStack(unshaped,4)));
		
		GameRegistry.addShapelessRecipe(new ItemStack(unshaped, 1, 0), 
				new Object[] {Recipes.getStackNoTemp(new ItemStack(ingot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1)});

		GameRegistry.addShapelessRecipe(new ItemStack(ingot, 1, 0), 
				new Object[] {Recipes.getStackNoTemp(new ItemStack(unshaped, 1))});
		
		MetalData data = new MetalData(unshaped, ingot, doubleIngot, sheet, doubleSheet, METAL, alloy, raw);
	
		METAL_ITEMS.put(id,data);
		
		return data;
	}
	
	public static IMetal getMetal(int id)
	{
		return METALS_MAP.get(id);
	}
	
	public static IMetal getMetal(String name)
	{
		return METALS_DICT.get(name);
	}
	
	public static MetalData getData(int id)
	{
		return METAL_ITEMS.get(id);
	}
	
	public static Iterable<Map.Entry<Integer,MetalData>> enumerate()
	{
		return METAL_ITEMS.entrySet();
	}
}
