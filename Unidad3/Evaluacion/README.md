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
Solution:
```scala        
        import org.apache.spark.sql.SparkSession
```

**2. Use lines of code to minimize errors.**
Solution:
```scala        
        import org.apache.log4j._
        Logger.getLogger("org").setLevel(Level.ERROR)
```
**3. Create an instance of the Spark session.**
Solution:
```scala        
        val spark = SparkSession.builder.appName("Evaluacion3").getOrCreate()
```

**4. Import the Kmeans library for the clustering algorithm.**
Solution:
```scala
        import org.apache.spark.ml.clustering.KMeans
```
**5. Loads the Wholesale Customers Data dataset.**
Solution:
```scala        
        val df = spark.read.option("header", "true").option("inferSchema","true")csv("/home/gussy/git_workspace/Big-Data2020/Unidad3/Evaluacion/Wholesale customers data.csv")
        val df = spark.read.option("header","true").option("inferSchema","true")csv("C:/Data2020/Big-Data2020/Unidad3/Evaluacion/Wholesale customers data.csv")
        df.show
```
**6. Select the following columns: Fresh, Milk, Grocery, Frozen, Detergents_Paper,
     Delicassen and call this set feature_data.**
Solution:
```scala        
        val  feature_data  = df.select("Fresh","Milk","Grocery","Frozen","Detergents_Paper","Delicassen")
        feature_data.show
```
Result:

**7. Import Vector Assembler and Vector.**
Solution:
```scala        
        import org.apache.spark.ml.feature.VectorAssembler
```
**8. Create a new Vector Assembler object for the feature columns as an input set, remembering that there are no labels.**
Solution:
```scala        
        val assembler = new VectorAssembler().setInputCols(Array("Fresh","Milk","Grocery","Frozen","Detergents_Paper","Delicassen")).setOutputCol("features")
```
**9. Use the assembler object to transform feature_data.**
Solution:
```scala        
        val data = assembler.transform(feature_data)
        data.show
```
**10. Create a Kmeans model with K = 3.**
Solution:
```scala        
        val kMeans = new KMeans().setK(3).setSeed(1L)
        val model = kMeans.fit(data)
```
**11. Evaluate the groups using Within Set Sum of Squared Errors WSSSE and print the centroids.**
Solution:
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
