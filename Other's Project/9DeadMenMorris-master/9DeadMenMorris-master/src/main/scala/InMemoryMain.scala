package main.scala

import main.scala.model.Heuristic.ConcreteHeuristic
import main.scala.model.Moves.{Move, NoMove}
import main.scala.model.Tree.{SlickNode, AbstractNode, MemoryNode}
import main.scala.model.{MyPhase, MyState}


/**
 * Created by tmnd on 24/05/14.
 */
object InMemoryMain extends App{
  override def main(args : Array[String]) = {

    var n : AbstractNode = new MemoryNode(new MyState(true,NoMove),new ConcreteHeuristic())
    val board = Map( true->List("A7","G7","C5","E5","E4","C3"), false->List("D6","F6","C3","B2","B4","C4"))
    var i = 0
    println(n.data)
    while(!n.data.hasWon(n.data.toMove)){
      println("Turn: "+i)
      n = nextMove(n)._1
      println("removed: "+n.data.removed)
      println("onTable: "+n.data.nrPieces)
      println("phase: "+n.data.phase)
      println(n.data)
      println(n.data.toMove+" to move:")
      i=i+1
    }
  }

  def nextMove(n : AbstractNode) : (AbstractNode,Move) = {
    val depth = n.data.phase match{
      case MyPhase.Phase1 => 4
      case MyPhase.Phase2 => 7
      case MyPhase.Phase3 => 4
    }
    val r = alphabeta(n,depth,n.data.toMove,true)
    val firstNode = r._1.firstNode(n)
    (firstNode,firstNode.data.move)
  }

  def alphabeta(node : AbstractNode, depth : Int, toMove : Boolean, maximizingPlayer : Boolean) : Tuple2[AbstractNode,Float]=
    alphabeta(node,depth,Float.MinValue,Float.MaxValue,toMove,maximizingPlayer)

  def alphabeta(node : AbstractNode, depth : Int, currA : Float, currB: Float, toMove : Boolean, maximizingPlayer : Boolean) : Tuple2[AbstractNode,Float] = {
    var newA = currA
    var newB = currB
    var newNode = node
    if (depth == 0 || node.data.hasWon(toMove))
      return (node, node.cost)
    var toPrune: Boolean = false
    val iterator = node.childrens.iterator
    if (maximizingPlayer) {
      while (iterator.hasNext && !toPrune) {
        val c = iterator.next
        val r = alphabeta(c, depth - 1, newA, newB, !toMove, false)
        if (r._2 > newA) {
          newA = r._2
          newNode = r._1
        }
        toPrune = (newB <= newA)
      }
      return (newNode, newA)
    }
    else{
      val iterator = node.childrens.iterator
      while(iterator.hasNext && !toPrune) {
        val child = iterator.next
        val r = alphabeta(child, depth - 1, newA, newB, !toMove, true)
        if (r._2 < newB) {
          newB = r._2
          newNode = r._1
        }
        toPrune = (newB <= newA)
      }
      return (newNode, newB)
    }
  }

  def max(a : Float, b : Float) = if (a>b) a else b
  def min(a : Float, b : Float) = if (a<b) a else b
}