package com.peffern.metals.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.TransformerExclusions({"com.peffern.metals"})
public class MetalsIndexLoadingPlugin implements IFMLLoadingPlugin
{
	@Override
	public String[] getASMTransformerClass() 
	{
		return new String[]{ClientProxyCT.class.getName(), BlockSetupCT.class.getName(), RecipesCT.class.getName(), WAILADataCT.class.getName()};
	}

	@Override
	public String getModContainerClass() 
	{
		return null;
	}

	@Override
	public String getSetupClass() 
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) 
	{
		
	}

	@Override
	public String getAccessTransformerClass() 
	{
		return null;
	}
}
