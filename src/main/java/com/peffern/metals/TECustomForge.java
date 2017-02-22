package com.peffern.metals;

import java.lang.reflect.Method;
import java.util.Random;

import javax.swing.JOptionPane;

import com.bioxx.tfc.Items.ItemBloom;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.TileEntities.TEForge;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Interfaces.ISmeltable;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class TECustomForge extends TEForge
{
	@Override
	public void cookItem(int i)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		if(fireItemStacks[i] != null)
		{
			HeatIndex index = manager.findMatchingIndex(fireItemStacks[i]);
			ItemStack inputCopy = fireItemStacks[i].copy();
			
			if(index != null && TFC_ItemHeat.getTemp(fireItemStacks[i]) > index.meltTemp)
			{
				float temperature = TFC_ItemHeat.getTemp(fireItemStacks[i]);
				
				if(index.hasOutput())
				{
					MetalData data = MetalsRegistry.getMetal(inputCopy.getItem());
					if (data != null && temperature >= data.heatRaw.meltTemp)
					{
						
						ItemStack meltedItem = new ItemStack(data.unshaped);
						TFC_ItemHeat.setTemp(meltedItem, temperature);
						
						ItemStack mold = null;
						int moldIndex = getMoldIndex();
						
						if(moldIndex != -1)
						{
							mold = fireItemStacks[moldIndex];
							ItemStack moldIS = mold;
							ItemStack outputCopy = meltedItem.copy();
							TFC_ItemHeat.setTemp(outputCopy, temperature);
							if(moldIS.stackSize <= 1)
							{
								moldIS.stackSize = 0;
								fireItemStacks[moldIndex] = null;
							}
							else
								moldIS.stackSize--;
							fireItemStacks[i] = outputCopy;
						}
						else
						{
							fireItemStacks[i] = null;
						}
						
						
					}
				}
			}
			super.cookItem(i);
		}
	}
}
