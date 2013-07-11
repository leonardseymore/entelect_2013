package za.co.entelect.competition.swing;

import za.co.entelect.competition.bots.ApproachTank;
import za.co.entelect.competition.bots.KeyboardControlledTank;
import za.co.entelect.competition.bots.MouseControlledTank;
import za.co.entelect.competition.domain.Directed;
import za.co.entelect.competition.domain.GameFactory;
import za.co.entelect.competition.domain.GameState;

public class AppSwing {
  public static void main(String [] args) throws Exception {
    final GameState gameState = GameFactory.smallBoard(50);
    GUI app = new GUI(gameState, 6);
    KeyboardControlledTank p1t1 = new KeyboardControlledTank("p1t1", 60, 20, gameState, gameState.getPlayer1(), Directed.Direction.LEFT);
    gameState.add(p1t1);

    ApproachTank p2t1 = new ApproachTank("p2t1", 40, 10, gameState, gameState.getPlayer2(), Directed.Direction.UP);
    p2t1.setFollowTank(p1t1);
    //DummyTank p2t1 = new DummyTank("p2t1", 40, 10, gameState, gameState.getPlayer2(), Directed.Direction.UP);
    gameState.add(p2t1);

    MouseControlledTank p1t2 = new MouseControlledTank("p1t2", 40, 60, gameState, gameState.getPlayer1(), Directed.Direction.DOWN);
    gameState.add(p1t2);
    app.setVisible(true);
    gameState.start();
    app.run();
  }
}
