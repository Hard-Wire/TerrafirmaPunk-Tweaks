package com.JAWolfe.terrafirmapunktweaks.Stevescarts.ModuleData;

import vswe.stevescarts.ModuleData.ModuleDataGroup;
import vswe.stevescarts.Modules.ModuleBase;

public class CustomModuleData extends vswe.stevescarts.ModuleData.ModuleData {

	public CustomModuleData(int arg0, String arg1, Class<? extends ModuleBase> arg2, int arg3) {
		super(arg0, arg1, arg2, arg3);	}

	public CustomModuleData AddRecipe(Object[][] recipe) {
		this.addRecipe(recipe);
		return this;
	}
	
	public CustomModuleData AddRequirement(ModuleDataGroup requirement) {
		this.addRequirement(requirement);
		return this;
	}
}
