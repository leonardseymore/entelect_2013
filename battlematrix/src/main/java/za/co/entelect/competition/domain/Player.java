package za.co.entelect.competition.domain;

public class Player {
  private String id;

  public Player(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Player)) return false;

    Player player = (Player) o;

    if (id != null ? !id.equals(player.id) : player.id != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Player{");
    sb.append("id='").append(id).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
