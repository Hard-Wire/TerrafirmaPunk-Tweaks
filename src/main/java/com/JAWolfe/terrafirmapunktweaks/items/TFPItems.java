package com.JAWolfe.terrafirmapunktweaks.items;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.logging.log4j.Level;

import com.JAWolfe.terrafirmapunktweaks.TerraFirmaPunkTweaks;
import com.JAWolfe.terrafirmapunktweaks.Stevescarts.ModuleData.CustomModuleData;
import com.JAWolfe.terrafirmapunktweaks.items.Stevescarts.ModuleTFCTree;
import com.JAWolfe.terrafirmapunktweaks.reference.Globals;
import com.JAWolfe.terrafirmapunktweaks.reference.References;
import com.bioxx.tfc.api.Metal;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import io.netty.handler.logging.LogLevel;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import vswe.stevescarts.Helpers.ComponentTypes;
import vswe.stevescarts.Helpers.Localization;
import vswe.stevescarts.Items.ModItems;
import vswe.stevescarts.ModuleData.ModuleData;
import vswe.stevescarts.ModuleData.ModuleDataGroup;

public class TFPItems {
	public static Item CustomBucketOil;
	public static Item CustomBucketBlood;
	public static Item CustomBucketOliveOil;
	public static Item CustomBucketWhaleOil;
	public static Item BlockMold;
	public static Item FenceMold;
	public static Item HalfSlabMold;
	public static Item MechCompMold;
	public static Item PistonMold;
	public static Item WireCoilMold;
	public static Item BearingMold;
	public static Item BoltMold;
	public static Item NailMold;
	public static Item NutMold;
	public static Item WasherMold;
	public static Item HopperMold;
	public static Item TFPNecronomicon;

	public static Item tungstenIngot;
	public static Item tungstenIngot2x;
	public static Item tungstenUnshaped;

	public static Item oreChunk;

	// Steves Carts Addons
	public static Item cartAddonTFCTree;

	public static void initialise() {
		CustomBucketOliveOil = new TFPCustomBucket().setUnlocalizedName("Wooden Bucket Olive Oil");
		GameRegistry.registerItem(CustomBucketOliveOil, CustomBucketOliveOil.getUnlocalizedName());

		Globals.TUNGSTEN = new Metal("Tungsten", tungstenUnshaped, tungstenIngot);

		oreChunk = new ItemTFPOre().setFolder("ore/").setUnlocalizedName("Ore");
		GameRegistry.registerItem(oreChunk, oreChunk.getUnlocalizedName());

		if (Loader.isModLoaded(References.MODID_BC)) {
			CustomBucketOil = new TFPCustomBucket().setUnlocalizedName("Wooden Bucket Oil");

			GameRegistry.registerItem(CustomBucketOil, CustomBucketOil.getUnlocalizedName());
		}

		if (Loader.isModLoaded(References.MODID_NECROMANCY)) {
			CustomBucketBlood = new TFPCustomBucket().setUnlocalizedName("Wooden Bucket Blood");
			GameRegistry.registerItem(CustomBucketBlood, CustomBucketBlood.getUnlocalizedName());

			TFPNecronomicon = new TFPNecronomicon().setUnlocalizedName("TFPNecronomicon");
			GameRegistry.registerItem(TFPNecronomicon, TFPNecronomicon.getUnlocalizedName());
		}

		if (Loader.isModLoaded(References.MODID_FORESTRY)) {
			BlockMold = new MetalMold().setUnlocalizedName("Block Mold");
			FenceMold = new MetalMold().setUnlocalizedName("Fence Mold");
			HalfSlabMold = new MetalMold().setUnlocalizedName("Half Slab Mold");
			MechCompMold = new MetalMold().setUnlocalizedName("Mechanical Component Mold");
			PistonMold = new MetalMold().setUnlocalizedName("Piston Mold");
			WireCoilMold = new MetalMold().setUnlocalizedName("Wire Coil Mold");
			BearingMold = new MetalMold().setUnlocalizedName("Bearing Mold");
			BoltMold = new MetalMold().setUnlocalizedName("Bolt Mold");
			NailMold = new MetalMold().setUnlocalizedName("Nail Mold");
			NutMold = new MetalMold().setUnlocalizedName("Nut Mold");
			WasherMold = new MetalMold().setUnlocalizedName("Washer Mold");
			HopperMold = new MetalMold().setUnlocalizedName("Hopper Mold");

			GameRegistry.registerItem(BlockMold, BlockMold.getUnlocalizedName());
			GameRegistry.registerItem(FenceMold, FenceMold.getUnlocalizedName());
			GameRegistry.registerItem(HalfSlabMold, HalfSlabMold.getUnlocalizedName());
			GameRegistry.registerItem(MechCompMold, MechCompMold.getUnlocalizedName());
			GameRegistry.registerItem(PistonMold, PistonMold.getUnlocalizedName());
			GameRegistry.registerItem(WireCoilMold, WireCoilMold.getUnlocalizedName());
			GameRegistry.registerItem(BearingMold, BearingMold.getUnlocalizedName());
			GameRegistry.registerItem(BoltMold, BoltMold.getUnlocalizedName());
			GameRegistry.registerItem(NailMold, NailMold.getUnlocalizedName());
			GameRegistry.registerItem(NutMold, NutMold.getUnlocalizedName());
			GameRegistry.registerItem(WasherMold, WasherMold.getUnlocalizedName());
			GameRegistry.registerItem(HopperMold, HopperMold.getUnlocalizedName());
		}

		if (Loader.isModLoaded(References.MODID_SC2)) {
			CustomBucketWhaleOil = new TFPCustomBucket().setUnlocalizedName("Wooden Bucket Whale Oil");
			GameRegistry.registerItem(CustomBucketWhaleOil, CustomBucketWhaleOil.getUnlocalizedName());
		}

		if (Loader.isModLoaded(References.MODID_STEVESCARTS)) {
			ModuleDataGroup woodcutterGroup = new ModuleDataGroup(Localization.MODULE_INFO.CUTTER_GROUP);
			ModuleData.getModules().stream().filter((module) -> module.getName().contains("Wood Cutter"))
					.forEach(module -> woodcutterGroup.add(module));

			CustomModuleData treeModule = new CustomModuleData(Collections.max(ModuleData.getList().keySet()) + 1, "Tree: TFP",
					ModuleTFCTree.class, 30);
			ArrayList<ModuleData> modules = new ArrayList<ModuleData>();
			modules.add(treeModule);

			Field validModules;
			try {
				validModules = ReflectionHelper.findField(ModItems.class, new String[] {"validModules"});
//				addRecipe = ModuleData.class.getDeclaredMethod("addRecipe", Object[][].class);
//				addRequirement = ModuleData.class.getDeclaredMethod("addRequirement", ModuleDataGroup.class);
//				validModules = ModItems.class.getDeclaredField("validModules");

				validModules.setAccessible(true);

				HashMap<Byte, Boolean> vM = ((HashMap<Byte, Boolean>) validModules.get(null));
				treeModule.AddRequirement(woodcutterGroup);
				treeModule.AddRecipe(new Object[][] { { Items.glowstone_dust, null, Items.glowstone_dust },
						{ Items.redstone, Blocks.sapling, Items.redstone }, { ComponentTypes.SIMPLE_PCB.getItemStack(),
								ComponentTypes.EMPTY_DISK.getItemStack(), ComponentTypes.SIMPLE_PCB.getItemStack() } });

			
				
				modules.forEach((module) -> {
					if(!module.getIsLocked())					
						vM.put(Byte.valueOf(module.getID()), true);
					TerraFirmaPunkTweaks.logger.log(Level.INFO, String.format("Adding Steve's Carts Module[%s:%d]", module.getName(), module.getID()));
					ItemStack submodule = new ItemStack(ModItems.modules, 1, module.getID());
					GameRegistry.registerCustomItemStack(submodule.getUnlocalizedName(), submodule);
				});

			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
