package com.peffern.metals;

import com.bioxx.tfc.Core.Metal.Alloy.EnumTier;
import com.bioxx.tfc.api.Crafting.AnvilReq;

import net.minecraft.item.Item;

public class ExpanderMetal extends BaseMetal
{

	Item ingot;
	
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
