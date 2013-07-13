package za.co.entelect.competition.swing;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.groovy.GameFactory;

public class AppSwing {
  public static void main(String [] args) throws Exception {
    //final GameState gameState = GameFactory.smallRandomBoard(50);
    //final GameState gameState = GameFactory.smallBoard();
    GameState gameState = GameFactory.fromFile("/map1.groovy");
    GUI app = new GUI(gameState, 5);
    //KeyboardControlledTank p1t1 = new KeyboardControlledTank("p1t1", 60, 20, gameState, gameState.getYou(), Directed.Direction.LEFT);
    //gameState.add(p1t1);

    //ApproachTank p2t1 = new ApproachTank("p2t2", 40, 10, gameState, gameState.getOpponent(), Directed.Direction.UP);
    //p2t1.setFollowTank(p1t1);
    //DummyTank p2t1 = new DummyTank("p2t1", 40, 10, gameState, gameState.getOpponent(), Directed.Direction.UP);
    //gameState.add(p2t1);

   // MouseControlledTank p1t2 = new MouseControlledTank("p1t2", 40, 60, gameState, gameState.getYou(), Directed.Direction.DOWN);
   // gameState.add(p1t2);
    app.setVisible(true);
    app.run();
  }
}
