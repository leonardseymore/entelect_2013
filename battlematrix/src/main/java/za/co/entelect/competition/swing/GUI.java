package za.co.entelect.competition.swing;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;
import za.co.entelect.competition.domain.GameState;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class GUI extends JFrame {

  private static final Logger logger = Logger.getLogger(GUI.class);

  public static final int DEFAULT_WIDTH = 700;
  public static final int DEFAULT_HEIGHT = 700;

  private GameState gameState;
  private Canvas canvas;
  private boolean printHelp;
  private boolean clearanceMap;
  private double zoomFactor = 1;

  private Keyboard keyboard;
  private Mouse mouse;
  private boolean paused = false;
  private String selectedTank;

  public GUI(GameState gameState, double zoomFactor) {
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

    keyboard = Keyboard.getInstance();
    addKeyListener(keyboard);
    canvas.addKeyListener(keyboard);

    Mouse.init(canvas, zoomFactor);
    mouse = Mouse.getInstance();
    addMouseListener(mouse);
    addMouseMotionListener(mouse);
    canvas.addMouseListener(mouse);
    canvas.addMouseMotionListener(mouse);
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
        if (keyboard.keyDownOnce(KeyEvent.VK_P)) {
          paused = !paused;
          if (paused) {
            setTitle(Constants.APP_TITLE + " - PAUSED");
          } else {
            setTitle(Constants.APP_TITLE);
          }
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_0)) {
          selectedTank = null;
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_1)) {
          selectedTank = "p1t1";
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_2)) {
          selectedTank = "p1t2";
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_3)) {
          selectedTank = "p2t1";
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_4)) {
          selectedTank = "p1t2";
        }
        if (paused) {
          continue;
        }
        gameState.update();

        g = bi.createGraphics();

        g.setColor(Constants.COLOR_SWING_BLANK);
        g.fillRect(0, 0, getWidth(), getHeight());

        AffineTransform t = g.getTransform();
        g.scale(zoomFactor, zoomFactor);
        gameState.accept(new GameRenderer(g));
        if (clearanceMap) {
          gameState.accept(new TacticalMapRenderer(g, selectedTank));
        }
        g.setTransform(t);

        if (printHelp) {
          g.setColor(Color.green);
          int y = 20;
          g.drawString("Press h for help", 20, y);
          g.drawString("Use arrow keys to move tank", 20, y += 12);
          g.drawString("Press SPACE to fire", 20, y += 12);
          g.drawString("Press c for clearance map", 20, y += 12);
          g.drawString("Press p toggle pause", 20, y += 12);
          g.drawString("Press ESC to exit", 20, y += 12);
        }

        graphics = buffer.getDrawGraphics();
        graphics.drawImage(bi, 0, 0, null);
        if (!buffer.contentsLost()) {
          buffer.show();
        }

        try {
          Thread.sleep(33);
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
