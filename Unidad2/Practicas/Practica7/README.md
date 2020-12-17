## Practice 7.
```scala
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

import org.apache.spark.sql.SparkSession

object MultilayerPerceptronClassifierExample {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("MultilayerPerceptronClassifierExample")
      .getOrCreate()
```

Load the file or dataframe to read in libsvm format and declare it as a value named "data".
```scala
    val data = spark.read.format("libsvm")
      .load("/home/gussy/git_workspace/Big-Data2020/Unidad2/Practicas/Practica7/sample_multiclass_classification_data.txt")
```

The data are divided into train and test randomly (60% for train and 40% for test).
```scala
    val splits = data.randomSplit(Array(0.6, 0.4), seed = 1234L)
    val train = splits(0)
    val test = splits(1)
```

The layers of the neural network are specified in an array.
```scala
    val layers = Array[Int](4, 5, 4, 3) //la capa de entrada es de tamaño 4 (caracteristicas), las dos intermedias de tamaño 5 y 4, y la de salida de tamaño 3 (clases)
```

The base classifier is declared and the training parameters are set.
```scala
    val trainer = new MultilayerPerceptronClassifier()
      .setLayers(layers)
      .setBlockSize(128)
      .setSeed(1234L)
      .setMaxIter(100)
```

Model training starts.
```scala
    val model = trainer.fit(train)
```

The precision of the test data is calculated.
```scala
    val result = model.transform(test)
    val predictionAndLabels = result.select("prediction", "label")
    val evaluator = new MulticlassClassificationEvaluator()
      .setMetricName("accuracy")
```

The accuracy of the model is displayed.
```scala
    println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")
    
    spark.stop()
  }
}
```
