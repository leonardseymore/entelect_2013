package za.co.entelect.competition.ai.decision.behavior;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;

public class BehaviorTreeFactory {

  private static final Logger logger = Logger.getLogger(BehaviorTreeFactory.class);

  public static Task fireAt() {
    Task tree = new Sequence()
      .a(new CanFireAt())
      .a(new Inverse(new FriendlyFire()))
      .a(new Fire());
    logger.debug("FireAt behavior branch:\n" + tree.toDot("FireAt"));
    return tree;
  }

  public static Task move() {
    Task tree = new Sequence()
      .a(new Inverse(new IsMoveIntoFire()))
      .a(new Move()
      );
    logger.debug("Move behavior branch:\n" + tree.toDot("Move"));
    return tree;
  }

  public static Task closestMove() {
    Task tree = new Sequence()
      .a(new SetClosestMove())
      .a(move());
    logger.debug("ClosestMove behavior branch:\n" + tree.toDot("Closest"));
    return tree;
  }


  public static Task moveToPos() {
    Task tree = new Sequence()
      .a(new SetMove())
      .a(move());
    logger.debug("MoveToPos behavior branch:\n" + tree.toDot("MoveToPos"));
    return tree;
  }

  public static Task avoidFire() {
    Task tree = new Sequence()
      .a(new InFireLine())
      .a(new SetClosestBullet())
      .a(
        new Selector()
          // Move out of the way
          .a(
            new Sequence()
              .a(new DodgeBullet())
              .a(new Move())
          )
          // Fire at bullet
          .a(
            new Sequence()
              .a(new IsFurtherThan(Constants.MIN_TIME_TO_FACE_AND_DESTROY_INLINE_BULLET))
              .a(new InLine())
              .a(new Inverse(new LookAt()))
          )
          .a(
            new Sequence()
              .a(new CanFireAt())
              .a(new Fire())
          )
          // TODO: can escape through the wall?
          // Spite by firing at the tank that owns the bullet
          .a(
            new Sequence()
              .a(new SetTargetFromBullet())
              .a(fireAt())
          )
      );
    logger.debug("AvoidFire behavior branch:\n" + tree.toDot("AvoidFire"));
    return tree;
  }

  public static Task defendBase() {
    Task tree = new Selector()
      .a(avoidFire())
      .a(closestMove());
    logger.debug("DefendBase behavior tree:\n" + tree.toDot("DefendBase"));
    return tree;
  }
  public static final Task DEFEND_BASE = defendBase();

  public static Task attackBase() {
    Task tree = new Selector()
      .a(avoidFire())
      .a(
        new Sequence()
          .a(new CanFireAt())
          .a(new Inverse(new FriendlyFire()))
          .a(new Fire())
      )
      .a(
        new Selector()
          .a(closestMove())
          .a(
            new Sequence()
              .a(new InLine())
              .a(new LookAt())
              .a(new Inverse(new FriendlyFire()))
              .a(new Fire())
          )
      );
    logger.debug("AttackBase behavior tree:\n" + tree.toDot("AttackBase"));
    return tree;
  }
  public static final Task ATTACK_BASE = attackBase();

  public static Task attackTank() {
    Task tree = new Selector()
      .a(avoidFire())
      .a(
        new Sequence()
          .a(new CanFireAt())
          .a(new Fire())
      )
      .a(
        new Selector()
          .a(closestMove())
          .a(
            new Sequence()
              .a(new InLine())
              .a(new LookAt())
            )
      );
    logger.debug("AttackTank behavior tree:\n" + tree.toDot("AttackTank"));
    return tree;
  }
  public static final Task ATTACK_TANK = attackTank();
}
