/**
 * Represents chess pieces (Note: Pawns are called pieces as well.)
 *
 * @author ajds6
 * @version 1.0
 */

public abstract class Piece {

    private Color color;

    /**
     * Creates a Piece with all required parameters.
     *
     * @param color the color of the square
     */
    public Piece(Color color) {
        this.color = color;
    }

    /**
     * @return the Color of the piece instance
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return a String representation of the piece's FEN name.
     */
    public abstract String fenName();

    /**
     * @return a String representation of the piece's algebraic name
     */
    public abstract String algebraicName();

    /**
     * @param square the Square that the piece moves from.
     * @return a Square[] containg all the squares the piece could move to from
     * square on a chess board containing only the piece.
     */
    public abstract Square[] movesFrom(Square square);
}
