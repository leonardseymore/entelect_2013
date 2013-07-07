package za.co.entelect.competition;

import org.apache.log4j.Logger;
import za.co.entelect.competition.bots.MouseControlledTank;
import za.co.entelect.competition.bots.movement.PathFinder;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.domain.GameState;

import java.awt.*;
import java.util.*;

public class GameElementSwingVisitor implements GameElementVisitor {

  public static final Logger logger = Logger.getLogger(GameElementSwingVisitor.class);

  private boolean verbose = false;

  private Graphics2D g;
  private GameState gameState;

  public GameElementSwingVisitor(Graphics2D g, GameState gameState) {
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
  }

  @Override
  public void visit(Base base) {
    if (verbose) {
      logger.debug("Visiting base [" + base + "]");
    }

    g.setColor(Constants.COLOR_SWING_BASE);
    g.fillRect(base.getX(), base.getY(), base.getW(), base.getH());
  }

  @Override
  public void visit(Bullet bullet) {
    if (verbose) {
      logger.debug("Visiting bullet [" + bullet + "]");
    }

    g.setColor(Constants.COLOR_SWING_BULLET);
    g.fillRect(bullet.getX(), bullet.getY(), bullet.getW(), bullet.getH());
  }

  @Override
  public void visit(Tank tank) {
    if (verbose) {
      logger.debug("Visiting tank [" + tank + "]");
    }

    Color tankColor = tank.getOwner() == gameState.getPlayer1() ? Constants.COLOR_SWING_TANK_PLAYER1 : Constants.COLOR_SWING_TANK_PLAYER2;
    g.setColor(tankColor);
    g.fillRect(tank.getX(), tank.getY(), tank.getW(), tank.getH());
    g.setColor(tankColor.darker());
    int [] turretPos = tank.turretPos();
    g.fillRect(turretPos[0], turretPos[1], 1, 1);

    if (tank instanceof MouseControlledTank) {
      Stack<PathFinder.Node> path = ((MouseControlledTank)tank).getPath();
      if (path != null) {
        for (PathFinder.Node node : path) {
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

    g.setColor(Constants.COLOR_SWING_WALL);
    g.fillRect(wall.getX(), wall.getY(), wall.getW(), wall.getH());
  }
}
