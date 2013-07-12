package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;
import za.co.entelect.competition.bots.tanks.DummyTank;
import za.co.entelect.competition.bots.tanks.RandomTank;

import java.io.*;
import java.util.Random;

public class GameFactory {

  private static final Logger logger = Logger.getLogger(GameFactory.class);

  public static GameState fromFile(String filename) {
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
    return fromString(builder.toString());
  }

  public static GameState fromString(String desc) {
    String[] lines = desc.split("\n");
    String dimParts[] = lines[0].split(" ");
    int w = Integer.parseInt(dimParts[0]);
    int h = Integer.parseInt(dimParts[1]);

    String[] tank1 = lines[1].split("=");
    String[] tank2 = lines[1].split("=");
    String[] tank3 = lines[1].split("=");
    String[] tank4 = lines[1].split("=");

    int offset = 5;
    GameState gameState = new GameState(w * 5, h * 5);
    for (int j = offset; j < lines.length; j++) {
      String line = lines[j];
      for (int i = 0; i < line.length(); i++) {
        int centerX = i * 5;
        int centerY = (j - offset) * 5;
        char c = line.charAt(i);
        switch (c) {
          case 'w':
            for (int jy = (j - offset) * 5; jy < (j - offset) * 5 + 5; jy++) {
              for (int ix = i * 5; ix < i * 5 + 5; ix++) {
                gameState.add(new Wall(ix, jy, gameState));
              }
            }
            break;
          case 'y':
            gameState.setYourBasePos(centerX, centerY);
            break;
          case 'o':
            gameState.setOpponentBasePos(centerX, centerY);
            break;
          case '1':
            try {
              Tank tank = (Tank)Class.forName(tank1[1]).newInstance();
              tank.setName("1");
              tank.setX(centerX);
              tank.setY(centerY);
              tank.setOwner(gameState.getYou());
              tank.setGameState(gameState);
              gameState.add(tank);
            } catch (Exception ex) {
              ex.printStackTrace();
            }
            break;
          case '2':
            //gameState.setTankPos("2", centerX, centerY);
            break;
          case '3':
            //gameState.setTankPos("3", centerX, centerY);
            break;
          case '4':
            //gameState.setTankPos("4", centerX, centerY);
            break;
        }
      }
    }
    return gameState;
  }
}
