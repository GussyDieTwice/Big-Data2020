## Practice 5.
```scala
import org.apache.spark.ml.Pipeline 
import org.apache.spark.ml.classification.{RandomForestClassificationModel, RandomForestClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}

import org.apache.spark.sql.SparkSession

object RandomForestClassifierExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("RandomForestClassifierExample")
      .getOrCreate()
```

Load the file or dataframe to read in libsvm format and declare it as a value named "data".
```scala
    val data = spark.read.format("libsvm").load("/home/gussy/git_workspace/Big-Data2020/Unidad2/Practicas/Practica5/sample_libsvm_data.txt")
```

Index new labels by adding the data extracted from the dataframe.
```scala
    val labelIndexer = new StringIndexer()
      .setInputCol("label")
      .setOutputCol("indexedLabel")
      .fit(data)
```

Automatically identify the category "features" and index the tag with the same name.
```scala
    val featureIndexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexedFeatures")
      .setMaxCategories(4) // las categorias que setMaxCategories>4 seran consideradas continuas
      .fit(data)
```

The data is divided into trainingData and TestData randomly (70% for training and 30% for test).
```scala
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
```

A Random Forest model is trained.
```scala
    val rf = new RandomForestClassifier()
      .setLabelCol("indexedLabel")
      .setFeaturesCol("indexedFeatures")
      .setNumTrees(10)
```

The indexed data returns to the way it was.
```scala
    val labelConverter = new IndexToString()
      .setInputCol("prediction")
      .setOutputCol("predictedLabel")
      .setLabels(labelIndexer.labels)
```

Indexed and forest data is pooled into a pipeline.
```scala
    val pipeline = new Pipeline()
      .setStages(Array(labelIndexer, featureIndexer, rf, labelConverter))
```

The model is trained.
```scala
    val model = pipeline.fit(trainingData)
```

Predictions are made.
```scala
    val predictions = model.transform(testData)
```

The columns are displayed to show and their first 5 lines.
```scala
    predictions.select("predictedLabel", "label", "features").show(5)
```

The precision of the test data is calculated.
```scala
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("indexedLabel")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${(1.0 - accuracy)}")

    val rfModel = model.stages(2).asInstanceOf[RandomForestClassificationModel]
    println(s"Learned classification forest model:\n ${rfModel.toDebugString}")

    spark.stop()
  }
}
```
