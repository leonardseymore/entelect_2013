package za.co.entelect.competition.domain;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

  public static String YOUR_BASE = "YBASE";
  public static String OPPONENT_BASE = "OBASE";
  private static String BULLET = "BULLET-";
  private static AtomicInteger bulletIdGen = new AtomicInteger();

  public static String nextBulletId() {
    return BULLET + bulletIdGen.incrementAndGet();
  }

}
