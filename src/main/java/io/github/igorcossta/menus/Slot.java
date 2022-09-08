package io.github.igorcossta.menus;

import java.util.List;

public enum Slot {
    SYSTEM(List.of(7, 8, 16, 17, 25, 26, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53)),
    STANDARD(List.of(0, 1, 2, 3, 4, 5, 6,
            9, 10, 11, 12, 13, 14, 15,
            18, 19, 20, 21, 22, 23, 24,
            27, 28, 29, 30, 31, 32, 33)),
    PURCHASABLE_CHESTS(List.of(8, 17, 26, 35, 44, 53));

    private final List<Integer> slots;

    Slot(List<Integer> slots) {
        this.slots = slots;
    }

    public List<Integer> getSlots() {
        return slots;
    }
}
