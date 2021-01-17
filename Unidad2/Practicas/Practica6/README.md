## Practice 6.

Import the libraries to use.
```scala
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{GBTClassificationModel, GBTClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
package org.apache.spark.examples.ml
```
Start Spark Session
```scala
import org.apache.spark.sql.SparkSession
```
Create obj to execute the main
```scala
object GradientBoostedTreeClassifierExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("GradientBoostedTreeClassifierExample")
      .getOrCreate()
```
Load the data file
```scala
    val data = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")
```
Create a "labelIndexer" value, adding metadata to the labels column.
Fit across the entire dataset to include all labels in the index.
```scala
    val labelIndexer = new StringIndexer()
      .setInputCol("label")
      .setOutputCol("indexedLabel")
      .fit(data)
```
Create a "featureIndexer" value Automatically identify categorical features and index them.
Set maxCategories so that entities with 4 distinct values ​​are treated as continuous.
```scala
    val featureIndexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexedFeatures")
      .setMaxCategories(4)
      .fit(data)
```
Create an Array value Divide the data into test (70%) and training (30%) sets.
```scala
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
```
Create a model "gbt" value
```scala
    val gbt = new GBTClassifier()
      .setLabelCol("indexedLabel")
      .setFeaturesCol("indexedFeatures")
      .setMaxIter(10)
      .setFeatureSubsetStrategy("auto")
```
Create a "labelConverter" value for the indexed labels make them back to original labels.
```scala
    val labelConverter = new IndexToString()
      .setInputCol("prediction")
      .setOutputCol("predictedLabel")
      .setLabels(labelIndexer.labels)
```
A pipeline value to index on chain and GBT in Pipeline.
```scala
    val pipeline = new Pipeline()
      .setStages(Array(labelIndexer, featureIndexer, gbt, labelConverter))
```
Create a "model" value to run the indexers
```scala
    val model = pipeline.fit(trainingData)
```
Create a "predictions" value to make the predictions
```scala
    val predictions = model.transform(testData)
```
Make the select of the columns to select
```scala
    predictions.select("predictedLabel", "label", "features").show(5)
```
Value "Evaluator" Select (prediction, true label)
```scala
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("indexedLabel")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
```
Create an "Accuracy" value that calculates the test error.
```scala
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${1.0 - accuracy}")

    val gbtModel = model.stages(2).asInstanceOf[GBTClassificationModel]
    println(s"Learned classification GBT model:\n ${gbtModel.toDebugString}")

    spark.stop()
  }
}
```
