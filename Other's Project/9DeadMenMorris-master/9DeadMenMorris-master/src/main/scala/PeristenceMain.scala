package main.scala

import main.scala.model.Heuristic.ConcreteHeuristic
import main.scala.model.Moves.{Move, NoMove}
import main.scala.model.{MyPhase, MyState}
import main.scala.model.Tree.{MemoryNode, SlickNode, AbstractNode}
import scala.slick.driver.H2Driver.simple._
import Client._

/**
 * Created by tmnd on 31/05/14.
 */
object PeristenceMain extends App{
  override def main(args : Array[String]) = {
    SlickNode.create
    var toMove = true
    var n : AbstractNode = new SlickNode(None, None, new MyState(toMove, NoMove).toStateString)
    //adding root
    SlickNode.addAll(List[SlickNode](n.asInstanceOf[SlickNode]))
    n = SlickNode.root
    var i = 0
    println(n.data)
    while(!n.data.hasWon(n.data.toMove)){
      val start = System.currentTimeMillis()
      println("Turn: "+i)
      n = nextMove(n)._1
      println("removed: "+n.data.removed)
      println("onTable: "+n.data.nrPieces)
      println("phase: "+n.data.phase)
      println("id: "+n.asInstanceOf[SlickNode].id)
      println(n.data)
      println(n.data.toMove+" to move:")
      i=i+1
      SlickNode.clean(n.asInstanceOf[SlickNode])
      val end = System.currentTimeMillis()
      println(end-start)
      if (end-start>60000){
        println("too slow")
        System.exit(-1)
      }
    }
  }

  def nextMove(n : AbstractNode) : (AbstractNode,Move) = {
    val depth = n.data.phase match{
      case MyPhase.Phase1 => 5
      case MyPhase.Phase2 => 6
      case MyPhase.Phase3 => 5
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
}
