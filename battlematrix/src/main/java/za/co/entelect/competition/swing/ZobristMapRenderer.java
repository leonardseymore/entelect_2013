package za.co.entelect.competition.swing;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.ai.tankoperator.PathAware;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.domain.Rectangle;

import java.awt.*;
import java.util.Stack;

public class ZobristMapRenderer implements GameElementVisitor {

  public static final Logger logger = Logger.getLogger(ZobristMapRenderer.class);

  private Graphics2D g;

  public ZobristMapRenderer(Graphics2D g) {
    this.g = g;
  }

  @Override
  public void visit(GameState gameState) {
    g.setColor(Constants.COLOR_SWING_BOARD);
    g.fillRect(0, 0, gameState.getW(), gameState.getH());

    long[][][] zobristKey = gameState.getZobristHash();
    for (int y = 0; y < gameState.getH(); y++) {
      for (int x = 0; x < gameState.getW(); x++) {
          Entity e = gameState.getEntityAt(x, y);
          if (e != null) {
            g.setColor(new Color((byte)zobristKey[x][y][e.getZobristIndex()]));
            g.fillRect(x, y, 1, 1);
          }
      }
    }
  }

  @Override
  public void visit(Base base) {

  }

  @Override
  public void visit(Bullet bullet) {

  }

  @Override
  public void visit(Tank tank) {

  }

  @Override
  public void visit(Wall wall) {
  }
}
