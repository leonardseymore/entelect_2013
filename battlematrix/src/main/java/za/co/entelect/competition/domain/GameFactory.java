package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;
import za.co.entelect.competition.bots.DummyTank;
import za.co.entelect.competition.bots.RandomTank;

public class GameFactory {

  private static final Logger logger = Logger.getLogger(GameFactory.class);

  public static GameState smallBoard(long tickInterval) {
    GameState gameState = new GameState(100, 100, tickInterval);
    gameState.add(new Base(gameState.getW() / 2, 0, gameState, gameState.getPlayer1()));
    gameState.add(new Base(gameState.getW() / 2, gameState.getH() - 1, gameState, gameState.getPlayer2()));

    Tank p2t1 = new DummyTank("p2t1", 24, 12, gameState, gameState.getPlayer2(), Directed.Direction.UP);
    gameState.add(p2t1);

    Tank p2t2 = new RandomTank("p2t1", 50, 80, gameState, gameState.getPlayer2(), Directed.Direction.RIGHT);
    gameState.add(p2t2);

    for (int i = 1; i < gameState.getW() - 1; i++) {
      gameState.add(new Wall(i, 5, gameState));
      gameState.add(new Wall(i, 6, gameState));
      gameState.add(new Wall(i, 7, gameState));
      gameState.add(new Wall(i, gameState.getH() - 6, gameState));
      gameState.add(new Wall(i, gameState.getH() - 7, gameState));
      gameState.add(new Wall(i, gameState.getH() - 8, gameState));
    }

    return gameState;
  }
}
