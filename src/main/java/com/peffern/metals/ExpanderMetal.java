package com.peffern.metals;

import com.bioxx.tfc.Core.Metal.Alloy.EnumTier;
import com.bioxx.tfc.api.Crafting.AnvilReq;

import net.minecraft.item.Item;

public class ExpanderMetal extends BaseMetal
{

	Item ingot;
	
	/**
	 * IMetal implementation for when an ingot item (e.g. from another mod) already exists
	 * @param name metal name
	 * @param unshapedN unlocalized name for unshaped metal
	 * @param unshapedI icon string for unshaped metal
	 * @param ingot the ingot item
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
	public ExpanderMetal(String name, String unshapedN, String unshapedI, Item ingot,
			String doubleIngotN, String doubleIngotI, String sheetN, String sheetI, String doubleSheetN,
			String doubleSheetI, String sheetBlockI, String trapDoorI, String dir, String src, Ingredient[] ingreds,
			double sh, double melt, EnumTier tier, AnvilReq anvil) 
	{
		super(name, unshapedN, unshapedI, null, null, doubleIngotN, doubleIngotI, sheetN, sheetI, doubleSheetN,
				doubleSheetI, sheetBlockI, trapDoorI, dir, src, ingreds, sh, melt, tier, anvil);
		this.ingot = ingot;
		
	}
	
	@Override
	public Item getExistingIngotItem()
	{
		return ingot;
	}

}
