package org.apache.spark.examples.ml
//Importar Librerias 
import org.apache.spark.ml.classification.LinearSVC
//Iniciar Sesion Spark
import org.apache.spark.sql.SparkSession
//Crear obj para ejecutar Main
object LinearSVCExample {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("LinearSVCExample")
      .getOrCreate()
// Crear un valor "training" que contendra el dataframe
    val training = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")
// Creamos un valor lscv con Max de 10 iteracion y un param de 0.1
    val lsvc = new LinearSVC()
      .setMaxIter(10)
      .setRegParam(0.1)
//Creamos un Fil en un valor "lsvModel"
    val lsvcModel = lsvc.fit(training)
// Imprimimos el Coeficiente y el Interccep 
    println(s"Coefficients: ${lsvcModel.coefficients} Intercept: ${lsvcModel.intercept}")

    spark.stop()
  }
}

