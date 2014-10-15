package main.scala.model.Heuristic

import main.scala.model.MyState

abstract class Heuristic {
  def calc(actual : MyState, future : MyState, player : Boolean) : Float
}

object Heuristic {
  def defaultHeuristic = new ConcreteHeuristic()
}