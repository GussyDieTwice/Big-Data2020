## Practice 9.
Import Libraries
```scala
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
```
Start spark
```scala
val spark = SparkSession.builder().getOrCreate()
```
Load the dataframe to read in libsvm format and declare it as a named value "data"
```scala
val data = spark.read.option("header", "true").option("inferSchema","true")csv("C:/Data2020/Big-Data2020/Unidad2/Practicas/Practica2/advertising.csv")
```
Print Scheme
```scala
data.printSchema()
```
Print all the data in the first column
```scala
data.head(1)
```
Value to put the columns in an array
```scala
val colnames = data.columns
```
Value to print the 1.0 data of each column
```scala
val firstrow = data.head(1)(0)
println("\n")
```
Example of affected rows
```scala
println("Example data row")
```
For cycle to print the Age, Area Income columns, among others in an orderly manner
```scala
for(ind <- Range(1, colnames.length)){
    println(colnames(ind))
    println(firstrow(ind))
    println("\n")
}
```
Value "timedata" to know the time of the data
```scala
val timedata = data.withColumn("Hour",hour(data("Timestamp")))
```
Show the DF columns that we select
```scala
val logregdata = timedata.select(data("Clicked on Ad").as("label"), $"Daily Time Spent on Site", $"Age", $"Area Income", $"Daily Internet Usage", $"Hour", $"Male")
```
Import apache libraries
```scala
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
```
This value only accepts numeric types, Boolean type and vector type in the affected columns
```scala
val assembler = (new VectorAssembler()
                  .setInputCols(Array("Daily Time Spent on Site", "Age","Area Income","Daily Internet Usage","Hour","Male"))
                  .setOutputCol("features"))
```
Load training data (70%) and test (30%)
```scala
val Array(training, test) = logregdata.randomSplit(Array(0.7, 0.3), seed = 12345)
```
Import Pipeline
```scala
import org.apache.spark.ml.Pipeline
```
Create a new LogisticRegression stored in the lr value
```scala
val lr = new LogisticRegression()
```
This value will contain a new Pipeline index with an array with the assambler
```scala
val pipeline = new Pipeline().setStages(Array(assembler, lr))
```
Create training pipeline model
```scala
val model = pipeline.fit(training)
```
Test model results
```scala
val results = model.transform(test)
```
Import MulticlassMetrics
```scala
import org.apache.spark.mllib.evaluation.MulticlassMetrics
```
Value for Predictions and Labels
```scala
val predictionAndLabels = results.select($"prediction",$"label").as[(Double, Double)].rdd
```
Create a New MilticlassMetrics with predictionandLabels created with previous
```scala
val metrics = new MulticlassMetrics(predictionAndLabels)
```
Print the Matrix
```scala
println("Confusion matrix:")
println(metrics.confusionMatrix)
```
Result of the metrics
```scala
metrics.accuracy
```
