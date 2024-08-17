package com.JAWolfe.terrafirmapunktweaks.inventory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import net.blay09.mods.craftingtweaks.DefaultProviderV2Impl;
import net.blay09.mods.craftingtweaks.api.RotationHandler;
import net.blay09.mods.craftingtweaks.api.TweakProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;


public class TFCCraftingGridTweakV2ProviderImpl extends DefaultProviderV2Impl
{
	public static int[] matrixIdxs = {0,1,2,3,45,46,47,48,49};
    protected int getNextInventorySlotForGridIndex(int idx) {
    	return matrixIdxs[idx];
    }
	
    @Override
    public void clearGrid(TweakProvider provider, int id, EntityPlayer entityPlayer, Container container, boolean phantomItems, boolean forced) {
        IInventory craftMatrix = provider.getCraftMatrix(entityPlayer, container, id);
        if (craftMatrix == null) {
            return;
        }
        int start = provider.getCraftingGridStart(entityPlayer, container, id);
        int size = provider.getCraftingGridSize(entityPlayer, container, id);
        for (int i = start; i < start + size; i++) {
            if (!phantomItems) {
                ItemStack itemStack = craftMatrix.getStackInSlot(i);
                if (!entityPlayer.inventory.addItemStackToInventory(itemStack)) {
                    if(forced) {
                        entityPlayer.dropPlayerItemWithRandomChoice(itemStack, false);
                        craftMatrix.setInventorySlotContents(i, null);
                    }
                    continue;
                }
            }
            craftMatrix.setInventorySlotContents(i, null);
        }
        container.detectAndSendChanges();
    }

    @Override
    public void balanceGrid(TweakProvider provider, int id, EntityPlayer entityPlayer, Container container) {
        IInventory craftMatrix = provider.getCraftMatrix(entityPlayer, container, id);
        if (craftMatrix == null) {
            return;
        }
        ArrayListMultimap<String, ItemStack> itemMap = ArrayListMultimap.create();
        Multiset<String> itemCount = HashMultiset.create();
        int start = provider.getCraftingGridStart(entityPlayer, container, id);
        int size = provider.getCraftingGridSize(entityPlayer, container, id);
        for (int i = start; i < start + size; i++) {
            int slotIndex = i;
            ItemStack itemStack = craftMatrix.getStackInSlot(slotIndex);
            if (itemStack != null && itemStack.getMaxStackSize() > 1) {
                String key = itemStack.getUnlocalizedName() + "@" + itemStack.getItemDamage();
                itemMap.put(key, itemStack);
                itemCount.add(key, itemStack.stackSize);
            }
        }
        for (String key : itemMap.keySet()) {
            List<ItemStack> balanceList = itemMap.get(key);
            int totalCount = itemCount.count(key);
            int countPerStack = totalCount / balanceList.size();
            int restCount = totalCount % balanceList.size();
            for (ItemStack itemStack : balanceList) {
                itemStack.stackSize = countPerStack;
            }
            int idx = 0;
            while (restCount > 0) {
                ItemStack itemStack = balanceList.get(idx);
                if (itemStack.stackSize < itemStack.getMaxStackSize()) {
                    itemStack.stackSize++;
                    restCount--;
                }
                idx++;
                if (idx >= balanceList.size()) {
                    idx = 0;
                }
            }
        }
        container.detectAndSendChanges();
    }

    @Override
    public void spreadGrid(TweakProvider provider, int id, EntityPlayer entityPlayer, Container container) {
        IInventory craftMatrix = provider.getCraftMatrix(entityPlayer, container, id);
        if (craftMatrix == null) {
            return;
        }
        while(true) {
            ItemStack biggestSlotStack = null;
            int biggestSlotSize = 1;
            int start = provider.getCraftingGridStart(entityPlayer, container, id);
            int size = provider.getCraftingGridSize(entityPlayer, container, id);
            for (int i = start; i < start + size; i++) {
                int slotIndex = i;
                ItemStack itemStack = craftMatrix.getStackInSlot(slotIndex);
                if (itemStack != null && itemStack.stackSize > biggestSlotSize) {
                    biggestSlotStack = itemStack;
                    biggestSlotSize = itemStack.stackSize;
                }
            }
            if (biggestSlotStack == null) {
                return;
            }
            boolean emptyBiggestSlot = false;
            for (int i = start; i < start + size; i++) {
                int slotIndex = i;
                ItemStack itemStack = craftMatrix.getStackInSlot(slotIndex);
                if (itemStack == null) {
                    if(biggestSlotStack.stackSize > 1) {
                        craftMatrix.setInventorySlotContents(slotIndex, biggestSlotStack.splitStack(1));
                    } else {
                        emptyBiggestSlot = true;
                    }
                }
            }
            if(!emptyBiggestSlot) {
                break;
            }
        }
        balanceGrid(provider, id, entityPlayer, container);
    }

    @Override
    public ItemStack putIntoGrid(TweakProvider provider, int id, EntityPlayer entityPlayer, Container container, ItemStack itemStack, int index) {
        IInventory craftMatrix = provider.getCraftMatrix(entityPlayer, container, id);
        if (craftMatrix == null) {
            return itemStack;
        }
        ItemStack craftStack = craftMatrix.getStackInSlot(index);
        if (craftStack != null) {
            if (craftStack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(craftStack, itemStack)) {
                int spaceLeft = Math.min(craftMatrix.getInventoryStackLimit(), craftStack.getMaxStackSize()) - craftStack.stackSize;
                if (spaceLeft > 0) {
                    ItemStack splitStack = itemStack.splitStack(Math.min(spaceLeft, itemStack.stackSize));
                    craftStack.stackSize += splitStack.stackSize;
                    if (itemStack.stackSize <= 0) {
                        return null;
                    }
                }
            }
        } else {
            ItemStack transferStack = itemStack.splitStack(Math.min(itemStack.stackSize, craftMatrix.getInventoryStackLimit()));
            craftMatrix.setInventorySlotContents(index, transferStack);
        }
        if (itemStack.stackSize <= 0) {
            return null;
        }
        return itemStack;
    }

    @Override
    public boolean transferIntoGrid(TweakProvider provider, int id, EntityPlayer entityPlayer, Container container, Slot sourceSlot) {
        IInventory craftMatrix = provider.getCraftMatrix(entityPlayer, container, id);
        if (craftMatrix == null) {
            return false;
        }
        int start = provider.getCraftingGridStart(entityPlayer, container, id);
        int size = provider.getCraftingGridSize(entityPlayer, container, id);
        ItemStack itemStack = sourceSlot.getStack();
        if (itemStack == null) {
            return false;
        }
        int firstEmptySlot = -1;
        for (int i = start; i < start + size; i++) {
            int slotIndex = ((Slot) container.inventorySlots.get(i)).getSlotIndex();
            ItemStack craftStack = craftMatrix.getStackInSlot(slotIndex);
            if (craftStack != null) {
                if (craftStack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(craftStack, itemStack)) {
                    int spaceLeft = Math.min(craftMatrix.getInventoryStackLimit(), craftStack.getMaxStackSize()) - craftStack.stackSize;
                    if (spaceLeft > 0) {
                        ItemStack splitStack = itemStack.splitStack(Math.min(spaceLeft, itemStack.stackSize));
                        craftStack.stackSize += splitStack.stackSize;
                        if (itemStack.stackSize <= 0) {
                            return true;
                        }
                    }
                }
            } else if (firstEmptySlot == -1) {
                firstEmptySlot = slotIndex;
            }
        }
        if (itemStack.stackSize > 0 && firstEmptySlot != -1) {
            ItemStack transferStack = itemStack.splitStack(Math.min(itemStack.stackSize, craftMatrix.getInventoryStackLimit()));
            craftMatrix.setInventorySlotContents(firstEmptySlot, transferStack);
            return true;
        }
        return false;
    }

    @Override
    public void rotateGrid(TweakProvider provider, int id, EntityPlayer entityPlayer, Container container, RotationHandler rotationHandler, boolean counterClockwise) {
        IInventory craftMatrix = provider.getCraftMatrix(entityPlayer, container, id);
        if (craftMatrix == null) {
            return;
        }
        int start = provider.getCraftingGridStart(entityPlayer, container, id);
        int size = provider.getCraftingGridSize(entityPlayer, container, id);
        IInventory matrixClone = new InventoryBasic("", false, size);
        for (int i = 0; i < size; i++) {
            matrixClone.setInventorySlotContents(i, craftMatrix.getStackInSlot(i));
        }
        for (int i = 0; i < size; i++) {
            if (!rotationHandler.ignoreSlotId(i)) {
            	final int slotIndex = start + rotationHandler.rotateSlotId(i, counterClockwise);
                craftMatrix.setInventorySlotContents(slotIndex, matrixClone.getStackInSlot(i));
            }
            
        }
        container.detectAndSendChanges();
    }
}