package za.co.entelect.competition.swing;

import org.codehaus.groovy.tools.shell.IO;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.groovy.Console;
import za.co.entelect.competition.groovy.GameFactory;

public class AppSwing {
  public static void main(String [] args) throws Exception {
    GameState gameState = GameFactory.fromFile(args[0]);
    GUI app = new GUI(gameState, 5);
    app.setVisible(true);
    Thread gameThread = new Thread(app);
    gameThread.start();
    //Console console = new Console(new IO(System.in, System.out, System.err), gameState);
  }
}
