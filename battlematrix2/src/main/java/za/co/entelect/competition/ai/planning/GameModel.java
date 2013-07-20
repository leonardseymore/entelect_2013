package za.co.entelect.competition.ai.planning;

import java.util.Collection;
import java.util.HashSet;

public class GameModel {
  private Collection<GameModelProp> props = new HashSet<>();

  public GameModel(Collection<GameModelProp> props) {
    this.props = props;
  }
}
