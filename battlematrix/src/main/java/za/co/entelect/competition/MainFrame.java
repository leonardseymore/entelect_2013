package za.co.entelect.competition;

import org.apache.log4j.Logger;
import za.co.entelect.competition.bots.ApproachTank;
import za.co.entelect.competition.bots.KeyboardControlledTank;
import za.co.entelect.competition.bots.MouseControlledTank;
import za.co.entelect.competition.domain.Directed;
import za.co.entelect.competition.domain.GameState;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class MainFrame extends JFrame {

  private static final Logger logger = Logger.getLogger(MainFrame.class);

  public static final int DEFAULT_WIDTH = 800;
  public static final int DEFAULT_HEIGHT = 700;

  private GameState gameState;
  private Canvas canvas;
  private boolean printHelp;
  private boolean clearanceMap;
  private double zoomFactor = 1;

  private Keyboard keyboard;
  private Mouse mouse;

  public MainFrame(GameState gameState, double zoomFactor) {
    this.gameState = gameState;
    this.zoomFactor = zoomFactor;

    setIgnoreRepaint(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle(Constants.APP_TITLE);
    setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    canvas = new Canvas();
    canvas.setIgnoreRepaint(true);
    canvas.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    add(canvas);
    pack();

    keyboard = new Keyboard();
    addKeyListener(keyboard);
    canvas.addKeyListener(keyboard);

    mouse = new Mouse(canvas, zoomFactor);
    addMouseListener(mouse);
    addMouseMotionListener(mouse);
    canvas.addMouseListener(mouse);
    canvas.addMouseMotionListener(mouse);

    KeyboardControlledTank p1t1 = new KeyboardControlledTank("p1t1", 60, 20, gameState, gameState.getPlayer1(), Directed.Direction.LEFT, keyboard);
    gameState.add(p1t1);

    ApproachTank p2t1 = new ApproachTank("p2t1", 24, 12, gameState, gameState.getPlayer2(), Directed.Direction.UP);
    p2t1.setFollowTank(p1t1);
    gameState.add(p2t1);

    MouseControlledTank p1t2 = new MouseControlledTank("p1t2", 40, 60, gameState, gameState.getPlayer1(), Directed.Direction.DOWN, mouse);
    gameState.add(p1t2);
  }

  public void run() {
    canvas.createBufferStrategy(2);

    BufferStrategy buffer = canvas.getBufferStrategy();
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice();
    GraphicsConfiguration gc = gd.getDefaultConfiguration();
    BufferedImage bi = gc.createCompatibleImage(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    Graphics graphics = null;
    Graphics2D g = null;

    while (true) {
      try {
        keyboard.poll();
        mouse.poll();

        if (keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
          break;
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_H)) {
          printHelp = !printHelp;
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_C)) {
          clearanceMap = !clearanceMap;
        }

        g = bi.createGraphics();

        g.setColor(Constants.COLOR_SWING_BLANK);
        g.fillRect(0, 0, getWidth(), getHeight());

        AffineTransform t = g.getTransform();
        g.scale(zoomFactor, zoomFactor);
        gameState.accept(new GameElementSwingVisitor(g, gameState));
        if (clearanceMap) {
          gameState.accept(new ClearanceMapVisitor(g, gameState));
        }
        g.setTransform(t);

        if (printHelp) {
          g.setColor(Color.green);
          int y = 20;
          g.drawString("Press h for help", 20, y);
          g.drawString("Use arrow keys to move tank", 20, y += 12);
          g.drawString("Press SPACE to fire", 20, y += 12);
          g.drawString("Press c for clearance map", 20, y += 12);
          g.drawString("Press ESC to exit", 20, y += 12);

        }

        graphics = buffer.getDrawGraphics();
        graphics.drawImage(bi, 0, 0, null);
        if (!buffer.contentsLost()) {
          buffer.show();
        }

        try {
          Thread.sleep(10);
        } catch (InterruptedException ex) {
          logger.warn("Thread interrupted", ex);
        }
      } finally {
        if (graphics != null) {
          graphics.dispose();
        }

        if (g != null) {
          g.dispose();
        }
      }
    }
  }
}
