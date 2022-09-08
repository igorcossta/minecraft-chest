package io.github.igorcossta.menus;

public enum Row {
    ONE(9), TWO(18), THREE(27), FOUR(36), FIVE(45), SIX(54);

    private final int size;

    Row(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }
}
