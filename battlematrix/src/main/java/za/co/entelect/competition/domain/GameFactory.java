package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;
import za.co.entelect.competition.DummyTank;
import za.co.entelect.competition.RandomTank;

public class GameFactory {

  private static final Logger logger = Logger.getLogger(GameFactory.class);

  public static GameState smallBoard(long tickInterval) {
    GameState gameState = new GameState(20, 20, tickInterval);
    gameState.add(new Base(10, 0, gameState, gameState.getPlayer1()));
    gameState.add(new Base(10, 19, gameState, gameState.getPlayer2()));

    Tank p2t1 = new DummyTank(14, 14, gameState, gameState.getPlayer2(), Directed.Direction.UP);
    gameState.add(p2t1);

    Tank p2t2 = new RandomTank(5, 13, gameState, gameState.getPlayer2(), Directed.Direction.RIGHT);
    gameState.add(p2t2);

    gameState.add(new Wall(1, 1, gameState));
    return gameState;
  }

  public static GameState mediumBoard(long tickInterval) {
    Player player1 = new Player("1");
    Player player2 = new Player("2");

    GameState gameState = new GameState(200, 200, tickInterval);
    gameState.add(new Base(100, 0, gameState, player1));
    gameState.add(new Base(100, 199, gameState, player2));
    Tank p1t1 = new DummyTank(150, 120, gameState, player1, Directed.Direction.RIGHT);
    gameState.add(p1t1);
    p1t1.fire();

    Tank p2t1 = new DummyTank(140, 140, gameState, player2, Directed.Direction.UP);
    gameState.add(p2t1);
    p2t1.fire();
    gameState.add(new Wall(10, 10, gameState));
    return gameState;
  }
}
