/*** Practica Evaluatoria Unidad 1
Encarnacion Ocampo Gustavo 16211993
Alcantar Balcon Adrian Giovanny ***/

//Responder las siguientes preguntas con Spark DataFrames utilizando el “CSV” Netflix_2011_2016.csv. 
//1. Comienza una simple sesión Spark. 
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

//2. Cargue el archivo Netflix Stock CSV, haga que Spark infiera los tipos de datos. 
val df = spark.read.option("header", "true").option("inferSchema","true")csv("/home/gussy/Escritorio/Trabajos/Datos Masivos/Unidad 1/Evaluacion/Netflix_2011_2016.csv")

//3. ¿Cuáles son los nombres de las columnas? 
df.columns

//4. ¿Cómo es el esquema? 
df.printSchema()

//5. Imprime las primeras 5 columnas. 
df.select("Date", "Open", "High", "Low", "Close").show()

//6. Usa describe () para aprender sobre el DataFrame. 
df.describe().show()

//7. Crea un nuevo dataframe con una columna nueva llamada “HV Ratio” que es la  relación entre el precio de la columna “High” frente a la columna “Volume” de  acciones negociadas por un día. 
//(Hint: Es una operación de columnas). 

//8. ¿Qué día tuvo el pico mas alto en la columna “Close”? 

//9. Escribe con tus propias palabras en un comentario de tu codigo. ¿Cuál es el  significado de la columna Cerrar “Close”? 

//10. ¿Cuál es el máximo y mínimo de la columna “Volume”?
df.select(max("Volume")).show()
df.select(min("Volume")).show()

//11.Con Sintaxis Scala/Spark $ conteste los siguiente: 
//◦ Hint: Basicamente muy parecido a la session de dates, tendran que crear otro  dataframe para contestar algunos de los incisos. 
//a. ¿Cuántos días fue la columna “Close” inferior a $ 600? 

//b. ¿Qué porcentaje del tiempo fue la columna “High” mayor que $ 500? 

//c. ¿Cuál es la correlación de Pearson entre columna “High” y la columna “Volumen”? 
df.select(corr("High", "Volume")).show()

//d. ¿Cuál es el máximo de la columna “High” por año? 

//e. ¿Cuál es el promedio de columna “Close” para cada mes del calendario? 
