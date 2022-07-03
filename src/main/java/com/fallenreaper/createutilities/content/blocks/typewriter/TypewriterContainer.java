package com.fallenreaper.createutilities.content.blocks.typewriter;

import com.fallenreaper.createutilities.index.CUContainerTypes;
import com.simibubi.create.foundation.gui.container.ContainerBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public class TypewriterContainer extends ContainerBase<TypewriterBlockEntity> {

    public TypewriterContainer(MenuType<?> type, int id, Inventory inv, TypewriterBlockEntity contentHolder) {
        super(type, id, inv, contentHolder);
    }
    public TypewriterContainer(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
    }



    @Override
    protected TypewriterBlockEntity createOnClient(FriendlyByteBuf extraData) {
        ClientLevel world = Minecraft.getInstance().level;
        BlockEntity tileEntity = world.getBlockEntity(extraData.readBlockPos());
        if (tileEntity instanceof TypewriterBlockEntity typewriter) {
            typewriter.readClient(extraData.readNbt());
            return typewriter;
        }
        return null;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot clickedSlot = getSlot(pIndex);
        if (!clickedSlot.hasItem())
            return ItemStack.EMPTY;
        ItemStack stack = clickedSlot.getItem();

        if (pIndex < 3) {
            moveItemStackTo(stack, 2, slots.size(), false);
        } else {
            if (moveItemStackTo(stack, 0, 1, false) || moveItemStackTo(stack, 2, 3, false)
                    || moveItemStackTo(stack, 4, 5, false))
                ;
        }

        return ItemStack.EMPTY;
    }

    @Override
    protected void initAndReadInventory(TypewriterBlockEntity contentHolder) {


    }
    @Override
    public boolean canDragTo(Slot slot) {
        return slot.index > contentHolder.inventory.getSlots() && super.canDragTo(slot);
    }

    public static TypewriterContainer create(int id, Inventory inv, TypewriterBlockEntity te) {
        return new TypewriterContainer(CUContainerTypes.TYPEWRITER_MENUTYPE.get(), id, inv, te);
    }



    @Override
    protected void addSlots() {
        int x = 0;
        int y = 0;
        int[] yOffsets = { y+20, y+ 49, y + 78};

        addSlot(new SlotItemHandler(contentHolder.inventory, 4, x + 173, y + 30));
        addSlot(new SlotItemHandler(contentHolder.inventory, 5, x + 173, y + 79));

            for (int i = 0; i < yOffsets.length; ++i) {

                addSlot(new SlotItemHandler(contentHolder.inventory, i, x + 45, yOffsets[i]));

        }

        addPlayerSlots(8, 148);
    }

    @Override
    protected void saveData(TypewriterBlockEntity contentHolder) {

    }

}
