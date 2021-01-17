import org.apache.spark.ml.linalg.{Matrix, Vectors}
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession

object CorrelationExample {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("CorrelationExample")
      .getOrCreate()
    import spark.implicits._

    // Se crea un valor llamado data al que se le asigna una secuencia de vectores como valor
    val data = Seq(
      Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))), // (0,3,1,-2)
      Vectors.dense(4.0, 5.0, 0.0, 3.0),
      Vectors.dense(6.0, 7.0, 0.0, 8.0),
      Vectors.sparse(4, Seq((0, 9.0), (3, 1.0))) // (0,3,9,1)
    )

    //Se crea un dataframe al cual se le asigna el valor de una Tupla llamada Tuple1, 
    //el dataframe contiene una columna llamada feautures
    val df = data.map(Tuple1.apply).toDF("features")
    //A un valor tipo Fila llamado coefficiente1 de una matriz se le asigna el valor de la correlacion de pearson aplicada en el dataframe
    //Aplicada a su columna features
    val Row(coeff1: Matrix) = Correlation.corr(df, "features").head
    println(s"Pearson correlation matrix:\n $coeff1") //Se imprime
    //A un valor tipo Fila llamado coefficiente2 de una matriz se le asigna el valor de la correlacion de spearman aplicada en el dataframe
    //Aplicada a su columna features
    val Row(coeff2: Matrix) = Correlation.corr(df, "features", "spearman").head
    println(s"Spearman correlation matrix:\n $coeff2") //Se imprime
    

    spark.stop()
  }
}

