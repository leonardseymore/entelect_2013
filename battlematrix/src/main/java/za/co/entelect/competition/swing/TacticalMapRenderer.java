package za.co.entelect.competition.swing;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.bots.tanks.ApproachTank;
import za.co.entelect.competition.bots.tanks.MouseControlledTank;
import za.co.entelect.competition.bots.pathfinding.PathFinder;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.domain.Rectangle;

import java.awt.*;
import java.util.Stack;

public class TacticalMapRenderer implements GameElementVisitor {

  public static final Logger logger = Logger.getLogger(TacticalMapRenderer.class);

  private boolean verbose = false;

  private Graphics2D g;
  private String selectedTank;

  public TacticalMapRenderer(Graphics2D g, String selectedTank) {
    this.g = g;
    this.selectedTank = selectedTank;
  }

  @Override
  public void visit(GameState gameState) {
    if (verbose) {
      logger.debug("Visiting gameState [" + gameState + "]");
    }
    g.setColor(Constants.COLOR_SWING_BOARD);
    g.fillRect(0, 0, gameState.getW(), gameState.getH());

    Tank tank = gameState.getTank(selectedTank);
    for (int y = 0; y < gameState.getH(); y++) {
      for (int x = 0; x < gameState.getW(); x++) {
        MapNode node = gameState.getMapNode(x, y);

        if (node.hasEntity()) {
          Entity entity = node.getEntity();
          if (entity != null) {
            g.setColor(Util.getColor(entity));
          } else {
            g.setColor(Color.green);
          }
        } else if (tank != null && gameState.canTankBeMovedTo(tank, x, y)) {
          g.setColor(Color.pink);
        } else {
          g.setColor(Color.darkGray);
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
    Color tankColor = Util.getColor(tank);
    if (tank instanceof MouseControlledTank) {
      Stack<PathFinder.Node> path = ((MouseControlledTank)tank).getPath();
      g.setColor(tankColor);
      drawPath(g, path);
    } else if (tank instanceof ApproachTank) {
      Stack<PathFinder.Node> path = ((ApproachTank)tank).getPath();
      g.setColor(tankColor);
      drawPath(g, path);
    }

    Tank focusTank = tank.getGameState().getTank(selectedTank);
    if (tank == focusTank) {
      g.setColor(Color.white);
      Rectangle rect = tank.getBoundingRect();
      g.drawRect(rect.getLeft(), rect.getTop(), tank.getW(), tank.getH());
    }
  }
  private void drawPath(Graphics2D g, Stack<PathFinder.Node> path) {
    if (path != null) {
      for (PathFinder.Node node : (Stack<PathFinder.Node>)path.clone()) {
        g.fillRect(node.getX(), node.getY(), 1, 1);
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
