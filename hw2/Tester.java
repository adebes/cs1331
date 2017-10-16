/*
Tester – an class that tests the chess classes and enums.
*/

public class Tester {

    public static void main(String[] args) {
        Piece knight = new Knight(Color.BLACK);
        assert knight.algebraicName().equals("N");
        assert knight.fenName().equals("n");
        Square[] attackedSquares = knight.movesFrom(new Square("f6"));
        // test that attackedSquares contains e8, g8, etc.
        Square a1 = new Square("a1");
        Square otherA1 = new Square('a', '1');
        Square h8 = new Square("h8");
        assert a1.equals(otherA1);
        assert !a1.equals(h8);

        System.out.println(a1.toString());
        System.out.println(otherA1.toString());
        System.out.println(h8.toString());
        System.out.println(a1.equals(otherA1));
        System.out.println(a1.equals(h8));

        Piece king = new King(Color.BLACK);
        System.out.println(king.getColor() + " " + king.fenName());
        System.out.println(king.algebraicName());
        // Square[] attackedSquares1 = king.movesFrom(new Square("f6"));
        // for (Square s : attackedSquares1) {
        //     System.out.println(s);
        // }

        Piece queen = new Queen(Color.WHITE);
        System.out.println(queen.getColor() + " " + queen.fenName());
        System.out.println(queen.algebraicName());
        // Square[] attackedSquares2 = queen.movesFrom(new Square("e4"));
        // for (Square s : attackedSquares2) {
        //     System.out.println(s);
        // }

        Piece bishop = new Bishop(Color.BLACK);
        System.out.println(bishop.getColor() + " " + bishop.fenName());
        System.out.println(bishop.algebraicName());
        // Square[] attackedSquares3 = bishop.movesFrom(new Square("c6"));
        // for (Square s : attackedSquares3) {
        //     System.out.println(s);
        // }

        Piece knight2 = new Knight(Color.WHITE);
        System.out.println(knight2.getColor() + " " + knight2.fenName());
        System.out.println(knight2.algebraicName());
        // Square[] attackedSquares4 = knight2.movesFrom(new Square("c6"));
        // for (Square s : attackedSquares4) {
        //     System.out.println(s);
        // }

        Piece rook = new Rook(Color.BLACK);
        System.out.println(rook.getColor() + " " + rook.fenName());
        System.out.println(rook.algebraicName());
        // Square[] attackedSquares5 = rook.movesFrom(new Square("c6"));
        // for (Square s : attackedSquares5) {
        //     System.out.println(s);
        // }

        Piece pawn = new Pawn(Color.BLACK);
        System.out.println(pawn.getColor() + " " + pawn.fenName());
        System.out.println(pawn.algebraicName());
        // Square[] attackedSquares6 = pawn.movesFrom(new Square("a6"));
        // for (Square s : attackedSquares6) {
        //     System.out.println(s);
        // }
    }
}
