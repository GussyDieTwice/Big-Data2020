## Practice 10.

Import libraries
```scala
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
```
Start Spark Session
```scala
import org.apache.spark.sql.SparkSession
```
Create Obj to run the main
```scala
object NaiveBayesExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("NaiveBayesExample")
      .getOrCreate()
```
Create value "data" that will save the dataframe format
```scala
    val data = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")
```
Create an Array that will contain the Training (70%) and Test (30%) data
```scala
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3), seed = 1234L)
```
Create a new "model" value for the NaiveBayes model
```scala
    val model = new NaiveBayes()
      .fit(trainingData)
```
Create a "predictions" value on the Test data
```scala
    val predictions = model.transform(testData)
    predictions.show()
```
Create an "Evaluator" value to Select Labels ("label", "prediction", "accuracy")
```scala
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("label")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
```
Create an "accuracy" value that will calculate the Error of the tests
```scala
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test set accuracy = $accuracy")

    spark.stop()
  }
}
```
