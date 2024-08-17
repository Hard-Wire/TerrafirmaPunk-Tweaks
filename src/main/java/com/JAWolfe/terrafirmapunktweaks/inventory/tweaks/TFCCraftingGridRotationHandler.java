package com.JAWolfe.terrafirmapunktweaks.inventory.tweaks;

import org.apache.logging.log4j.Level;

import com.JAWolfe.terrafirmapunktweaks.TerraFirmaPunkTweaks;

import net.blay09.mods.craftingtweaks.api.RotationHandler;

public class TFCCraftingGridRotationHandler implements RotationHandler {
	public TFCInventorySizeHandler sizeHandler;

	public TFCCraftingGridRotationHandler(TFCInventorySizeHandler sizeHandler) {
		this.sizeHandler = sizeHandler;
	}

	@Override
	public boolean ignoreSlotId(final int slotId) {
		return false;
	}

	@Override
	public int rotateSlotId(final int slotId, final boolean counterClockwise) {		
		if (this.sizeHandler.GetTFCContainerSize() == 9)
			if (!counterClockwise) {
				switch (slotId) {
				case 0:
					return 1;

				case 1:
					return 2;

				case 2:
					return 5;

				case 5:
					return 8;

				case 8:
					return 7;

				case 7:
					return 6;

				case 6:
					return 3;
				case 3:
					return 0;
				}
			} else {
				switch (slotId) {
				case 0:
					return 3;

				case 1:
					return 0;

				case 2:
					return 1;

				case 3:
					return 6;

				case 5:
					return 2;

				case 6:
					return 7;

				case 7:
					return 8;

				case 8:
					return 5;

				}
			}
		else
			if (!counterClockwise) {
				switch (slotId) {
				case 0: 
					return 1;				
				case 1: 
					return 4;
				case 4:
					return 3;
				case 3: 
					return 0;	
				}
			} else {
				switch (slotId) {
				case 0: 
					return 3;
				case 3: 
					return 4;
				case 4:
					return 1;
				case 1: 
					return 0;
				}
			}	
		return slotId;
	}
}
