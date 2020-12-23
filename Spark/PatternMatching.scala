import scala.util.Random


object PatternMatching extends App {

  // switch on steroids
  
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    case Person(n, a) if a < 21 => s"Hi, my name is $n and I can't drink in the US"
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
    case _ => "I don't know who I am"
  }
  println(greeting)

  
sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal
  case class Horse(breed:String) extends Animal

  val animal: Animal = Dog("Terra Nova")
  animal match {
    case Dog("Terra Nova") | Parrot ("somebreed") => println("dog or parrot")
    case Horse("breed") => print("I'm a horse")
  }

// 3 - tuples
    val aTuple = (1,2)
    val matchATuple = aTuple match {
      case (1, 1) =>
      case (something, 2) => s"I've found $something"
    }

    val nestedTuple = (1, (2, 3))
    val matchANestedTuple = nestedTuple match {
      case (_, (2, v)) =>
    }
    // PMs can be NESTED!


    // 5 - list patterns
    val aStandardList = List(1,2,3,42)
    val standardListMatching = aStandardList match {
      case List(1, _, _, _) => // extractor - advanced
      case List(1, _*) => // list of arbitrary length - advanced
      case 1 :: List(_) => // infix pattern
      case List(1,2,3) :+ 42 => // infix pattern
    }
  
  //with Lists
  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"the only element is $head.")
    case _ =>
  }
  
   // generators are also based on PATTERN MATCHING
  val tuples = List((1,2), (3,4))
  val filterTuples = for {
    (first, second) <- tuples
  } yield first * second
  
  
  // big idea #3
  val tuple = (1,2,3)
  val (a, b, c) = tuple
  println(b)
  // multiple value definitions based on PATTERN MATCHING
  // ALL THE POWER

  val head :: tail = list
  println(head)
  println(tail)

  // big idea #4 - NEW
  // partial function based on PATTERN MATCHING
  val mappedList = list.map {
    case v if v % 2 == 0 => v + " is even"
    case 1 => "the one"
    case _ => "something else"
  } // partial function literal
  
  println(mappedList)
  
  
  //In case we have a class but we can't make a case class for patter matching we can create an object with the unapply method
  object even {
    def unapply(arg: Int): Boolean = arg % 2 == 0
  }

  object singleDigit {
    def unapply(arg: Int): Boolean = arg > -10 && arg < 10
  }

  val n: Int = 8
  val mathProperty = n match {
    case singleDigit() => "single digit"
    case even() => "an even number"
    case _ => "no property"
  }

  println(mathProperty)
  
}
