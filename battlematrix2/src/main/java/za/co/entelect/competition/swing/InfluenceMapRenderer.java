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

public class InfluenceMapRenderer implements GameElementVisitor {

  public static final Logger logger = Logger.getLogger(InfluenceMapRenderer.class);

  private Graphics2D g;

  private enum InfluenceMapType {
    COMBINED, YINFLUENCE, OINFLUENCE, INFLUENCEY, INFLUENCEO
  }

  private InfluenceMapType mapType = InfluenceMapType.COMBINED;

  public InfluenceMapRenderer() {
  }

  public void setG(Graphics2D g) {
    this.g = g;
  }

  @Override
  public void visit(GameState gameState) {
    Keyboard keyboard = Keyboard.getInstance();
    if (keyboard.keyDownOnce(KeyEvent.VK_0)) {
      mapType = InfluenceMapType.COMBINED;
    }
    if (keyboard.keyDownOnce(KeyEvent.VK_1)) {
      mapType = InfluenceMapType.YINFLUENCE;
    }
    if (keyboard.keyDownOnce(KeyEvent.VK_2)) {
      mapType = InfluenceMapType.OINFLUENCE;
    }
    if (keyboard.keyDownOnce(KeyEvent.VK_3)) {
      mapType = InfluenceMapType.INFLUENCEY;
    }
    if (keyboard.keyDownOnce(KeyEvent.VK_4)) {
      mapType = InfluenceMapType.INFLUENCEO;
    }

    g.setColor(Constants.COLOR_SWING_BOARD);
    g.fillRect(0, 0, gameState.getW(), gameState.getH());

    InfluenceMap imap = gameState.getInfluenceMap();
    float[][] yInfluenceMap = imap.getyInfluenceMap();
    float[][] oInfluenceMap = imap.getoInfluenceMap();
    int[][] frontLine = imap.getFrontLine();
    for (int x = 0; x < gameState.getW(); x++) {
      for (int y = 0; y < gameState.getH(); y++) {
        Color color = Color.black;
        float[][] influenceMap;
        float val;
        switch (mapType) {
          case YINFLUENCE:
            influenceMap = imap.getyInfluenceMap();
            val = influenceMap[x][y];
            color = Color.getHSBColor(0.66f, 1, Math.min(1f, Math.abs(val)));
            break;
          case OINFLUENCE:
            influenceMap = imap.getoInfluenceMap();
            val = influenceMap[x][y];
            color = Color.getHSBColor(1f, 1, Math.min(1f, Math.abs(val)));
            break;
          case INFLUENCEY:
            influenceMap = imap.getInfluenceYMap();
            val = influenceMap[x][y];
            if (val > 0) {
              color = Color.getHSBColor(0.66f, 1, Math.min(1f, val));
            } else if (val < 0) {
              color = Color.getHSBColor(1f, 1, Math.min(1f, Math.abs(val)));
            }
            break;
          case INFLUENCEO:
            influenceMap = imap.getInfluenceOMap();
            val = influenceMap[x][y];
            if (val > 0) {
              color = Color.getHSBColor(0.66f, 1, Math.min(1f, val));
            } else if (val < 0) {
              color = Color.getHSBColor(1f, 1, Math.min(1f, Math.abs(val)));
            }
            break;
          default:
            color = new Color(Math.min(1f, oInfluenceMap[x][y]), 0, Math.min(1f, yInfluenceMap[x][y]));
            break;
        }
        if (frontLine[x][y] == 1) {
          color = Color.CYAN;
        }
        g.setColor(color);
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
        //  g.fillRect(i, j, 1, 1);
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
      Stack<PathFinder.Node> path = ((PathAware)tank.getTankOperator()).getPath();
      g.setColor(tankColor);
      drawPath(g, path);
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
