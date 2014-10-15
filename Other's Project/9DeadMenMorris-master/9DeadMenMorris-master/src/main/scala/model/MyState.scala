package main.scala.model

import main.scala.model.Moves._
import main.scala.model.MyPhase.MyPhase
import java.util.StringTokenizer

class MyState (val toMove : Boolean,
               val move : Move,
               val phase : MyPhase = MyPhase.Phase1,
               val positions : Map[String,Position] = MyState.emptyPositions,
               val removed : Map[Boolean,Int] = Map(true->0,false->0)){

  private val _pieces = scala.collection.mutable.Map[Boolean,Option[List[Position]]](true->None,false->None)
  private var _bPieces = scala.collection.mutable.Map[Boolean,Option[Int]](true->None,false->None)

  require(move!=null)
  if (onBoard(true)+removed(true)>9)
    require(onBoard(true)+removed(true)<=9)
  if (onBoard(false)+removed(false)>9)
    require(onBoard(false)+removed(false)<=9)

  //kinda visitor
  def stateAfterMove(m : Move) : MyState = {
    m match{
      case p  : PutMove         => stateAfterMove(p)
      case pr : PutRemoveMove   => stateAfterMove(pr)
      case s  : ShiftMove       => stateAfterMove(s)
      case sr : ShiftRemoveMove => stateAfterMove(sr)
      case f  : FlyMove         => stateAfterMove(f)
      case fr : FlyRemoveMove   => stateAfterMove(fr)
      case _                    => throw new Exception("unsupported move")
    }
  }

  private def stateAfterMove(m : PutMove) = {
    val actualP = positions(m.d.name)
    require(phase==MyPhase.Phase1)
    require(actualP.content==None)
    val newMap = positions+(actualP.name->new Position(actualP.name,Some(toMove)))
    val allPedine = newMap.values.count(p => p.content!=None) + removed(true) + removed(false)
    val newPhase = if (allPedine >= 18) MyPhase.Phase2
                   else MyPhase.Phase1
    new MyState(!toMove,PutMove(actualP),newPhase,newMap,removed)
  }

  private def stateAfterMove(m : PutRemoveMove) = {
    val actualP = positions(m.d.name)
    val actualToRem = positions(m.toRemove.name)
    require(phase==MyPhase.Phase1)
    require(actualP.content==None)
    require(isLegalToRemove(actualToRem))
    val newMap = positions+(actualP.name->new Position(actualP.name,Some(toMove))) +
                  (actualToRem.name->new Position(actualToRem.name))
    val newRemoved = removed+(actualToRem.content.get->(removed(actualToRem.content.get)+1))
    val allPedine = newMap.values.count(p => p.content!=None) + newRemoved(true) + newRemoved(false)
    val newPhase = if (allPedine == 18) MyPhase.Phase2
    else MyPhase.Phase1
    new MyState(!toMove,PutRemoveMove(actualP,actualToRem),newPhase,newMap,newRemoved)
  }

  private def stateAfterMove(m : ShiftMove) = {
    val actualO = positions(m.o.name)
    val actualD = positions(m.d.name)
    if (onBoard(toMove)>3 && onBoard(!toMove)==3)
      require(phase==MyPhase.Phase2 || phase==MyPhase.Phase3)
    else
      require(phase==MyPhase.Phase2)
    require(actualO.content==Some(toMove))
    require(actualD.content==None)
    if (phase == MyPhase.Phase2 || onBoard(toMove)>3) //anche se siamo in phase3 ma io ho più pedine non posso muovere ovunque ;)
      require(positions(actualO.name).isNeighbourOf(positions(actualD.name)))
    val newMap = positions + (actualO.name->new Position(actualO.name)) +
                  (actualD.name->new Position(actualD.name,Some(toMove)))
    new MyState(!toMove,ShiftMove(actualO,actualD),phase,newMap,removed)
  }

  private def stateAfterMove(m : ShiftRemoveMove) = {
    val actualO = positions(m.o.name)
    val actualD = positions(m.d.name)
    val actualToRem = positions(m.toRemove.name)
    if (onBoard(toMove)>3 && onBoard(!toMove)==3)
      require(phase==MyPhase.Phase2 || phase==MyPhase.Phase3)
    else
      require(phase==MyPhase.Phase2)
    require(actualO.content==Some(toMove))
    require(actualD.content==None)
    require(isLegalToRemove(actualToRem))
    if (phase == MyPhase.Phase2 || onBoard(toMove)>3) //anche se siamo in phase3 ma io ho più pedine non posso muovere ovunque ;)
      require(positions(actualO.name).isNeighbourOf(positions(actualD.name)))
    val newMap = positions + (actualO.name->new Position(actualO.name)) +
      (actualD.name->new Position(actualD.name,Some(toMove))) +
      (actualToRem.name->new Position(actualToRem.name))
    val newRemoved = removed + (actualToRem.content.get -> (removed(actualToRem.content.get)+1))
    val newPhase = if (newRemoved(!toMove)==6 || newRemoved(toMove)==6) MyPhase.Phase3
                   else MyPhase.Phase2
    new MyState(!toMove,ShiftRemoveMove(actualO,actualD,actualToRem),newPhase,newMap,newRemoved)
  }

  private def stateAfterMove(m : FlyMove) = {
    val actualO = positions(m.o.name)
    val actualD = positions(m.d.name)
    require(actualO.content==Some(toMove))
    require(actualD.content==None)
    require(phase==MyPhase.Phase3)
    val newPositions = positions + (actualO.name -> new Position(actualO.name)) +
      (actualD.name -> new Position(actualD.name,Some(toMove)))

    new MyState(!toMove,FlyMove(actualO,actualD),phase,newPositions,removed)
  }

  private def stateAfterMove(m : FlyRemoveMove) = {
    val actualO = positions(m.o.name)
    val actualD = positions(m.d.name)
    val actualToRem = positions(m.toRemove.name)
    require(actualO.content==Some(toMove))
    require(actualD.content==None)
    require(phase==MyPhase.Phase3)
    require(isLegalToRemove(actualToRem))
    val newPositions = positions + (actualO.name -> new Position(actualO.name)) +
      (actualD.name -> new Position(actualD.name,Some(toMove))) + (actualToRem.name -> new Position(actualToRem.name))
    val newRemoved = removed + (actualToRem.content.get -> (removed(actualToRem.content.get)+1))
    new MyState(!toMove,FlyMove(actualO,actualD),phase,newPositions,newRemoved)
  }

  def onBoard(c : Boolean) : Int = {
    if (_pieces(c)==None)
      pieces(c)
    _pieces(c).get.size
  }

  def nrPieces : Map[Boolean,Int] = {
    val t = onBoard(true)
    val f = onBoard(false)
    Map[Boolean,Int]() + (true->t) + (false->f)
  }

  def eaten(c : Boolean) : Int = removed(c)

  def isLegalToRemove(p : Position, c : Boolean = !toMove) : Boolean = !isPartOfMill(p,c)

  def removable(c : Boolean) : List[Position] = positions.values.filter(p => isLegalToRemove(p,!c)).toList

  def hasWon(c : Boolean) : Boolean = if (phase==MyPhase.Phase1) false
                                      else onBoard(!c)<3 || cantMove(!c)

  def hasLost(c : Boolean) : Boolean = hasWon(!c)

  def cantMove(c : Boolean) : Boolean = {
    var free = 0
    for (p <- positions.values.filter(p => p.content == Some(c))){

      free+=(p.neighbourhood(0).map(s => positions(s)) ++
              p.neighbourhood(1).map(s => positions(s))).
        count(pp => pp.content==None) //conto le posizione vicine libere
      if (free>0) //se sono più di zero posso muovermi
        return false
    }
    free==0 //se sono zero allora non posso muovermi :( LOST
  }

  def pieces(c : Boolean) : List[Position] = {
    if (_pieces(c) == None)
      _pieces(c) = Some(positions.values.filter(p => p.content == Some(c)).toList)
    _pieces(c).get
  }

  def blockedPieces(c : Boolean) : Int = {
    if (_bPieces(c)==None)
      _bPieces(c) = Some(pieces(c).filter(p => isBlocked(p)).size)
    _bPieces(c).get
  }

  def isBlocked(p : Position) : Boolean = {
    (positions(p.name).neighbourhood(0).map(s => positions(s)) ++
      positions(p.name).neighbourhood(1).map(s => positions(s))).
      filter(pp => pp.content==None).size==0 //se tra i neighbours ce ne sono 0 None allora è blocked
  }

  def closedMills(c : Boolean) : Int = {
    var toCheck = pieces(c)
    var toRet = 0
    while(toCheck.size>0){
      val p = toCheck.head
      require(p.content!=None)
      require(p.content.get==c)
      if (isPartOfMill2(p,c,0)){
        toCheck = toCheck.filterNot(pp => pp isInCol p.col)
        toRet += 1
      }
      if (isPartOfMill2(p,c,1)){
        toCheck = toCheck.filterNot(pp => pp isInRow p.row)
        toRet += 1
      }
      toCheck = toCheck.filterNot(pp => pp==p)
    }
    toRet
  }

  def doubleMorris(c : Boolean) : Int = {
    var toCheck = pieces(c)
    var toRet = 0
    while(toCheck.size>0){
      val p = toCheck.head
      require(p.content == Some(c))
      if (isPartOfMill2(p,c,0) && isPartOfMill2(p,c,1)){
        toCheck = toCheck.filterNot(pp => ((pp isInCol p.col) || (pp isInRow p.row)))
        toRet+=1
      }
      toCheck = toCheck.filterNot(pp => pp==p)
    }
    toRet
  }

  def openedMorris(c : Boolean) : Int = {
    var toRet = 0
    var checked = Array[List[Position]](List(),List())
    for(p <- pieces(c);
        i <- 0 to 1;
        if !(checked(i) contains p)){
      if (p.neighbourhood(i).size == 2){
        val ns = p.neighbourhood(i).map(s => positions(s))
        if (ns(0).content == Some(c) && ns(1).content == None &&
          ns(1).neighbourhood(oppoDir(i)).map(s => positions(s)).count(p => p.content == Some(c))>0) toRet += 1
        else
          if (ns(1).content == Some(c) && ns(0).content == None &&
            ns(0).neighbourhood(oppoDir(i)).map(s => positions(s)).count(p => p.content == Some(c))>0) toRet +=1
      }
      else{
        val n = positions(p.neighbourhood(i).head)
        val nextn = n.neighbourhood(i).map(pp => positions(pp)).filter(pp => pp!=p).head
        if (n.content == Some(c) && nextn.content==None &&
          nextn.neighbourhood(oppoDir(i)).map(s => positions(s)).count(p => p.content == Some(c))>0) toRet+=1
        else
          if (n.content == None && nextn.content == Some(c) &&
            n.neighbourhood(oppoDir(i)).map(s => positions(s)).count(p => p.content == Some(c))>0) toRet+=1
      }
    }
    toRet
  }

  def oppoDir (x : Int) : Int = Math.abs(x-1)

  val emptyPositions : List[Position] = positions.values.filter(p => p.content==None).toList

  def removablePieces(c : Boolean) : List[Position] = pieces(c).filterNot(p => isPartOfMill(p,c))

  def moveCreatesMill(m : Move, c : Boolean) : Boolean = stateAfterMove(m).isPartOfMill(m.d,c)

  def isPartOfMill(p : Position, c : Boolean) : Boolean = {
    val actualPos = positions(p.name)
    if (actualPos.content == Some(c))
      isPartOfMill2(actualPos, c, 0) || isPartOfMill2(actualPos, c, 1)
    else
      false
  }

  private def isPartOfMill2(p : Position, c : Boolean, d : Int) : Boolean = {
    val neighbours = p.neighbourhood(d).map(s => positions(s))
    if (neighbours.size == 2)
    {
      neighbours(0).content == Some(c) && neighbours(1).content == Some(c)
    }
    else{
      val neigh = neighbours.head
      neigh.content == Some(c) && neigh.neighbourhood(d).map(s => positions(s)).filterNot(pp => pp == p).head.content == Some(c)
    }
  }

  def twoPcsConf(c : Boolean) : Int = {
    var toRet = 0
    var checked = Array[List[Position]](List(),List())
    for (p <- pieces(c);
         i <- 0 to 1;
         if !checked(i).contains(p)){
      if(p.neighbourhood(i).length==2){
        val neighbours = p.neighbourhood(i).map(s=>positions(s))
        checked(i) :::= neighbours
        if (neighbours.count(pp => pp.content==p.content)==1 &&
            neighbours.count(pp => pp.content==None)==1)
          toRet+=1
      }
      else{ //lunghezza 1
        val pp = positions(p.neighbourhood(i).head)
        val ppp = pp.neighbourhood(i).map(s => positions(s)).filter(ppp => ppp!=p).head
        checked(i):::= List(pp,ppp)
        if ((pp.content == p.content && ppp.content == None) ||
            (ppp.content == p.content && pp.content == None))
          toRet+=1
      }
    }
    toRet
  }

  def threePcsConf(c : Boolean) : Int = {
    var toRet = 0
    for(conf <- MyState.ThreePcsConf)
      if (conf.count(s => positions(s).content==Some(c))==3)
        toRet+=1
    toRet
  }

  override def toString : String = {
    var toRet = ""
    toRet+="7"+positions("A7")+"--"+positions("D7")+"--"+positions("G7")+"\n"
    toRet+="6-"+positions("B6")+"-"+positions("D6")+"-"+positions("F6")+"-\n"
    toRet+="5--"+positions("C5")+positions("D5")+positions("E5")+"--\n"
    toRet+="4"+positions("A4")+positions("B4")+positions("C4")+"-"+
              positions("E4")+positions("F4")+positions("G4")+"\n"
    toRet+="3--"+positions("C3")+positions("D3")+positions("E3")+"--\n"
    toRet+="2-"+positions("B2")+"-"+positions("D2")+"-"+positions("F2")+"-\n"
    toRet+="1"+positions("A1")+"--"+positions("D1")+"--"+positions("G1")+"\n"
    toRet+=" abcdefg"
    toRet
  }

  def toStateString : String = {
    val delimiter = "_"
    var s = (toMove match{
      case true  => "1"
      case false => "0"
    })
    s +=delimiter

    s += (phase match {
      case MyPhase.Phase1 => "1"
      case MyPhase.Phase2 => "2"
      case MyPhase.Phase3 => "3"
    })
    s += delimiter

    s += move.toStr + delimiter

    positions.foreach(kv => s += kv._2.toStr + kv._1 + delimiter)

    s += removed(true) + delimiter
    s += removed(false) + delimiter

    return s
  }
}

object MyState{
    val emptyPositions : Map[String,Position] =
        Map(
          ("A1", new Position("A1")),
          ("D1", new Position("D1")),
          ("G1", new Position("G1")),
          ("A7", new Position("A7")),
          ("D7", new Position("D7")),
          ("G7", new Position("G7")),
        
          ("B2", new Position("B2")),
          ("D2", new Position("D2")),
          ("F2", new Position("F2")),
          ("B6", new Position("B6")),
          ("D6", new Position("D6")),
          ("F6", new Position("F6")),
        
          ("C3", new Position("C3")),
          ("D3", new Position("D3")),
          ("E3", new Position("E3")),
          ("C5", new Position("C5")),
          ("D5", new Position("D5")),
          ("E5", new Position("E5")),
        
          ("A4", new Position("A4")),
          ("B4", new Position("B4")),
          ("C4", new Position("C4")),
          ("E4", new Position("E4")),
          ("F4", new Position("F4")),
          ("G4", new Position("G4"))
        )
  def map(m : Map[Boolean,List[String]]) : Map[String,Position] = {
    var toRet : Map[String,Position] = emptyPositions
    for (k <- m.keys){
      for (v <- m(k)){
        toRet += (v -> new Position(v,Some(k)))
      }
    }
    toRet
  }

  val ThreePcsConf = List(
    List("A1","A7","G1"),
    List("G1","A7","G7"),
    List("G1","A1","G7"),
    List("A1","A7","G7"),

    List("B2","B6","F2"),
    List("B6","F6","F2"),
    List("B2","F2","F6"),
    List("B2","B6","F6"),

    List("C5","C3","E3"),
    List("C3","E5","E3"),
    List("C5","E5","E3"),
    List("C3","C5","E5")
  )
  def stateFromStr(s : String) : MyState = {
    val tokenizer = new StringTokenizer(s,"_")
    val toMove = tokenizer.nextToken match {
      case "0" => false
      case "1" => true
      case _   => throw new Exception("Invalid player")
    }

    val phase = tokenizer.nextToken match {
      case "1" => MyPhase.Phase1
      case "2" => MyPhase.Phase2
      case "3" => MyPhase.Phase3
      case _   => throw new Exception("Invalid Phase")
    }

    val move = Move.moveFromStr(tokenizer.nextToken)

    var positions : Map[String,Position] = Map()

    for (i <- 0 to 23) {
      val token = tokenizer.nextToken
      val pl = token.head
      val po = token.tail
      pl match {
        case 'x' => positions += (po -> new Position(po,Some(true)))
        case 'o' => positions += (po -> new Position(po,Some(false)))
        case '.' => positions += (po -> new Position(po))
        case _   => new Exception("Invalid content")
      }
    }

    val removed = Map((true,  tokenizer.nextToken.toInt),
                      (false, tokenizer.nextToken.toInt))

    new MyState(toMove,move,phase,positions,removed)

  }
  /*
  def fromString(toMove : Boolean, move : String, phase : Int, board : String, removedT : Int, removedF : Int) : MyState = {

    new MyState(toMove,Move.strToMove)
  }
  */
}