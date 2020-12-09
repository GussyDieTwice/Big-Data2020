package org.apache.spark.examples.ml
// Importar las librerias a utilizar
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{GBTClassificationModel, GBTClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
// Iniciar Sesion Spark
import org.apache.spark.sql.SparkSession
// Crear obj para ejecutar el main
object GradientBoostedTreeClassifierExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("GradientBoostedTreeClassifierExample")
      .getOrCreate()
// Cargar el archivo de datos
    val data = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")
//Crear un valor "labelIndexer", agregando metadatos a la columna de etiquetas. 
//Encajar en todo el conjunto de datos para incluir todas las etiquetas en el índice.
    val labelIndexer = new StringIndexer()
      .setInputCol("label")
      .setOutputCol("indexedLabel")
      .fit(data)
//Crear un valor "featureIndexer" Identifique automáticamente características categóricas e indexelas. 
//Establezca maxCategories para que las entidades con 4 valores distintos se traten como continuas.
    val featureIndexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexedFeatures")
      .setMaxCategories(4)
      .fit(data)
//Crear un valor Array Divida los datos en conjuntos de prueba(70%) y entrenamiento (30%).
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
//Crear un valor "gbt" de modelo
    val gbt = new GBTClassifier()
      .setLabelCol("indexedLabel")
      .setFeaturesCol("indexedFeatures")
      .setMaxIter(10)
      .setFeatureSubsetStrategy("auto")
//Crear un valor "labelConverter"  para las etiquetas indexadas haerlas de  nuevo a etiquetas originales.
    val labelConverter = new IndexToString()
      .setInputCol("prediction")
      .setOutputCol("predictedLabel")
      .setLabels(labelIndexer.labels)
//Un valor pipeline para indexar en cadena y GBT en Pipeline.
    val pipeline = new Pipeline()
      .setStages(Array(labelIndexer, featureIndexer, gbt, labelConverter))
//Crear un valor "model" para ejecutar los indexadores
    val model = pipeline.fit(trainingData)
//Crear un valor "predictions" para realizar las predicciones 
    val predictions = model.transform(testData)
//Hacer el select de las columas a seleccionar 
    predictions.select("predictedLabel", "label", "features").show(5)
//Valor "Evaluator" Seleccione (predicción, etiqueta verdadera) 
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("indexedLabel")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
//Crear un valor "Accuracy" que calcule el error de prueba.
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${1.0 - accuracy}")

    val gbtModel = model.stages(2).asInstanceOf[GBTClassificationModel]
    println(s"Learned classification GBT model:\n ${gbtModel.toDebugString}")

    spark.stop()
  }
}