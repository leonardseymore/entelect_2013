package za.co.entelect.competition.swing;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.ai.tankoperator.PathAware;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.domain.Rectangle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Stack;

public class ClearanceMapRenderer implements GameElementVisitor {

  public static final Logger logger = Logger.getLogger(ClearanceMapRenderer.class);

  private boolean verbose = false;

  private Graphics2D g;
  private Tank selectedTank;
  private GameState gameState;

  public ClearanceMapRenderer() {
  }

  public void setG(Graphics2D g) {
    this.g = g;
  }

  @Override
  public void visit(GameState gameState) {
    Keyboard keyboard = Keyboard.getInstance();
    if (keyboard.keyDownOnce(KeyEvent.VK_0)) {
      selectedTank = null;
    }
    if (keyboard.keyDownOnce(KeyEvent.VK_1)) {
      selectedTank = (Tank)gameState.getEntity(Ids.Y1);
    }
    if (keyboard.keyDownOnce(KeyEvent.VK_2)) {
      selectedTank = (Tank)gameState.getEntity(Ids.Y2);
    }
    if (keyboard.keyDownOnce(KeyEvent.VK_3)) {
      selectedTank = (Tank)gameState.getEntity(Ids.O1);
    }
    if (keyboard.keyDownOnce(KeyEvent.VK_4)) {
      selectedTank = (Tank)gameState.getEntity(Ids.O2);
    }

    g.setColor(Constants.COLOR_SWING_BOARD);
    g.fillRect(0, 0, gameState.getW(), gameState.getH());

    for (int y = 0; y < gameState.getH(); y++) {
      for (int x = 0; x < gameState.getW(); x++) {
        GameElement e = gameState.getElementAt(x, y);
        if (e != null) {
          Entity entity = gameState.getEntityAt(x, y);
          if (entity != null) {
            g.setColor(Util.getColor(entity));
          } else {
            g.setColor(Constants.COLOR_SWING_WALL);
          }
        } else if (selectedTank != null && gameState.canTankBeMovedTo(selectedTank, x, y)) {
          if ((x + y) % 2 == 0) {
            g.setColor(Color.pink);
          } else {
            g.setColor(Color.pink.darker());
          }
        } else {
          g.setColor(Color.darkGray);
        }
        g.fillRect(x, y, 1, 1);
      }
    }
    this.gameState = gameState;
  }

  @Override
  public void visit(Walls walls) {
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
    if (tank.getTankOperator() instanceof PathAware) {
      Stack<PathFinder.Node> path = ((PathAware)tank.getTankOperator()).getPath();
      g.setColor(tankColor);
      drawPath(g, path);
    }

    if (tank == selectedTank) {
      g.setColor(Color.white);
      Rectangle rect = tank.getRect();
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
}
