# Test Unit-3.

## Introduction.
K-Means (translated as K-Medias in Spanish), is a method of grouping or clustering.
Clustering is a technique to find and classify K groups of data (clusters). Thus, the elements that share similar characteristics will be together in the same group, separated from the other groups with which they do not share characteristics.
To find out if the data are similar or different, the K-means algorithm uses the distance between the data. Observations that are similar will have a smaller distance between them. In general, the Euclidean distance is used as a measure, although other functions can also be used.

* Load the data.
* Initialize K to your chosen number of neighbors.
* For each example in the data.
* Calculate the distance between the query sample and the current sample from the data.
* Add the distance and index from the example to an ordered collection
* Sort the ordered collection of distances and indices from least to greatest (in ascending order) by the distances.
* Choose the first K entries in the sorted collection.
* Get the labels of the selected K inputs.
* If there is regression, it returns the mean of the K labels.
* If it is classification, return the K tags fashion

## Instruction.
Develop the following instructions in Spark with the Scala programming language.

## Objective
El objetivo de este examen práctico es tratar de agrupar los clientes de regiones específicas de un distribuidor al mayoreo. Esto en base a las ventas de algunas categorías de productos.
Las fuente de datos se encuentra en el repositorio: https://github.com/jcromerohdz/BigData/blob/master/Spark_clustering/Wholesale%20customers%20data.csv

**Obj.**
```scala
object Evaluacion3 {

  def main(args: Array[String]): Unit = {
```
**1. Import a simple Spark session.**

Solution: We import the Library to start Spark session

```scala        
        import org.apache.spark.sql.SparkSession
```

**2. Use lines of code to minimize errors.**

Solution: We imported the log4j Library to help us minimize errors in our code.

```scala        
        import org.apache.log4j._
        Logger.getLogger("org").setLevel(Level.ERROR)
```
**3. Create an instance of the Spark session.**

Solution: For the creation of the instance we declare a value called “spark” and we give it an appName “Evaluation3”

```scala        
        val spark = SparkSession.builder.appName("Evaluacion3").getOrCreate()
```

**4. Import the Kmeans library for the clustering algorithm.**

Solution: We import the Kmeans library to run our algorithm to work

```scala
        import org.apache.spark.ml.clustering.KMeans
```
**5. Loads the Wholesale Customers Data dataset.**

Solution: With a value called "df" each member loads the dataset on their machine.

```scala        
        val df = spark.read.option("header", "true").option("inferSchema","true")csv("/home/gussy/git_workspace/Big-Data2020/Unidad3/Evaluacion/Wholesale customers data.csv")
        val df = spark.read.option("header","true").option("inferSchema","true")csv("C:/Data2020/Big-Data2020/Unidad3/Evaluacion/Wholesale customers data.csv")
        df.show
```
**6. Select the following columns: Fresh, Milk, Grocery, Frozen, Detergents_Paper,
     Delicassen and call this set feature_data.**
     
Solution: The Fresh, Milk, etc columns of our dataframe are selected and saved in the feature_data value.

```scala        
        val  feature_data  = df.select("Fresh","Milk","Grocery","Frozen","Detergents_Paper","Delicassen")
        feature_data.show
```
Result:
```scala
+-----+-----+-------+------+----------------+----------+
|Fresh| Milk|Grocery|Frozen|Detergents_Paper|Delicassen|
+-----+-----+-------+------+----------------+----------+
|12669| 9656|   7561|   214|            2674|      1338|
| 7057| 9810|   9568|  1762|            3293|      1776|
| 6353| 8808|   7684|  2405|            3516|      7844|
|13265| 1196|   4221|  6404|             507|      1788|
|22615| 5410|   7198|  3915|            1777|      5185|
| 9413| 8259|   5126|   666|            1795|      1451|
|12126| 3199|   6975|   480|            3140|       545|
| 7579| 4956|   9426|  1669|            3321|      2566|
| 5963| 3648|   6192|   425|            1716|       750|
| 6006|11093|  18881|  1159|            7425|      2098|
| 3366| 5403|  12974|  4400|            5977|      1744|
|13146| 1124|   4523|  1420|             549|       497|
|31714|12319|  11757|   287|            3881|      2931|
|21217| 6208|  14982|  3095|            6707|       602|
|24653| 9465|  12091|   294|            5058|      2168|
|10253| 1114|   3821|   397|             964|       412|
| 1020| 8816|  12121|   134|            4508|      1080|
| 5876| 6157|   2933|   839|             370|      4478|
|18601| 6327|  10099|  2205|            2767|      3181|
| 7780| 2495|   9464|   669|            2518|       501|
+-----+-----+-------+------+----------------+----------+
```
**7. Import Vector Assembler and Vector.**

Solution: Vector Assembler is imported to help us integrate several columns in a single arrangement or Vector.

```scala        
        import org.apache.spark.ml.feature.VectorAssembler
```
**8. Create a new Vector Assembler object for the feature columns as an input set, remembering that there are no labels.**

Solution: In the value assembler the library of the same name is started and the selected columns are integrated into an arrangement or vector called features.

```scala        
        val assembler = new VectorAssembler().setInputCols(Array("Fresh","Milk","Grocery","Frozen","Detergents_Paper","Delicassen")).setOutputCol("features")
```
**9. Use the assembler object to transform feature_data.**

Solution: In the data value, the assembler value is saved to which the feature data value was added, leaving as a result the union of both.

```scala        
        val data = assembler.transform(feature_data)
        data.show
```
**10. Create a Kmeans model with K = 3.**

Solution: The Kmeans model is started with K = 3 to start the grouping and it is saved in the value called kMeans, then a model value is declared in which we will run this model with the data value.

```scala        
        val kMeans = new KMeans().setK(3).setSeed(1L)
        val model = kMeans.fit(data)
```
**11. Evaluate the groups using Within Set Sum of Squared Errors WSSSE and print the centroids.**

Solution: The WSSSE is obtained and saved in the error value for printing.
We also proceed to print the Centroids of the clusters formed by Kmeans. 

```scala
        val WSSSE = model.computeCost(data)
        println(s"Within Set Sum of Squared Errors = $WSSSE")

        println("Cluster Centers: ")
        model.clusterCenters.foreach(println)

        spark.stop()

  }
}
```
## Conclusion.

## Sources.
* https://spark.apache.org/docs/2.4.7/ml-clustering.html#k-means
