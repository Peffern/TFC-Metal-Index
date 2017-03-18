package com.peffern.metals;

import com.bioxx.tfc.Blocks.BlockMetalSheet;
import com.bioxx.tfc.TileEntities.TEMetalSheet;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

/**
 * Custom sheet - renders the new metals
 * @author peffern
 *
 */
public class BlockCustomMetalSheet extends BlockMetalSheet
{
	
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		super.registerBlockIcons(register);
		
		//setup icons
		MetalsRegistry.registerIcons(register);
	}
	
	@Override
	public IIcon getIcon(IBlockAccess blockaccess, int i, int j, int k, int meta)
	{
		TEMetalSheet te = (TEMetalSheet) blockaccess.getTileEntity(i, j, k);
		if(te != null)
		{
			if(te.metalID < metalNames.length)
				//old behavior
				return super.getIcon(blockaccess, i, j, k, meta);
			else
			{
				//new metals
				IMetal metalObj = MetalsRegistry.getMetal(te.metalID);
				return metalObj.getSheetBlockIcon();
			}
				
		}
		else
			return super.getIcon(blockaccess, i, j, k, meta);
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(meta >= 0)
		{
			if(meta < icons.length)
				//old behavior
				return super.getIcon(side, meta);
			//new metals
			IMetal metalObj = MetalsRegistry.getMetal(meta);
			return metalObj.getSheetBlockIcon();

		}
		return super.getIcon(side, meta);
	}
	
}
