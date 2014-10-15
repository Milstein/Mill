package main.scala.model.Tree

import main.scala.model.Heuristic.Heuristic
import main.scala.model.Moves.Move
import main.scala.model.{MyState, StateGenerator}

/**
 * Created by tmnd on 31/05/14.
 */
abstract class AbstractNode {
  def parent : AbstractNode
  def data : MyState
  def cost : Float
  def childrens : Iterable[AbstractNode]
  def eraseParent : Unit
  def firstNode(p : AbstractNode) : AbstractNode = {
    if (parent==p || parent==null){
      return this
    }
    else
      parent.firstNode(p)
  }
  def firstMove(p : AbstractNode) : Move = {
    firstNode(p).data.move
  }

  def costCalculatorInstance : Heuristic = null
}
