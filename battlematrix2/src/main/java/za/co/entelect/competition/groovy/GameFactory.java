package za.co.entelect.competition.groovy;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Point;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.Walls;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

public class GameFactory {
  public static GameState fromFile(String filename) throws ScriptException, NoSuchMethodException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(GameFactory.class.getResourceAsStream(filename)));
    StringBuilder builder = new StringBuilder();
    try {
      String line = reader.readLine();
      while (line != null) {
        builder.append(line + "\n");
        line = reader.readLine();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return fromScript(builder.toString());
  }

  public static GameState fromScript(String script) throws ScriptException, NoSuchMethodException {
    ScriptEngineManager sem = new ScriptEngineManager();
    ScriptEngine scriptEngine = sem.getEngineByName("groovy");
    scriptEngine.eval(script);
    Invocable inv = (Invocable) scriptEngine;
    int[] size = (int[]) inv.invokeFunction("getSize");
    int w = size[0];
    int h = size[1];
    String mapDesc = (String) inv.invokeFunction("getMap");
    Map map = parseMap(mapDesc, w, h);

    Point ybase = map.getyBase();
    Point obase = map.getoBase();

    GameState gameState = new GameState(w, h);
    gameState.setWalls(map.walls);
    gameState.setYourBase(ybase.getX(), ybase.getY());
    gameState.setOpponentBase(obase.getX(), obase.getY());


    Point tank1Pos = map.getTank1Pos();
    if (tank1Pos != null) {
      Tank tank1 = (Tank) inv.invokeFunction("getTank1");
      tank1.setX(tank1Pos.getX());
      tank1.setY(tank1Pos.getY());
      gameState.addTank(tank1);
    }
    Point tank2Pos = map.getTank2Pos();
    if (tank2Pos != null) {
      Tank tank2 = (Tank) inv.invokeFunction("getTank2");
      tank2.setX(tank2Pos.getX());
      tank2.setY(tank2Pos.getY());
      gameState.addTank(tank2);
    }
    Point tank3Pos = map.getTank3Pos();
    if (tank3Pos != null) {
      Tank tank3 = (Tank) inv.invokeFunction("getTank3");
      tank3.setX(tank3Pos.getX());
      tank3.setY(tank3Pos.getY());
      gameState.addTank(tank3);
    }
    Point tank4Pos = map.getTank4Pos();
    if (tank3Pos != null) {
      Tank tank4 = (Tank) inv.invokeFunction("getTank4");
      tank4.setX(tank4Pos.getX());
      tank4.setY(tank4Pos.getY());
      gameState.addTank(tank4);
    }
    return gameState;
  }

  public static Map parseMap(String mapDesc, int w, int h) {
    Map map = new Map(w, h);
    String[] lines = mapDesc.split("\n");

    for (int j = 0; j < lines.length; j++) {
      String line = lines[j];
      for (int i = 0; i < line.length(); i++) {
        int centerX = i * 5 + 2;
        int centerY = j * 5 + 2;
        Point centerPoint = new Point(centerX, centerY);
        char c = line.charAt(i);
        switch (c) {
          case 'w':
            for (int jy = j * 5; jy < j * 5 + 5; jy++) {
              for (int ix = i * 5; ix < i * 5 + 5; ix++) {
                map.addWall(ix, jy);
              }
            }
            break;
          case 'y':
            map.setyBase(centerPoint);
            break;
          case 'o':
            map.setoBase(centerPoint);
            break;
          case '1':
            map.setTank1Pos(centerPoint);
            break;
          case '2':
            map.setTank2Pos(centerPoint);
            break;
          case '3':
            map.setTank3Pos(centerPoint);
            break;
          case '4':
            map.setTank4Pos(centerPoint);
            break;
        }
      }
    }
    return map;
  }

  public static class Map {

    private Walls walls;

    private Point yBase;
    private Point oBase;

    private Point tank1Pos;
    private Point tank2Pos;
    private Point tank3Pos;
    private Point tank4Pos;

    private Map(int w, int h) {
      walls = new Walls(w, h);
    }

    public Walls getWalls() {
      return walls;
    }

    public void setWalls(Walls walls) {
      this.walls = walls;
    }

    public Point getyBase() {
      return yBase;
    }

    public void setyBase(Point yBase) {
      this.yBase = yBase;
    }

    public Point getoBase() {
      return oBase;
    }

    public void setoBase(Point oBase) {
      this.oBase = oBase;
    }

    public Point getTank1Pos() {
      return tank1Pos;
    }

    public void setTank1Pos(Point tank1Pos) {
      this.tank1Pos = tank1Pos;
    }

    public Point getTank2Pos() {
      return tank2Pos;
    }

    public void setTank2Pos(Point tank2Pos) {
      this.tank2Pos = tank2Pos;
    }

    public Point getTank3Pos() {
      return tank3Pos;
    }

    public void setTank3Pos(Point tank3Pos) {
      this.tank3Pos = tank3Pos;
    }

    public Point getTank4Pos() {
      return tank4Pos;
    }

    public void setTank4Pos(Point tank4Pos) {
      this.tank4Pos = tank4Pos;
    }

    public void addWall(int x, int y) {
      walls.putWall(x, y);
    }
  }
}
