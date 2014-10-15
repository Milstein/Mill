package main.scala.model.Moves

/**
 * Created by tmnd on 29/05/14.
 */

import main.scala.model.Position
import it.unibo.ai.didattica.mulino.actions.{Phase2Action, Action}

case class ShiftRemoveMove(o : Position, d : Position, toRemove : Position) extends Move{
  require (o.col == d.col || o.row == d.row)
  override def toString : String = "ShiftRemoveMove "+o.name+" -> "+d.name + " // "+toRemove.name
  override def toStr : String = ShiftRemoveMove.PREFIX+o.name+d.name+toRemove.name
  override def toAction : Action = {
    val toRet = new Phase2Action()
    toRet.setFrom(o.coordinates)
    toRet.setTo(d.coordinates)
    toRet.setRemoveOpponentChecker(toRemove.coordinates)
    return toRet
  }
}

object ShiftRemoveMove{
  val PREFIX = "SR"
}