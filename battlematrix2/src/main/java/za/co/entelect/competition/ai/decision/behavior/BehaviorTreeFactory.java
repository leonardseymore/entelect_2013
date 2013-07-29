package za.co.entelect.competition.ai.decision.behavior;

import org.apache.log4j.Logger;

public class BehaviorTreeFactory {

  public static final Logger logger = Logger.getLogger(BehaviorTreeFactory.class);

  private static Task attackBase;
  static {
    attackBase = new Sequence()
      .a(new MoveToClosest())
      .a(
        new Selector()
          .a(
            new Sequence()
              .a(new InLine())
              .a(new LookAt())
          )
          .a(new Fire())
      )
      .a(new Fire());
  }

  public static Task attackBase() {
    //logger.debug("AttackBase behavior tree\n" + attackBase.toDot());
    return attackBase;
  }
}
