package main.scala.model.Moves

import main.scala.model.Position
import it.unibo.ai.didattica.mulino.actions.{Phase2Action, Action}

/**
 * Created by tmnd on 29/05/14.
 */
case class ShiftMove(o : Position, d: Position) extends Move{
  require (o.col == d.col || o.row == d.row)
  override def toString : String = "ShiftMove "+o.name+" -> "+d.name
  override def toStr : String = ShiftMove.PREFIX+o.name+d.name
  override def toAction : Action = {
    val toRet = new Phase2Action()
    toRet.setFrom(o.coordinates)
    toRet.setTo(d.coordinates)
    toRet.setRemoveOpponentChecker(null)
    return toRet
  }
}
object ShiftMove{
  val PREFIX = "SM"
}