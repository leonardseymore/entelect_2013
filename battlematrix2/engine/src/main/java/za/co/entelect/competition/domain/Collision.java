package za.co.entelect.competition.domain;

public class Collision {
  public Entity source;
  public Entity target;
  public CollisionType type;

  public Collision(Entity source, Entity target, CollisionType type) {
    this.source = source;
    this.target = target;
    this.type = type;
  }
}
