package za.co.entelect.competition.domain;

public class MapNode {
  private Entity entity;
  private Entity clearanceEntity;
  private int clearance = 1;

  public MapNode() {
    setEntity(null);
  }

  public MapNode(Entity entity) {
    setEntity(entity);
  }

  public void setEntity(Entity entity) {
    this.entity = entity;
  }

  public Entity getEntity() {
    return entity;
  }

  public int getClearance() {
    return clearance;
  }

  public void setClearance(int clearance) {
    this.clearance = clearance;
  }

  public Entity getClearanceEntity() {
    return clearanceEntity;
  }

  public void setClearanceEntity(Entity clearanceEntity) {
    this.clearanceEntity = clearanceEntity;
  }

  public int getObstruction() {
    if (clearance > 0) {
      return Obstruction.NONE;
    }

    if (clearanceEntity == null) {
      return Obstruction.BORDER;
    }

    switch (clearanceEntity.getType()) {
      case BASE:
        return Obstruction.BASE;
      case BULLET:
        return Obstruction.BULLET;
      case TANK:
        return Obstruction.TANK;
      case WALL:
        return Obstruction.WALL;
    }

    return Obstruction.NONE;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("MapNode{");
    sb.append("entity=").append(entity);
    sb.append(", clearance=").append(clearance);
    sb.append(", clearanceEntity=").append(clearanceEntity);
    sb.append('}');
    return sb.toString();
  }
}
