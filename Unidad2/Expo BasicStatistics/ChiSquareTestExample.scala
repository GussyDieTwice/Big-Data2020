import org.apache.spark.ml.linalg.{Vector, Vectors}
import org.apache.spark.ml.stat.ChiSquareTest
import org.apache.spark.sql.SparkSession

object ChiSquareTestExample {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("ChiSquareTestExample")
      .getOrCreate()
    import spark.implicits._

    // Se declara un valor de nombre data al que se le asgina una secuencia de vectores dense
    val data = Seq(
      (0.0, Vectors.dense(0.5, 10.0)),
      (0.0, Vectors.dense(1.5, 20.0)),
      (1.0, Vectors.dense(1.5, 30.0)),
      (0.0, Vectors.dense(3.5, 30.0)),
      (0.0, Vectors.dense(3.5, 40.0)),
      (1.0, Vectors.dense(3.5, 40.0))
    )

    //se crea un nuevo dataframe al que se le asigna el valor data en las columnas label y features
    val df = data.toDF("label", "features")
    //se crea un valor chi al que se le aplica ChiSquare mediamente las librerias, al data frame en sus columnas features y label
    val chi = ChiSquareTest.test(df, "features", "label").head 
    println(s"pValues = ${chi.getAs[Vector](0)}")
    println(s"degreesOfFreedom ${chi.getSeq[Int](1).mkString("[", ",", "]")}")
    println(s"statistics ${chi.getAs[Vector](2)}")
    // 

    spark.stop()
  }
}
// scalastyle:on println
