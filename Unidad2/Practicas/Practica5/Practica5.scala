import org.apache.spark.ml.Pipeline 
import org.apache.spark.ml.classification.{RandomForestClassificationModel, RandomForestClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}

import org.apache.spark.sql.SparkSession

object RandomForestClassifierExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("RandomForestClassifierExample")
      .getOrCreate()

    //Cargar el archivo o dataframe a leer en formato libsvm y declararlo como valor con nombre "data"
    val data = spark.read.format("libsvm").load("/home/gussy/git_workspace/Big-Data2020/Unidad2/Practicas/Practica5/sample_libsvm_data.txt")

    //Indexar nuevas etiquetas agregando los datos extraidos del dataframe
    val labelIndexer = new StringIndexer()
      .setInputCol("label")
      .setOutputCol("indexedLabel")
      .fit(data)

    //Identificar automaticamente la categoria "features" e indexar en etiqueta con el mismo nombre
    val featureIndexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexedFeatures")
      .setMaxCategories(4) // las categorias que setMaxCategories>4 seran consideradas continuas
      .fit(data)

    //Se dividen los datos en trainingData y TestData de manera aleatoria (70% para training y 30% a test)
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

    //Se entrena un modelo de Random Forest
    val rf = new RandomForestClassifier()
      .setLabelCol("indexedLabel")
      .setFeaturesCol("indexedFeatures")
      .setNumTrees(10)

    //Los datos indexados vuelven a como estaban
    val labelConverter = new IndexToString()
      .setInputCol("prediction")
      .setOutputCol("predictedLabel")
      .setLabels(labelIndexer.labels)

    //Se juntan los datos indexados y en el forest en un pipeline
    val pipeline = new Pipeline()
      .setStages(Array(labelIndexer, featureIndexer, rf, labelConverter))

    //Se entrena el modelo
    val model = pipeline.fit(trainingData)

    //Se realizan las predicciones
    val predictions = model.transform(testData)

    //Se despliegan las columnas para mostrar y sus primeros 5 renglones
    predictions.select("predictedLabel", "label", "features").show(5)

    //Se calcula la precision de los datos de prueba
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("indexedLabel")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${(1.0 - accuracy)}")

    val rfModel = model.stages(2).asInstanceOf[RandomForestClassificationModel]
    println(s"Learned classification forest model:\n ${rfModel.toDebugString}")

    spark.stop()
  }
}

/* Resultado:
+--------------+-----+--------------------+                                     
|predictedLabel|label|            features|
+--------------+-----+--------------------+
|           0.0|  0.0|(692,[121,122,123...|
|           0.0|  0.0|(692,[124,125,126...|
|           0.0|  0.0|(692,[125,126,127...|
|           0.0|  0.0|(692,[126,127,128...|
|           0.0|  0.0|(692,[126,127,128...|
+--------------+-----+--------------------+
only showing top 5 rows

Test Error = 0.0
Learned classification forest model:
 RandomForestClassificationModel (uid=rfc_2a2124884d29) with 10 trees
  Tree 0 (weight 1.0):
    If (feature 412 <= 18.5)
     If (feature 553 <= 138.5)
      If (feature 184 <= 254.5)
       Predict: 0.0
      Else (feature 184 > 254.5)
       Predict: 1.0
     Else (feature 553 > 138.5)
      Predict: 1.0
    Else (feature 412 > 18.5)
     Predict: 1.0
  Tree 1 (weight 1.0):
    If (feature 463 <= 2.0)
     If (feature 545 <= 215.0)
      If (feature 490 <= 43.0)
       Predict: 1.0
      Else (feature 490 > 43.0)
       Predict: 0.0
     Else (feature 545 > 215.0)
      Predict: 0.0
    Else (feature 463 > 2.0)
     Predict: 0.0
  Tree 2 (weight 1.0):
    If (feature 540 <= 87.0)
     If (feature 101 <= 84.5)
      Predict: 0.0
     Else (feature 101 > 84.5)
      Predict: 1.0
    Else (feature 540 > 87.0)
     Predict: 1.0
  Tree 3 (weight 1.0):
    If (feature 328 <= 35.5)
     If (feature 341 <= 19.0)
      If (feature 458 <= 36.0)
       Predict: 0.0
      Else (feature 458 > 36.0)
       Predict: 1.0
     Else (feature 341 > 19.0)
      Predict: 1.0
    Else (feature 328 > 35.5)
     Predict: 1.0
  Tree 4 (weight 1.0):
    If (feature 429 <= 7.0)
     If (feature 358 <= 10.5)
      If (feature 598 <= 254.0)
       Predict: 0.0
      Else (feature 598 > 254.0)
       Predict: 1.0
     Else (feature 358 > 10.5)
      Predict: 1.0
    Else (feature 429 > 7.0)
     Predict: 1.0
  Tree 5 (weight 1.0):
    If (feature 462 <= 62.5)
     Predict: 1.0
    Else (feature 462 > 62.5)
     Predict: 0.0
  Tree 6 (weight 1.0):
    If (feature 512 <= 1.5)
     If (feature 517 <= 20.5)
      If (feature 147 in {1.0})
       Predict: 0.0
      Else (feature 147 not in {1.0})
       Predict: 1.0
     Else (feature 517 > 20.5)
      Predict: 0.0
    Else (feature 512 > 1.5)
     Predict: 1.0
  Tree 7 (weight 1.0):
    If (feature 512 <= 1.5)
     If (feature 288 <= 154.5)
      Predict: 0.0
     Else (feature 288 > 154.5)
      Predict: 1.0
    Else (feature 512 > 1.5)
     Predict: 1.0
  Tree 8 (weight 1.0):
    If (feature 462 <= 62.5)
     Predict: 1.0
    Else (feature 462 > 62.5)
     Predict: 0.0
  Tree 9 (weight 1.0):
    If (feature 385 <= 4.0)
     If (feature 603 <= 252.5)
      If (feature 377 <= 0.5)
       If (feature 624 <= 104.0)
        Predict: 1.0
       Else (feature 624 > 104.0)
        Predict: 0.0
      Else (feature 377 > 0.5)
       Predict: 0.0
     Else (feature 603 > 252.5)
      If (feature 179 <= 3.0)
       Predict: 0.0
      Else (feature 179 > 3.0)
       Predict: 1.0
    Else (feature 385 > 4.0)
     Predict: 1.0
*/


