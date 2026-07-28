import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Row
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions.Window

object deduplicate {
  def main(args:Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Deduplicate Dataframe Demo")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._ //why after the spark session need to get clarity

    //val df = spark.read.option("header", "true").csv("src/main/resources/employee.csv")
    //using create dataframe
    val data = Seq(
      Row("U1", "E1", "2024-01-01 10:00:00", "view", 0),
      Row("U1", "E2", "2024-01-01 10:05:00", "purchase", 100),
      Row("U1", "E3", "2024-01-01 11:00:00", "purchase", 50),
      Row("U1", "E3", "2024-01-01 11:00:00", "purchase", 50),
      Row("U2", "E4", "2024-01-01 09:00:00", "purchase", 200),
      Row("U2", "E5", "2024-01-01 09:10:00", "view", 0),
      Row("U2", "E6", "2024-01-01 10:00:00", "purchase", 300)
    )
    val schema = StructType(List(
      StructField("user_id", StringType, nullable = true),
      StructField("event_id", StringType, nullable = true),
      StructField("event_time", StringType, nullable = true),
      StructField("event_type", StringType, nullable = true),
      StructField("amount", IntegerType, nullable = true)
    ))
    
    val orders_df = spark.createDataFrame(spark.sparkContext.parallelize(data), schema)

    /*using .todf - alternative approach (commented out)

    val df = Seq(
      ("U1", "E1", "2024-01-01 10:00:00", "view", 0),
      ("U1", "E2", "2024-01-01 10:05:00", "purchase", 100),
      ("U1", "E3", "2024-01-01 11:00:00", "purchase", 50),
      ("U1", "E3", "2024-01-01 11:00:00", "purchase", 50),
      ("U2", "E4", "2024-01-01 09:00:00", "purchase", 200),
      ("U2", "E5", "2024-01-01 09:10:00", "view", 0),
      ("U2", "E6", "2024-01-01 10:00:00", "purchase", 300)
    ).toDF("user_id", "event_id", "event_time", "event_type", "amount")
    */

    println("Orders table created")
    orders_df.show()
    println("Removed duplicates using Drop Duplicate function")
    val deduplicatedDF = orders_df.dropDuplicates("event_id")
    deduplicatedDF.show()
    val deduplicatedDF1 = orders_df.dropDuplicates("event_id","user_id")
    println("Removed duplicates using Drop Duplicate function 2")
    deduplicatedDF1.show()

    //using window function
    val windowSpec = Window.partitionBy("event_id").orderBy(col("event_time").desc)
    //val deduplicatedDFWithWindow = orders_df.withColumn("row_number", row_number().over(windowSpec))
    val deduplicatedDFWithWindow = orders_df.withColumn("row_number", row_number().over(windowSpec)).filter(col("row_number") === 1).drop("row_number")

  }

}
