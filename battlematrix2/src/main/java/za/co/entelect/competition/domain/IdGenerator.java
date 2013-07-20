package za.co.entelect.competition.domain;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

  public static final String YOUR_BASE = "YBASE";
  public static final String OPPONENT_BASE = "OBASE";
  public static final String Y1 = "Y1";
  public static final String Y2 = "Y2";
  public static final String O1 = "O1";
  public static final String O2 = "O2";
  private static final String BULLET = "BULLET-";
  private static final AtomicLong bulletIdGen = new AtomicLong();

  public static String nextBulletId() {
    return BULLET + bulletIdGen.incrementAndGet();
  }
}
