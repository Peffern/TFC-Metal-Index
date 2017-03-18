package com.peffern.metals;

import java.lang.reflect.Field;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.TileEntities.TECrucible;
import com.bioxx.tfc.api.HeatRaw;
import com.bioxx.tfc.api.Metal;
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
				//if the item fails to be any tfc items
				if(!(itemToSmelt instanceof ItemMeltedMetal) && !(itemToSmelt instanceof ISmeltable))
				{
					//check to see if it's one of ours
					MetalData data = MetalsRegistry.getMetal(itemToSmelt);
					if(data != null)
					{
						HeatRaw raw = data.heatRaw;
						try
						{
							//reflect on cook delay
							Field cookDelay = TECrucible.class.getDeclaredField("cookDelay");
							cookDelay.setAccessible(true);
							int cd = ((Integer)cookDelay.get(this)).intValue();
							
							if(temperature >= raw.meltTemp && cd == 0)
							{
								Metal m = data.metal;
								//must be an ingot from another mod that got 'ExpanderMetal'-ified
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
