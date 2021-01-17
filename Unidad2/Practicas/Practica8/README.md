## Practice 8.
Import libreries
```scala
package org.apache.spark.examples.ml
import org.apache.spark.ml.classification.LinearSVC
```
Login spark
```scala
import org.apache.spark.sql.SparkSession
```
Create obj to execute Mainobject LinearSVCExample
```scala
{

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("LinearSVCExample")
      .getOrCreate()
```
Create a "training" value that will contain the dataframe
```scala
    val training = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")
```
We create an lscv value with Max of 10 iteration and a param of 0.1
```scala
    val lsvc = new LinearSVC()
      .setMaxIter(10)
      .setRegParam(0.1)
```
We create a Fil in a value "lsvModel"
```scala
val lsvcModel = lsvc.fit(training)
```
We print the Coefficient and the Interccep
```scala
    println(s"Coefficients: ${lsvcModel.coefficients} Intercept: ${lsvcModel.intercept}")

    spark.stop()
  }
}
```
