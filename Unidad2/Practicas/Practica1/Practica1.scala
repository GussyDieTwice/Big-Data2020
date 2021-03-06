////////////////////////////////////////////
//// LINEAR REGRESSION EXERCISE ///////////
/// Coplete las tareas comentadas ///
/////////////////////////////////////////

// Import LinearRegression
import org.apache.spark.ml.regression.LinearRegression
// Opcional: Utilice el siguiente codigo para configurar errores
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
// Inicie una simple Sesion Spark
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()
// Utilice Spark para el archivo csv Clean-Ecommerce .
val data = spark.read.option("header", "true").option("inferSchema","true")csv("/home/gussy/git_workspace/Big-Data2020/Unidad2/Practicas/Practica1/Clean-Ecommerce.csv")
// Imprima el schema en el DataFrame.
data.printSchema()
// Imprima un renglon de ejemplo del DataFrane.
data.head(1)

//////////////////////////////////////////////////////
//// Configure el DataFrame para Machine Learning ////
//////////////////////////////////////////////////////

// Transforme el data frame para que tome la forma de
// ("label","features")
val colnames = data.columns
val firstrow = data.head(1)(0)
println("\n")
println("Example Data Row")
for(ind <- Range(1, colnames.length))
{
    println(colnames(ind))
    println(firstrow(ind))
    println("\n")
}
// Importe VectorAssembler y Vectors
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
// Renombre la columna Yearly Amount Spent como "label"
// Tambien de los datos tome solo la columa numerica 
// Deje todo esto como un nuevo DataFrame que se llame df
val df = data.select(data("Yearly Amount Spent").as("label"), $"Avg Session Length", $"Time on App", $"Time on Website", $"Length of Membership", $"Yearly Amount Spent")
// Que el objeto assembler convierta los valores de entrada a un vector
val assembler = new VectorAssembler().setInputCols(Array("Avg Area Income", "Avg Area House Age", "Avg Area Number of Rooms", "Avg Area Number of Bedrooms", "Area Population")).setOutputCol("features")
// Utilice el objeto VectorAssembler para convertir la columnas de entradas del df
// a una sola columna de salida de un arreglo llamado  "features"
// Configure las columnas de entrada de donde se supone que leemos los valores.
// Llamar a esto nuevo assambler.
// Utilice el assembler para transform nuestro DataFrame a dos columnas: label and features
val assembler = new VectorAssembler().setInputCols(Array("Avg Session Length", "Time on App", "Time on Website", "Length of Membership", "Yearly Amount Spent")).setOutputCol("features")
val output = assembler.transform(df).select($"label", $"features")
output.show()
// Crear un objeto para modelo de regresion linea.
val lr = new LinearRegression()
// Ajuste el modelo para los datos y llame a este modelo lrModelo
val lrModel = lr.fit(output)
// Imprima the  coefficients y intercept para la regresion lineal
println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")
// Resuma el modelo sobre el conjunto de entrenamiento imprima la salida de algunas metricas!
// Utilize metodo .summary de nuestro  modelo para crear un objeto
// llamado trainingSummary
val trainingSummary = lrModel.summary
println(s"numIterations: ${trainingSummary.totalIterations}")
println(s"objectiveHistory: ${trainingSummary.objectiveHistory.toList}")
// Muestre los valores de residuals, el RMSE, el MSE, y tambien el R^2 .
trainingSummary.residuals.show()
println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")
println(s"MSE: ${trainingSummary.meanSquaredError}")
println(s"r2: ${trainingSummary.r2}")
// Buen trabajo!

/* Resultado:
root
 |-- Email: string (nullable = true)
 |-- Avatar: string (nullable = true)
 |-- Avg Session Length: double (nullable = true)
 |-- Time on App: double (nullable = true)
 |-- Time on Website: double (nullable = true)
 |-- Length of Membership: double (nullable = true)
 |-- Yearly Amount Spent: double (nullable = true)

res2: Array[org.apache.spark.sql.Row] = Array([mstephenson@fernandez.com,Violet,34.49726772511229,12.65565114916675,39.57766801952616,4.0826206329529615,587.9510539684005])

colnames: Array[String] = Array(Email, Avatar, Avg Session Length, Time on App, Time on Website, Length of Membership, Yearly Amount Spent)

firstrow: org.apache.spark.sql.Row = [mstephenson@fernandez.com,Violet,34.49726772511229,12.65565114916675,39.57766801952616,4.0826206329529615,587.9510539684005]

Example Data Row

Avatar
Violet


Avg Session Length
34.49726772511229


Time on App
12.65565114916675


Time on Website
39.57766801952616


Length of Membership
4.0826206329529615


Yearly Amount Spent
587.9510539684005

+------------------+--------------------+
|             label|            features|
+------------------+--------------------+
| 587.9510539684005|[34.4972677251122...|
| 392.2049334443264|[31.9262720263601...|
|487.54750486747207|[33.0009147556426...|
| 581.8523440352177|[34.3055566297555...|
| 599.4060920457634|[33.3306725236463...|
|  637.102447915074|[33.8710378793419...|
| 521.5721747578274|[32.0215955013870...|
| 549.9041461052942|[32.7391429383803...|
| 570.2004089636196|[33.9877728956856...|
| 427.1993848953282|[31.9365486184489...|
| 492.6060127179966|[33.9925727749537...|
| 522.3374046069357|[33.8793608248049...|
| 408.6403510726275|[29.5324289670579...|
| 573.4158673313865|[33.1903340437226...|
| 470.4527333009554|[32.3879758531538...|
| 461.7807421962299|[30.7377203726281...|
|457.84769594494855|[32.1253868972878...|
|407.70454754954415|[32.3388993230671...|
| 452.3156754800354|[32.1878120459321...|
|  605.061038804892|[32.6178560628234...|
+------------------+--------------------+
only showing top 20 rows

Coefficients: [-5.279741241757083E-12,-7.938812604695143E-12,-8.908222631266152E-14,-1.2633517480293161E-11,1.0000000000002052] Intercept: 2.1570404555444455E-10

numIterations: 1

objectiveHistory: List(0.0)

+--------------------+
|           residuals|
+--------------------+
|1.477928890381008...|
|-2.50111042987555...|
|3.581135388230905...|
|-2.27373675443232...|
|-1.59161572810262...|
|3.410605131648481...|
|-9.09494701772928...|
|1.818989403545856...|
|-2.27373675443232...|
|2.785327524179592...|
|3.240074875066057...|
|-1.81898940354585...|
|-2.16004991671070...|
|-2.50111042987555...|
|-2.10320649784989...|
|-2.21689333557151...|
|-4.20641299569979...|
|7.389644451905042...|
|7.958078640513122...|
|-1.81898940354585...|
+--------------------+
only showing top 20 rows

RMSE: 2.036094446753258E-12

MSE: 4.145680596099457E-24

r2: 1.0
*/