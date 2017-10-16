/**
 * A subclass of Piece that represents the Bishop piece.
 *
 * @author ajds6
 * @version 1.0
 */
public class Bishop extends Piece {

    /**
     * Creates a Bishop with all required parameters.
     *
     * @param c the color of the square
     */
    public Bishop(Color c) {
        super(c);
    }

    @Override
    public String fenName() {
        if (this.getColor() == (Color.WHITE)) {
            return "B";
        } else {
            return "b";
        }
    }

    @Override
    public String algebraicName() {
        return "B";
    }

    @Override
    public Square[] movesFrom(Square square) {
        String strPossibleMoves = "";
        char file = square.toString().charAt(0);
        char rank = square.toString().charAt(1);
        Square[] result = new Square[0];
        while ((file + 1 <= 'h') && (rank + 1 <= '8')) {
            char adjFile = (char) (file + 1);
            char adjRank = (char) (rank + 1);
            strPossibleMoves += " " + adjFile + adjRank;
            file += 1;
            rank += 1;
        }
        file = square.toString().charAt(0);
        rank = square.toString().charAt(1);
        while ((file - 1 >= 'a') && (rank - 1 >= '1')) {
            char adjFile = (char) (file - 1);
            char adjRank = (char) (rank - 1);
            strPossibleMoves += " " + adjFile + adjRank;
            file -= 1;
            rank -= 1;
        }
        file = square.toString().charAt(0);
        rank = square.toString().charAt(1);
        while ((file + 1 <= 'h') && (rank - 1 >= '1')) {
            char adjFile = (char) (file + 1);
            char adjRank = (char) (rank - 1);
            strPossibleMoves += " " + adjFile + adjRank;
            file += 1;
            rank -= 1;
        }
        file = square.toString().charAt(0);
        rank = square.toString().charAt(1);
        while ((file - 1 >= 'a') && (rank + 1 <= '8')) {
            char adjFile = (char) (file - 1);
            char adjRank = (char) (rank + 1);
            strPossibleMoves += " " + adjFile + adjRank;
            file -= 1;
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
