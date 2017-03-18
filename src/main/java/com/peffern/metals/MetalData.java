package com.peffern.metals;

import com.bioxx.tfc.Core.Metal.Alloy;
import com.bioxx.tfc.api.HeatRaw;
import com.bioxx.tfc.api.Metal;

import net.minecraft.item.Item;

/**
 * Bundle class for return data of the metal registry
 * @author peffern
 *
 */
public class MetalData 
{
	//the items
	public Item unshaped;
	public Item ingot;
	public Item ingot2X;
	public Item sheet;
	public Item sheet2X;
	
	//assorted registry things
	public Metal metal;
	
	public Alloy alloy;
	
	public HeatRaw heatRaw;
	
	public MetalData(Item melted, Item ingot, Item ingot2, Item sheet, Item sheet2, Metal metal, Alloy alloy, HeatRaw raw)
	{
		this.unshaped = melted;
		this.ingot = ingot;
		this.ingot2X = ingot2;
		this.sheet = sheet;
		this.sheet2X = sheet2;
		this.metal = metal;
		this.alloy = alloy;
		this.heatRaw = raw;
	}
}
