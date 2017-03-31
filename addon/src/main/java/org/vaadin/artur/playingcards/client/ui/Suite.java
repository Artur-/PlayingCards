package org.vaadin.artur.playingcards.client.ui;

public enum Suite {
    HEARTS("hearts"), DIAMONDS("diamonds"), CLUBS("clubs"), SPADES("spades");

    private String id;

    public String getId() {
        return id;
    }

    private Suite(String id) {
        this.id = id;
    }

    public static Suite getByOrdinal(int ordinal) {
        return values()[ordinal];
    }

    public Color getColor() {
        if (this == HEARTS || this == DIAMONDS)
            return Color.RED;
        else
            return Color.BLACK;
    }

    public static enum Color {
        BLACK, RED;

        public static Color getByOrdinal(int ordinal) {
            return values()[ordinal];
        }

    }
}
