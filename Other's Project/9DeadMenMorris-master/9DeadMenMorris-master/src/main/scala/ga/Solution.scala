package ga

import scala.util.Random

/**
 * Created by tmnd on 02/06/14.
 */
case class Solution(genes : String){
  val rand = Random
  def prole (s : Solution)(implicit mutationRatio : Float, crossingNumber : Int) : List[Solution] =
  {
    val prole = Array("","")
    var x = Set[Int]()
    while (x.size<crossingNumber)
      x += (rand.nextInt(genes.length-2)+1)
    val xList = x.toList.sorted
    var start = 0
    var turn = false
    for (n <- xList){
      if (turn){
        prole(0) ++= this.genes.slice(start,n)
        prole(1) ++= s.genes.slice(start,n)
      }
      else{
        prole(1) ++= this.genes.slice(start,n)
        prole(0) ++= s.genes.slice(start,n)
      }
      start = n
      turn = !turn
    }
    if (turn){
      prole(0) ++= this.genes.slice(start,this.genes.length)
      prole(1) ++= s.genes.slice(start,s.genes.length)
    }
    else{
      prole(1) ++= this.genes.slice(start,this.genes.length)
      prole(0) ++= s.genes.slice(start,s.genes.length)
    }
    val upperBound = (mutationRatio * genes.length).toInt
    for (i <- 0 until upperBound){
      val ind1 = rand.nextInt(genes.length)
      val ind2 = rand.nextInt(genes.length)
      prole(0) = prole(0).updated(ind1,Solution.not(prole(0).charAt(ind1)))
      prole(1) = prole(1).updated(ind2,Solution.not(prole(1).charAt(ind2)))
    }
    prole.map(g => Solution(g)).toList
  }
  override def toString : String = genes
}

object Solution extends App{

  implicit def Bool2Int(b : Boolean) : Int = if(b) 1 else 0
  def not(c : Char) = if(c=='1') '0' else '1'

  def Lottery(l : List[(Solution,Float)], maxN : Int) : (Solution, Solution) = {
    val rand = Random
    require(maxN <= l.length)
    var myList = l.sortBy(x => x._2).reverse.slice(0,maxN)
    var totPoints = 0f
    myList.foreach(x => totPoints+=x._2)
    val candidate1 = (rand.nextGaussian()%totPoints).toFloat
    val candidate2 = (rand.nextGaussian()%totPoints).toFloat
    var i = 0f
    val iterator1 = myList.iterator
    while(i<candidate1){
      val x = iterator1.next
      i += x._2
    }
    val actCandidate1 = iterator1.next._1
    val iterator2 = myList.iterator
    i = 0f
    while(i<candidate2){
      val x = iterator2.next
      i += x._2
    }
    val actCandidate2 = iterator2.next._1
    (actCandidate1,actCandidate2)
  }

  override def main(args : Array[String]) = {
    val rand = Random
    implicit val mutationRatio = 0.05f
    implicit val crossingNumber = 5
    val s1 = Solution(generateRandomBinaryString(19*16))
    val s2 = Solution(generateRandomBinaryString(19*16))
    println("padre : "+s1.genes)
    println("madre : "+s2.genes)
    var prole = List[Solution]()
    for (i <- 0 to 25)
      prole :::= (s1 prole s2)
    prole ::= s1
    prole ::= s2
    println(prole.map(s => getIntValues(16,s.genes)))
    /*val x : List[(Solution,Float)] = prole.map(z => (z,rand.nextFloat))
    val r = Lottery(x,50)
    val values1 = getIntValues(16,r._1.genes)
    val values2 = getIntValues(16,r._2.genes)
    println(values1)
    println(values2)
    //println(prole)
    */

  }

  def generateRandomBinaryString(length : Int) = {
    var toRet = ""
    for(i <- 0 until length)
      toRet += Random.nextInt(2)
    toRet
  }

  def getIntValues(length : Int,s : String) : List[Int] = {
    var i = 0
    var toRet = List[Int]()
    while(i<s.length){
      toRet ::= Bin2Integer(s.substring(i,i+length))
      i+= length
    }
    toRet
  }

  def Bin2Integer(x : String) : Integer = {
    if (x.charAt(0)=='0') //positivo
      return Integer.valueOf(x.substring(1),2)
    else{
      return -(Math.pow(2,x.length-1).toInt-Integer.valueOf(x.substring(1),2))
    }

  }
}
