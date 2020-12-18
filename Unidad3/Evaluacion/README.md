```scala
object Evaluacion3 {

  def main(args: Array[String]): Unit = {
```
1. Import a simple Spark session.
```scala        
        import org.apache.spark.sql.SparkSession
```
2. Use lines of code to minimize errors
```scala        
        import org.apache.log4j._
        Logger.getLogger("org").setLevel(Level.ERROR)
```
3. Create an instance of the Spark session
```scala        
        val spark = SparkSession.builder.appName("Evaluacion3").getOrCreate()
```

4. Import the Kmeans library for the clustering algorithm.
```scala
        import org.apache.spark.ml.clustering.KMeans
```
5. Loads the Wholesale Customers Data dataset
```scala        
        val df = spark.read.option("header", "true").option("inferSchema","true")csv("/home/gussy/git_workspace/Big-Data2020/Unidad3/Evaluacion/Wholesale customers data.csv")
        val df = spark.read.option("header","true").option("inferSchema","true")csv("C:/Data2020/Big-Data2020/Unidad3/Evaluacion/Wholesale customers data.csv")
        df.show
```
6. Select the following columns: Fresh, Milk, Grocery, Frozen, Detergents_Paper,
     Delicassen and call this set feature_data
```scala        
        val  feature_data  = df.select("Fresh","Milk","Grocery","Frozen","Detergents_Paper","Delicassen")
        feature_data.show
```
7. Import Vector Assembler and Vector
```scala        
        import org.apache.spark.ml.feature.VectorAssembler
```
8. Create a new Vector Assembler object for the feature columns as an input set, remembering that there are no labels
```scala        
        val assembler = new VectorAssembler().setInputCols(Array("Fresh","Milk","Grocery","Frozen","Detergents_Paper","Delicassen")).setOutputCol("features")
```
9. Use the assembler object to transform feature_data
```scala        
        val data = assembler.transform(feature_data)
        data.show
```
10. Create a Kmeans model with K = 3
```scala        
        val kMeans = new KMeans().setK(3).setSeed(1L)
        val model = kMeans.fit(data)
```
11. Evaluate the groups using Within Set Sum of Squared Errors WSSSE and print the centroids.
```scala
        val WSSSE = model.computeCost(data)
        println(s"Within Set Sum of Squared Errors = $WSSSE")

        println("Cluster Centers: ")
        model.clusterCenters.foreach(println)

        spark.stop()

  }
}
```
