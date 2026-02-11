package io.realmit.interfass.menu;

public enum InterfassSlot {
    SLOT_0(0),
    SLOT_1(1);

    private final int slot;

    InterfassSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}
