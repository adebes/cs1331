/**
 * A subclass of Piece that represents the Knight piece.
 *
 * @author ajds6
 * @version 1.0
 */
public class Knight extends Piece {

    /**
     * Creates a Knight with all required parameters.
     *
     * @param c the color of the square
     */
    public Knight(Color c) {
        super(c);
    }

    @Override
    public String fenName() {
        if (this.getColor() == (Color.WHITE)) {
            return "N";
        } else {
            return "n";
        }
    }

    @Override
    public String algebraicName() {
        return "N";
    }

    @Override
    public Square[] movesFrom(Square square) {
        String strPossibleMoves = "";
        char file = square.toString().charAt(0);
        char rank = square.toString().charAt(1);
        Square[] result = new Square[0];
        // checks the space two ranks up and one file right
        if ((file + 1 <= 'h') && (rank + 2 <= '8')) {
            char adjFile = (char) (file + 1);
            char adjRank = (char) (rank + 2);
            strPossibleMoves += " " + adjFile + adjRank;
        }
        // checks the space two ranks up and one file left
        if ((file - 1 >= 'a') && (rank + 2 <= '8')) {
            char adjFile = (char) (file - 1);
            char adjRank = (char) (rank + 2);
            strPossibleMoves += " " + adjFile + adjRank;
        }
        // checks the space two ranks down and one file right
        if ((file + 1 <= 'h') && (rank - 2 >= '1')) {
            char adjFile = (char) (file + 1);
            char adjRank = (char) (rank - 2);
            strPossibleMoves += " " + adjFile + adjRank;
        }
        // checks the space two ranks down and one file left
        if ((file - 1 >= 'a') && (rank - 2 >= '1')) {
            char adjFile = (char) (file - 1);
            char adjRank = (char) (rank - 2);
            strPossibleMoves += " " + adjFile + adjRank;
        }
        // checks the space one rank up and two files right
        if ((file + 2 <= 'h') && (rank + 1 <= '8')) {
            char adjFile = (char) (file + 2);
            char adjRank = (char) (rank + 1);
            strPossibleMoves += " " + adjFile + adjRank;
        }
        // checks the space one rank down and two files right
        if ((file + 2 <= 'h') && (rank - 1 >= '1')) {
            char adjFile = (char) (file + 2);
            char adjRank = (char) (rank - 1);
            String newSpace = (Character.toString(adjFile)
                + Character.toString(adjRank));
            strPossibleMoves += " " + newSpace;
        }
        // checks the space one rank up and two files left
        if ((file - 2 >= 'a') && (rank + 1 <= '8')) {
            char adjFile = (char) (file - 2);
            char adjRank = (char) (rank + 1);
            strPossibleMoves += " " + adjFile + adjRank;
        }
        // checks the space one rank down and two files left
        if ((file - 2 >= 'a') && (rank - 1 >= '1')) {
            char adjFile = (char) (file - 2);
            char adjRank = (char) (rank - 1);
            strPossibleMoves += " " + adjFile + adjRank;
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
