package com.peffern.metals;

import java.lang.reflect.Field;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Metal.MetalPair;
import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.TileEntities.TECrucible;
import com.bioxx.tfc.TileEntities.TEForge;
import com.bioxx.tfc.api.HeatRaw;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Interfaces.ISmeltable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TECustomCrucible extends TECrucible
{
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			
			ItemStack stackToSmelt = storage[0];
			if(stackToSmelt != null)
			{
				
				Item itemToSmelt = stackToSmelt.getItem();
				if(!(itemToSmelt instanceof ItemMeltedMetal) && !(itemToSmelt instanceof ISmeltable))
				{
					MetalData data = MetalsRegistry.getMetal(itemToSmelt);
					if(data != null)
					{
						HeatRaw raw = data.heatRaw;
						try
						{
							Field cookDelay = TECrucible.class.getDeclaredField("cookDelay");
							cookDelay.setAccessible(true);
							int cd = ((Integer)cookDelay.get(this)).intValue();
							if(temperature >= raw.meltTemp && cd == 0)
							{
								Metal m = data.metal;
								if(addMetal(m,100))
								{
									temperature *= 0.9f;
									cookDelay.set(this, 40);
									if(stackToSmelt.stackSize <= 1)
										storage[0] = null;
									else
										storage[0].stackSize--;
									updateGui((byte) 0);
								}
							}
						}
						catch(Exception ex)
						{
							System.err.println(ex.getMessage());
						}
						
					}
					
				}
			}
		}
		super.updateEntity();
	}
}
