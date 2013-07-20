package za.co.entelect.competition.ai.blackboard;

import za.co.entelect.competition.domain.Base;
import za.co.entelect.competition.domain.Bullet;
import za.co.entelect.competition.domain.Point;
import za.co.entelect.competition.domain.Tank;

public class BlackboardFactBullet extends BlackboardFact {
  public BlackboardFactAttr<Bullet> handle;
  public BlackboardFactAttr<String> id;
  public BlackboardFactAttr<Point> pos;
  public BlackboardFactAttr<Tank> tank;
}
