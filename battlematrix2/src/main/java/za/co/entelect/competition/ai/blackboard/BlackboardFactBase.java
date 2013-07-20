package za.co.entelect.competition.ai.blackboard;

import za.co.entelect.competition.domain.Base;
import za.co.entelect.competition.domain.Player;
import za.co.entelect.competition.domain.Point;

public class BlackboardFactBase extends BlackboardFact {
  public BlackboardFactAttr<Base> handle;
  public BlackboardFactAttr<String> id;
  public BlackboardFactAttr<Point> pos;
  public BlackboardFactAttr<Player> owner;
}
