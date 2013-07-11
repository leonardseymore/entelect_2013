package za.co.entelect.competition.domain;

public class MapNode {
  private Entity entity;

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

  public boolean hasEntity() {
    return entity != null;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("MapNode{");
    sb.append("entity=").append(entity);
    sb.append('}');
    return sb.toString();
  }
}
