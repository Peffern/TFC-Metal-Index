package com.peffern.metals;

import com.bioxx.tfc.Core.Metal.Alloy.EnumTier;
import com.bioxx.tfc.api.Crafting.AnvilReq;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public interface IMetal
{
	public IIcon getSheetBlockIcon();
	
	public IIcon getTrapDoorIcon();
	
	public String getMetalName();
	
	public String getResourceDir();
	
	public String getResource();
	
	public String getUnshapedIcon();
	
	public String getIngotIcon();
	
	public String get2XIngotIcon();
	
	public String getSheetIcon();
	
	public String get2XSheetIcon();
	
	public String getUnshapedUName();
	
	public String getIngotUName();
	
	public String get2XIngotUName();
	
	public String getSheetUName();
	
	public String get2XSheetUName();
	
	public EnumTier getTier();
	
	public AnvilReq getReq();
	
	public Ingredient[] getAlloyIngreds();
	
	public double getSpecificHeat();
	
	public double getMeltingPoint();
	
	public void registerIcons(IIconRegister register);	
}
