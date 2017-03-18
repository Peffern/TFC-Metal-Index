package com.peffern.metals;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftingHandler 
{
	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent e)
	{
		EntityPlayer player = e.player;
		ItemStack itemstack = e.crafting;
		Item item = itemstack.getItem();
		IInventory iinventory = e.craftMatrix;

		if(iinventory != null)
		{

			if (!player.worldObj.isRemote)
			{
				//when you pull an ingot out of a mold
				MetalData data = MetalsRegistry.getMetal(item);
				if(data != null)
				{
					for (int i = 0; i < iinventory.getSizeInventory(); i++)
					{
						ItemStack is = iinventory.getStackInSlot(i);
						if (is == null)
							continue;
						else if (is.getItem().equals(data.unshaped))
						{
							//give the player the mold back
							if (player.worldObj.rand.nextInt(20) == 0)
								player.worldObj.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f, player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
							else
								TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.ceramicMold, 1, 1), player);

							break;
						}
					}
				}
				
			}
		}
	}
}
