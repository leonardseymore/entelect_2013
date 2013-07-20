package za.co.entelect.competition.ai.blackboard;

import za.co.entelect.competition.domain.Base;
import za.co.entelect.competition.domain.Player;
import za.co.entelect.competition.domain.Point;
import za.co.entelect.competition.domain.Tank;

public class BlackboardFactTank extends BlackboardFact {
  public BlackboardFactAttr<Tank> handle;
  public BlackboardFactAttr<String> id;
  public BlackboardFactAttr<Point> pos;
  public BlackboardFactAttr<Player> owner;
  public BlackboardFactAttr<Boolean> canFire;
}
