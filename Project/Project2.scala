import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.StringIndexer 
import org.apache.spark.ml.feature.VectorAssembler

object Project2 {

  def main(args: Array[String]): Unit = {

        import org.apache.log4j._
        Logger.getLogger("org").setLevel(Level.ERROR)

        val t1 = System.nanoTime

        //val spark = SparkSession.builder.getOrCreate()
        val spark = SparkSession.builder.appName("DecisionTreeClassificationExample").getOrCreate()

    
        val df = spark.read.option("header","true").option("inferSchema","true").option("delimiter",";")csv("C:/Principal/Big-Data2020/Project/bank-full.csv")
        df.show()

        val indexer = new StringIndexer().setInputCol("y").setOutputCol("indexedLabel").fit(df)
        val indexed = indexer.transform(df).drop("y").withColumnRenamed("indexedLabel", "label")
        indexed.show()

        val assembler = new VectorAssembler().setInputCols(Array("balance","day","duration","pdays","previous")).setOutputCol("features")

        val data = assembler.transform(indexed)
        data.show()

        val featuresLabel = data.withColumnRenamed("y", "label")

        val dataIndexed = featuresLabel.select("label","features")
        dataIndexed.show()

        val indexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(dataIndexed)
        val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(dataIndexed) // features with > 4 distinct values are treated as continuous.


        val Array(trainingData, testData) = dataIndexed.randomSplit(Array(0.7, 0.3))


        val dt = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")


        val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(indexer.labels)

        val pipeline = new Pipeline().setStages(Array(indexer, featureIndexer, dt, labelConverter))


        val model = pipeline.fit(trainingData)


        val predictions = model.transform(testData)

        predictions.select("predictedLabel", "label", "features").show(5)


        val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")

        val accuracy = evaluator.evaluate(predictions)

        println(s"Test Error = ${(1.0 - accuracy)}")

        val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]

        println(s"Learned classification tree model:\n ${treeModel.toDebugString}")

        val duration = (System.nanoTime - t1) / 1e9d
        println("Process Duration: " + duration + " seconds")
        
        spark.stop()
  }
}


