package za.co.entelect.competition;

import org.apache.log4j.Logger;
import za.co.entelect.competition.bots.MouseControlledTank;
import za.co.entelect.competition.bots.movement.PathFinder;
import za.co.entelect.competition.domain.*;

import java.awt.*;
import java.util.Stack;

public class ClearanceMapVisitor implements GameElementVisitor {

  public static final Logger logger = Logger.getLogger(ClearanceMapVisitor.class);

  private boolean verbose = false;

  private Graphics2D g;
  private GameState gameState;

  public ClearanceMapVisitor(Graphics2D g, GameState gameState) {
    this.g = g;
    this.gameState = gameState;
  }

  @Override
  public void visit(GameState gameState) {
    if (verbose) {
      logger.debug("Visiting gameState [" + gameState + "]");
    }
    g.setColor(Constants.COLOR_SWING_BOARD);
    g.fillRect(0, 0, gameState.getW(), gameState.getH());

    GameState.MapNode[][] map = gameState.getMap();
    for (int y = 0; y < gameState.getH(); y++) {
      for (int x = 0; x < gameState.getW(); x++) {
        GameState.MapNode node = map[x][y];
        if (node.isClear()) {
          Entity entity = node.getEntity();
          if (entity == null) {
            g.setColor(Color.green);
          } else {
            g.setColor(Color.green.darker());
          }
        } else {
          g.setColor(Color.gray);
        }
        g.fillRect(x, y, 1, 1);
      }
    }
  }

  @Override
  public void visit(Base base) {
    if (verbose) {
      logger.debug("Visiting base [" + base + "]");
    }
  }

  @Override
  public void visit(Bullet bullet) {
    if (verbose) {
      logger.debug("Visiting bullet [" + bullet + "]");
    }
  }

  @Override
  public void visit(Tank tank) {
    if (verbose) {
      logger.debug("Visiting tank [" + tank + "]");
    }
    Color tankColor = Color.magenta;
    if (tank instanceof MouseControlledTank) {
      Stack<PathFinder.Node> path = ((MouseControlledTank)tank).getPath();
      if (path != null) {
        for (PathFinder.Node node : (Stack<PathFinder.Node>)path.clone()) {
          g.setColor(tankColor);
          g.fillRect(node.getX(), node.getY(), 1, 1);
        }
      }
    }
  }

  @Override
  public void visit(Wall wall) {
    if (verbose) {
      logger.debug("Visiting wall [" + wall + "]");
    }
  }
}
