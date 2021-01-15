import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
            
        import org.apache.log4j._
        Logger.getLogger("org").setLevel(Level.ERROR)

        val t1 = System.nanoTime

        val spark = SparkSession.builder().getOrCreate()


        val df = spark.read.option("header","true").option("inferSchema", "true").option("delimiter",";").format("csv").load("C:/Principal/Big-Data2020/Project/bank-full.csv")
        df.show()
        val indexer = new StringIndexer().setInputCol("y").setOutputCol("indexedLabel").fit(df)
        val indexed = indexer.transform(df).drop("y").withColumnRenamed("indexedLabel", "label")


        val assembler = new VectorAssembler().setInputCols(Array("balance","day","duration","pdays","previous")).setOutputCol("features")

        val data = assembler.transform(indexed)
        data.show()

        val featuresLabel = data.withColumnRenamed("y", "label")

        val dataIndexed = featuresLabel.select("label","features")
        dataIndexed.show()


        val Array(training, test) = dataIndexed.randomSplit(Array(0.7, 0.3), seed = 12345)

        val lr = new LogisticRegression().setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8).setFamily("multinomial")


        val logisticModel = lr.fit(training)


        val results = logisticModel.transform(test)


        import org.apache.spark.mllib.evaluation.MulticlassMetrics


        val predictionAndLabels = results.select($"prediction",$"label").as[(Double, Double)].rdd
        val metrics = new MulticlassMetrics(predictionAndLabels)


        println("Confusion matrix:")
        println(metrics.confusionMatrix)


        println("Accuracy: " + metrics.accuracy) 
        println(s"Test Error = ${(1.0 - metrics.accuracy)}")

        val duration = (System.nanoTime - t1) / 1e9d
        println("Process Duration: " + duration + " seconds")


        
