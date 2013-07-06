package za.co.entelect.competition;

import za.co.entelect.competition.domain.GameFactory;
import za.co.entelect.competition.domain.GameState;

public class AppSwing {
  public static void main(String [] args) throws Exception {
    final GameState gameState = GameFactory.smallBoard(100);
    MainFrame app = new MainFrame(gameState, 20);
    app.setVisible(true);
    gameState.start();
    app.run();
  }
}
