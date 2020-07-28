package com.sundogsoftware.spark

import scala.collection.immutable
import scala.collection.mutable

object Basics {
  def main(args: Array[String]) {

    //PRINT
    val phoneName= "Titanic"
    println(s"Name: $phoneName")

    //TUPLES

    val myTup2 = Tuple2(4, "iFruit")
    println(myTup2)
    println(myTup2._1)
    println(myTup2._2)
    println(myTup2.swap)

    val myTup6 = ("Plato", "Kant", "Voltaire", "Descartes", "deBeauvoir", "Camus")
    val myStr = myTup6.toString
    println(myTup6)
    println(myTup6._1.sorted)

    println(myTup6.productElement(2))

    //CASE
    val phoneWireless= "enabled"
    var msg = "Radio state Unknown"

    phoneWireless match {
      case "enabled"    => msg = "Radio is On"
      case "disabled"   => msg = "Radio is Off"
      case "connected"  => msg = "Radio On, Protocol Up"
    }

    println(msg)

    //FUNCTIONS

    //Con argumentos
    def CtoF(celsius: Double) = {
      (celsius * 9 / 5) + 32
    }

    //Pasar funciones como parametro
    def convertList(myList:List[Double], convert:(Double) => Double) {

      for(n <- myList)
        println(n,convert(n)) //al llamar a convert es como si llamara a CtoF
    }

    val phoneCelsius = List(34.0, 23.5, 12.2)
    convertList(phoneCelsius, CtoF)

    //funciones anonimas
    convertList(phoneCelsius, cc => (cc * 9 / 5) + 32)

    //CONVERTIONS
    val myList = List("Titanic", "F01L", "enabled", 32)
    val myArray = myList.toArray

    println("de list a iterable")
    val myIterable= myList.toIterable

    println("de iterable a list")
    val myList2 = myIterable.toList

    println("De array a list")
    val myList3 = myArray.toList

    println("De tuple a list")
    val myTup = (4, "MeToo", "1.0", 37.5, 41.3, "Enabled")
    val myList4 = myTup.productIterator.toList

    println("de String a array")
    val myStr = "A Banana"
    val myArray2 = myStr.toArray
    myStr.toList
    myStr.toSeq
    myStr.toSet


    //Collections

    println("-----SEQ------")
    println("En SEQ puedes acceder por index y preguntar si contiene un valor")
    val mySeq = Seq("MeToo", "Ronin", "iFruit")
    println(mySeq(0))
    println(mySeq.contains("manolo"))

    println("-----MAP------")
    println("los maps son inmutables, para que sean mutables..")
    println("val mutRec = scala.collection.mutable.Map(..")
    val myMap: Map[Int,String] = Map(1 -> "a", 2 -> "b")
    val myMap2 = Map(1 -> "a", 2 -> "b")
    println(myMap2.contains(1))
    println(myMap2.keys)
    println( myMap2.values)

    println("-----SET------")
    println("SET no contiene duplicados, le puedes preguntar si contiene un valor")
    val mySet = Set("Titanic", "Sorrento", "Ronin", "Titanic", "Sorrento", "Ronin")
    mySet.foreach(println)
    println(mySet("manolo"))

    println("-----LIST------")
    println("En list puedes acceder por indice,Lists can contain	Collection	and	Tuple elements	as	well	as	simple	types")
    println("Flexible	size –Elements	are	immutable,	so	they	cannot	be	changed	by	assignment")
    println("Fast	addition	and	removal	at	head")
    println("Slow	access	to	arbitrary	indexes")
    val models = List("Titanic", "Sorrento", "Ronin")
    val devices = List(("Sorrento", 10), ("Sorrento", 20), ("iFruit", 30))
    println(devices(1))

    println("-----BufferList------")
    println("Como list pero mutable")
    println("Flexible	size")
    println("Elements	are	mutable")
    println("Constant	time	append	and	prepend	operations")
    val listBuf = scala.collection.mutable.ListBuffer.empty[Int]
    listBuf += 17
    listBuf += 29
    listBuf.foreach(println)
    listBuf -= 17
    listBuf.foreach(println)


    println("-----Arrays------")
    println("no mutable, pero puedes actualizar los valores")
    println("Created	with	a	fixed	number	of	elements	and	not	resizable")
    println("Fast	access	to	arbitrary	indexes")
    val devs = Array("iFruit", "MeToo", "Ronin")
    println(devs(2))
    val devi: Array[String] = new Array[String](4)
    devi(0)="first"
    devi(1)="second"

    println("-----Vector------")
    println("Vector has	flexible	size and can be updated")
    var vec = Vector(1, 18, 6)
    vec.updated(1, 30)
    vec = vec :+ 5
    println(vec)


    //LOOPS
    val sorrentoPhones= List("F00L", "F01L", "F10L", "F11L", "F20L", "F21L", "F22L", "F23L", "F24L")


    //While
    var i = 0
    while (i < sorrentoPhones.length)
    {
      println(sorrentoPhones(i))
      i = i + 1
    }
    //while con iterador
    val iter = sorrentoPhones.toIterator
    while (iter.hasNext) {
      print(iter.next+ " ")
    }
    
    //For usando la i, no recomendable debido a problemas de escalabilidad

    for (i <- 0 to sorrentoPhones.length- 1) {
      println(sorrentoPhones(i))
    }

    for (i <- 0 until sorrentoPhones.length) {
      println(sorrentoPhones(i))
    }

    for (i <- 0 until sorrentoPhones.length by 2) {
      println(sorrentoPhones(i))
    }

    //For sin usar variable local i
    for (model <- sorrentoPhones) {
      print(model + " ")
    }

    //fors anidados
    val phonebrands= List("iFruit", "MeToo")
    val newmodels= List("Z1", "Z-Pro")
    for (brand <- phonebrands; model <- newmodels) {
      println(brand + " " + model)
    }
    
 //Some - None
 
val a = Map("foo" -> "bar", "bar" -> "foo")
a.get("foo") //returns Some(bar)
a.get("nothing") //returns None


val r = a.get("foo") match {
  case Some(_) => println("we matched") //returns we matched
  case None => println("or did we")
}
