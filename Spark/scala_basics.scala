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
    println("En SEQ puedes acceder por index y preguntar si contiene un valor, head and tail appending prepending Sorting")
    val mySeq = Seq("MeToo", "Ronin", "iFruit")
    println(mySeq(0))
    println(mySeq.contains("manolo"))
    println(mySeq.reverse)
    println(mySeq.sorted)
    println(mySeq ++ Seq("another","added","name"))
    val range:Seq[Int] = 1 to 10 //1 until 10
    range.foreach(println)
    
    println("-----LIST------")
    println("En list puedes acceder por indice,Lists can contain	Collection	and	Tuple elements	as	well	as	simple	types")
    println("Flexible	size –Elements	are	immutable,	so	they	cannot	be	changed	by	assignment")
    println("Fast	addition	and	removal	at	head")
    println("Slow	access	to	arbitrary	indexes")
    val models = List("Titanic", "Sorrento", "Ronin")
    val devices = List(("Sorrento", 10), ("Sorrento", 20), ("iFruit", 30))
    println(devices(1))
    val aList = List(1,2,3)
    val prepended = 42 +: aList :+ 89 //List(42, 1, 2, 3, 89)
    val apples5 = List.fill(5)("apple") //List(apple, apple, apple, apple, apple)
    println(aList.mkString("-|-")) //1-|-2-|-3
    
    println("-----Arrays------")
    println("no mutable, pero puedes actualizar los valores")
    println("Created	with	a	fixed	number	of	elements	and	not	resizable")
    println("Fast	access	to	arbitrary	indexes")
    val devs = Array("iFruit", "MeToo", "Ronin")
    println(devs(2))
    val devi: Array[String] = new Array[String](4)
    val threeElements = Array.ofDim[String](3)
    devi(0)="first"
    devi(1)="second"

    println("-----Vector------")
    println("Vector has	flexible	size and can be updated")
    var vec = Vector(1, 18, 6)
    vec.updated(1, 30)
    vec = vec :+ 5
    println(vec)

    println("-----MAP------")
    println("los maps son inmutables, para que sean mutables..")
    println("val mutRec = scala.collection.mutable.Map(..")
    val myMap: Map[Int,String] =  Map()
    val myMap: Map[Int,String] = Map(1 -> "a", 2 -> "b")
    val myMap2 = Map(1 -> "a", 2 -> "b")
    
    myMap2 foreach (x => println (x._1 + "-->" + x._2))
    for ((k,v) <- myMap) printf("key: %s, value: %s\n", k, v)
    println(myMap2.contains(1))
    println(myMap2.keys)
    println( myMap2.values)

    println("-----SET------")
    println("SET no contiene duplicados, le puedes preguntar si contiene un valor")
    val mySet = Set("Titanic", "Sorrento", "Ronin", "Titanic", "Sorrento", "Ronin")
    mySet.foreach(println)
    println(mySet("manolo"))

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


    


    //LOOPS
    val sorrentoPhones= List("F00L", "F01L", "F10L", "F11L", "F20L", "F21L", "F22L", "F23L", "F24L")


    //While -- avoid loops in Scala use functional programing instead
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
  
  //Flatten
  List(List(1, 2), List(3, 4), List(5,6)).flatten //List[Int] = List(1, 2, 3, 4, 5, 6)
}
    
//Tailrec recursion in the last line of the function, useful for avoiding stack overflow
@tailrec
  def concatenateTailrec(aString: String, n: Int, accumulator: String): String =
    if (n <= 0) accumulator
    else concatenateTailrec(aString, n - 1, aString + accumulator)    
    
    
   //Call by Value - Call by Name 
  def calledByValue(x: Long): Unit = {
    println(x)
    println(x)
  }

  def calledByName(x: => Long): Unit = {
    println(x) 
    println(x)
  }

  calledByValue(System.nanoTime()) //2328821855021 - 2328821855021 -> The function nanoTime() is evaluated before being sent to the callByValue
  calledByName(System.nanoTime()) //2410996413033 - 2410996441417 -> The function nanoTime() is evaluated everytime is used inside callByName
    
    //Default arguments
    def savePicture(format: String = "jpg", width: Int = 1920, height: Int = 1080): Unit = println("saving picture")
  savePicture(width = 800)

  /*
    1. pass in every leading argument
    2. name the arguments
   */

  savePicture(height = 600, width = 800, format = "bmp")
    
    //The difference between objects and classes is that objects can not receive parameters
    //Objects are singleton by default
    
    object Person { // type + its only instance
    // "static"/"class" - level functionality
    val N_EYES = 2
    }
    val person1 = Person //you musnt instanciate with "new" 
    val person2 = Person
    println(person1 == person2) //true
    
    //You can either extend your object with App (object MyScalaAppRocks extends App) or create a main method (def main(String:Array))
    
    //Inheritance
    // constructors
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }
  class Adult(name: String, age: Int, idCard: String) extends Person(name,age) //you have to specify the parameters from the super class
   //if you want to prevent a class being override
    // preventing overrides
  // 1 - use final on member
  // 2 - use final on the entire class
  // 3 - seal the class = extend classes in THIS FILE, prevent extension in other files
    
    // traits vs abstract classes
  // 1 - traits do not have constructor parameters
  // 2 - multiple traits may be  inherited by the same class
  // 3 - traits = behavior, abstract class = "thing"
    // abstract
  abstract class Animal {
    val creatureType: String = "wild"
    def eat: Unit
  }
     // traits
  trait Carnivore {
    def eat(animal: Animal): Unit
    val preferredMeal: String = "fresh meat"
  }

  trait ColdBlooded
  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"
    def eat: Unit = println("nomnomnom")
    def eat(animal: Animal): Unit = println(s"I'm a croc and I'm eating ${animal.creatureType}")
  }
    
    
    
    // anonymous classes
    abstract class Animal {
    def eat: Unit
  }

  
  val funnyAnimal: Animal = new Animal { // anonymous class
    override def eat: Unit = println("ahahahahahaah")
  }
  /*
    equivalent with
    class AnonymousClasses$$anon$1 extends Animal {
      override def eat: Unit = println("ahahahahahaah")
    }
    val funnyAnimal: Animal = new AnonymousClasses$$anon$1
   */
