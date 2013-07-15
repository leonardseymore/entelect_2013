package za.co.entelect.competition.domain;

public interface TankOperator {
  TankAction getAction(GameState gameState, Tank tank);
}
