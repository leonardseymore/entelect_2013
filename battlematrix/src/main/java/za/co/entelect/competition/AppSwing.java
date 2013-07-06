package za.co.entelect.competition;

import za.co.entelect.competition.domain.GameFactory;
import za.co.entelect.competition.domain.GameState;

import javax.swing.*;

public class AppSwing {
  public static void main(String [] args) throws Exception {
    final GameState gameState = GameFactory.smallBoard(100);
//    SwingUtilities.invokeLater(new Runnable() {
//      public void run() {
//        new MainFrame(gameState).setVisible(true);
//      }
//    });

    gameState.start();
    MainFrame app = new MainFrame(gameState);
    app.setVisible(true);
    app.run();
  }
}
