package za.co.entelect.competition.ai.decision.behavior;

import org.apache.log4j.Logger;

public class BehaviorTreeFactory {

  public static final Logger logger = Logger.getLogger(BehaviorTreeFactory.class);

  public static Task attackBase() {
    Task tree = new Sequence()
      .addChild(new MoveToClosest())
      .addChild(
        new Selector()
          .addChild(
            new Sequence()
              .addChild(new InLine())
              .addChild(new LookAt())
          )
          .addChild(new Fire())
      )
      .addChild(new Fire());
    //logger.debug("AttackBase behavior tree\n" + tree.toDot());
    return tree;
  }
}
