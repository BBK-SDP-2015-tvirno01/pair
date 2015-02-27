import static org.junit.Assert.*;
import org.junit.*;

public class BoardTest {
    Board testBoard;

    @org.junit.Before
    public void setUp() throws Exception {
        testBoard = new Board();
        for(int i=0;i<6;i++) {
            testBoard.makeMove(new Move((i % 2 != 0 ? Player.YELLOW : Player.RED), 0));
        }
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testMakeMove() throws Exception {
        for(int i = 0;i<6;i++){
            assert(testBoard.getTile(5-i,0) == (i%2!=0 ? Player.YELLOW : Player.RED));
        }
    }
    @Test(expected = IllegalArgumentException.class)
    public void testMakeMoveThrowsFullColumnException(){
        testBoard.makeMove(new Move(Player.RED,0));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testMakeMoveThrowsColumnOutOfRangeException(){
        testBoard.makeMove(new Move(Player.RED,7));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testMakeMoveThrowsNullPlayerException(){
        testBoard.makeMove(new Move(null,7));
    }

    @org.junit.Test
    public void testGetPossibleMoves() throws Exception {
        Move[] testMoves = testBoard.getPossibleMoves(Player.RED);

        for(int i = 0;i<5;i++){
            assert(testMoves[i].getPlayer() == Player.RED);
            assert(testMoves[i].getColumn() == i+1);
            assert(testMoves.length == 6);
        }
        for(int j=0;j<4;j++) testBoard.makeMove(new Move(Player.YELLOW,1));
        Move[] output = testBoard.getPossibleMoves(Player.RED);
        Move[] expected = {};
        assertArrayEquals(expected,output);
    }

    @Test
    public void testGetPossibleMovesCompletelyFilledBoard(){

        for(int i = 1;i<7;i++){
            for(int j=0;j<6;j++){
                testBoard.makeMove(new Move(fillBoardHelper(i,j),i));
            }
        }

        Move[] output = testBoard.getPossibleMoves(Player.RED);
        Move[] expected = {};
        assertArrayEquals(expected,output);

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
}