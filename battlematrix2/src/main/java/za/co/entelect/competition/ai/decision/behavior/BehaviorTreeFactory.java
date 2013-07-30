package za.co.entelect.competition.ai.decision.behavior;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;

public class BehaviorTreeFactory {

  public static final Logger logger = Logger.getLogger(BehaviorTreeFactory.class);

  private static Task fireAt;
  static {
    fireAt = new Sequence()
      .a(new CanFireAt())
      .a(new Inverse(new FriendlyFire()))
      .a(new Fire());
    logger.debug("FireAt behavior branch\n" + fireAt.toDot("FireAt"));
  }

  private static Task move;
  static {
    move = new Sequence()
      .a(new IsSafeMove())
      .a(new Move()
      );
    logger.debug("Move behavior branch\n" + move.toDot("Move"));
  }

  private static Task closestMove;
  static {
    closestMove = new Sequence()
      .a(new SetClosestMove())
      .a(move);
    logger.debug("ClosestMove behavior branch\n" + closestMove.toDot("Closest"));
  }

  private static Task avoidFire;
  static {
    avoidFire = new Sequence()
      .a(new InFireLine())
      .a(new SetClosestBullet())
      .a(
        new Selector()
          .a(
            new Sequence()
              .a(new IsFurtherThan(Constants.MIN_TIME_TO_FACE_AND_DESTROY_INLINE_BULLET))
              .a(new InLine())
              .a(new Inverse(new LookAt()))
          )
          .a(fireAt)
          .a(new DodgeBullet())
        // TODO: FIRE BACK
      );
    logger.debug("AvoidFire behavior branch\n" + avoidFire.toDot("AvoidFire"));
  }

  private static Task defendBase;
  static {
    defendBase = new Selector()
      .a(avoidFire)
      .a(closestMove);
    logger.debug("DefendBase behavior tree\n" + defendBase.toDot("DefendBase"));
  }

  private static Task attackBase;
  static {
    attackBase = new Selector()
      .a(avoidFire)
      .a(
        new Sequence()
          .a(new CanFireAt())
          .a(new Fire())
      )
      .a(
        new Selector()
          .a(closestMove)
          .a(
            new Sequence()
              .a(new InLine())
              .a(new LookAt())
              .a(new Inverse(new FriendlyFire()))
              .a(new Fire())
          )
      );
    logger.debug("AttackBase behavior tree\n" + attackBase.toDot("AttackBase"));
  }

  private static Task attackTank;
  static {
    attackTank = new Selector()
      .a(avoidFire)
      .a(
        new Sequence()
          .a(new CanFireAt())
          .a(new Fire())
      )
      .a(
        new Selector()
          .a(closestMove)
          .a(
            new Sequence()
              .a(new InLine())
              .a(new LookAt())
            )
      );
    logger.debug("AttackTank behavior tree\n" + attackTank.toDot("AttackTank"));
  }

  public static Task avoidFire() {
    return avoidFire;
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
