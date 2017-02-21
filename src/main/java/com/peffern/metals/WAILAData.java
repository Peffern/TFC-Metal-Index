package com.peffern.metals;

import java.util.List;

import javax.swing.JOptionPane;

import com.bioxx.tfc.TileEntities.TEMetalTrapDoor;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WAILAData implements IWailaDataProvider
{
	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.addConfig("TerraFirmaCraft", "tfc.oreQuality");

		reg.registerBodyProvider(new WAILAData(), TEMetalTrapDoor.class);
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		int l = currenttip.size()-1;
		String last = currenttip.get(l);
		if(last.equals("<ERROR>"))
		{
			NBTTagCompound tag = accessor.getNBTData();
			ItemStack sheetStack = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("sheetType"));
			int v = sheetStack.getItemDamage() & 31;
			IMetal metalObj = MetalsRegistry.getMetal(v);
			String val = metalObj.getMetalName();
			currenttip.set(l,val);
			
		}
		// TODO Auto-generated method stub
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
			int y, int z) {

		// TODO Auto-generated method stub
		return null;
	}
}
