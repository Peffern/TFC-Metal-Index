package com.peffern.metals;

import com.bioxx.tfc.Core.Metal.Alloy.EnumTier;
import com.bioxx.tfc.api.Crafting.AnvilReq;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

/**
 * Core implementation of IMetal -
 * used for creating your own metals.
 * @author peffern
 *
 */
public class BaseMetal implements IMetal
{
	String unshapedName;
	String unshapedIcon;
	String ingotName;
	String ingotIcon;
	String doubleIngotName;
	String doubleIngotIcon;
	String sheetName;
	String sheetIcon;
	String doubleSheetName;
	String doubleSheetIcon;
	
	String sheetBlockIconName;
	String trapDoorIconName;
	String metalName;
	String resourceDir;
	String resource;
	
	IIcon sheetBlockIcon;
	IIcon trapDoorIcon;
	
	double specificHeat;
	double meltingPoint;
	
	Ingredient[] ingredients;
	
	EnumTier enumTier;
	AnvilReq req;
		
	/**
	 * Standard constructor for metal parameters
	 * @param name metal name
	 * @param unshapedN unlocalized name for unshaped metal
	 * @param unshapedI icon string for unshaped metal
	 * @param ingotN unlocalized name for ingot
	 * @param ingotI icon string for ingot
	 * @param doubleIngotN unlocalized name for double ingot
	 * @param doubleIngotI icon string for double ingot
	 * @param sheetN unlocalized name for sheet
	 * @param sheetI icon string for sheet
	 * @param doubleSheetN unlocalized name for double sheet
	 * @param doubleSheetI icon string for double sheet
	 * @param sheetBlockI icon string for sheet block texture
	 * @param trapDoorI icon string for trapdoor block texture
	 * @param dir resource directory for block textures
	 * @param src block texture location
	 * @param ingreds array of alloy ingredients (can be null)
	 * @param sh specific heat
	 * @param melt melt temperature
	 * @param tier melting tier (vessel, crucible, etc.)
	 * @param anvil anvil tier (metal level)
	 */
	public BaseMetal(String name, String unshapedN, String unshapedI, String ingotN, String ingotI, String doubleIngotN, String doubleIngotI, String sheetN, String sheetI, String doubleSheetN, String doubleSheetI, String sheetBlockI, String trapDoorI, String dir, String src, Ingredient[] ingreds, double sh, double melt, EnumTier tier, AnvilReq anvil)
	{
		metalName = name;
		unshapedName = unshapedN;
		unshapedIcon = unshapedI;
		ingotName = ingotN;
		ingotIcon = ingotI;
		doubleIngotName = doubleIngotN;
		doubleIngotIcon = doubleIngotI;
		sheetName = sheetN;
		sheetIcon = sheetI;
		doubleSheetName = doubleSheetN;
		doubleSheetIcon = doubleSheetI;
		
		sheetBlockIconName = sheetBlockI;
		trapDoorIconName = trapDoorI;
		resourceDir = dir;
		resource = src;
		
		specificHeat = sh;
		meltingPoint = melt;
		
		ingredients = ingreds;
		
		if(ingredients == null)
			ingredients = new Ingredient[0];
		
		enumTier = tier;
		req = anvil;
	}
	
	public IIcon getSheetBlockIcon()
	{
		return sheetBlockIcon;
	}
	
	public IIcon getTrapDoorIcon()
	{
		return trapDoorIcon;
	}
	
	public String getMetalName()
	{
		return metalName;
	}
	
	public String getResourceDir()
	{
		return resourceDir;
	}
	
	public String getResource()
	{
		return resource;
	}
	
	public String getUnshapedIcon()
	{
		return unshapedIcon;
	}
	
	public String getIngotIcon()
	{
		return ingotIcon;
	}
	
	public String get2XIngotIcon()
	{
		return doubleIngotIcon;
	}
	
	public String getSheetIcon()
	{
		return sheetIcon;
	}
	
	public String get2XSheetIcon()
	{
		return doubleSheetIcon;
	}
	
	public String getUnshapedUName()
	{
		return unshapedName;
	}
	
	public String getIngotUName()
	{
		return ingotName;
	}
	
	public String get2XIngotUName()
	{
		return doubleIngotName;
	}
	
	public String getSheetUName()
	{
		return sheetName;
	}
	
	public String get2XSheetUName()
	{
		return doubleSheetName;
	}
	
	public double getSpecificHeat()
	{
		return specificHeat;
	}
	
	public double getMeltingPoint()
	{
		return meltingPoint;
	}
	
	public Ingredient[] getAlloyIngreds()
	{
		return ingredients;
	}
	
	public EnumTier getTier()
	{
		return enumTier;
	}
	
	public void registerIcons(IIconRegister register)
	{
		sheetBlockIcon = register.registerIcon(sheetBlockIconName);
		trapDoorIcon = register.registerIcon(trapDoorIconName);
	}
	
	public AnvilReq getReq()
	{
		return req;
	}
	
	public Item getExistingIngotItem()
	{
		return null;
	}
}
