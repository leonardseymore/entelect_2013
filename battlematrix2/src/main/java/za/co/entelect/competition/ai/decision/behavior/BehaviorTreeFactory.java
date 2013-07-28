package za.co.entelect.competition.ai.decision.behavior;

public class BehaviorTreeFactory {
  public static Task attackBase() {
    return new Sequence()
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
  }
}
