package com.JAWolfe.terrafirmapunktweaks.minetweaker.Buildcraft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.core.recipes.FlexibleRecipe;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IngredientAny;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;

public class BuildcraftRecipes 
{
private BuildcraftRecipes() {}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<IFlexibleRecipe<ItemStack>> removeRecipes(IIngredient output, IIngredient[] inputs, Collection<? extends IFlexibleRecipe<ItemStack>> recipes)
	{
		if (output == IngredientAny.INSTANCE && (inputs == null || inputs.length == 0))
			return new ArrayList<IFlexibleRecipe<ItemStack>>(recipes);
		
		List<IFlexibleRecipe<ItemStack>> results = new ArrayList<IFlexibleRecipe<ItemStack>>();
		for (IFlexibleRecipe<ItemStack> recipe : recipes)
		{
			if (recipe instanceof FlexibleRecipe && matches(output, inputs, (FlexibleRecipe) recipe))
				results.add(recipe);
		}
		
		return results;
	}
	
	private static boolean matches(IIngredient output, IIngredient[] inputs, FlexibleRecipe<ItemStack> recipe)
	{
		ItemStack recipeOutput = (ItemStack) recipe.getOutput();
		if (!output.matches(MineTweakerMC.getIItemStack(recipeOutput)))
			return false;
		
		if (inputs == null || inputs.length == 0)
			return true;
		
		boolean[] matches = new boolean[inputs.length];
		outer: for (Object recipeInputObject : recipe.getInputs())
		{
			IIngredient recipeIngredient = MineTweakerMC.getIIngredient(recipeInputObject);
			for (int i = 0; i < inputs.length; i++) 
			{
				if (!matches[i] && inputs[i].contains(recipeIngredient))
				{
					matches[i] = true;
					continue outer;
				}
			}
			return false;
		}
		
		return true;
	}
}
