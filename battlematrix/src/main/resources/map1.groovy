import za.co.entelect.competition.ai.tankoperator.DummyTankOperator
import za.co.entelect.competition.ai.tankoperator.GoalMouseControlledTankOperator
import za.co.entelect.competition.ai.tankoperator.KeyboardControlledTankOperator
import za.co.entelect.competition.ai.tankoperator.MouseControlledTankOperator
import za.co.entelect.competition.ai.tankoperator.RandomTankOperator
import za.co.entelect.competition.domain.Directed
import za.co.entelect.competition.domain.Tank
import za.co.entelect.competition.domain.TankId

w = 20
h = 20

def getSize() {
  return [w * 5, h * 5] as int[]
}

def getMap() {
  return """wwwwwwwwwwwwwwwwwwww
w                  w
w  w         4wo   w
w  w         www   w
w  wwwwww  w  w3   w
w          w       w
w  w    wwww  wwwwww
w  w               w
w  w ww w  w    w  w
w  w    w  w    w  w
w  w    w  w ww w  w
w  w    w  w    w  w
w               w  w
wwwwww  wwww    w  w
w       w          w
w  1 w  w  wwwwww  w
w   www         w  w
w   yw 2        w  w
w                  w
wwwwwwwwwwwwwwwwwwww"""
}

def getTank1() {
  return new Tank(TankId.Y1, new DummyTankOperator())
}

def getTank2() {
  return new Tank(TankId.Y2, new KeyboardControlledTankOperator())
}

def getTank3() {
  Tank tank = new Tank(TankId.O1, new GoalMouseControlledTankOperator())
  tank.setDirection(Directed.Direction.RIGHT);
  return tank
}

def getTank4() {
  return new Tank(TankId.O2, new DummyTankOperator())
}