package za.co.entelect.competition.domain;

public interface GameElementVisitor {
  void visit(GameState gameState);
  void visit(Base base);
  void visit(Bullet bullet);
  void visit(Tank tank);
  void visit(Walls walls);
}
