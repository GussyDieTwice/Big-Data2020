import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession

import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

val spark = SparkSession.builder().getOrCreate()
//Cargar el dataframe a leer en formato libsvm y declararlo como valor con nombre "data"
val data = spark.read.option("header", "true").option("inferSchema","true")csv("C:/Data2020/Big-Data2020/Unidad2/Practicas/Practica2/advertising.csv")

// Imprimir el Esquema
data.printSchema()
//Imprime todos los datos de la primer columna
data.head(1)
//Valor para meter en un array las columnas
val colnames = data.columns
//valor para imprimir los datos 1,0 de cada columna
val firstrow = data.head(1)(0)
println("\n")
//Ejemplo de filas afectadas
println("Example data row")
//Ciclo for para imprimir las columnas Age,Area Income, entre otras de forma ordenada
for(ind <- Range(1, colnames.length)){
    println(colnames(ind))
    println(firstrow(ind))
    println("\n")
}
//Valor "timedata" para saber el tiempo de los datos
val timedata = data.withColumn("Hour",hour(data("Timestamp")))
//Muestra las columnas de DF que seleccionamos
val logregdata = timedata.select(data("Clicked on Ad").as("label"), $"Daily Time Spent on Site", $"Age", $"Area Income", $"Daily Internet Usage", $"Hour", $"Male")
//Importar librerias apache
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
// Este valor solo acepta tipos numéricos, tipo booleano y tipo de vector en las colums afectadas
val assembler = (new VectorAssembler()
                  .setInputCols(Array("Daily Time Spent on Site", "Age","Area Income","Daily Internet Usage","Hour","Male"))
                  .setOutputCol("features"))

//Carga los datos de entrenamiento(70%) y prueba (30%)
val Array(training, test) = logregdata.randomSplit(Array(0.7, 0.3), seed = 12345)
//Importar Pipeline
import org.apache.spark.ml.Pipeline
//Crear un new LogisticRegression almacenado en el valor lr
val lr = new LogisticRegression()
//Este valor contendra un new Pipeline index con un array con el assambler
val pipeline = new Pipeline().setStages(Array(assembler, lr))
//Crear modelo pipeline entrenamiento
val model = pipeline.fit(training)
//Resultados del modelo prueba
val results = model.transform(test)
//Importar libreria MulticlassMetrics
import org.apache.spark.mllib.evaluation.MulticlassMetrics
//Valor para las prediciones y etiquedas 
val predictionAndLabels = results.select($"prediction",$"label").as[(Double, Double)].rdd
//crear un New MilticlassMetricas con predictionandLabels creados con anterioridas 
val metrics = new MulticlassMetrics(predictionAndLabels)
//Imprimir las Matrix 
println("Confusion matrix:")
println(metrics.confusionMatrix)
//Resultado de la metrics
metrics.accuracy

