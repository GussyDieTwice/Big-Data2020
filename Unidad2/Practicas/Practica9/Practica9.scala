import org.apache.spark.ml.classification.{LogisticRegression, OneVsRest}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

import org.apache.spark.sql.SparkSession

object OneVsRestExample {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .appName(s"OneVsRestExample")
      .getOrCreate()

     //Cargar el archivo o dataframe a leer en formato libsvm y declararlo como valor con nombre "inputData"
    val inputData = spark.read.format("libsvm")
      .load("/home/gussy/git_workspace/Big-Data2020/Unidad2/Practicas/Practica9/sample_multiclass_classification_data.txt")

    //Se dividen los datos en train y test de manera aleatoria (80% para train y 20% para test)
    val Array(train, test) = inputData.randomSplit(Array(0.8, 0.2))

    //Se declara el clasificador base y se establecen los parametros del entrenamiento
    val classifier = new LogisticRegression()
      .setMaxIter(10)
      .setTol(1E-6)
      .setFitIntercept(true)

    //Se declara el clasficador One vs Rest
    val ovr = new OneVsRest().setClassifier(classifier)

    //Se inicia el entrenamiento del modelo
    val ovrModel = ovr.fit(train)

    //Se realizan las predicciones
    val predictions = ovrModel.transform(test)

    //Se calcula la precision de los datos de prueba
    val evaluator = new MulticlassClassificationEvaluator()
      .setMetricName("accuracy")

    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${1 - accuracy}")

    spark.stop()
  }
}
/* Resultado:
Test Error = 0.0
*/
