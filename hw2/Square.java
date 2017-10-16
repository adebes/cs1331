/**
 * Represents squares on a chess board
 *
 * @author ajds6
 * @version 1.0
 */

public class Square {

    private char file;
    private char rank;
    private String squareName;

    /**
     * Creates a Square with all required parameters.
     *
     * @param file the column of the square
     * @param rank the row of the square
     */
    public Square(char file, char rank) {
        this.file = file;
        this.rank = rank;
        squareName = "" + file + rank;
    }

    /**
     * Constructor delegating the appropriate parameters to the other
     * constructor above to initialize the instance variables.
     *
     * @param name a String representation of the square name
     */
    public Square(String name) {
        this(name.charAt(0), name.charAt(1));
    }

    /**
     * @return a String representation of the square name
     */
    public String toString() {
        return squareName;
    }

    /**
     * @return a boolean indicating whether the Square has the same
     *         String name as another Square.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof Square)) {
            return false;
        }
        Square s = (Square) other;
        return ((this.file == s.file) && (this.rank == s.rank));
    }
}
