import za.co.entelect.competition.Constants
import za.co.entelect.competition.ai.tankoperator.DummyTankOperator
import za.co.entelect.competition.domain.Direction
import za.co.entelect.competition.domain.IdGenerator
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
w  w          wo   w
w  w         www   w
w  wwwwww  w  w3   w
w          w       w
wwwwwwwwwwwwwwwwwwww
w  w               w
w  w ww w  w    w  w
w  w    w  w  4 w  w
w  w 1  w  w    w  w
w  w    w  w ww w  w
w               w  w
wwwwwwwwwwwwwwwwwwww
w       w          w
w    w  w  wwwwww  w
w   www         w  w
w   yw 2        w  w
w                  w
wwwwwwwwwwwwwwwwwwww"""
}

def getTank1() {
  return new Tank(IdGenerator.Y1, Player.YOU, Direction.UP, new DummyTankOperator())
}

def getTank2() {
  return new Tank(IdGenerator.Y2, Player.YOU, Direction.DOWN, new DummyTankOperator())
}

def getTank3() {
  return new Tank(IdGenerator.O1, Player.OPPONENT, Direction.RIGHT, new DummyTankOperator())
}

def getTank4() {
  return new Tank(IdGenerator.O2, Player.OPPONENT, Direction.LEFT, new DummyTankOperator())
}