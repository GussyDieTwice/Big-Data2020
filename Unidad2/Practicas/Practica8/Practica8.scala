package org.apache.spark.examples.ml

import org.apache.spark.ml.classification.LinearSVC
import org.apache.spark.sql.SparkSession

object LinearSVCExample {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("LinearSVCExample")
      .getOrCreate()

    val training = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")

    val lsvc = new LinearSVC()
      .setMaxIter(10)
      .setRegParam(0.1)

    val lsvcModel = lsvc.fit(training)

    println(s"Coefficients: ${lsvcModel.coefficients} Intercept: ${lsvcModel.intercept}")

    spark.stop()
  }
}

