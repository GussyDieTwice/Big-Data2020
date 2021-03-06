/*** Practica Evaluatoria Unidad 1
Encarnacion Ocampo Gustavo 16211993
Alcantar Balcon Adrian Giovanny 16210504 ***/

//Responder las siguientes preguntas con Spark DataFrames utilizando el “CSV” Netflix_2011_2016.csv. 
//1. Comienza una simple sesión Spark. 
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import spark.implicits._

val spark = SparkSession.builder().getOrCreate()

//2. Cargue el archivo Netflix Stock CSV, haga que Spark infiera los tipos de datos. 
val df = spark.read.option("header", "true").option("inferSchema","true")csv("/home/gussy/git_workspace/Big-Data2020/Unidad1/Evaluaciones/Netflix_2011_2016.csv")
val df = spark.read.option("header", "true").option("inferSchema","true")csv("C:/Users/adria/Documents/GitHub/Big-Data2020/Unidad1/Evaluaciones/Netflix_2011_2016.csv")

//3. ¿Cuáles son los nombres de las columnas? 
df.columns

//4. ¿Cómo es el esquema? 
df.printSchema()

//5. Imprime las primeras 5 columnas. 
df.head(5)

//6. Usa describe () para aprender sobre el DataFrame. 
df.describe().show()

//7. Crea un nuevo dataframe con una columna nueva llamada “HV Ratio” que es la  relación entre el precio de la columna “High” frente a la columna “Volume” de  acciones negociadas por un día. 
//(Hint: Es una operación de columnas). 
val hvRatio=df.withColumn("HV Ratio", df("High")/df("Volume"))
hvRatio.show()

//8. ¿Qué día tuvo el pico mas alto en la columna “Close”? 
df.groupBy(dayofweek(df("Date")).alias("Day")).max("Close").sort(asc("Day")).show()

//9. Escribe con tus propias palabras en un comentario de tu codigo. ¿Cuál es el  significado de la columna Cerrar “Close”? 
//Es el Total de los valores con los que cerraron esa fecha 

//10. ¿Cuál es el máximo y mínimo de la columna “Volume”?
df.select(max("Volume"), min("Volume")).show() 

//11.Con Sintaxis Scala/Spark $ conteste los siguiente: 
//◦ Hint: Basicamente muy parecido a la session de dates, tendran que crear otro  dataframe para contestar algunos de los incisos. 

//a. ¿Cuántos días fue la columna “Close” inferior a $ 600? 
df.filter($"Close"<600).count()

//b. ¿Qué porcentaje del tiempo fue la columna “High” mayor que $ 500? 
val perTiem = df.filter($"High">500).count()
val totalR = df.count()
val percen = (perTiem*1.0)/(totalR*100)

//c. ¿Cuál es la correlación de Pearson entre columna “High” y la columna “Volumen”? 
df.select(corr($"High", $"Volume")).show()

//d. ¿Cuál es el máximo de la columna “High” por año? 
val df2 = df.withColumn("Year", year(df("Date")))
val dfmaxyear = df2.groupBy("Year").max()
dfmaxyear.select($"Year", $"max(High)").show()

//e. ¿Cuál es el promedio de columna “Close” para cada mes del calendario? 
val monthdf = df.withColumn("Month",month(df("Date")))
val monthavgs = monthdf.select($"Month",$"Close").groupBy("Month").mean()
monthavgs.select($"Month",$"avg(Close)").show()