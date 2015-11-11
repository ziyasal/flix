package ca.uwaterloo.flix.util.misc

object ScalaNative {
  // Note that these values are compiled as a static method
  val strTuple2 = ("foo", "bar")
  val tuple6 = (true, false, 1, 5, ("foo", "bar"), 42)
  val set = Set(1, 2, 3)
  val setOfTuples = Set((0, "zero"), (1, "un"), (2, "deux"), (3, "trois"), (4, "quatre"), (5, "cinq"))
  val tupleOfSets = (Set(0, 1, 2, 3), Set("zero", "un", "deux", "trois"), Set("zero", "one", "two", "three"))

  def mkTuple2(t1: Int, t2: Int) = (t1, t2)
  def incrTuple2(t: (Int, Int)) = (t._1 + 1, t._2 + 1)
  def mkTuple3(t1: Int, t2: Int, t3: Int) = (t1, t2, t3)
  def incrTuple3(t: (Int, Int, Int)) = (t._1 + 1, t._2 + 1, t._3 + 1)
  def mkTuple4(t1: Int, t2: Int, t3: Int, t4: Int) = (t1, t2, t3, t4)
  def incrTuple4(t: (Int, Int, Int, Int)) = (t._1 + 1, t._2 + 1, t._3 + 1, t._4 + 1)
  def mkTuple5(t1: Int, t2: Int, t3: Int, t4: Int, t5: Int) = (t1, t2, t3, t4, t5)
  def incrTuple5(t: (Int, Int, Int, Int, Int)) = (t._1 + 1, t._2 + 1, t._3 + 1, t._4 + 1, t._5 + 1)

  def reverseTuple6(t: (Boolean, Boolean, Int, Int, (String, String), Int)) = (t._6, t._5, t._4, t._3, t._2, t._1)

  def mkSet(i: Int) = (0 until i).toSet
  def incrSetBy(s: Set[Int], i: Int) = s.map(_ + i)
}