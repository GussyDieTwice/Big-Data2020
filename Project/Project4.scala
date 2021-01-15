import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

object Project4 {

  def main(args: Array[String]): Unit = {      
        
        import org.apache.log4j._
        Logger.getLogger("org").setLevel(Level.ERROR)

        val t1 = System.nanoTime

        val spark = SparkSession.builder.getOrCreate()
        val df = spark.read.option("header", "true").option("inferSchema","true").option("delimiter",";")csv("/home/gussy/git_workspace/Big-Data2020/Project/bank-full.csv")

        df.show()

        val indexer = new StringIndexer().setInputCol("y").setOutputCol("indexedLabel").fit(df)
        val indexed = indexer.transform(df).drop("y").withColumnRenamed("indexedLabel", "label")
        indexed.show()  

        val assembler = new VectorAssembler().setInputCols(Array("balance","day","duration","pdays","previous")).setOutputCol("features")
        val data = assembler.transform(indexed)
        data.show()

        val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(indexed)
        println(s"Found labels: ${labelIndexer.labels.mkString("[", ", ", "]")}")


        val splits = data.randomSplit(Array(0.7, 0.3), seed = 1234L)
        val train = splits(0)
        val test = splits(1)
    
        val layers = Array[Int](5, 4, 1, 2)

        val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)

        val model = trainer.fit(train)

        val result = model.transform(test)
        val predictionAndLabels = result.select("prediction", "label")
        predictionAndLabels.show()
        val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
    
        println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")

        val duration = (System.nanoTime - t1) / 1e9d
        println("Process Duration: " + duration + " seconds")

        spark.stop()
  }
}