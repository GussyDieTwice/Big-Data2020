import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

import org.apache.spark.sql.SparkSession

object MultilayerPerceptronClassifierExample {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("MultilayerPerceptronClassifierExample")
      .getOrCreate()

    //Cargar el archivo o dataframe a leer en formato libsvm y declararlo como valor con nombre "data"
    val data = spark.read.format("libsvm")
      .load("/home/gussy/git_workspace/Big-Data2020/Unidad2/Practicas/Practica7/sample_multiclass_classification_data.txt")

    //Se dividen los datos en train y test de manera aleatoria (60% para train y 40% para test)
    val splits = data.randomSplit(Array(0.6, 0.4), seed = 1234L)
    val train = splits(0)
    val test = splits(1)

    //Se especifican las capas de la red neuronal en un arreglo
    val layers = Array[Int](4, 5, 4, 3) //la capa de entrada es de tamaño 4 (caracteristicas), las dos intermedias de tamaño 5 y 4, y la de salida de tamaño 3 (clases)

    //Se declara el clasificador base y se establecen los parametros del entrenamiento
    val trainer = new MultilayerPerceptronClassifier()
      .setLayers(layers)
      .setBlockSize(128)
      .setSeed(1234L)
      .setMaxIter(100)

    //Se inicia el entrenamiento del modelo
    val model = trainer.fit(train)

    //Se calcula la precision de los datos de prueba
    val result = model.transform(test)
    val predictionAndLabels = result.select("prediction", "label")
    val evaluator = new MulticlassClassificationEvaluator()
      .setMetricName("accuracy")

    //Se despliega la exactitud del modelo
    println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")

    spark.stop()
  }
}

/* Resultado:
Test set accuracy = 0.9019607843137255
*/