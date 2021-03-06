//Importar las Librerias a utilizar
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorAssembler

object Evaluacion2 {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Evaluacion2").getOrCreate()
   
    //1. Cargar en un dataframe Iris.csv 
    val df = spark.read.option("header", "true").option("inferSchema","true")csv("/home/gussy/git_workspace/Big-Data2020/Unidad2/Evaluacion/iris.csv")
    //val df = spark.read.option("header", "true").option("inferSchema","true")csv("C:/Data2020/Big-Data2020/Unidad2/Evaluacion/iris.csv")
   
    //2.¿Cuáles son los nombres de las columnas?
    val datos = df.columns
    df.columns
    //3. ¿Cómo es el esquema?
    val esquema = df.printSchema
    df.printSchema
    //4.Imprime las primeras 5 columnas.
    val imp = df.head(5)
    df.head(5)
    //5.Usa el metodo describe () para aprender mas sobre los datos del  DataFrame.
    val desc = df.describe().show()
    df.describe().show() 

    //6. Haga la transformación pertinente para los datos categoricos los cuales seran nuestras etiquetas a clasificar.
      //Crear un valor indexer que contendra las species en dentro del label en forma de datos categoricos 
    val indexer = new StringIndexer().setInputCol("species").setOutputCol("indexedLabel").fit(df)
    // En el valor indexed hacemos las tranformacion del DF eliminando Species por la nueva columna "label"
    val indexed = indexer.transform(df).drop("species").withColumnRenamed("indexedLabel", "label")
    indexed.show()
    //El valor assembler es para ensamblar el vector con las columnas sepal_length,sepal_width,pedal_length,pedal_width
    // en una column nueva llamada feature
    val assembler = new VectorAssembler().setInputCols(Array("sepal_length","sepal_width","petal_length","petal_width")).setOutputCol("features")
      // En el valor data, llamamos  a nuestro valor assembler y hacemos la transformacion , con nuestro valor creado anterior indexed
    val data = assembler.transform(indexed)
    data.show()
    //Con ayuda de este valor podremos saber los index que tomaron las species
    val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(indexed)
    //Mandamos a imprimir
    println(s"Labels: ${indexer.labels.mkString("[", ", ", "]")}")
    //Labels: [versicolor, virginica, setosa]
    println(s"indexedLabels: ${labelIndexer.labels.mkString("[", ", ", "]")}")
    //indexedLabels: [1.0, 0.0, 2.0]

    //7. Construya el modelos de clasificación y explique su arquitectura.
    // Separamos los datos de Entrenamiento y de Prueba que usaremos
    val splits = data.randomSplit(Array(0.7, 0.3), seed = 1234L)
    val train = splits(0)
    val test = splits(1)
    // Se especifican las capas de la red neuronal:
    // El tamaños de nuestra capa de Entrada sera de 4 (caracteristicas), dos capas intermedias
    // una de tamaño 5 y la otra de tamaño 4
    // y 3 de salida (las clases)
    val layers = Array[Int](4, 5, 4, 3)

    // En el valor trainer establecemos los parametros de entrenamiento
    val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)
    // Con el valor model Se entrena el modelo
    val model = trainer.fit(train)

    // En esta zona calculamos la precision de los datos de prueba
    val result = model.transform(test)
    val predictionAndLabels = result.select("prediction", "label")
    val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
    
    //8. Imprima los resultados del modelo
      //Imprimimos nuestra exactitud de nuestro modelo
     println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")

    spark.stop()
  }
}
