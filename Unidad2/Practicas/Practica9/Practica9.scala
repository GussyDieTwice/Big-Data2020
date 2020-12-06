package org.apache.spark.examples.ml

import org.apache.spark.ml.classification.{LogisticRegression, OneVsRest}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

import org.apache.spark.sql.SparkSession

object OneVsRestExample {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .appName(s"OneVsRestExample")
      .getOrCreate()

    val inputData = spark.read.format("libsvm")
      .load("data/mllib/sample_multiclass_classification_data.txt")

    val Array(train, test) = inputData.randomSplit(Array(0.8, 0.2))

    val classifier = new LogisticRegression()
      .setMaxIter(10)
      .setTol(1E-6)
      .setFitIntercept(true)

    val ovr = new OneVsRest().setClassifier(classifier)

    val ovrModel = ovr.fit(train)

    val predictions = ovrModel.transform(test)

    val evaluator = new MulticlassClassificationEvaluator()
      .setMetricName("accuracy")

    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${1 - accuracy}")

    spark.stop()
  }

}

