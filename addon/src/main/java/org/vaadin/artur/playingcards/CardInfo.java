package org.vaadin.artur.playingcards;

import java.util.Objects;

import org.vaadin.artur.playingcards.shared.Suite;

public class CardInfo {
    private String symbol;
    private int rank;

    public CardInfo() {

    }

    public CardInfo(Suite suite, int rank) {
        this.symbol = suite.getSymbol();
        this.rank = rank;
    }

    public Suite getSuite() {
        return Suite.getBySymbol(symbol);
    }

    public void setSuite(Suite suite) {
        this.symbol = suite.getSymbol();
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRankSymbol() {
        if (rank == 1) {
            return "a";
        }
        if (rank < 11) {
            return String.valueOf(rank);
        } else if (rank == 11) {
            return "j";
        } else if (rank == 12) {
            return "q";
        } else if (rank == 13) {
            return "k";
        }

        return "?";
    }

    public void setRankSymbol(String rankSymbol) {
        if (rankSymbol.equals("a")) {
            setRank(1);
        } else if (rankSymbol.equals("j")) {
            setRank(11);
        } else if (rankSymbol.equals("q")) {
            setRank(12);
        } else if (rankSymbol.equals("k")) {
            setRank(13);
        } else {
            setRank(Integer.parseInt(rankSymbol));
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + rank;
        result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CardInfo other = (CardInfo) obj;
        if (rank != other.rank) {
            return false;
        }
        if (!Objects.equals(symbol, other.symbol)) {
            return false;
        }
        return true;
    }

}
