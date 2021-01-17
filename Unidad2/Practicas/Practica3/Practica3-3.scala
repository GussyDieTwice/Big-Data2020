import org.apache.spark.ml.linalg.{Vector, Vectors}
import org.apache.spark.ml.stat.Summarizer
import org.apache.spark.sql.SparkSession

object SummarizerExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("SummarizerExample")
      .getOrCreate()

    import spark.implicits._
    //Importar Summarizer 
    import Summarizer._

    // Valor data contendra dos vectores 
    val data = Seq(
      (Vectors.dense(2.0, 3.0, 5.0), 1.0),
      (Vectors.dense(4.0, 6.0, 7.0), 2.0)
    )
    // Valor df para hacer referencia a "Feactures" y "weight"
    val df = data.toDF("features", "weight")
    // Dos valores para la media y varianza;
    //Selec las metricas (Mean,Variance)
    val (meanVal, varianceVal) = df.select(metrics("mean", "variance")
    //Metodo summary que se lo aplicaremos a  Feactures y Weight y le damos un alias
      .summary($"features", $"weight").as("summary"))
    //Seleccionamos el Summary con su respectiva metrica
      .select("summary.mean", "summary.variance")
    // Le damos la forma en que deseamos imprimir
      .as[(Vector, Vector)].first()
    // Mandamos a la impresion para mostrar los valores de la Mean y Varianza 
    println(s"with weight: mean = ${meanVal}, variance = ${varianceVal}")
    // Segundo Valor sin usar el metodo Summary 
    val (meanVal2, varianceVal2) = df.select(mean($"features"), variance($"features"))
      .as[(Vector, Vector)].first()

    println(s"without weight: mean = ${meanVal2}, sum = ${varianceVal2}")

    spark.stop()
  }
}

