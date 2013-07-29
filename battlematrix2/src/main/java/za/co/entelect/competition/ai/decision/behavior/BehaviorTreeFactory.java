package za.co.entelect.competition.ai.decision.behavior;

import org.apache.log4j.Logger;

public class BehaviorTreeFactory {

  public static final Logger logger = Logger.getLogger(BehaviorTreeFactory.class);

  private static Task defendBase;
  static {
    defendBase = new MoveToClosest();
    logger.debug("DefendBase behavior tree\n" + defendBase.toDot("DefendBase"));
  }

  private static Task attackBase;
  static {
    attackBase = new Selector()
      .a(
        new Sequence()
          .a(new CanFireAt())
          .a(new Fire())
      )
      .a(
        new Sequence()
          .a(new MoveToClosest())
          .a(new InLine())
          .a(new LookAt())
          .a(new Inverse(new FriendlyFire()))
          .a(new Fire())
      );
    logger.debug("AttackBase behavior tree\n" + attackBase.toDot("AttackBase"));
  }

  private static Task attackTank;
  static {
    attackTank = new Selector()
      .a(
        new Sequence()
          .a(new CanFireAt())
          .a(new Fire())
      )
      .a(
        new Sequence()
          .a(new MoveToClosest())
          .a(new InLine())
          .a(new LookAt())
      );
    logger.debug("AttackTank behavior tree\n" + attackTank.toDot("AttackTank"));
  }

  public static Task defendBase() {
    return defendBase;
  }

  public static Task attackBase() {
    return attackBase;
  }

  public static Task attackTank() {
    return attackTank;
  }
}
