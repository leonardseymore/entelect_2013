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

public class DirichletDomainRenderer implements GameElementVisitor {

  public static final Logger logger = Logger.getLogger(DirichletDomainRenderer.class);

  private Graphics2D g;

  public DirichletDomainRenderer() {
  }

  public void setG(Graphics2D g) {
    this.g = g;
  }

  @Override
  public void visit(GameState gameState) {
    g.setColor(Constants.COLOR_SWING_BOARD);
    g.fillRect(0, 0, gameState.getW(), gameState.getH());

    DirichletDomains dirichletDomains = gameState.getDirichletDomains();
    for (int x = 0; x < gameState.getW(); x++) {
      for (int y = 0; y < gameState.getH(); y++) {
        Color color = Color.black;
        Base base = dirichletDomains.getBase(x, y);
        if (base != null) {
          color = Util.getColor(base);
        }
        g.setColor(color.darker());
        g.fillRect(x, y, 1, 1);
      }
    }
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
    int [] turretPos = tank.turretPos();
    g.fillRect(turretPos[0], turretPos[1], 1, 1);

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
