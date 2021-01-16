//Support Vector Machine
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.classification.LinearSVC
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.mllib.evaluation.MulticlassMetrics
     
        
        import org.apache.log4j._
        Logger.getLogger("org").setLevel(Level.ERROR)

        val t1 = System.nanoTime

        val df = spark.read.option("header", "true").option("inferSchema","true").option("delimiter",";")csv("/home/gussy/git_workspace/Big-Data2020/Project/bank-full.csv")
        val indexer = new StringIndexer().setInputCol("y").setOutputCol("indexedLabel").fit(df)
        val indexed = indexer.transform(df).drop("y").withColumnRenamed("indexedLabel", "label")


        val vectorFeatures = (new VectorAssembler().setInputCols(Array("balance","day","duration","pdays","previous")).setOutputCol("features"))

        val features = vectorFeatures.transform(indexed)

        val labelFeatures = features.withColumnRenamed("y", "label")

        val dataIndexed = labelFeatures.select("label","features")

        val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(dataIndexed)
        val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(dataIndexed) // features with > 4 distinct values are treated as continuous.


        val Array(train, test) = dataIndexed.randomSplit(Array(0.7, 0.3), seed = 12345)
        val lsvc = new LinearSVC().setMaxIter(10).setRegParam(0.1)
        val lsvcModel = lsvc.fit(train)
        val results = lsvcModel.transform(test)

        val predictionAndLabels = results.select($"prediction",$"label").as[(Double, Double)].rdd
        val metrics = new MulticlassMetrics(predictionAndLabels)

        println("Confusion matrix:")
        println(metrics.confusionMatrix)

        println("Accurancy: " + metrics.accuracy) 
        println(s"Test Error = ${(1.0 - metrics.accuracy)}")
        println(s"Coefficients: ${lsvcModel.coefficients} Intercept: ${lsvcModel.intercept}")

        val duration = (System.nanoTime - t1) / 1e9d
        println("Process Duration: " + duration + " seconds")
