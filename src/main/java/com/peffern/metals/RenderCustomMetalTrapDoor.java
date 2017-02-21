package com.peffern.metals;

import com.bioxx.tfc.Blocks.BlockMetalSheet;
import com.bioxx.tfc.Render.Blocks.RenderMetalTrapDoor;
import com.bioxx.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;

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
}
