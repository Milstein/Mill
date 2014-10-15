package main.scala.model.Tree

import main.scala.model.Heuristic.Heuristic
import main.scala.model.Moves.Move
import main.scala.model.{MyState, StateGenerator}

/**
 * Created by tmnd on 26/05/14.
 */
class MemoryNode (_data : MyState,
            costCalculator : Heuristic,
            private var _parent : MemoryNode = null) extends AbstractNode{

  def parent : MemoryNode = _parent

  private var _childrens : List[MemoryNode]= null
  private var _cost : Float = -1

  def data = _data

  def cost = {
    if (_cost == -1)
      _cost = costCalculator.calc(if (parent==null) null else parent.data,data,data.toMove)
    _cost
  }

  def childrens : Iterable[MemoryNode] = {
    if (_childrens==null)
      _childrens = StateGenerator.nextStates(data).map(s => new MemoryNode(s,costCalculator,this))
    _childrens
  }
  override def costCalculatorInstance : Heuristic = costCalculator
  def eraseParent = _parent = null
}