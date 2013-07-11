package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;
import za.co.entelect.competition.bots.ApproachTank;
import za.co.entelect.competition.bots.DummyTank;
import za.co.entelect.competition.bots.RandomTank;

import java.util.Random;

public class GameFactory {

  private static final Logger logger = Logger.getLogger(GameFactory.class);

  public static GameState smallBoard(long tickInterval) {
    GameState gameState = new GameState(100, 100, tickInterval);
    gameState.add(new Base(gameState.getW() / 2, 0, gameState, gameState.getPlayer1()));
    gameState.add(new Base(gameState.getW() / 2, gameState.getH() - 1, gameState, gameState.getPlayer2()));

    Tank p2t2 = new DummyTank("p2t1", 50, 75, gameState, gameState.getPlayer2(), Directed.Direction.RIGHT);
    gameState.add(p2t2);

    for (int i = 1; i < gameState.getW() - 1; i++) {
      gameState.add(new Wall(i, 5, gameState));
      gameState.add(new Wall(i, 6, gameState));
      gameState.add(new Wall(i, 7, gameState));
      gameState.add(new Wall(i, gameState.getH() - 6, gameState));
      gameState.add(new Wall(i, gameState.getH() - 7, gameState));
      gameState.add(new Wall(i, gameState.getH() - 8, gameState));

      gameState.add(new Wall(i, 15, gameState));
      gameState.add(new Wall(i, 16, gameState));
      gameState.add(new Wall(i, 17, gameState));
      gameState.add(new Wall(i, gameState.getH() - 16, gameState));
      gameState.add(new Wall(i, gameState.getH() - 17, gameState));
      gameState.add(new Wall(i, gameState.getH() - 18, gameState));
    }

    for (int i = 1; i < gameState.getH() - 1; i++) {
      gameState.add(new Wall(15, i, gameState));
      gameState.add(new Wall(16, i, gameState));
      gameState.add(new Wall(17, i, gameState));

      gameState.add(new Wall(gameState.getW() - 15, i, gameState));
      gameState.add(new Wall(gameState.getW() - 16, i, gameState));
      gameState.add(new Wall(gameState.getW() - 17, i, gameState));
    }

    return gameState;
  }

  public static GameState smallRandomBoard(long tickInterval, int odds) {
    GameState gameState = new GameState(100, 100, tickInterval);
    gameState.add(new Base(gameState.getW() / 2, 0, gameState, gameState.getPlayer1()));
    gameState.add(new Base(gameState.getW() / 2, gameState.getH() - 1, gameState, gameState.getPlayer2()));

    Tank p2t2 = new RandomTank("p2t1", 50, 80, gameState, gameState.getPlayer2(), Directed.Direction.RIGHT);
    gameState.add(p2t2);

    Random random = new Random();
    for (int i = 1; i < gameState.getW(); i++) {
      for (int j = 0; j < gameState.getH(); j++) {
        //if (gameState.getEntityAt(i, j) == null) {
          if (random.nextInt(odds) == 1) {
            gameState.add(new Wall(i, j, gameState));
          }
        //}
      }
    }

    return gameState;
  }
}
