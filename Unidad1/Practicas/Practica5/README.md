# Practice 5.

Import and login spark
```scala
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions.MutableAggregationBuffer
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction
import org.apache.spark.sql.types._
val spark = SparkSession.builder().getOrCreate()
```
CSV value
```
val df = spark.read.option("header", "true").option("inferSchema","true")csv("//home/gussy/git_workspace/Big-Data2020/Unidad1/Practicas/Netflix_2011_2016.csv")
val df = spark.read.option("header", "true").option("inferSchema","true")csv("C:/Users/adria/Documents/GitHub/Big-Data2020/Unidad1/Practicas/Netflix_2011_2016.csv")
```
# Perform 16 functions of the chosen csv

1. Show existing columns and information about them
```scala
df.printSchema()
```
2. Show the entire dataframe
```scala
df.show()
```
3. Show the columns of the dataframe
```scala
df.columns
```
4. Select only the "Date" column
```scala
df.select ("Date").show()
```
5. Filter everything that is greater than 100 in the "Low" column
```scala
df.filter ($ "Low"> 100).show()
```
6. Summation of column "Adj Close"
```scala
df.select (sum ("Adj Close")).show()
```
7. Show the minimum value of the column "Low"
```scala
df.select (min ("Low")).show()
```
8. Show the maximum value of the column "High"
```scala
df.select (max ("High")). show()
```
9. Show average of "Volume" column
```scala
df.select (avg ("Volume")).show()
```
10. Shows the Pearson correlation between the columns "High" and "Low"
```scala
df.select (corr ("High", "Low")).show()
```
11. Select the Date and Volume Columns and to this add + 1000 to the field
```scala
df.select ($ "Date", $ "Volume" + 1000).show()
```
12. Prints the physical plan to the console for debugging purposes.
```scala
df.explain ()
```
13. Controls a dataset locally and returns the new dataset
```scala
df.localCheckpoint()
```
14. Returns a set of objects with duplicate elements removed.
```scala
df.select (collect_set ("Volume")).show()
```
15. Returns the sum of distinct values in the expression.
```scala
df.select (sumDistinct ("Volume")).show()
```
16. Finds the absolute value of a numeric value.
```scala
df.select (abs (sum ("Volume"))).show()
```
