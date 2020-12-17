## Practice 3.

### Correlation.
```scala
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
```

A value called data is created and a sequence of vectors is assigned as a value.
```scala
    val data = Seq(
      Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))), // (0,3,1,-2)
      Vectors.dense(4.0, 5.0, 0.0, 3.0),
      Vectors.dense(6.0, 7.0, 0.0, 8.0),
      Vectors.sparse(4, Seq((0, 9.0), (3, 1.0))) // (0,3,9,1)
    )
```

A dataframe is created to which the value of a Tuple called Tuple1 is assigned, the dataframe contains a column called feautures.
```scala 
    val df = data.map(Tuple1.apply).toDF("features")
```

A Row type value called coefficient1 of a matrix is assigned the value of the pearson correlation applied to the dataframe.
Applied to your features column.
```scala
    val Row(coeff1: Matrix) = Correlation.corr(df, "features").head
    println(s"Pearson correlation matrix:\n $coeff1")
```

A Row type value called coefficient2 of a matrix is assigned the value of the spearman correlation applied to the dataframe.
Applied to your features column.
```scala
    val Row(coeff2: Matrix) = Correlation.corr(df, "features", "spearman").head
    println(s"Spearman correlation matrix:\n $coeff2") //Se imprime

    spark.stop()
  }
}
```

### ChiSquare.
```scala
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
```
A name value data is declared to which a sequence of dense vectors is assigned.
```scala
    val data = Seq(
      (0.0, Vectors.dense(0.5, 10.0)),
      (0.0, Vectors.dense(1.5, 20.0)),
      (1.0, Vectors.dense(1.5, 30.0)),
      (0.0, Vectors.dense(3.5, 30.0)),
      (0.0, Vectors.dense(3.5, 40.0)),
      (1.0, Vectors.dense(3.5, 40.0))
    )
```

A new dataframe is created that is assigned the value data in the label and features columns.
```scala
    val df = data.toDF("label", "features")

A chi value is created to which ChiSquare is applied through the libraries, to the data frame in its features and label columns.
```scala
    val chi = ChiSquareTest.test(df, "features", "label").head 
    println(s"pValues = ${chi.getAs[Vector](0)}")
    println(s"degreesOfFreedom ${chi.getSeq[Int](1).mkString("[", ",", "]")}")
    println(s"statistics ${chi.getAs[Vector](2)}")
    
    spark.stop()
  }
}
```

### Summarizer.
```scala
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
```

Import Summarizer. 
```scala
    import Summarizer._
```
 
Data value will contain two vectors.
 ```scala
    val data = Seq(
      (Vectors.dense(2.0, 3.0, 5.0), 1.0),
      (Vectors.dense(4.0, 6.0, 7.0), 2.0)
    )
 ```

Df value to refer to "Feactures" and "weight".
```scala
    val df = data.toDF("features", "weight")
```

Two values for the mean and variance;
Select the metrics (Mean, Variance).
```scala
    val (meanVal, varianceVal) = df.select(metrics("mean", "variance")
```

Summary method that we will apply to Feactures and Weight and give it an alias.
```scala
      .summary($"features", $"weight").as("summary"))
```

Select the Summary with its respective metric.
```scala
      .select("summary.mean", "summary.variance")
```

Give it the way we want to print.
```scala
      .as[(Vector, Vector)].first()
```

Send to printing to show the values of the Mean and Variance.
```scala
    println(s"with weight: mean = ${meanVal}, variance = ${varianceVal}")
```

Second Value without using the Summary method.
```scala
    val (meanVal2, varianceVal2) = df.select(mean($"features"), variance($"features"))
      .as[(Vector, Vector)].first()
    println(s"without weight: mean = ${meanVal2}, sum = ${varianceVal2}")

    spark.stop()
  }
}
```
