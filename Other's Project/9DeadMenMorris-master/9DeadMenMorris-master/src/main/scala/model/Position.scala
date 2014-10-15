package main.scala.model

class Position(val name : String,
               val content : Option[Boolean] = None){
    //neighbourhood(0) = neighbours in NS direction
    //neighbourhood(1) = neighbours in WE direction
    val neighbourhood : Array[List[String]] = Position.Neighbours(this)

    def isNeighbourOf(p : Position) : Boolean = (neighbourhood(0) contains p.name) || (neighbourhood(1) contains p.name)
    def isEmpty : Boolean = content==None
    def row : Char = name(1)
    def col : Char = name(0)
    def isInRow(r : Char) : Boolean = row == r
    def isInCol(c : Char) : Boolean = col == c

    override def toString : String = {
      content match {
        case Some(true) => "x"
        case Some(false) => "o"
        case _ => "."
      }
    }

    def toStr : String = {
      content match {
        case Some(true) => "x"
        case Some(false) => "o"
        case _ => "."
      }
    }

    def coordinates = (""+col+row).toLowerCase

    override def equals(o: Any) = o match {
      case that: Position => that.name.equalsIgnoreCase(this.name)
      case _ => false
    }
}

object Position{
    /*
    returns the neighbours of the passed Position
    */
    def Neighbours(p : Position) : Array[List[String]] = {
        p.name.toUpperCase match {
            case "A1" => Array(List("A4"),List("D1"))
            case "D1" => Array(List("D2"),List("A1","G1"))
            case "G1" => Array(List("G4"),List("D1"))
            case "B2" => Array(List("B4"),List("D2"))
            case "D2" => Array(List("D1","D3"),List("B2","F2"))
            case "F2" => Array(List("F4"),List("D2"))
            case "C3" => Array(List("C4"),List("D3"))
            case "D3" => Array(List("D2"),List("C3","E3"))
            case "E3" => Array(List("E4"),List("D3"))
            case "A4" => Array(List("A1","A7"),List("B4"))
            case "B4" => Array(List("B2","B6"),List("A4","C4"))
            case "C4" => Array(List("C3","C5"),List("B4"))
            case "E4" => Array(List("E3","E5"),List("F4"))
            case "F4" => Array(List("F2","F6"),List("E4","G4"))
            case "G4" => Array(List("G1","G7"),List("F4"))
            case "C5" => Array(List("C4"),List("D5"))
            case "D5" => Array(List("D6"),List("C5","E5"))
            case "E5" => Array(List("E4"),List("D5"))
            case "B6" => Array(List("B4"),List("D6"))
            case "D6" => Array(List("D5","D7"),List("B6","F6"))
            case "F6" => Array(List("F4"),List("D6"))
            case "A7" => Array(List("A4"),List("D7"))
            case "D7" => Array(List("D6"),List("A7","G7"))
            case "G7" => Array(List("G4"),List("D7"))
            case _ => throw new IllegalArgumentException("Unrecognized position")
        }
    }
}