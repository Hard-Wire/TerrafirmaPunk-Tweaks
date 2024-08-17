package com.JAWolfe.terrafirmapunktweaks.inventory.tweaks;

import java.util.List;

import net.blay09.mods.craftingtweaks.api.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.inventory.*;

import cpw.mods.fml.relauncher.*;

import com.JAWolfe.terrafirmapunktweaks.inventory.TFCCraftingGridTweakV2ProviderImpl;
import com.JAWolfe.terrafirmapunktweaks.reference.References;
import com.bioxx.tfc.Containers.ContainerPlayerTFC;

public class TFCCraftingGridTweakProvider implements TweakProvider, TFCInventorySizeHandler {
	private final DefaultProviderV2 defaultProvider;
	private final RotationHandler smallRotationHandler;

	public TFCCraftingGridTweakProvider() {
		this.defaultProvider = new TFCCraftingGridTweakV2ProviderImpl();
		this.smallRotationHandler = new TFCCraftingGridRotationHandler(this);
	}

	protected int size;

	@Override
	public int GetTFCContainerSize() {
		return this.size;
	}

	@Override
	public boolean load() {
		return true;
	}

	@Override
	public IInventory getCraftMatrix(final EntityPlayer entityPlayer, final Container container, final int id) {
		return (IInventory) ((ContainerPlayer) container).craftMatrix;
	}

	@Override
	public boolean requiresServerSide() {
		return false;
	}

	@Override
	public int getCraftingGridStart(final EntityPlayer entityPlayer, final Container container, final int id) {
		return 0;
	}

	@Override
	public int getCraftingGridSize(final EntityPlayer entityPlayer, final Container container, final int id) {
		this.size = 4;
		if (container instanceof ContainerPlayerTFC) {
			if (entityPlayer.getEntityData().hasKey("craftingTable"))
				this.size = 9;
			else
				this.size = 4;
			return  ((ContainerPlayerTFC)container).craftMatrix.getSizeInventory();
		}

		return this.size;
	}

	@Override
	public void clearGrid(final EntityPlayer entityPlayer, final Container container, final int id,
			final boolean forced) {
		this.defaultProvider.clearGrid(this, id, entityPlayer, container, false, forced);
	}

	@Override
	public void rotateGrid(final EntityPlayer entityPlayer, final Container container, final int id,
			final boolean counterClockwise) {
		this.defaultProvider.rotateGrid(this, id, entityPlayer, container, this.smallRotationHandler, counterClockwise);
	}

	@Override
	public void balanceGrid(final EntityPlayer entityPlayer, final Container container, final int id) {
		this.defaultProvider.balanceGrid(this, id, entityPlayer, container);
	}

	@Override
	public void spreadGrid(final EntityPlayer entityPlayer, final Container container, final int id) {
		this.defaultProvider.spreadGrid(this, id, entityPlayer, container);
	}

	@Override
	public boolean canTransferFrom(final EntityPlayer entityPlayer, final Container container, final int id,
			final Slot sourceSlot) {
		return this.defaultProvider.canTransferFrom(entityPlayer, container, sourceSlot);
	}

	@Override
	public boolean transferIntoGrid(final EntityPlayer entityPlayer, final Container container, final int id,
			final Slot sourceSlot) {
		return this.defaultProvider.transferIntoGrid(this, id, entityPlayer, container, sourceSlot);
	}

	@Override
	public ItemStack putIntoGrid(final EntityPlayer entityPlayer, final Container container, final int id,
			final ItemStack itemStack, final int index) {
		return this.defaultProvider.putIntoGrid(this, id, entityPlayer, container, itemStack, index);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void initGui(final GuiContainer guiContainer, final List buttonList) {
		final int paddingLeft = 1;
		final int paddingTop = -16;
		buttonList.add(CraftingTweaksAPI.createRotateButton(0, guiContainer.guiLeft + guiContainer.xSize / 2 + 1,
				guiContainer.guiTop - 16));
		buttonList.add(CraftingTweaksAPI.createBalanceButton(0, guiContainer.guiLeft + guiContainer.xSize / 2 + 1 + 18,
				guiContainer.guiTop - 16));
		buttonList.add(CraftingTweaksAPI.createClearButton(0, guiContainer.guiLeft + guiContainer.xSize / 2 + 1 + 36,
				guiContainer.guiTop - 16));
	}

	@Override
	public String getModId() {
		return References.MODID_TFC;
	}
}