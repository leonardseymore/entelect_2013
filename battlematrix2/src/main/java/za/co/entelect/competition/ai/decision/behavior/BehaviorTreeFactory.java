package za.co.entelect.competition.ai.decision.behavior;

import org.apache.log4j.Logger;

public class BehaviorTreeFactory {

  public static final Logger logger = Logger.getLogger(BehaviorTreeFactory.class);

  public static Task attackBase() {
    Task tree = new Sequence()
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
    //logger.debug("AttackBase behavior tree\n" + tree.toDot());
    return tree;
  }
}
