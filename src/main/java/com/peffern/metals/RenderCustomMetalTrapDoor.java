package com.peffern.metals;

import com.bioxx.tfc.Blocks.BlockMetalSheet;
import com.bioxx.tfc.Render.Blocks.RenderMetalTrapDoor;
import com.bioxx.tfc.TileEntities.TEMetalTrapDoor;
import com.bioxx.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderCustomMetalTrapDoor extends RenderMetalTrapDoor
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		renderer.setRenderBounds(0.125F, 0.4F, 0F, 1F, 0.475F, 1f);
		renderInvBlock(block, metadata&255, renderer);
		renderer.setRenderBounds(0.0F, 0.4F, 0.1F, 0.125F, 0.525F, 0.4f);
		int id = metadata >> 5;
		IIcon[] icons = ((BlockMetalSheet)TFCBlocks.metalSheet).icons;
		IIcon icon;
		if(id < icons.length)
			icon = icons[id];
		else
			icon = MetalsRegistry.getMetal(id).getSheetBlockIcon();
		
		renderInvBlock(block, icon, renderer);
		renderer.setRenderBounds(0.0F, 0.4F, 0.6F, 0.125F, 0.525F, 0.9f);
		renderInvBlock(block, icon, renderer);
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		IBlockAccess access = renderer.blockAccess;
		TEMetalTrapDoor te = (TEMetalTrapDoor)access.getTileEntity(x,y,z);
		if(te != null)
		{
			ItemStack stack = te.sheetStack;
			if(stack!= null)
			{
				int v = te.sheetStack.getItemDamage() >> 5;
				IIcon[] icons = ((BlockMetalSheet)TFCBlocks.metalSheet).icons;
				if(v >= icons.length)
				{
					IIcon[] icons2 = new IIcon[v+1];
					System.arraycopy(icons, 0, icons2, 0, icons.length);
					IMetal metalObj = MetalsRegistry.getMetal(v);
					IIcon metalIcon = metalObj.getSheetBlockIcon();
					icons2[v] = metalIcon;
					((BlockMetalSheet)TFCBlocks.metalSheet).icons = icons2;
					
				}
				boolean value = render(block, x, y, z, renderer);
				
				((BlockMetalSheet)TFCBlocks.metalSheet).icons = icons;
				
				return value;
			}
		}
		return super.renderWorldBlock(world, x, y, z, block, modelId, renderer);
		
	}
}
