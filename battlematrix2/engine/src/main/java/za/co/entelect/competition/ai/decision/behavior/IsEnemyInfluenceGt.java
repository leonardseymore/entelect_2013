package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.*;

public class IsEnemyInfluenceGt extends Task {

  private float influence;

  public IsEnemyInfluenceGt(float influence) {
    this.influence = influence;
  }

  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    TankAction tankAction = blackboard.getNextTankAction();
    int nextX = tank.getX();
    int nextY = tank.getY();
    switch (tankAction) {
      case UP:
        nextY--;
        break;
      case RIGHT:
        nextX++;
        break;
      case DOWN:
        nextY++;
        break;
      case LEFT:
        nextX--;
        break;
    }
    InfluenceMap influenceMap = gameState.getInfluenceMap();
    float[][] enemyInfluence = influenceMap.getEnemyInfluence(tank);
    return enemyInfluence[nextX][nextY] > influence;
  }

  @Override
  protected String getLabel() {
    return "IsEnemyInfluenceGt" + String.format("%.2f", influence) + "?";
  }
}
