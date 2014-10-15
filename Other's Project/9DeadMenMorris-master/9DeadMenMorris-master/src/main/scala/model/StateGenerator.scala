package main.scala.model

import main.scala.model.Moves._

/**
 * Created by tmnd on 26/05/14.
 */

object StateGenerator {
  def nextStates(s : MyState) : List[MyState] = {
    s.phase match {
      case MyPhase.Phase1 => phaseOneNextStates(s)
      case MyPhase.Phase2 => phaseTwoNextStates(s)
      case MyPhase.Phase3 => phaseThreeNextStates(s)
      case _ => throw new IllegalArgumentException("Nonsense Phase")
    }
  }

  private def phaseOneNextStates(s : MyState) : List[MyState] = {
    var toRet = List[MyState]()
    for (p <- s.emptyPositions){
      if (s.moveCreatesMill(PutMove(p),s.toMove))
        for(pp <- s.removablePieces(!s.toMove))
          toRet ::= s.stateAfterMove(PutRemoveMove(p,pp))
      else
        toRet ::= s.stateAfterMove(PutMove(p))
    }
    toRet
  }

  private def phaseTwoNextStates(s : MyState) : List[MyState] = {
    var toRet = List[MyState]()
    val pcs = s.pieces(s.toMove)
    for (pc <- pcs){
      val possibleDest = pc.neighbourhood(0).map(p => s.positions(p)).filter(p => p.content==None) :::
        pc.neighbourhood(1).map(p => s.positions(p)).filter(p => p.content==None)
      for(p <- possibleDest){
        if(s.moveCreatesMill(ShiftMove(pc,p),s.toMove))
          for(pp <- s.removablePieces(!s.toMove))
            toRet ::= s.stateAfterMove(ShiftRemoveMove(pc,p,pp))
        else
          toRet ::= s.stateAfterMove(ShiftMove(pc,p))
      }
    }
    toRet
  }

  private def phaseThreeNextStates(s : MyState) : List[MyState] = {
    //sono io in fase 3 o l'altro ? :D
    val myPcs = s.pieces(s.toMove)
    if (myPcs.length>3)
      phaseTwoNextStates(s)
    else{
      var toRet = List[MyState]()
      for (pp <- myPcs;
           p <- s.emptyPositions) {
        if (s.moveCreatesMill(FlyMove(pp,p), s.toMove))
          for (ppp <- s.removablePieces(!s.toMove))
            toRet ::= s.stateAfterMove(FlyRemoveMove(pp,p,ppp))
        else
            toRet ::= s.stateAfterMove(FlyMove(pp, p))
      }
      toRet
    }
  }

}
