import java.util.Vector;

/**
 *
 * @author kai
 */
public class Position {
    
    // All possible moves to adjacent fields
    public final static int[][] moves = {{1,7}, {0,2,9}, {1,3}, {2,4,11}, {3,5}, {4,6,13}, {5,7}, {6,0,15}, 
        {9,15}, {8,10,1,17}, {9,11}, {10,12,3,19}, {11,13}, {12,14,5,21}, {13,15}, {14,8,7,23}, 
        {23,17}, {16,18,9}, {17,19}, {18,20,11}, {19,21}, {20,22,13}, {21,23}, {22,16,15}};
    
    // All posible mills on the board
    public final static int[][] mills = {{0,1,2}, {2,3,4}, {4,5,6}, {6,7,0}, {8,9,10}, {10,11,12}, {12,13,14}, {14,15,8}, {16,17,18}, 
		{18,19,20}, {20,21,22}, {22,23,16}, {1,9,17}, {3,11,19}, {5,13,21}, {7,15,23}};
    
    // There are always 2 possible mills for each position
    public static int[][][] mills2 = null;
        //{{{1,2},{7, 6}}, {{0,2},{9,17}}, {{0,1},{3,4}}, {{2,4},{19,11}}, {{2,3},{5,6}}, {{4,6},{21,13}}, {{4,5},{0,7}}, {{0,6},{15,23}},
        //{{9,10},{14,15}}, {{8,10},{1,17}}, {{8,9},{11,12}}, {{10,12},{3,19}}, {{10,11},{13,14}}, {{12,14},{5,21}}, {{12,13},{8,15}}, {{8,14},{7,23}},
        //{{17,18},{22,23}}, {{16,18},{1,9}}, {{16,17},{19,20}}, {{18,20},{3,11}}, {{18,19},{21,22}}, {{20,22},{13,21}}, {{20,21},{16,23}}, {{16,22},{7,15}}};

    static {
        mills2 = new int[24][2][2];
        for(int h = 0; h < 24; h++) {
            int m = 0;
            for(int i = 0; i < mills.length; i++) {
                for(int j = 0; j < mills[i].length; j++) {
                    if(mills[i][j] == h) {
                        mills2[h][m][0] = mills[i][(j + 1) % 3];
                        mills2[h][m][1] = mills[i][(j + 2) % 3];
                        m++;
                    }
                }
            }
        }
    }

    public byte[] board = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public byte whiteDeck = 9;
    public byte blackDeck = 9;
    public short turns = 1;

    public boolean whitesTurn() {
        return turns % 2 == 1;
    }
    
    public boolean blacksTurn() {
        return !whitesTurn();
    }
    
    public int myColor() {
        return whitesTurn() ? 1 : -1;
    }
    
    public int otherColor() {
        return whitesTurn() ? -11 : 1;
    }
    
    public boolean wouldCreateMill(int from, int to, int color) {
        // Check if the other two fields are of the same color
        if(color == board[mills2[to][0][0]] && board[mills2[to][0][0]] == board[mills2[to][0][1]]) {
            // Return true if a new piece was put on the board or if the piece was not moved from one of the other positions
            if(from < 0 || from != mills2[to][0][0] && from != mills2[to][0][1])
                return true;
        }
        // Do the same test with the other possible mill
        if(color == board[mills2[to][1][0]] && board[mills2[to][1][0]] == board[mills2[to][1][1]]) {
            if(from < 0 || from != mills2[to][1][0] && from != mills2[to][1][1])
                return true;
        }
        return false;
    }

    public void doTurn(Turn t) {
        // Check validity of the turn
        int otherColor = otherColor();
        int myColor = myColor();
        if(t.white != whitesTurn())
            throw new RuntimeException("It is not " + (t.white ? "whites" : "blacks") + " turn!");
        if(board[t.to] != 0)
            throw new RuntimeException("Field " + t.to + " occupied!");
        // If a mill is created we to check some special rules
        if(wouldCreateMill(t.from, t.to, myColor)) {
            // Check if all other pieces are in a mill
            boolean canRemove = false;
            for(int i = 0; i < 24; i++) {
                if(board[i] == otherColor && !wouldCreateMill(-1, i, otherColor)) {
                    canRemove = true;
                    break;
                }
            }
            // If we can take a piece off the board we need to make sure the player takes the right one
            if(canRemove) {
                if(t.remove < 0)
                    throw new RuntimeException("You have to remove a piece from the board!");
                if(board[t.remove] == 0)
                    throw new RuntimeException("There is no piece on this postion to remove!");
                if(board[t.remove] != otherColor)
                    throw new RuntimeException("You have to remove the other ones piece!");
                if(wouldCreateMill(-1, t.remove, otherColor))
                    throw new RuntimeException("You can't take a piece from a mill!");
            } else {
                if(t.remove >= 0)
                    throw new RuntimeException("All the others pieces are part of a mill and can't be removed!");
            }
        } else if(t.remove >= 0) {
            throw new RuntimeException("You have create a mill to remove a piece from the board!");
        }
        if(t.from == -1) { // Put a new piece on the board
            if(t.white && whiteDeck < 1)
                throw new RuntimeException("Whites deck is empty!");
            if(!t.white && blackDeck < 1)
                throw new RuntimeException("Blacks deck is empty!");
            // Now do the turn
            if(t.white) {
                board[t.to] = 1;
                whiteDeck--;
            } else {
                board[t.to] = -1;
                blackDeck--;
            }
        } else { // Move a piece
            if(t.white && whiteDeck > 0)
                throw new RuntimeException("White has still pieces on the deck!");
            if(!t.white && blackDeck > 0)
                throw new RuntimeException("Black has still pieces on the deck!");
            if(board[t.from] != myColor)
                throw new RuntimeException("This is not " + (t.white ? "whites" : "blacks") + " piece!");
            boolean isAdjacent = false;
            for(int i = 0; i < moves[t.from].length; i++) {
                if(moves[t.from][i] == t.to)
                    isAdjacent = true;
            }
            if(!isAdjacent)
                throw new RuntimeException("The fields are not adjacent!");
            // Now do the turn
            board[t.to] = board[t.from];
            board[t.from] = 0;
        }
        if(t.remove >= 0)
            board[t.remove] = 0;
        turns++;
    }

    public Turn[] genTurnList() {
        Vector turns = new Vector();
        turns.add(new Turn(true, 0));
        turns.add(new Turn(false, 3));
        return (Turn[]) turns.toArray(new Turn[0]);
    }

}
