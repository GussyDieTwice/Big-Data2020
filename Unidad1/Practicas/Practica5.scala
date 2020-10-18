//Practicar con 15 funciones predeterminadas de Spark para Dataframes, con la liga que hace referencia a este tema 

import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()
val df = spark.read.option("header", "true").option("inferSchema","true")csv("//home/gussy/git_workspace/Big-Data2020/Unidad1/Practicas/Netflix_2011_2016.csv")

//1. Mostrar las columnas existentes e informacion sobre ellas
df.printSchema()
//2. Mostrar el dataframe entero
df.show()
//3. Mostrar las columnas del dataframe
df.columns
//4. Seleccionar solo la columna "Date"
df.select("Date").show()
//5. Filtrar todo lo que sea mayor a 100 en la columna "Low"
df.filter($"Low">100).show()
//6.Sumatoria de la columna "Adj Close"
df.select(sum("Adj Close")).show()
//7. Mostrar el valor minimo de la columna "Low"
df.select(min("Low")).show()
//8. Mostrar el valor maximo de la columna "High"
df.select(max("High")).show()
//9. Mostrar el promedio de la columna "Volume"
df.select(avg("Volume")).show()
//10. Muestra la correlacion de Pearson entre las columnas "High" y "Low"
df.select(corr("High", "Low")).show()
