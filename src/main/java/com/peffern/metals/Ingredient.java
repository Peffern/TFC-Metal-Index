package com.peffern.metals;

import com.bioxx.tfc.api.Metal;

/**
 * Bundle class for alloy ingredients
 * @author peffern
 *
 */
public class Ingredient
{
	public Metal metal;
	
	public float min;
	
	public float max;
	
	public Ingredient(Metal m, float low, float high)
	{
		metal = m;
		min = low;
		max = high;
	}
}