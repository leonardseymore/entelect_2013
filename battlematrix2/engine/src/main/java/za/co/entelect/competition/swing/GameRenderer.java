package za.co.entelect.competition.swing;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.tankoperator.PathAware;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.domain.Point;
import za.co.entelect.competition.domain.Rectangle;

import java.awt.*;
import java.util.Stack;

public class GameRenderer implements GameElementVisitor {

  public static final Logger logger = Logger.getLogger(GameRenderer.class);

  private boolean verbose = false;

  private Graphics2D g;

  public GameRenderer() {
  }

  public void setG(Graphics2D g) {
    this.g = g;
  }

  @Override
  public void visit(GameState gameState) {
    g.setColor(Constants.COLOR_SWING_BOARD);
    g.fillRect(0, 0, gameState.getW(), gameState.getH());
  }

  @Override
  public void visit(Walls walls) {
    g.setColor(Constants.COLOR_SWING_WALL);
    for (int j = 0; j < walls.getH(); j++) {
      for (int i = 0; i < walls.getW(); i++) {
        if (walls.hasWall(i, j)) {
          g.fillRect(i, j, 1, 1);
        }
      }
    }
  }

  @Override
  public void visit(Base base) {
    g.setColor(Util.getColor(base));
    g.fillRect(base.getX(), base.getY(), base.getW(), base.getH());
  }

  @Override
  public void visit(Bullet bullet) {
    g.setColor(Constants.COLOR_SWING_BULLET);
    g.fillRect(bullet.getX(), bullet.getY(), bullet.getW(), bullet.getH());
  }

  @Override
  public void visit(Tank tank) {
    Rectangle rect = tank.getRect();
    Color tankColor = Util.getColor(tank);
    g.setColor(tankColor);
    g.fillRect(rect.getLeft(), rect.getTop(), tank.getW(), tank.getH());
    g.setColor(tankColor.darker());
    Point turretPos = tank.getTurretPos();
    g.fillRect(turretPos.getX(), turretPos.getY(), 1, 1);

    if (tank.getTankOperator() instanceof PathAware) {
      Stack<Trackable> path = ((PathAware)tank.getTankOperator()).getPath();
      g.setColor(tankColor);
      drawPath(g, path);
    }
  }

  private void drawPath(Graphics2D g, Stack<Trackable> path) {
    if (path != null) {
      for (Trackable node : (Stack<Trackable>)path.clone()) {
        g.fillRect(node.getX(), node.getY(), 1, 1);
      }
    }
  }
}
