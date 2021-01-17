package org.apache.spark.examples.ml

//Importar librerias 
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
//Iniciar Sesion Spark
import org.apache.spark.sql.SparkSession
//Crear Obj para ejecutar el main
object NaiveBayesExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("NaiveBayesExample")
      .getOrCreate()
//Crear valor "data" que guardara el formato del dataframe
    val data = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")
//Crear un Arra que contendra los datos de Entrenamiento(70%) y Prueba(30%)
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3), seed = 1234L)
//Crear un valor nuevo "model" para el modelo NaiveBayes
    val model = new NaiveBayes()
      .fit(trainingData)
//Crear un valor "predictions" sobre los datos de Pruebas
    val predictions = model.transform(testData)
    predictions.show()
//Crear un valor "Evaluator" para Seleccionar las Etiquetas("label","prediction","accuracy")
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("label")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
//Crear un valor "accuracy" que calculara el Error de las pruebas
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test set accuracy = $accuracy")

    spark.stop()
  }
}

