package org.apache.spark.examples.ml
// Importar las librerias que se utilizaran 
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
//Iniciar Sesion Spark
import org.apache.spark.sql.SparkSession
//Definir Obj para Ejecutar el Main
object DecisionTreeClassificationExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("DecisionTreeClassificationExample")
      .getOrCreate()
// Definir un valor "data" Dataque contendra el dataframe
    val data = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")
// Definir un nuevo valor "labelIndexer"
    val labelIndexer = new StringIndexer()
    // Guardamos el Input Column "label" 
      .setInputCol("label")
      //Y mostraremos en la etiqueta "indexedLabel"
      .setOutputCol("indexedLabel")
      .fit(data)
// Definir un nuevo valor "featureIndexer"
    val featureIndexer = new VectorIndexer()
    // Extraemos Datos del Column "features"
      .setInputCol("features")
      //Y Mostramos en la etiqueta "indexedFeatures" e indexamos 
      .setOutputCol("indexedFeatures")
      .setMaxCategories(4) 
      .fit(data)
// Definimos un Valor en Array para nuestros datos de prueba(70%) y entrenamiento(30%)
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
// Crear un valor nuevo para el model
    val dt = new DecisionTreeClassifier()
      .setLabelCol("indexedLabel")
      .setFeaturesCol("indexedFeatures")
// Hacemos conversion de los index a Etiquetas
    val labelConverter = new IndexToString()
      .setInputCol("prediction")
      .setOutputCol("predictedLabel")
      .setLabels(labelIndexer.labels)
// Creamos un valor pipeline para el arbol y forma de cadena 
    val pipeline = new Pipeline()
      .setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))
// Un valor "model" para ejecutar los datos de entrenamiento 
    val model = pipeline.fit(trainingData)
// Un valor "predictions" para ejecutar los datos de prueba
    val predictions = model.transform(testData)
// Realizamos el select de las 5 lineas
    predictions.select("predictedLabel", "label", "features").show(5)
// Crear un valor "evaluator" para saber cuales son los datos verderos
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("indexedLabel")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
// Crear un valor "accuaracy" para conocer el error de nuestros datos 
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${(1.0 - accuracy)}")
//Mostrar el valor del arbol con el valor "treeModel"
    val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
    println(s"Learned classification tree model:\n ${treeModel.toDebugString}")

    spark.stop()
  }
}

