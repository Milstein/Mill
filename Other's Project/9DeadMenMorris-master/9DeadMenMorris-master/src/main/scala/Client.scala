package main.scala

import ga.Solution
import it.unibo.ai.didattica.mulino.actions.Phase1Action
import it.unibo.ai.didattica.mulino.actions.Action
import it.unibo.ai.didattica.mulino.client.MulinoClient
import it.unibo.ai.didattica.mulino.domain.State
import it.unibo.ai.didattica.mulino.engine.TCPMulino
import java.io._
import java.net.Socket

import main.scala.model.Heuristic.{Heuristic, ConcreteHeuristic}
import main.scala.model.Moves.{Move, NoMove}
import main.scala.model.{MyPhase, MyState}
import main.scala.model.Tree.{MemoryNode, SlickNode, AbstractNode}
import scala.slick.driver.H2Driver.simple._
import scala.language.implicitConversions

import util.Converter._


/**
 * Created by tmnd on 01/06/14.
 */
class Client {
  private var player: State.Checker = null
  private var playerSocket: Socket = null
  private var in: ObjectInputStream = null
  private var out: ObjectOutputStream = null

  def this(player: State.Checker) {
    this()
    this.player = player
    var port: Int = 0
    player match {
      case State.Checker.WHITE => port = TCPMulino.whiteSocket
      case State.Checker.BLACK => port = TCPMulino.blackSocket
      case _ =>
        System.exit(5)
    }
    playerSocket = new Socket("localhost", port)
    out = new ObjectOutputStream(playerSocket.getOutputStream)
    in = new ObjectInputStream(new BufferedInputStream(playerSocket.getInputStream))
  }

  def write(action: Action) {
    out.writeObject(action)
  }

  def read: State = {
    return in.readObject.asInstanceOf[State]
  }

  def getPlayer: State.Checker = {
    return player
  }

  def setPlayer(player: State.Checker) {
    this.player = player
  }
}

object Client {
  implicit var costCalculator = Heuristic.defaultHeuristic
  def main(args: Array[String]) {
    var player: State.Checker = null
    if (args.length == 0) {
     // System.out.println("You must specify which player you are (White or Black)!")
      System.exit(-1)
    }

    if (args.length == 2) {
      val v = (Solution.getIntValues(16,args(1)))
      costCalculator = new ConcreteHeuristic(v(0),v(1),v(2),v(3),v(4),
                                             v(5),v(6),v(7),v(8),v(9),
                                             v(10),v(11),v(12),v(13),v(14),
                                             v(15),v(16),v(17),v(18))
    }
    //System.out.println("Selected client: " + args(0))
    if ("white".equalsIgnoreCase(args(0))) player = State.Checker.WHITE
                                      else player = State.Checker.BLACK
    var currentState: State = null
    var myCurrentState: AbstractNode = null
    //adding root
    if (player eq State.Checker.WHITE) {
      implicit val toMove = true
      val client: MulinoClient = new MulinoClient(State.Checker.WHITE)
      //System.out.println("You are player " + client.getPlayer.toString + "!")
      //System.out.println("Current state:")
      currentState = client.read
      myCurrentState = currentState
      SlickNode.create
      SlickNode.addAll(List[SlickNode](myCurrentState.asInstanceOf[SlickNode]))
      myCurrentState = SlickNode.root
      //System.out.println(currentState.toString)
      while (true) {
        //System.out.println("Player " + client.getPlayer.toString + ", do your move: ")
        val r = nextMove(myCurrentState)
        //println(r._1)
        //println(r._2)
        client.write(r._2)
        currentState = client.read
        //System.out.println("Effect of your move: ")
        //System.out.println(currentState.toString)
        //System.out.println("Waiting for your opponent move... ")
        SlickNode.drop
        SlickNode.create
        currentState = client.read
        myCurrentState = currentState
        SlickNode.addAll(List[SlickNode](myCurrentState.asInstanceOf[SlickNode]))
        myCurrentState = SlickNode.root
        //System.out.println("Your Opponent did his move, and the result is: ")
        //System.out.println(currentState.toString)
      }
    }
    else {
      implicit val toMove = false
      val client: MulinoClient = new MulinoClient(State.Checker.BLACK)
      currentState = client.read
      //System.out.println("You are player " + client.getPlayer.toString + "!")
      //System.out.println("Current state:")
      //System.out.println(currentState.toString)
      SlickNode.create
      while (true) {
        //System.out.println("Waiting for your opponent move...")
        currentState = client.read
        //System.out.println("Your Opponent did his move, and the result is: ")
        //System.out.println(currentState.toString)
        //System.out.println("Player " + client.getPlayer.toString + ", do your move: ")
        myCurrentState = currentState
        //println(myCurrentState.costCalculatorInstance)
        SlickNode.addAll(List(myCurrentState.asInstanceOf[SlickNode]))
        myCurrentState = SlickNode.root
        val r = nextMove(myCurrentState)
        //println(r._1)
        //println(r._2)
        client.write(r._2)
        currentState = client.read
        //System.out.println("Effect of your move: ")
        //System.out.println(currentState.toString)
        SlickNode.drop
        SlickNode.create
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
