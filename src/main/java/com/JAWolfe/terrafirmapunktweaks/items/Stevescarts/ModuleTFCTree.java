package com.JAWolfe.terrafirmapunktweaks.items.Stevescarts;

import com.bioxx.tfc.Blocks.Flora.BlockLogNatural;
import com.bioxx.tfc.Blocks.Flora.BlockLogVert;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import vswe.stevescarts.Carts.MinecartModular;
import vswe.stevescarts.Modules.Addons.Plants.ModuleModTrees;

public class ModuleTFCTree extends ModuleModTrees {

	public ModuleTFCTree(MinecartModular cart) {
		super(cart);
		// TODO Auto-generated constructor stub
	}

	public boolean isLeaves(Block b, int x, int y, int z) {
		return b.isLeaves((getCart()).worldObj, x, y, z);
	}

	public boolean isWood(Block b, int x, int y, int z) {
		return b instanceof BlockLogNatural || b instanceof BlockLogVert || b.isWood((getCart()).worldObj, x, y, z);
	}

	public boolean isSapling(ItemStack sapling) {
		if (sapling != null) {

			if (isStackSapling(sapling))
				return true;
			if (sapling.getItem() instanceof net.minecraft.item.ItemBlock) {
				Block b = Block.getBlockFromItem(sapling.getItem());

				if (b instanceof net.minecraft.block.BlockSapling) {
					return true;
				}

				return (b != null && isStackSapling(new ItemStack(b, 1, 32767)));
			}
		}

		return false;
	}

	private boolean isStackSapling(ItemStack sapling) {
		int id = OreDictionary.getOreID(sapling);
		String name = OreDictionary.getOreName(id);
		return (name != null && name.startsWith("treeSapling"));
	}

}
