import za.co.entelect.competition.ai.tankoperator.DummyTankOperator
import za.co.entelect.competition.ai.tankoperator.MouseControlledTankOperator
import za.co.entelect.competition.ai.tankoperator.TimmyTankOperator
import za.co.entelect.competition.domain.Direction
import za.co.entelect.competition.domain.Ids
import za.co.entelect.competition.domain.Player
import za.co.entelect.competition.domain.Tank

w = 20
h = 20

def getSize() {
  return [w * 5, h * 5] as int[]
}

def getMap() {
  return """wwwwwwwwwwwwwwwwwwww
w                  w
w               o  w
w             3    w
w       4          w
w                  w
w                  w
w                  w
w                  w
w                  w
w                  w
w                  w
w                  w
w                  w
w                  w
w  1      2        w
w                  w
w  y               w
w                  w
wwwwwwwwwwwwwwwwwwww"""
}

def getTank1() {
  return new Tank(Ids.Y1, Player.YOU, Direction.UP, new DummyTankOperator())
}

def getTank2() {
  return new Tank(Ids.Y2, Player.YOU, Direction.DOWN, new MouseControlledTankOperator())
}

def getTank3() {
  return new Tank(Ids.O1, Player.OPPONENT, Direction.RIGHT, new TimmyTankOperator())
}

def getTank4() {
  return new Tank(Ids.O2, Player.OPPONENT, Direction.LEFT, new DummyTankOperator())
}