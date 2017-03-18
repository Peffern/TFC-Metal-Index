package com.peffern.metals;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockIngotPile;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Render.Models.ModelIngotPile;
import com.bioxx.tfc.Render.TESR.TESRIngotPile;
import com.bioxx.tfc.TileEntities.TEIngotPile;
import com.bioxx.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

/**
 * Custom ingot pile tile entity
 * renders new meatls properly
 * @author peffern
 *
 */
public class CustomTESRIngotPile extends TESRIngotPile
{
	private final ModelIngotPile model = new ModelIngotPile();

	@Override
	public void renderTileEntityIngotPileAt(TEIngotPile te, double a, double b, double c, float d)
	{

		Block pile = te.getBlockType();
		if (te.getWorldObj() != null && te.getStackInSlot(0) != null && pile == TFCBlocks.ingotPile)
		{
			int i = ((BlockIngotPile) pile).getStack(te.getWorldObj(), te);
			IMetal metal = MetalsRegistry.getMetal(te.type);
			String dir;
			String src;
			if(metal == null)
			{
				//tfc metals
				dir = Reference.MOD_ID;
				src = "textures/blocks/metal/" + te.type + ".png";
			}
			else
			{
				//new metals
				dir = metal.getResourceDir();
				src = metal.getResource();
			}
			
			//procede with rendering
			TFC_Core.bindTexture(new ResourceLocation(dir, src));
			GL11.glPushMatrix();
			GL11.glTranslatef((float)a + 0.0F, (float)b + 0F, (float)c + 0.0F); //size
			model.renderIngots(i);
			GL11.glPopMatrix();
		}
	}
}
