import za.co.entelect.competition.ai.tankoperator.DummyTankOperator
import za.co.entelect.competition.ai.tankoperator.KeyboardControlledTankOperator
import za.co.entelect.competition.ai.tankoperator.MouseControlledTankOperator
import za.co.entelect.competition.ai.tankoperator.RandomTankOperator
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
w   1w  w  wwwwww  w
w   www         w  w
w   yw2         w  w
w                  w
wwwwwwwwwwwwwwwwwwww"""
}

def getTank1() {
  return new Tank(TankId.Y1, new KeyboardControlledTankOperator())
}

def getTank2() {
  return new Tank(TankId.Y2, new DummyTankOperator())
}

def getTank3() {
  return new Tank(TankId.O1, new MouseControlledTankOperator())
}

def getTank4() {
  return new Tank(TankId.O2, new DummyTankOperator())
}