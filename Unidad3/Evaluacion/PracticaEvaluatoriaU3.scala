object Evaluacion3 {

  def main(args: Array[String]): Unit = {

        //1. Importar una simple sesión Spark.
        import org.apache.spark.sql.SparkSession

        //2. Utilice las lineas de código para minimizar errores
        import org.apache.log4j._
        Logger.getLogger("org").setLevel(Level.ERROR)

        //3. Cree una instancia de la sesión Spark
        val spark = SparkSession.builder.appName("Evaluacion3").getOrCreate()

        //4. Importar la librería de Kmeans para el algoritmo de agrupamiento.
        import org.apache.spark.ml.clustering.KMeans

        //5. Carga el dataset de Wholesale Customers Data
        val df = spark.read.option("header", "true").option("inferSchema","true")csv("/home/gussy/git_workspace/Big-Data2020/Unidad3/Evaluacion/Wholesale customers data.csv")
        //val df = spark.read.option("header","true").option("inferSchema","true")csv("C:/Data2020/Big-Data2020/Evaluacion/Wholesale customers data.csv")
        df.show

        //6. Seleccione las siguientes columnas: Fresh, Milk, Grocery, Frozen, Detergents_Paper, 
        //Delicassen y llamar a este conjunto feature_data
        val  feature_data  = df.select("Fresh","Milk","Grocery","Frozen","Detergents_Paper","Delicassen")
        feature_data.show

        //7. Importar Vector Assembler y Vector
        import org.apache.spark.ml.feature.VectorAssembler

        //8. Crea un nuevo objeto Vector Assembler para las columnas de caracteristicas como un conjunto de entrada, recordando que no hay etiquetas
        val assembler = new VectorAssembler().setInputCols(Array("Fresh","Milk","Grocery","Frozen","Detergents_Paper","Delicassen")).setOutputCol("features")

        //9. Utilice el objeto assembler para transformar feature_data
        val data = assembler.transform(feature_data)
        data.show

        //10. Crear un modelo Kmeans con K=3
        val kMeans = new KMeans().setK(3).setSeed(1L)
        val model = kMeans.fit(data)

        //11. Evalúe  los grupos utilizando Within Set Sum of Squared Errors WSSSE e imprima los centroides.
        val WSSSE = model.computeCost(data)
        println(s"Within Set Sum of Squared Errors = $WSSSE")

        println("Cluster Centers: ")
        model.clusterCenters.foreach(println)

        spark.stop()

  }
}