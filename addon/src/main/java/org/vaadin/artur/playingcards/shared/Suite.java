package org.vaadin.artur.playingcards.shared;

public enum Suite {
    HEARTS("♥"), DIAMONDS("♦"), CLUBS("♣"), SPADES("♠");

    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    private Suite(String id) {
        this.symbol = id;
    }

    public static Suite getByOrdinal(int ordinal) {
        return values()[ordinal];
    }

    public Color getColor() {
        if (this == HEARTS || this == DIAMONDS) {
            return Color.RED;
        } else {
            return Color.BLACK;
        }
    }

    public static enum Color {
        BLACK, RED;

        public static Color getByOrdinal(int ordinal) {
            return values()[ordinal];
        }

    }

    public static Suite getBySymbol(String symbol) {
        for (Suite suite : values()) {
            if (symbol.equals(suite.getSymbol())) {
                return suite;
            }
        }
        return null;
    }
}
