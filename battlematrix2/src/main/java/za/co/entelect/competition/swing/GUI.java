package za.co.entelect.competition.swing;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;
import za.co.entelect.competition.domain.Game;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Ids;
import za.co.entelect.competition.domain.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GUI extends JFrame {

  private static final Logger logger = Logger.getLogger(GUI.class);

  public static final int DEFAULT_WIDTH = 500;
  public static final int DEFAULT_HEIGHT = 520;

  private GameRenderer gameRenderer = new GameRenderer();
  private ClearanceMapRenderer clearanceMapRenderer = new ClearanceMapRenderer();
  private InfluenceMapRenderer influenceMapRenderer = new InfluenceMapRenderer();
  private DirichletDomainRenderer dirichletDomainRenderer = new DirichletDomainRenderer();

  private static enum Map {
    USER, CLEARANCE, INFLUENCE, DIRICHLET
  }

  private GameState gameState;
  private Game game;
  private Canvas canvas;
  private boolean printHelp;
  private double zoomFactor = 1;

  private Keyboard keyboard;
  private Mouse mouse;
  private boolean paused = false;

  private Map map = Map.USER;
  private Font arial = new Font("Arial", Font.BOLD, 10);

  private int frameSleep = 33;
  private int frameSleepMultiplier = 1;

  public GUI(GameState gameState, double zoomFactor) {
    this.gameState = gameState;
    this.game = new Game(gameState);
    this.zoomFactor = zoomFactor;

    setIgnoreRepaint(true);
    setTitle(Constants.APP_TITLE);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
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

        if (keyboard.keyDownOnce(KeyEvent.VK_H)) {
          printHelp = !printHelp;
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_U)) {
          map = Map.USER;
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_C)) {
          map = Map.CLEARANCE;
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_I)) {
          map = Map.INFLUENCE;
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_D)) {
          map = Map.DIRICHLET;
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_P)) {
          paused = !paused;
          if (paused) {
            setTitle(Constants.APP_TITLE + " - PAUSED");
          } else {
            setTitle(Constants.APP_TITLE);
          }
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_F)) {
          frameSleepMultiplier = Math.max(frameSleepMultiplier - 1, 0);
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_S)) {
          frameSleepMultiplier++;
        }
        if (paused) {
          continue;
        }
        game.update();

        g = bi.createGraphics();
        g.setFont(arial);

        g.setColor(Constants.COLOR_SWING_BLANK);
        g.fillRect(0, 0, getWidth(), getHeight());

        AffineTransform t = g.getTransform();
        g.scale(zoomFactor, zoomFactor);
        switch (map) {
          case USER:
            gameRenderer.setG(g);
            gameState.accept(gameRenderer);
            break;
          case CLEARANCE:
            clearanceMapRenderer.setG(g);
            gameState.accept(clearanceMapRenderer);
            break;
          case INFLUENCE:
            influenceMapRenderer.setG(g);
            gameState.accept(influenceMapRenderer);
            break;
          case DIRICHLET:
            dirichletDomainRenderer.setG(g);
            gameState.accept(dirichletDomainRenderer);
            break;
        }
        g.setTransform(t);

        if (printHelp) {
          g.setColor(new Color(123,123,123,190));
          g.fillRect(0, 0, getWidth(), 100);
          g.setColor(Color.white);

          int x = 10;
          int y = 10;
          g.drawString("u=user map (default), c=clearance map", x, y += 12);
          g.drawString("p toggle pause", x, y += 12);
          g.drawString("frame sleep: " + frameSleep * frameSleepMultiplier, x, y += 12);
          g.drawString(game.getStrategyManager().toString(), x, y += 12);
        }

        if (gameState.isGameOver()) {
          g.setColor(new Color(123,123,123,190));
          g.fillRect(0, 0, getWidth(), getHeight());
          int x = 10;
          int y = 10;
          String winner = gameState.getYourBase() == null ? "You loose!" : "You WIN!";
          g.setColor(gameState.getYourBase() == null ? Constants.COLOR_SWING_TANK_OPPONENT : Constants.COLOR_SWING_TANK_YOU);
          g.drawString("Game Over: " + winner, x, y += 12);
        }

        graphics = buffer.getDrawGraphics();
        graphics.drawImage(bi, 0, 0, null);
        if (!buffer.contentsLost()) {
          buffer.show();
        }

        try {
          Thread.sleep(frameSleep * frameSleepMultiplier);
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
