import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorAssembler

object Evaluacion2 {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Evaluacion2").getOrCreate()

    val df = spark.read.option("header", "true").option("inferSchema","true")csv("/home/gussy/git_workspace/Big-Data2020/Unidad2/Evaluacion/iris.csv")

    df.columns
    df.printSchema
    df.head(5)
    df.describe().show() 

    val indexer = new StringIndexer().setInputCol("species").setOutputCol("indexedSpecies").fit(df)
    val indexed = indexer.transform(df).drop("species").withColumnRenamed("indexedSpecies", "species")
    indexed.show()

    val assembler = new VectorAssembler().setInputCols(Array("sepal_length","sepal_width","petal_length","petal_width")).setOutputCol("features")
    val data = assembler.transform(indexed)
    data.show()

    val labelIndexer = new StringIndexer().setInputCol("species").setOutputCol("indexedSpecies").fit(indexed)

    println(s"Labels: ${indexer.labels.mkString("[", ", ", "]")}")
    println(s"indexedLabels: ${labelIndexer.labels.mkString("[", ", ", "]")}")

/*
    // Split the data into train and test
    val splits = data.randomSplit(Array(0.6, 0.4), seed = 1234L)
    val train = splits(0)
    val test = splits(1)

    // specify layers for the neural network:
    // input layer of size 4 (features), two intermediate of size 5 and 4
    // and output of size 3 (classes)
    val layers = Array[Int](4, 5, 4, 3)

    // create the trainer and set its parameters
    val trainer = new MultilayerPerceptronClassifier()
      .setLayers(layers)
      .setBlockSize(128)
      .setSeed(1234L)
      .setMaxIter(100)

    // train the model
    val model = trainer.fit(train)

    // compute accuracy on the test set
    val result = model.transform(test)
    val predictionAndLabels = result.select("prediction", "label")
    val evaluator = new MulticlassClassificationEvaluator()
      .setMetricName("accuracy")

    println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")
    */

    spark.stop()
  }
}