

/**
 * Created by Andrew on 16/02/15.
 */
public class testCreateGameTree {

    public static void main(String[] args) {

        Board b = new Board();
        for(int i=0;i==6;i++) {
            b.makeMove(new Move((i%2!=0 ? Player.RED : Player.YELLOW),i));
        }

        State s = new State(Player.YELLOW,b,new Move(Player.RED,6));

        AI.createGameTree(s,2);
        AI.minimax(new AI(Player.YELLOW,2),s);
        s.writeToFile();


    }

}
