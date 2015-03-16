import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
        testAI = new AI(Player.RED,2);
        AI.createGameTree(testState,2);



    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetMoves() throws Exception {
        testAI.minimax(testState);
        int count = 0;
        Boolean check = false;
        Move[] possMoves = testAI.getMoves(testBoard);
        for(Move m : possMoves){
            System.out.println(m.toString());
        }

        for(State child : testState.getChildren()){
            if(child.getValue()==testState.getValue()){
                count++;
                for(Move m : possMoves){
                    if(m.getColumn()==child.getLastMove().getColumn() && m.getPlayer()==child.getLastMove().getPlayer()){
                        check = true;
                    }
                }
            }

        }

        assertTrue(check);
        assertEquals(possMoves.length,count);
    }

    @Test
    public void testGetMovesFullBoard(){

        for(int i = 1;i<7;i++){
            for(int j=0;j<6;j++){
                testBoard.makeMove(new Move(fillBoardHelper(i,j),i));
            }
        }
        AI.createGameTree(testState,2);
        Move[] possMoves = testAI.getMoves(testBoard);
        testState.writeToFile();
        assertEquals(0,possMoves.length);

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
        writeToFile("testcreategametree", testState);
    }

    @Test
    public void testMinimax() throws Exception {
        testAI.minimax(testState);
        writeToFile("testminimax", testState);

    }



    public void writeToFile(String filename,State s) {
        try (PrintWriter writer = new PrintWriter(filename + ".txt", "UTF-8")) {
            writer.println(s);
            java.awt.Toolkit.getDefaultToolkit().beep();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}