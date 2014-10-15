package main.scala.model.Moves


import main.scala.model.{MyPhase, Position}
import it.unibo.ai.didattica.mulino.actions.Action

/**
 * Created by tmnd on 29/05/14.
 */
abstract class Move {
  def d : Position
  def toStr : String
  def toAction : Action
}
object Move{
  def moveFromStr(s : String) : Move = {
    val prefix = s.substring(0,2)
    /*
    prefix match{
      case PutMove.PREFIX         => new PutMove(new Position(s.substring(2)))

      case PutRemoveMove.PREFIX   => new PutRemoveMove(new Position(s.substring(2,4)),
                                                                           new Position(s.substring(4,6)))
      case FlyMove.PREFIX         => new FlyMove(new Position(s.substring(2,4)),
                                                                     new Position(s.substring(4,6)))
      case FlyRemoveMove.PREFIX   => new FlyRemoveMove(new Position(s.substring(2,4)),
                                                                           new Position(s.substring(4,6)),
                                                                           new Position(s.substring(6,8)))
      case ShiftMove.PREFIX       => new ShiftMove(new Position(s.substring(2,4)),
                                                                       new Position(s.substring(4,6)))
      case ShiftRemoveMove.PREFIX => new ShiftRemoveMove(new Position(s.substring(2,4)),
                                                                             new Position(s.substring(4,6)),
                                                                             new Position(s.substring(6,8)))
    }
    */
    //THIS SYNTAX GAVES ME CANCER
    if (prefix == PutMove.PREFIX) new PutMove(new Position(s.substring(2))) else
    if (prefix == PutRemoveMove.PREFIX) new PutRemoveMove(new Position(s.substring(2,4)),
                                                          new Position(s.substring(4,6))) else
    if (prefix == FlyMove.PREFIX) new FlyMove(new Position(s.substring(2,4)),
                                              new Position(s.substring(4,6))) else
    if (prefix == FlyRemoveMove.PREFIX) new FlyRemoveMove(new Position(s.substring(2,4)),
                                                          new Position(s.substring(4,6)),
                                                          new Position(s.substring(6,8))) else
    if (prefix == ShiftMove.PREFIX) new ShiftMove(new Position(s.substring(2,4)),
                                                  new Position(s.substring(4,6))) else
    if (prefix == ShiftRemoveMove.PREFIX) new ShiftRemoveMove(new Position(s.substring(2,4)),
                                                              new Position(s.substring(4,6)),
                                                              new Position(s.substring(6,8))) else NoMove
  }
}