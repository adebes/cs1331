public class Card {

    private String[] validRanks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};
    private String[] validSuits = {"hearts", "spades", "clubs", "diamonds"};

    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        setRank(rank);
        setSuit(suit);
    }

    public String toString() { // instance method: operates on the instance of
                                // a method
        return String.format("%s of %s", rank, suit);
    }

    private void setRank(String rank) {
        if (contains(validRanks, rank)) {
            this.rank = rank;
        } else {
            System.out.printf("Invalid rank: %s%n", rank);
            System.exit(1);
        }
    }

    private void setSuit(String suit) {
        if (contains(validSuits, suit)) {
            this.suit = suit;
        } else {
            System.out.printf("Invalid rank: %s%n", suit);
            System.exit(1);
        }
    }

    public String getRank() {
        return this.rank;
    }

    public void getSuit(String suit) {
        this.suit = suit;
    }

    private boolean contains(String[] array, String value) {
        for (String e : array) {
            if (e.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
