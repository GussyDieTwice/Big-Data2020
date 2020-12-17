## Practice 9.
```scala
import org.apache.spark.ml.classification.{LogisticRegression, OneVsRest}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

import org.apache.spark.sql.SparkSession

object OneVsRestExample {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .appName(s"OneVsRestExample")
      .getOrCreate()
```

Load the file or dataframe to read in libsvm format and declare it as a value named "inputData".
```scala
    val inputData = spark.read.format("libsvm")
      .load("/home/gussy/git_workspace/Big-Data2020/Unidad2/Practicas/Practica9/sample_multiclass_classification_data.txt")
```

The data are divided into train and test randomly (80% for train and 20% for test).
```scala
    val Array(train, test) = inputData.randomSplit(Array(0.8, 0.2))
```

The base classifier is declared and the training parameters are set.
```scala
    val classifier = new LogisticRegression()
      .setMaxIter(10)
      .setTol(1E-6)
      .setFitIntercept(true)
```

The One vs Rest classifier is declared.
```scala
    val ovr = new OneVsRest().setClassifier(classifier)
```

Model training starts.
```scala
    val ovrModel = ovr.fit(train)
```

Predictions are made.
```scala
    val predictions = ovrModel.transform(test)
```

The precision of the test data is calculated.
```scala
    val evaluator = new MulticlassClassificationEvaluator()
      .setMetricName("accuracy")
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${1 - accuracy}")

    spark.stop()
  }
}
```
