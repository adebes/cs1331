import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PgnReader {
    /**
     * Find the tagName tag pair in a PGN game and return its value.
     *
     * @see http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm
     *
     * @param tagName the name of the tag whose value you want
     * @param game a `String` containing the PGN text of a chess game
     * @return the value in the named tag pair
     */
    public static String tagValue(String tagName, String game) {
        int index = game.indexOf(tagName);
        if (index == -1) {
            return "NOT GIVEN";
        }
        int startIndex = index + tagName.length() + 2;
        int endIndex = game.indexOf("\"", startIndex);
        String result = game.substring(startIndex, endIndex);
        return result;
    }
    /**
     * Play out the moves in game and return a String with the game's
     * final position in Forsyth-Edwards Notation (FEN).
     *
     * @see http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm#c16.1
     *
     * @param game a `String` containing a PGN-formatted chess game or opening
     * @return the game's final position in FEN.
     */
    public static String finalPosition(String game) {
        char[][] boardState =
            {{'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'},
            {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
            {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}};
        String[] moves = infoExtract(game);
        int count = 0;
        // loop that iterates through a String[] called moves (which contains
        // the moves in one game) and passes them into the boardUpdate method
        // along with the current board state and color of the player who has
        // made the move.
        for (String m : moves) {
            disambiguate(m);
            boolean color = ((count % 2) == 0) ? true : false;
            boardUpdate(boardState, m, color);
            count++;
        }
        int spaces = 0;
        String finalPosition = "";
        for (int i = 0; i < boardState.length; i++) {
            spaces = 0;
            for (char j : boardState[i]) {
                if (j == 'x') {
                    spaces += 1;
                } else if (spaces > 0) {
                    finalPosition += spaces;
                    finalPosition += j;
                    spaces = 0;
                } else {
                    finalPosition += j;
                }
            }
            if (spaces > 0) {
                finalPosition += spaces;
            }
            if (i != (boardState.length - 1)) {
                finalPosition += '/';
            }
        }
        return finalPosition;
    }
    public static void printBoard(char[][] board) {
        for (char[] i : board) {
            for (char j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static char disambiguate(String oldMove) {
        char result = ' ';
        if (oldMove.contains("x")) {
            int captureIndex = oldMove.indexOf('x');
            if (oldMove.substring(0, captureIndex).length() == 2) {
                result = oldMove.charAt(captureIndex - 1);
            }
        } else {
            if (oldMove.length() >= 4) {
                if (Character.isUpperCase(oldMove.charAt(0))
                    && (Character.isLetter(oldMove.charAt(2)))) {
                    result = oldMove.charAt(1);
                }
            }
        }
        return result;
    }
    public static String cleanMoves(String oldMove, char removeChar) {
        String result = "";
        if ((removeChar != ' ')
            && (oldMove.contains(Character.toString(removeChar)))) {
            int removeIndex = oldMove.indexOf(removeChar);
            result += (oldMove.substring(0, removeIndex)
                + oldMove.substring(removeIndex + 1, oldMove.length()));
            return result;
        }
        return oldMove;
    }
    /**
    * The boardUpdate method takes in two parameters, the current state of the
    * board (char[][]) and the move that will alter the board state (String).
    */
    public static char[][] boardUpdate(char[][] currentState, String move,
        boolean isWhite) {
        char disambiguator = disambiguate(move);
        String newMove = cleanMoves(move, disambiguator);
        if (isRook(newMove)) {
            char player = (isWhite) ? ('R') : ('r');
            rookMove(currentState, newMove, player, disambiguator);
        } else if (isKnight(newMove)) {
            char player = (isWhite) ? ('N') : ('n');
            knightMove(currentState, newMove, player, disambiguator);
        } else if (isBishop(newMove)) {
            char player = (isWhite) ? ('B') : ('b');
            bishopMove(currentState, newMove, player, disambiguator);
        } else if (isQueen(newMove)) {
            char player = (isWhite) ? ('Q') : ('q');
            queenMove(currentState, newMove, player, disambiguator);
        } else if (isKing(newMove)) {
            char player = (isWhite) ? ('K') : ('k');
            kingMove(currentState, newMove, player, disambiguator);
        } else if (newMove.contains("O")) {
            castlingMove(currentState, newMove, isWhite);
        } else {
            char player = (isWhite) ? ('P') : ('p');
            pawnMove(currentState, newMove, player);
        }
        return currentState;
    }
    /*
    The rookMove method takes in the current state of the board, the move, and
    a boolean indicating the player who has made the move. It returns nothing,
    but updates the current state of the board by the move that was passed in.
    rookMove analyzes moves passed in by first identifying the player who made
    the move and thus assigning the corresponding character ('R' or 'r'). Then
    the method identifies whether the move is a capture move in order to
    correctly assign the character values of the rank and file, which will later
    be translated to index values on the currentState array. Furthermore, four
    for-loops are created in order to check where the piece could have made its
    move from. These for loops terminate once they reach the edge of the board,
    thus preventing any indexing errors. Finally, once the appropriate space on
    the board has been identified, the board is updated to show that the piece
    has moved to a new location.
    */
    public static void rookMove(char[][] currentState, String move,
        char c, char rookDis) {
        char fileMoved = (move.contains("x")) ? (move.charAt(2))
            : (move.charAt(1));
        char rankMoved = (move.contains("x")) ? (move.charAt(3))
            : (move.charAt(2));
        int fileIndex = fileMoved - 'a';
        int rankIndex = '8' - rankMoved;
        for (int i = rankIndex; i <= 7; i++) {
            if (rookDis != ' ') {
                if (Character.isDigit(rookDis)
                    && (('8' - rookDis) == i)) {
                    if (currentState[i][fileIndex] == c) {
                        currentState[i][fileIndex] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                    }
                } else {
                    if ((rookDis - 'a') == fileIndex) {
                        if (currentState[i][fileIndex] == c) {
                            currentState[i][fileIndex] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                }
            } else {
                if (currentState[i][fileIndex] == c) {
                    currentState[i][fileIndex] = 'x';
                    currentState[rankIndex][fileIndex] = c;
                }
                if (i + 1 < 7) {
                    if ((currentState[i + 1][fileIndex] != 'x')
                        && (currentState[i + 1][fileIndex] != c)) {
                        i = 7;
                    }
                }
            }
        }
        for (int i = rankIndex; i >= 0; i--) {
            if (rookDis != ' ') {
                if (Character.isDigit(rookDis)
                    && (('8' - rookDis) == i)) {
                    if (currentState[i][fileIndex] == c) {
                        currentState[i][fileIndex] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                    }
                } else {
                    if ((rookDis - 'a') == fileIndex) {
                        if (currentState[i][fileIndex] == c) {
                            currentState[i][fileIndex] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                }
            } else {
                if (currentState[i][fileIndex] == c) {
                    currentState[i][fileIndex] = 'x';
                    currentState[rankIndex][fileIndex] = c;
                }
                if (i - 1 > 0) {
                    if ((currentState[i - 1][fileIndex] != 'x')
                        && (currentState[i - 1][fileIndex] != c)) {
                        i = 0;
                    }
                }
            }
        }
        for (int i = fileIndex; i <= 7; i++) {
            if (rookDis != ' ') {
                if (Character.isDigit(rookDis)
                    && (('8' - rookDis) == rankIndex)) {
                    if (currentState[rankIndex][i] == c) {
                        currentState[rankIndex][i] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                    }
                } else {
                    if ((rookDis - 'a') == i) {
                        if (currentState[rankIndex][i] == c) {
                            currentState[rankIndex][i] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                }
            } else {
                if (currentState[rankIndex][i] == c) {
                    currentState[rankIndex][i] = 'x';
                    currentState[rankIndex][fileIndex] = c;
                }
                if (i + 1 < 7) {
                    if ((currentState[rankIndex][i + 1] != 'x')
                        && (currentState[rankIndex][i + 1] != c)) {
                        i = 7;
                    }
                }
            }
        }
        for (int i = fileIndex; i >= 0; i--) {
            if (rookDis != ' ') {
                if (Character.isDigit(rookDis)
                    && (('8' - rookDis) == rankIndex)) {
                    if (currentState[rankIndex][i] == c) {
                        currentState[rankIndex][i] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                    }
                } else {
                    if ((rookDis - 'a') == i) {
                        if (currentState[rankIndex][i] == c) {
                            currentState[rankIndex][i] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                }
            } else {
                if (currentState[rankIndex][i] == c) {
                    currentState[rankIndex][i] = 'x';
                    currentState[rankIndex][fileIndex] = c;
                }
                if (i - 1 > 0) {
                    if ((currentState[rankIndex][i - 1] != 'x')
                        && (currentState[rankIndex][i - 1] != c)) {
                        i = 0;
                    }
                }
            }
        }
    }
    /*
    The knightMove method takes in the current state of the board, the move, and
    a boolean indicating the player who has made the move. It returns nothing,
    but updates the current state of the board by the move that was passed in.
    knightMove analyzes moves passed in by first identifying the player who made
    the move and thus assigning the corresponding character ('N' or 'n'). Then
    the method identifies whether the move is a capture move in order to
    correctly assign the character values of the rank and file, which will later
    be translated to index values on the currentState array. Furthermore, a
    series of if-statements are created in order to check where the piece could
    have made its move from. These statements will only execute according to
    whether the move could have come from inside the board, thus preventing any
    indexing errors. Finally, once the appropriate space on the board has been
    identified, the board is updated to show that the piece has moved to a new
    location.
    */
    public static void knightMove(char[][] currentState, String move,
        char c, char knightDis) {
        char fileMoved = (move.contains("x")) ? (move.charAt(2))
            : (move.charAt(1));
        char rankMoved = (move.contains("x")) ? (move.charAt(3))
            : (move.charAt(2));
        int fileIndex = fileMoved - 'a';
        int rankIndex = '8' - rankMoved;
        if ((rankIndex + 2 < 8) && (fileIndex + 1 < 8)) {
            if (knightDis != ' ') {
                if (Character.isDigit(knightDis)) {
                    if ((rankIndex + 2 < 8) && (fileIndex + 1 < 8)
                        && (('8' - knightDis) == (rankIndex + 2))) {
                        if (currentState[rankIndex + 2][fileIndex + 1] == c) {
                            currentState[rankIndex + 2][fileIndex + 1] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                } else {
                    if ((rankIndex + 2 < 8) && (fileIndex + 1 < 8)
                        && ((knightDis - 'a') == (fileIndex + 1))) {
                        if (currentState[rankIndex + 2][fileIndex + 1] == c) {
                            currentState[rankIndex + 2][fileIndex + 1] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                }
            } else {
                if ((rankIndex + 2 < 8) && (fileIndex + 1 < 8)) {
                    if (currentState[rankIndex + 2][fileIndex + 1] == c) {
                        currentState[rankIndex + 2][fileIndex + 1] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                    }
                }
            }
        }
        if ((rankIndex + 2 < 8) && (fileIndex - 1 > -1)) {
            if (knightDis != ' ') {
                if (Character.isDigit(knightDis)) {
                    if ((rankIndex + 2 < 8) && (fileIndex - 1 < 8)
                        && (('8' - knightDis) == (rankIndex + 2))) {
                        if (currentState[rankIndex + 2][fileIndex - 1] == c) {
                            currentState[rankIndex + 2][fileIndex - 1] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                } else {
                    if ((rankIndex + 2 < 8) && (fileIndex - 1 < 8)
                        && ((knightDis - 'a') == (fileIndex - 1))) {
                        if (currentState[rankIndex + 2][fileIndex - 1] == c) {
                            currentState[rankIndex + 2][fileIndex - 1] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                }
            } else {
                if ((rankIndex + 2 < 8) && (fileIndex - 1 < 8)) {
                    if (currentState[rankIndex + 2][fileIndex - 1] == c) {
                        currentState[rankIndex + 2][fileIndex - 1] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                    }
                }
            }
        }
        if ((rankIndex - 2 > -1) && (fileIndex + 1 < 8)) {
            if (knightDis != ' ') {
                if (Character.isDigit(knightDis)) {
                    if ((rankIndex - 2 < 8) && (fileIndex + 1 < 8)
                        && (('8' - knightDis) == (rankIndex - 2))) {
                        if (currentState[rankIndex - 2][fileIndex + 1] == c) {
                            currentState[rankIndex - 2][fileIndex + 1] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                } else {
                    if ((rankIndex - 2 < 8) && (fileIndex + 1 < 8)
                        && ((knightDis - 'a') == (fileIndex + 1))) {
                        if (currentState[rankIndex - 2][fileIndex + 1] == c) {
                            currentState[rankIndex - 2][fileIndex + 1] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                }
            } else {
                if ((rankIndex - 2 < 8) && (fileIndex + 1 < 8)) {
                    if (currentState[rankIndex - 2][fileIndex + 1] == c) {
                        currentState[rankIndex - 2][fileIndex + 1] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                    }
                }
            }
        }
        if ((rankIndex - 2 > -1) && (fileIndex - 1 > -1)) {
            if (knightDis != ' ') {
                if (Character.isDigit(knightDis)) {
                    if ((rankIndex - 2 < 8) && (fileIndex - 1 < 8)
                        && (('8' - knightDis) == (rankIndex - 2))) {
                        if (currentState[rankIndex - 2][fileIndex - 1] == c) {
                            currentState[rankIndex - 2][fileIndex - 1] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                } else {
                    if ((rankIndex - 2 < 8) && (fileIndex - 1 < 8)
                        && ((knightDis - 'a') == (fileIndex - 1))) {
                        if (currentState[rankIndex - 2][fileIndex - 1] == c) {
                            currentState[rankIndex - 2][fileIndex - 1] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                }
            } else {
                if ((rankIndex - 2 < 8) && (fileIndex - 1 < 8)) {
                    if (currentState[rankIndex - 2][fileIndex - 1] == c) {
                        currentState[rankIndex - 2][fileIndex - 1] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                    }
                }
            }
        }
        if ((rankIndex + 1 < 8) && (fileIndex + 2 < 8)) {
            if (knightDis != ' ') {
                if (Character.isDigit(knightDis)) {
                    if ((rankIndex + 1 < 8) && (fileIndex + 2 < 8)
                        && (('8' - knightDis) == (rankIndex + 1))) {
                        if (currentState[rankIndex + 1][fileIndex + 2] == c) {
                            currentState[rankIndex + 1][fileIndex + 2] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                } else {
                    if ((rankIndex + 1 < 8) && (fileIndex + 2 < 8)
                        && ((knightDis - 'a') == (fileIndex + 2))) {
                        if (currentState[rankIndex + 1][fileIndex + 2] == c) {
                            currentState[rankIndex + 1][fileIndex + 2] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                }
            } else {
                if ((rankIndex + 1 < 8) && (fileIndex + 2 < 8)) {
                    if (currentState[rankIndex + 1][fileIndex + 2] == c) {
                        currentState[rankIndex + 1][fileIndex + 2] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                    }
                }
            }
        }
        if ((rankIndex - 1 > -1) && (fileIndex + 2 < 8)) {
            if (knightDis != ' ') {
                if (Character.isDigit(knightDis)) {
                    if ((rankIndex - 1 < 8) && (fileIndex + 2 < 8)
                        && (('8' - knightDis) == (rankIndex - 1))) {
                        if (currentState[rankIndex - 1][fileIndex + 2] == c) {
                            currentState[rankIndex - 1][fileIndex + 2] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                } else {
                    if ((rankIndex - 1 < 8) && (fileIndex + 2 < 8)
                        && ((knightDis - 'a') == (fileIndex + 2))) {
                        if (currentState[rankIndex - 1][fileIndex + 2] == c) {
                            currentState[rankIndex - 1][fileIndex + 2] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                }
            } else {
                if ((rankIndex - 1 < 8) && (fileIndex + 2 < 8)) {
                    if (currentState[rankIndex - 1][fileIndex + 2] == c) {
                        currentState[rankIndex - 1][fileIndex + 2] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                    }
                }
            }
        }
        if ((rankIndex + 1 < 8) && (fileIndex - 2 > -1)) {
            if (knightDis != ' ') {
                if (Character.isDigit(knightDis)) {
                    if ((rankIndex + 1 < 8) && (fileIndex - 2 < 8)
                        && (('8' - knightDis) == (rankIndex + 1))) {
                        if (currentState[rankIndex + 1][fileIndex - 2] == c) {
                            currentState[rankIndex + 1][fileIndex - 2] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                } else {
                    if ((rankIndex + 1 < 8) && (fileIndex - 2 < 8)
                        && ((knightDis - 'a') == (fileIndex - 2))) {
                        if (currentState[rankIndex + 1][fileIndex - 2] == c) {
                            currentState[rankIndex + 1][fileIndex - 2] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                }
            } else {
                if ((rankIndex + 1 < 8) && (fileIndex - 2 < 8)) {
                    if (currentState[rankIndex + 1][fileIndex - 2] == c) {
                        currentState[rankIndex + 1][fileIndex - 2] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                    }
                }
            }
        }
        if ((rankIndex - 1 > -1) && (fileIndex - 2 > -1)) {
            if (knightDis != ' ') {
                if (Character.isDigit(knightDis)) {
                    if ((rankIndex - 1 < 8) && (fileIndex - 2 < 8)
                        && (('8' - knightDis) == (rankIndex - 1))) {
                        if (currentState[rankIndex - 1][fileIndex - 2] == c) {
                            currentState[rankIndex - 1][fileIndex - 2] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                } else {
                    if ((rankIndex - 1 < 8) && (fileIndex - 2 < 8)
                        && ((knightDis - 'a') == (fileIndex - 2))) {
                        if (currentState[rankIndex - 1][fileIndex - 2] == c) {
                            currentState[rankIndex - 1][fileIndex - 2] = 'x';
                            currentState[rankIndex][fileIndex] = c;
                        }
                    }
                }
            } else {
                if ((rankIndex - 1 < 8) && (fileIndex - 2 < 8)) {
                    if (currentState[rankIndex - 1][fileIndex - 2] == c) {
                        currentState[rankIndex - 1][fileIndex - 2] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                    }
                }
            }
        }
    }
    /*
    The bishopMove method takes in the current state of the board, the move, and
    a boolean indicating the player who has made the move. It returns nothing,
    but updates the current state of the board by the move that was passed in.
    bishopMove analyzes moves passed in by first identifying the player who made
    the move and thus assigning the corresponding character ('B' or 'b'). Then
    the method identifies whether the move is a capture move in order to
    correctly assign the character values of the rank and file, which will later
    be translated to index values on the currentState array. Furthermore, four
    while-loops are created in order to check where the piece could have made
    its move from. These while-loops terminate once they reach the edge of the
    board, thus preventing any indexing errors. Finally, once the appropriate
    space on the board has been identified, the board is updated to show that
    the piece has moved to a new location.
    */
    public static void bishopMove(char[][] currentState, String move,
        char c, char bishopDis) {
        char fileMoved = (move.contains("x")) ? (move.charAt(2))
            : (move.charAt(1));
        char rankMoved = (move.contains("x")) ? (move.charAt(3))
            : (move.charAt(2));
        int fileIndex = fileMoved - 'a';
        int rankIndex = '8' - rankMoved;
        int rcount = rankIndex;
        int fcount = fileIndex;
        if (bishopDis != ' ') {
            if (Character.isDigit(bishopDis)) {
                if (('8' - bishopDis) > rankIndex) {
                    boolean found = false;
                    boolean blocked = false;
                    while ((!found) && (!blocked) && (rcount + 1 < 8)
                        && (fcount + 1 < 8)) {
                        if ((currentState[rcount][fcount] != 'x')
                            && (currentState[rcount][fcount] != c)) {
                            blocked = true;
                        }
                        if (currentState[rcount + 1][fcount + 1] == c) {
                            currentState[rankIndex][fileIndex] = c;
                            currentState[rcount + 1][fcount + 1] = 'x';
                            found = true;
                        }
                        rcount += 1;
                        fcount += 1;
                    }
                    rcount = rankIndex;
                    fcount = fileIndex;
                    blocked = false;
                    while ((!found) && (!blocked) && (rcount + 1 < 8)
                        && (fcount - 1 > -1)) {
                        if ((currentState[rcount][fcount] != 'x')
                            && (currentState[rcount][fcount] != c)) {
                            blocked = true;
                        }
                        if (currentState[rcount + 1][fcount - 1] == c) {
                            currentState[rankIndex][fileIndex] = c;
                            currentState[rcount + 1][fcount - 1] = 'x';
                            found = true;
                        }
                        rcount += 1;
                        fcount -= 1;
                    }
                } else if (('8' - bishopDis) < rankIndex) {
                    boolean found = false;
                    boolean blocked = false;
                    while ((!found) && (!blocked) && (rcount - 1 > -1)
                        && (fcount - 1 > -1)) {
                        if ((currentState[rcount][fcount] != 'x')
                            && (currentState[rcount][fcount] != c)) {
                            blocked = true;
                        }
                        if (currentState[rcount - 1][fcount - 1] == c) {
                            currentState[rankIndex][fileIndex] = c;
                            currentState[rcount - 1][fcount - 1] = 'x';
                            found = true;
                        }
                        rcount -= 1;
                        fcount -= 1;
                    }
                    rcount = rankIndex;
                    fcount = fileIndex;
                    blocked = false;
                    while ((!found) && (!blocked) && (rcount - 1 > -1)
                        && (fcount + 1 < 8)) {
                        if ((currentState[rcount][fcount] != 'x')
                            && (currentState[rcount][fcount] != c)) {
                            blocked = true;
                        }
                        if (currentState[rcount - 1][fcount + 1] == c) {
                            currentState[rankIndex][fileIndex] = c;
                            currentState[rcount - 1][fcount + 1] = 'x';
                            found = true;
                        }
                        rcount -= 1;
                        fcount += 1;
                    }
                }
            } else {
                if ((bishopDis - 'a') > fileIndex) {
                    boolean found = false;
                    boolean blocked = false;
                    while ((!found) && (!blocked) && (rcount + 1 < 8)
                        && (fcount + 1 < 8)) {
                        if ((currentState[rcount][fcount] != 'x')
                            && (currentState[rcount][fcount] != c)) {
                            blocked = true;
                        }
                        if (currentState[rcount + 1][fcount + 1] == c) {
                            currentState[rankIndex][fileIndex] = c;
                            currentState[rcount + 1][fcount + 1] = 'x';
                            found = true;
                        }
                        rcount += 1;
                        fcount += 1;
                    }
                    rcount = rankIndex;
                    fcount = fileIndex;
                    blocked = false;
                    while ((!found) && (!blocked) && (rcount - 1 > -1)
                        && (fcount + 1 < 8)) {
                        if ((currentState[rcount][fcount] != 'x')
                            && (currentState[rcount][fcount] != c)) {
                            blocked = true;
                        }
                        if (currentState[rcount - 1][fcount + 1] == c) {
                            currentState[rankIndex][fileIndex] = c;
                            currentState[rcount - 1][fcount + 1] = 'x';
                            found = true;
                        }
                        rcount -= 1;
                        fcount += 1;
                    }
                } else if ((bishopDis - 'a') < fileIndex) {
                    boolean found = false;
                    boolean blocked = false;
                    while ((!found) && (!blocked) && (rcount + 1 < 8)
                        && (fcount - 1 > -1)) {
                        if ((currentState[rcount][fcount] != 'x')
                            && (currentState[rcount][fcount] != c)) {
                            blocked = true;
                        }
                        if (currentState[rcount + 1][fcount - 1] == c) {
                            currentState[rankIndex][fileIndex] = c;
                            currentState[rcount + 1][fcount - 1] = 'x';
                            found = true;
                        }
                        rcount += 1;
                        fcount -= 1;
                    }
                    rcount = rankIndex;
                    fcount = fileIndex;
                    blocked = false;
                    while ((!found) && (!blocked) && (rcount - 1 > -1)
                        && (fcount - 1 > -1)) {
                        if ((currentState[rcount][fcount] != 'x')
                            && (currentState[rcount][fcount] != c)) {
                            blocked = true;
                        }
                        if (currentState[rcount - 1][fcount - 1] == c) {
                            currentState[rankIndex][fileIndex] = c;
                            currentState[rcount - 1][fcount - 1] = 'x';
                        }
                        rcount -= 1;
                        fcount -= 1;
                    }
                }
            }
        } else {
            boolean found = false;
            boolean blocked = false;
            while ((!found) && (!blocked) && (rcount + 1 < 8)
                && (fcount + 1 < 8)) {
                if ((currentState[rcount + 1][fcount + 1] != 'x')
                    && (currentState[rcount + 1][fcount + 1] != c)) {
                    blocked = true;
                }
                if (currentState[rcount + 1][fcount + 1] == c) {
                    currentState[rankIndex][fileIndex] = c;
                    currentState[rcount + 1][fcount + 1] = 'x';
                    found = true;
                }
                rcount += 1;
                fcount += 1;
            }
            rcount = rankIndex;
            fcount = fileIndex;
            blocked = false;
            while ((!found) && (!blocked) && (rcount - 1 > -1)
                && (fcount - 1 > -1)) {
                if ((currentState[rcount - 1][fcount - 1] != 'x')
                    && (currentState[rcount - 1][fcount - 1] != c)) {
                    blocked = true;
                }
                if (currentState[rcount - 1][fcount - 1] == c) {
                    currentState[rankIndex][fileIndex] = c;
                    currentState[rcount - 1][fcount - 1] = 'x';
                    found = true;
                }
                rcount -= 1;
                fcount -= 1;
            }
            rcount = rankIndex;
            fcount = fileIndex;
            blocked = false;
            while ((!found) && (!blocked) && (rcount + 1 < 8)
                && (fcount - 1 > -1)) {
                if ((currentState[rcount + 1][fcount - 1] != 'x')
                    && (currentState[rcount + 1][fcount - 1] != c)) {
                    blocked = true;
                }
                if (currentState[rcount + 1][fcount - 1] == c) {
                    currentState[rankIndex][fileIndex] = c;
                    currentState[rcount + 1][fcount - 1] = 'x';
                    found = true;
                }
                rcount += 1;
                fcount -= 1;
            }
            rcount = rankIndex;
            fcount = fileIndex;
            blocked = false;
            while ((!found) && (!blocked) && (rcount - 1 > -1)
                && (fcount + 1 < 8)) {
                if ((currentState[rcount - 1][fcount + 1] != 'x')
                    && (currentState[rcount - 1][fcount + 1] != c)) {
                    blocked = true;
                }
                if (currentState[rcount - 1][fcount + 1] == c) {
                    currentState[rankIndex][fileIndex] = c;
                    currentState[rcount - 1][fcount + 1] = 'x';
                }
                rcount -= 1;
                fcount += 1;
            }
        }
    }
    /*
    The pawnMove method takes in the current state of the board, the move, and
    a boolean indicating the player who has made the move. It returns nothing,
    but updates the current state of the board by the move that was passed in.
    pawnMove analyzes moves passed in by first identifying the player who made
    the move and thus assigning the corresponding character ('P' or 'p'). Then
    the method identifies whether the move is a capture move in order to
    correctly assign the character values of the rank and file, which will later
    be translated to index values on the currentState array. It also uses this
    information to determine that the pawn has moves from the diagonals instead
    of its regular forwards movement. Furthermore, two if-statements are created
    (when the move is a capture) in order to check where the piece could have
    made its move from. When the move is not a capture, for-loops are used to
    find the piece, which came from the same file. These if-statements will not
    execute and the for-loops will terminate once they reach the edge of the
    board, thus preventing any indexing errors. Finally, once the appropriate
    space on the board has been identified, the board is updated to show that
    the piece has moved to a new location.
    */
    public static void pawnMove(char[][] currentState, String move,
        char c) {
        if (move.contains("x")) {
            int captureIndex = move.indexOf('x');
            char fileMoved = move.charAt(captureIndex + 1);
            char rankMoved = move.charAt(captureIndex + 2);
            char pawnDis = move.charAt(captureIndex - 1);
            int fileIndex = fileMoved - 'a';
            int rankIndex = '8' - rankMoved;
            int disIndex = pawnDis - 'a';
            if (c == 'P') {
                if (fileIndex + 1 <= 7) {
                    if ((currentState[rankIndex + 1][fileIndex + 1] == c)
                        && ((fileIndex + 1) == disIndex)) {
                        currentState[rankIndex][fileIndex] = c;
                        currentState[rankIndex + 1][fileIndex + 1] = 'x';
                    }
                }
                if (fileIndex - 1 >= 0) {
                    if (currentState[rankIndex + 1][fileIndex - 1] == c
                        && ((fileIndex - 1) == disIndex)) {
                        currentState[rankIndex][fileIndex] = c;
                        currentState[rankIndex + 1][fileIndex - 1] = 'x';
                    }
                }
                if ((currentState[rankIndex + 1][fileIndex] == 'p')
                    && (rankIndex == 2)) {
                    currentState[rankIndex + 1][fileIndex] = 'x';
                }
            } else {
                if (fileIndex - 1 >= 0) {
                    if (currentState[rankIndex - 1][fileIndex - 1] == c
                        && ((fileIndex - 1) == disIndex)) {
                        currentState[rankIndex][fileIndex] = c;
                        currentState[rankIndex - 1][fileIndex - 1] = 'x';
                    }
                }
                if (fileIndex + 1 <= 7) {
                    if (currentState[rankIndex - 1][fileIndex + 1] == c
                        && ((fileIndex + 1) == disIndex)) {
                        currentState[rankIndex][fileIndex] = c;
                        currentState[rankIndex - 1][fileIndex + 1] = 'x';
                    }
                }
                if ((currentState[rankIndex - 1][fileIndex] == 'p')
                    && ((rankIndex - 1) == 5)) {
                    currentState[rankIndex - 1][fileIndex] = 'x';
                }
            }
            if (move.contains("=")) {
                int equalsIndex = move.indexOf("=");
                char promotion = move.charAt((equalsIndex + 1));
                currentState[rankIndex][fileIndex] = promotion;
            }
        } else {
            char fileMoved = move.charAt(0);
            char rankMoved = move.charAt(1);
            int fileIndex = fileMoved - 'a';
            int rankIndex = '8' - rankMoved;
            // loop checks 2 spaces behind the space where the new move
            // was made for a 'P'.
            if (c == 'P') {
                for (int i = rankIndex; i < 8; i++) {
                    if (currentState[i][fileIndex] == c) {
                        currentState[i][fileIndex] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                        i = 7;
                    }
                }
            } else {
                for (int i = rankIndex; i > 0; i--) {
                    if (currentState[i][fileIndex] == c) {
                        currentState[i][fileIndex] = 'x';
                        currentState[rankIndex][fileIndex] = c;
                        i = 1;
                    }
                }
            }
            if (move.contains("=")) {
                int equalsIndex = move.indexOf("=");
                char promotion = move.charAt((equalsIndex + 1));
                currentState[rankIndex][fileIndex] = promotion;
            }
        }
    }
    /*
    The queenMove method takes in the current state of the board, the move, and
    a boolean indicating the player who has made the move. It returns nothing,
    but updates the current state of the board by the move that was passed in.
    queenMove analyzes moves passed in by first identifying the player who made
    the move and thus assigning the corresponding character ('Q' or 'q'). Then
    the method identifies whether the move is a capture move in order to
    correctly assign the character values of the rank and file, which will later
    be translated to index values on the currentState array. Furthermore, four
    while-loops and four for-loops are created in order to check where the piece
    could have made its move from. The while-loops determine whether the piece
    has made its move from the diagonals, and the for-loops from the laterals.
    These while/for-loops terminate once they reach the edge of the board, thus
    preventing any indexing errors. Finally, once the appropriate space on the
    board has been identified, the board is updated to show that the piece has
    moved to a new location.
    */
    public static void queenMove(char[][] currentState, String move,
        char c, char queenDis) {
        rookMove(currentState, move, c, queenDis);
        bishopMove(currentState, move, c, queenDis);
    }
    /*
    The kingMove method takes in the current state of the board, the move, and
    a boolean indicating the player who has made the move. It returns nothing,
    but updates the current state of the board by the move that was passed in.
    kingMove analyzes moves passed in by first identifying the player who made
    the move and thus assigning the corresponding character ('K' or 'k'). Then
    the method identifies whether the move is a capture move in order to
    correctly assign the character values of the rank and file, which will later
    be translated to index values on the currentState array. Furthermore, eight
    if-statements are created in order to check where the piece could have made
    its move from. These if-statements terminate once they reach the edge of the
    board, thus preventing any indexing errors. Finally, once the appropriate
    space on the board has been identified, the board is updated to show that
    the piece has moved to a new location.
    */
    public static void kingMove(char[][] currentState, String move,
        char c, char kingDis) {
        char fileMoved = (move.contains("x")) ? (move.charAt(2))
            : (move.charAt(1));
        char rankMoved = (move.contains("x")) ? (move.charAt(3))
            : (move.charAt(2));
        int fileIndex = fileMoved - 'a';
        int rankIndex = '8' - rankMoved;
        if ((fileIndex + 1 < 8) && (rankIndex + 1 < 8)) {
            if (currentState[rankIndex + 1][fileIndex + 1] == c) {
                currentState[rankIndex + 1][fileIndex + 1] = 'x';
            }
        }
        if ((fileIndex + 1) < 8) {
            if (currentState[rankIndex][fileIndex + 1] == c) {
                currentState[rankIndex][fileIndex + 1] = 'x';
            }
        }
        if ((rankIndex + 1) < 8) {
            if (currentState[rankIndex + 1][fileIndex] == c) {
                currentState[rankIndex + 1][fileIndex] = 'x';
            }
        }
        if ((fileIndex - 1 > -1) && (rankIndex - 1 > -1)) {
            if (currentState[rankIndex - 1][fileIndex - 1] == c) {
                currentState[rankIndex - 1][fileIndex - 1] = 'x';
            }
        }
        if ((rankIndex - 1) > -1) {
            if (currentState[rankIndex - 1][fileIndex] == c) {
                currentState[rankIndex - 1][fileIndex] = 'x';
            }
        }
        if ((fileIndex - 1) > -1) {
            if (currentState[rankIndex][fileIndex - 1] == c) {
                currentState[rankIndex][fileIndex - 1] = 'x';
            }
        }
        if ((fileIndex + 1 < 8) && (rankIndex - 1 > -1)) {
            if (currentState[rankIndex - 1][fileIndex + 1] == c) {
                currentState[rankIndex - 1][fileIndex + 1] = 'x';
            }
        }
        if ((fileIndex - 1 > -1) && (rankIndex + 1 < 8)) {
            if (currentState[rankIndex + 1][fileIndex - 1] == c) {
                currentState[rankIndex + 1][fileIndex - 1] = 'x';
            }
        }
        currentState[rankIndex][fileIndex] = c;
    }
    /*
    The castlingMove method takes in the current state of the board, the move,
    and a boolean indicating the player who has made the move. It returns
    nothing, but updates the current state of the board by the move that was
    passed in. castlingMove is a special move that will automatically take the
    king's space on the board and replace it with the rook with which it has
    castled. The method checks if the move corresponds to a King's Side Castling
    or Queen's Side. After determining this, it determines which player made the
    move, and finally, updates the board to show that the pieces have moved to a
    new location.
    */
    public static void castlingMove(char[][] currentState, String move,
        boolean color) {
        if (move.equals("O-O")) {
            if (color) {
                // move King two spaces to the right
                currentState[7][4] = 'x';
                currentState[7][6] = 'K';
                // move King's Side Rook to the left of the King
                currentState[7][5] = 'R';
                currentState[7][7] = 'x';
            } else {
                // move King two spaces to the right
                currentState[0][4] = 'x';
                currentState[0][6] = 'k';
                // move King's Side Rook to the left of the King
                currentState[0][5] = 'r';
                currentState[0][7] = 'x';
            }
        } else {
            if (color) {
                // move King two spaces to the left
                currentState[7][4] = 'x';
                currentState[7][2] = 'K';
                // move King's Side Rook to the right of the King
                currentState[7][3] = 'R';
                currentState[7][0] = 'x';
            } else {
                // move King two spaces to the left
                currentState[0][4] = 'x';
                currentState[0][2] = 'k';
                // move King's Side Rook to the right of the King
                currentState[0][3] = 'r';
                currentState[0][0] = 'x';
            }
        }
    }
    /**
    * These next five methods take in a String representing a particular move
    * and return a boolean value, which confirms if the move was made by a
    * certain chess piece (rook, knight, bishop, queen, king). To check this,
    * the method uses the assumption that PGN-format specifies the first
    * character of a move as the piece that does that move by either an
    * uppercase or lowercase letter (except if it's a pawn, which has no
    * corresponding letter).
    */
    public static boolean isRook(String move) {
        if (move.charAt(0) == 'R') {
            return true;
        }
        return false;
    }
    public static boolean isKnight(String move) {
        if (move.charAt(0) == 'N') {
            return true;
        }
        return false;
    }
    public static boolean isBishop(String move) {
        if (move.charAt(0) == 'B') {
            return true;
        }
        return false;
    }
    public static boolean isQueen(String move) {
        if (move.charAt(0) == 'Q') {
            return true;
        }
        return false;
    }
    public static boolean isKing(String move) {
        if (move.charAt(0) == 'K') {
            return true;
        }
        return false;
    }
    /**
    * The infoExtract method takes in a String with the PGN-formatted game
    * information and returns a String[] (called "result") containing only the
    * moves made in the game. The method first finds the index where the moves
    * first appear in the String and splits it between the spaces. Second, to
    * get rid of the unnecessary elements (such as "1.") a initializer variables
    * and a for loop are created to iterate through the moves array (called
    * "sArray"). This loop iterates  through the entirety of the array, but only
    * adds the value of every second and third element of sArray to the returned
    * String[] result.
    */
    public static String[] infoExtract(String game) {
        int index = game.indexOf("1. ");
        String rawMoves = game.substring(index, game.length());
        String[] linesArray = rawMoves.split("\n");
        String resultString = "";
        for (String line : linesArray) {
            String[] movesArray = line.split(" ");
            for (String s2 : movesArray) {
                if (!s2.contains(".") && !s2.contains("0")
                    && (!s2.contains("1/2"))) {
                    resultString += " " + s2;
                }
            }
        }
        String[] resultArray = resultString.trim().split(" ");
        return resultArray;
    }
    /**
     * Reads the file named by path and returns its content as a String.
     *
     * @param path the relative or abolute path of the file to read
     * @return a String containing the content of the file
     */
    public static String fileContent(String path) {
        Path file = Paths.get(path);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Add the \n that's removed by readline()
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            System.exit(1);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String game = fileContent(args[0]);
        System.out.format("Event: %s%n", tagValue("Event", game));
        System.out.format("Site: %s%n", tagValue("Site", game));
        System.out.format("Date: %s%n", tagValue("Date", game));
        System.out.format("Round: %s%n", tagValue("Round", game));
        System.out.format("White: %s%n", tagValue("White", game));
        System.out.format("Black: %s%n", tagValue("Black", game));
        System.out.format("Result: %s%n", tagValue("Result", game));
        System.out.println("Final Position:");
        System.out.println(finalPosition(game));

    }
}
