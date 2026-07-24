import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object EmployeesDataFrameDemo {

  def main(args:Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Employee Dataframe Demo")
      .master("local[*]")
      .getOrCreate()
  }

}
