package za.co.entelect.competition.ai.decision.behavior;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;

public class BehaviorTreeFactory {

  private static final Logger logger = Logger.getLogger(BehaviorTreeFactory.class);

  public static Task fireAt;
  static {
    fireAt = new Sequence()
      .a(new CanFireAt())
      .a(new Inverse(new FriendlyFire()))
      .a(new Fire());
    logger.debug("FireAt behavior branch\n" + fireAt.toDot("FireAt"));
  }

  public static Task move;
  static {
    move = new Sequence()
      .a(new IsSafeMove())
      .a(new Move()
      );
    logger.debug("Move behavior branch\n" + move.toDot("Move"));
  }

  public static Task closestMove;
  static {
    closestMove = new Sequence()
      .a(new SetClosestMove())
      .a(move);
    logger.debug("ClosestMove behavior branch\n" + closestMove.toDot("Closest"));
  }

  public static Task moveToPos;
  static {
    moveToPos = new Sequence()
      .a(new SetMove())
      .a(move);
    logger.debug("MoveToPos behavior branch\n" + moveToPos.toDot("MoveToPos"));
  }

  public static Task avoidFire;
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

  public static Task defendBase;
  static {
    defendBase = new Selector()
      .a(avoidFire)
      .a(closestMove);
    logger.debug("DefendBase behavior tree\n" + defendBase.toDot("DefendBase"));
  }

  public static Task attackBase;
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

  public static Task frontLine;
  static {
    frontLine = new Selector()
      .a(avoidFire)
      .a(moveToPos);
    logger.debug("MoveToFrontLine behavior tree\n" + frontLine.toDot("MoveToFrontLine"));
  }

  public static Task attackTank;
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
}
