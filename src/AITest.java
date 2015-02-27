import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AITest {
    Board testBoard;
    State testState;
    AI testAI;

    @Before
    public void setUp() throws Exception {
        testBoard = new Board();
        testState = new State(Player.RED, testBoard, null);
        for (int i = 0; i < 6; i++) {
            testBoard.makeMove(new Move((i % 2 != 0 ? Player.YELLOW : Player.RED), 0));
        }
        testAI = new AI(Player.YELLOW,2);
        testAI.createGameTree(testState,2);
        testAI.minimax(testState);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetMoves() throws Exception {
        int count = 0;
        Move[] possMoves = testAI.getMoves(testBoard);
        List<Move> moveList = new ArrayList<>(Arrays.asList(possMoves));
        for(State child : testState.getChildren()) {
            if(child.getValue()==testState.getValue()){
                assertTrue(moveList.contains(child.getLastMove()));
                count ++;
            }else {
                assertFalse(moveList.contains(child.getLastMove()));
            }

        }
        assertEquals(possMoves.length, count);
    }

    @Test
    public void testGetMovesFullBoard(){
        for(int i = 1;i<7;i++){
            for(int j=0;j<6;j++){
                testBoard.makeMove(new Move(fillBoardHelper(i,j),i));
            }
        }
        testState = new State(Player.RED, testBoard, null);
        testAI.createGameTree(testState,2);
        testAI.minimax(testState);

        assertNull(testAI.getMoves(testBoard));


    }

    // helper function to fill the board to create a tie game
    private Player fillBoardHelper(int i, int j){
        if(j%2==0)
        { if(i%3==0){
            return Player.RED;
        }else{
            return Player.YELLOW;
        }}else{
            if(i%3==0){
                return Player.YELLOW;
            }else{
                return Player.RED;
            }
        }
    }

    @Test
    public void testCreateGameTree() throws Exception {

    }

    @Test
    public void testMinimax() throws Exception {
        
    }



}