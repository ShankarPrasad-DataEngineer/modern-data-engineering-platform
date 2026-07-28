import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.expressions.Cube
import org.apache.spark.sql.functions._

object ScalaBasics {

  def main(args:Array[String]): Unit = {

    //Scala Variables 2 types
    var a = 10 //This is mutable means value of the variable can be changed
    val b = 20 //This is immutable means value of the variable cannot be changed

    //If else statement
    if (a >= 10 && b <= 20){
      println("a is greater than or equal to 10 and b is less than or equal to 20")
    } else {
      println("a is less than 10 or b is greater than 20")
    }



    // Call a function inside a function

    //Function 1
    def cube(x: Int): Int ={
      x * x * x
    }
    println(cube(3))

    //Function 2
    def fun2(x: Int, cube: Int => Int): Int = {
      cube(x)
    }
    val result = fun2(3, cube)
    println(result)
  }

}

