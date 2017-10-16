/**
 * A subclass of Piece that represents the Pawn piece.
 *
 * @author ajds6
 * @version 1.0
 */
public class Pawn extends Piece {

    /**
     * Creates a Pawn with all required parameters.
     *
     * @param c the color of the square
     */
    public Pawn(Color c) {
        super(c);
    }

    @Override
    public String fenName() {
        if (this.getColor() == (Color.WHITE)) {
            return "P";
        } else {
            return "p";
        }
    }

    @Override
    public String algebraicName() {
        return "";
    }

    @Override
    public Square[] movesFrom(Square square) {
        String strPossibleMoves = "";
        char file = square.toString().charAt(0);
        char rank = square.toString().charAt(1);
        Square[] result = new Square[0];
        if (this.getColor() == (Color.WHITE)) {
            if (rank + 1 <= '8') {
                if (rank == '2') {
                    char adjRank = (char) (rank + 1);
                    strPossibleMoves += " " + file + adjRank;
                    adjRank = (char) (rank + 2);
                    strPossibleMoves += " " + file + adjRank;
                } else {
                    char adjRank = (char) (rank + 1);
                    strPossibleMoves += " " + file + adjRank;
                }
            }
        } else {
            if (rank - 1 >= '1') {
                if (rank == '7') {
                    char adjRank = (char) (rank - 1);
                    strPossibleMoves += " " + file + adjRank;
                    adjRank = (char) (rank - 2);
                    strPossibleMoves += " " + file + adjRank;
                } else {
                    char adjRank = (char) (rank - 1);
                    strPossibleMoves += " " + file + adjRank;
                }
            }
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
