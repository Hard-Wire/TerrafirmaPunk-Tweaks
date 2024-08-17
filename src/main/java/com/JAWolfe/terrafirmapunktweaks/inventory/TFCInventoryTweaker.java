package com.JAWolfe.terrafirmapunktweaks.inventory;

import org.apache.logging.log4j.Level;

import codechicken.nei.api.API;
import codechicken.nei.recipe.DefaultOverlayHandler;
import net.minecraft.client.gui.inventory.GuiContainer;

import com.JAWolfe.terrafirmapunktweaks.TerraFirmaPunkTweaks;
import com.JAWolfe.terrafirmapunktweaks.inventory.tweaks.TFCCraftingGridTweakProvider;
import com.JAWolfe.terrafirmapunktweaks.reference.References;

import cpw.mods.fml.common.Loader;
import net.blay09.mods.craftingtweaks.CraftingTweaks;
import net.blay09.mods.craftingtweaks.api.CraftingTweaksAPI;
import net.blay09.mods.craftingtweaks.api.TweakProvider;

public class TFCInventoryTweaker {
	public static void TweakInventory() {
		if (Loader.isModLoaded(References.MODID_INVENTORYTWEAKS) && Loader.isModLoaded(References.MODID_TFC)) {
			TerraFirmaPunkTweaks.logger.log(Level.INFO, String.format("Adding TFC Inventory Crafting Tweak"));
			TweakProvider tp = (TweakProvider) new TFCCraftingGridTweakProvider();
			registerProvider("com.bioxx.tfc.Containers.ContainerPlayerTFC", tp);
		}
	}

	private static void registerProvider(final String className, final TweakProvider provider) {
		try {
			CraftingTweaksAPI.registerProvider((Class) Class.forName(className), provider);
		} catch (ClassNotFoundException e) {
			System.err.println("Could not register Crafting Tweaks addon for " + provider.getModId()
					+ " - internal names have changed.");
		}
	}
}
