import org.apache.spark.sql.SparkSession

object HelloSpark {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("spark test")
      .master("local[*]")
      .getOrCreate()
    //println("spark Version: " + spark.version)

    spark.stop()

  }
}