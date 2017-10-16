/**
 * A subclass of Piece that represents the Rook piece.
 *
 * @author ajds6
 * @version 1.0
 */
public class Rook extends Piece {

    /**
     * Creates a Rook with all required parameters.
     *
     * @param c the color of the square
     */
    public Rook(Color c) {
        super(c);
    }

    @Override
    public String fenName() {
        if (this.getColor() == (Color.WHITE)) {
            return "R";
        } else {
            return "r";
        }
    }

    @Override
    public String algebraicName() {
        return "R";
    }

    @Override
    public Square[] movesFrom(Square square) {
        String strPossibleMoves = "";
        char file = square.toString().charAt(0);
        char rank = square.toString().charAt(1);
        Square[] result = new Square[0];
        while (file + 1 <= 'h') {
            char adjFile = (char) (file + 1);
            strPossibleMoves += " " + adjFile + rank;
            file += 1;
        }
        file = square.toString().charAt(0);
        while (file - 1 >= 'a') {
            char adjFile = (char) (file - 1);
            strPossibleMoves += " " + adjFile + rank;
            file -= 1;
        }
        file = square.toString().charAt(0);
        while (rank - 1 >= '1') {
            char adjRank = (char) (rank - 1);
            strPossibleMoves += " " + file + adjRank;
            rank -= 1;
        }
        rank = square.toString().charAt(1);
        while (rank + 1 <= '8') {
            char adjRank = (char) (rank + 1);
            strPossibleMoves += " " + file + adjRank;
            rank += 1;
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
