package za.co.entelect.competition;

import org.apache.log4j.Logger;
import za.co.entelect.competition.domain.GameFactory;
import za.co.entelect.competition.domain.GameState;

import java.io.FileWriter;
import java.io.IOException;

public class App {

  private static final Logger logger = Logger.getLogger(App.class);

  public static void main(String[] args) {
    GameState gameState = GameFactory.smallBoard();
    logger.info("\n\n" + gameState.toAscii());

    try (FileWriter fw = new FileWriter("/tmp/base.ppm")) {
      fw.write(gameState.toPpm());
    } catch (IOException ex) {
      logger.error("Failed writing file", ex);
    }
  }
}
