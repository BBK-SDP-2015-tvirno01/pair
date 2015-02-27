import org.junit.Test;

import static org.junit.Assert.*;

public class StateTest {
    Board testBoard;
    State testState;
    Board dupBoard;

    @org.junit.Before
    public void setUp() throws Exception {
        testBoard = new Board();
        testState = new State(Player.RED, testBoard, null);
        for (int i = 0; i < 6; i++) {
            testBoard.makeMove(new Move((i % 2 != 0 ? Player.YELLOW : Player.RED), 0));
        }
    }

    @Test
    public void testInitializeChildren() throws Exception {
        testState.initializeChildren();
        int count = 0;
        for (State child : testState.getChildren()) {
            assert (child.getLastMove().getPlayer() == Player.RED);
            assertEquals(child.getLastMove().getColumn(), count + 1);
            for (int j = 0; j < 6; j++) {
                assert (child.getBoard().getTile(6 - j, 0) == (j % 2 != 0 ? Player.YELLOW : Player.RED));
                assertNull (child.getChildren());
            }
            count++;
        }
        assertEquals(6, count);
    }
}