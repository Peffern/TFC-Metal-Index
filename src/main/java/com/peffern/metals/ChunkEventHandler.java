package com.peffern.metals;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;

/**
 * setup anvil on world load
 * @author peffern
 *
 */
public class ChunkEventHandler 
{
    @SubscribeEvent 
    public void onLoadWorld(WorldEvent.Load e) 
    { 
        if (!e.world.isRemote && e.world.provider.dimensionId == 0) 
        { 
            TFCMetalIndex.anvilInit(e.world); 
        } 
    } 
    
	@SubscribeEvent
	public void onUnloadWorld(WorldEvent.Unload e)
	{
		
	}
}
