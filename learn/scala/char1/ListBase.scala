package char1

/**
  * Created by Anur IjuoKaruKas on 2019/6/10
  */
object ListBase extends App {

    val firstList = List(1, 2)
    val secondList = List(2, 3)

    val thirdList = firstList ::: secondList

    println(firstList)
    println(secondList)

    println(thirdList)

    var firstListVar = List(1, 2)
    var secondListVar = List(2, 3)

    var thirdListVar = firstListVar :: secondListVar

    println(firstListVar)
    println(secondListVar)

    println(thirdListVar)


    println(thirdList:::List(1,2,3):::thirdList:::Nil)

    println(firstListVar:::Nil)
}
