/**
 * A subclass of Piece that represents the King piece.
 *
 * @author ajds6
 * @version 1.0
 */
public class King extends Piece {

    /**
     * Creates a King with all required parameters.
     *
     * @param c the color of the square
     */
    public King(Color c) {
        super(c);
    }

    @Override
    public String fenName() {
        if (this.getColor() == (Color.WHITE)) {
            return "K";
        } else {
            return "k";
        }
    }

    @Override
    public String algebraicName() {
        return "K";
    }

    @Override
    public Square[] movesFrom(Square square) {
        Square[] stub = new Square[0]; //stub value
        String strPossibleMoves = "";
        char file = square.toString().charAt(0);
        char rank = square.toString().charAt(1);
        Square[] result = new Square[0];
        // checks the space at the top
        if (((rank + 1) <= '8')) {
            char adjRank = (char) (rank + 1);
            strPossibleMoves +=  (" " + file) + adjRank;
        }
        // checks the space at the top right
        if (((file + 1) <= 'h') && ((rank + 1) <= '8')) {
            char adjFile = (char) (file + 1);
            char adjRank = (char) (rank + 1);
            strPossibleMoves +=  (" " + adjFile) + adjRank;
        }
        // checks the space at the right
        if ((file + 1) <= 'h') {
            char adjFile = (char) (file + 1);
            strPossibleMoves += (" " + adjFile) + rank;
        }
        // checks the space at the bottom right
        if (((file + 1) <= 'h') && ((rank - 1) >= '1')) {
            char adjFile = (char) (file + 1);
            char adjRank = (char) (rank - 1);
            strPossibleMoves += (" " + adjFile) + adjRank;
        }
        // checks the space at the bottom
        if (((rank - 1) >= '1')) {
            char adjRank = (char) (rank - 1);
            strPossibleMoves += (" " + file) + adjRank;
        }
        // checks the space at the bottom left
        if (((file - 1) >= 'a') && ((rank - 1) >= '1')) {
            char adjFile = (char) (file - 1);
            char adjRank = (char) (rank - 1);
            strPossibleMoves += (" " + adjFile) + adjRank;
        }
        // checks the space at the left
        if (((file - 1) >= 'a')) {
            char adjFile = (char) (file - 1);
            strPossibleMoves += (" " + adjFile) + rank;
        }
        // checks the space at the top left
        if (((file - 1) >= 'a') && ((rank + 1) <= '8')) {
            char adjFile = (char) (file - 1);
            char adjRank = (char) (rank + 1);
            strPossibleMoves += (" " + adjFile) + adjRank;
        }
        if (!(strPossibleMoves.equals(""))) {
            String[] arrPossibleMoves = strPossibleMoves.trim().split(" ");
            result = new Square[arrPossibleMoves.length];
            for (int i = 0; i < arrPossibleMoves.length; i++) {
                result[i] = new Square(arrPossibleMoves[i]);
            }
        }
        return result;
    }
}
