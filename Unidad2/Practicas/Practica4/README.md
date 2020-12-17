## Practice 4.
Import the libraries that will be used
```scala
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
package org.apache.spark.examples.ml
```
Login Spark
```scala
import org.apache.spark.sql.SparkSession
```
Define Obj to Execute the Main
```scala
object DecisionTreeClassificationExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("DecisionTreeClassificationExample")
      .getOrCreate()
```
Define a "data" Data value that will contain the dataframe
```scala
    val data = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")
```
Define a new value "labelIndexer" We save the Input Column "label" And we will show in the label "indexedLabel"
```scala
    val labelIndexer = new StringIndexer()
    .setInputCol("label")
    .setOutputCol("indexedLabel")
    .fit(data)
```
Define a new value "featureIndexer" Extract Data from Column "features" and Show in the tag "indexedFeatures" and index
```scala
    val featureIndexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexedFeatures")
      .setMaxCategories(4) 
      .fit(data)
```
We define a Value in Array for our test data (70%) and training (30%)
```scala
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
```
Create a new value for the model
```scala
    val dt = new DecisionTreeClassifier()
      .setLabelCol("indexedLabel")
      .setFeaturesCol("indexedFeatures")
```
We convert the indexes to Tags
```scala
    val labelConverter = new IndexToString()
      .setInputCol("prediction")
      .setOutputCol("predictedLabel")
      .setLabels(labelIndexer.labels)
```
We create a pipeline value for the tree and string shape
```scala
    val pipeline = new Pipeline()
      .setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))
```
A "model" value to run the training data
```scala
    val model = pipeline.fit(trainingData)
```
A "predictions" value to run the test data
```scala
    val predictions = model.transform(testData)
```
We make the select of the 5 lines
```scala
    predictions.select("predictedLabel", "label", "features").show(5)
```
Create an "evaluator" value to know what the true data is
```scala
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("indexedLabel")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
```
Create an "accuaracy" value to know the error of our data
```scala
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${(1.0 - accuracy)}")
```
Show the value of the tree with the value "treeModel"
```scala
    val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
    println(s"Learned classification tree model:\n ${treeModel.toDebugString}")

    spark.stop()
  }
}
```
