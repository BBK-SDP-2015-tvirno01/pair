import static org.junit.Assert.*;
import org.junit.*;

public class BoardTest {
    Board testBoard;

    @org.junit.Before
    public void setUp() throws Exception {
        testBoard = new Board();
        for(int i=0;i<6;i++) {
            testBoard.makeMove(new Move((i % 2 != 0 ? Player.YELLOW : Player.RED), 3));
        }


    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testMakeMove() throws Exception {
        for(int i = 0;i<6;i++){
            assert(testBoard.getTile(5-i,3) == (i%2!=0 ? Player.YELLOW : Player.RED));
        }
    }
    @Test(expected = IllegalArgumentException.class)
    public void testMakeMoveThrowsFullColumnException(){
        testBoard.makeMove(new Move(Player.RED,3));
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
        
    }
}