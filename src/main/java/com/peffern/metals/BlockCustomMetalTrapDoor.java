package com.peffern.metals;

import com.bioxx.tfc.Blocks.BlockMetalTrapDoor;
import com.bioxx.tfc.TileEntities.TEMetalTrapDoor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

/**
 * Custom trapdoor - renders pewter
 * @author peffern
 *
 */
public class BlockCustomMetalTrapDoor extends BlockMetalTrapDoor
{
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		super.registerBlockIcons(register);
		
		MetalsRegistry.registerIcons(register);
	}
	
	@Override
	public IIcon getIcon(IBlockAccess blockaccess, int i, int j, int k, int meta)
	{
		TEMetalTrapDoor te = (TEMetalTrapDoor) blockaccess.getTileEntity(i, j, k);
		
		if(te!= null && te.sheetStack != null)
		{
			int v = te.sheetStack.getItemDamage() & 31;
			if (v < icons.length)
				return icons[v];
			else
			{
				System.out.println(v);
				IMetal metalObj = MetalsRegistry.getMetal(v);
				return metalObj.getTrapDoorIcon();
			}
		}
		else
			return super.getIcon(blockaccess, i,j,k,meta);
				
		
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		int v = meta & 31;
		if (v < icons.length)
			return icons[v];
		else
		{
			System.out.println("m"+v);
			IMetal metalObj = MetalsRegistry.getMetal(v);
			return metalObj.getTrapDoorIcon();
		}
	}
}
