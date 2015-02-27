
import java.util.ArrayList;
import java.util.List;

/**
 * An instance represents a Solver that intelligently determines
 * Moves using the Minimax algorithm.
 */
public class AI implements Solver {

    private Player player; // the current player

    /**
     * The depth of the search in the game space when evaluating moves.
     */
    private int depth;

    /**
     * Constructor: an instance with player p who searches to depth d
     * when searching the game space for moves.
     */
    public AI(Player p, int d) {
        player = p;
        depth = d;
    }

    /**
     * Return this Solver's preferred Moves. If this Solver prefers one
     * Move above all others, return an array of length 1. Larger arrays
     * indicate equally preferred Moves.
     * An array of size 0 indicates that there are no possible moves.
     * Precondition: b is not null.
     */
    @Override
    public Move[] getMoves(Board b) {
        List<Move> mvList = new ArrayList<>();
        State sRoot = new State(player,b,null);
        createGameTree(sRoot,depth);
        minimax(sRoot);

        for(State s : sRoot.getChildren()){
            if(s.getValue()==sRoot.getValue()){
                mvList.add(s.getLastMove());
            }
        }

        Move[] result = {};

        return mvList.toArray(result);

    }

    /**
     * Generate the game tree with root s of depth d.
     * The game tree's nodes are State objects that represent the state of a game
     * and whose children are all possible States that can result from the next move.
     * <p/>
     * NOTE: this method runs in exponential time with respect to d.
     * With d around 5 or 6, it is extremely slow and will start to take a very
     * long time to run.
     * <p/>
     * Note: If s has a winner (four in a row), it should be a leaf.
     */
    public static void createGameTree(State s, int d) {
        if(d > 1){
            s.initializeChildren();
            for(State child : s.getChildren()){
                createGameTree(child,d-1);
            }

        }
    }

    /**
     * Call minimax in ai with state s.
     */
    public static void minimax(AI ai, State s) {
        ai.minimax(s);
    }

    /**
     * State s is a node of a game tree (i.e. the current State of the game).
     * Use the Minimax algorithm to assign a numerical value to each State of the
     * tree rooted at s, indicating how desirable that java.State is to this player.
     */
    public void minimax(State s){
        Boolean sPlayer = s.getPlayer() == player;

        if(s.getChildren().length == 0) {     //leaf node
            s.setValue(evaluateBoard(s.getBoard()));
        } else {
            for(State child : s.getChildren()){
                minimax(child);               //stay in same AI instance
            }
            if(sPlayer){
                s.setValue(getMaxValue(s.getChildren()));
            } else {
                s.setValue(getMinValue(s.getChildren()));
            }
        }
    }

    private int getMaxValue(State[] children){
        int result = Integer.MIN_VALUE;
        for(State s: children){
            result = (s.getValue() > result ? s.getValue(): result);
        }
        return result;
    }

    private int getMinValue(State[] children){
        int result = Integer.MAX_VALUE;
        for(State s: children){
            result = (s.getValue() < result ? s.getValue(): result);
        }
        return result;
    }
    /**
     * Evaluate the desirability of Board b for this player
     * Precondition: b is a leaf node of the game tree (because that is most
     * effective when looking several moves into the future).
     */
    public int evaluateBoard(Board b) {
        Player winner = b.hasConnectFour();
        int value = 0;
        if (winner == null) {
            // Store in sum the value of board b. 
            List<Player[]> locs = b.winLocations();
            for (Player[] loc : locs) {
                for (Player p : loc) {
                    value += (p == player ? 1 : p != null ? -1 : 0);
                }
            }
        } else {
            // There is a winner
            int numEmpty = 0;
            for (int r = 0; r < Board.NUM_ROWS; r = r + 1) {
                for (int c = 0; c < Board.NUM_COLS; c = c + 1) {
                    if (b.getTile(r, c) == null) numEmpty += 1;
                }
            }
            value = (winner == player ? 1 : -1) * 10000 * numEmpty;
        }
        return value;
    }
}
