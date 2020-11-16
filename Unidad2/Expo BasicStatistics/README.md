# Basic Statistics (Estadísticas Básicas)

## Correlation (Correlacion).
### Correlacion de Pearson.
El coeficiente de correlación de Pearson es una prueba que mide la relación estadística entre dos variables continuas. Si la asociación entre los elementos no es lineal, entonces el coeficiente no se encuentra representado adecuadamente.

El coeficiente de correlación puede tomar un rango de valores de +1 a -1. Un valor de 0 indica que no hay asociación entre las dos variables. Un valor mayor que 0 indica una asociación positiva. Es decir, a medida que aumenta el valor de una variable, también lo hace el valor de la otra. Un valor menor que 0 indica una asociación negativa; es decir, a medida que aumenta el valor de una variable, el valor de la otra disminuye.

¿Cómo se calcula el coeficiente de correlación de Pearson?
La fórmula del coeficiente de correlación de Pearson es la siguiente:
![Pearson Formula](https://www.webyempresas.com/wp-content/uploads/2018/05/formula.jpg)
Donde:
“x” es igual a la variable número uno, “y” pertenece a la variable número dos, “Zx” es la desviación estándar de la variable uno, “Zy” es la desviación estándar de la variable dos y “N” es es número de datos.
![Desviacion Estandar Formula](https://economipedia.com/wp-content/uploads/Formula-Desviacion-Tipica.jpg)

Ventajas y desventajas del coeficiente de correlación de Pearson.
Entre las principales ventajas del coeficiente de correlación de Karl Pearson se encuentran:

*El valor es independiente de cualquier unidad que se utiliza para medir las variables.
*Si la muestra es grande, es más probable la exactitud de la estimación.

Alguna de las desventajas del coeficiente de correlación son:

*Es necesario las dos variables sean medidas a un nivel cuantitativo continuo.
*La distribución de las variables deben ser semejantes a la curva normal.

### Correlacion de Spearman.
El coeficiente de correlación de Spearman es una medida no paramétrica de la correlación de rango (dependencia estadística del ranking entre dos variables). Se utiliza principalmente para el análisis de datos.

(Las pruebas no paramétricas son aquellas que se encargan de analizar datos que no tienen una distribución particular y se basan una hipótesis, pero los datos no están organizados de forma normal. Aunque tienen algunas limitaciones, cuentan con resultados estadísticos ordenados que facilita su comprensión.)

Mide la fuerza y la dirección de la asociación entre dos variables clasificadas. Pero antes de hablar de la correlación de Spearman, es importante entender la correlación de Pearson, la cual es una medida estadística de la fuerza de una relación lineal entre datos emparejados.

### Matriz de Correlacion.

### Programa Ejemplo.
```scala
import org.apache.spark.ml.linalg.{Matrix, Vectors}
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession

object CorrelationExample {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("CorrelationExample")
      .getOrCreate()
    import spark.implicits._

    val data = Seq(
      Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))),
      Vectors.dense(4.0, 5.0, 0.0, 3.0),
      Vectors.dense(6.0, 7.0, 0.0, 8.0),
      Vectors.sparse(4, Seq((0, 9.0), (3, 1.0)))
    )

    val df = data.map(Tuple1.apply).toDF("features")
    val Row(coeff1: Matrix) = Correlation.corr(df, "features").head
    println(s"Pearson correlation matrix:\n $coeff1")

    val Row(coeff2: Matrix) = Correlation.corr(df, "features", "spearman").head
    println(s"Spearman correlation matrix:\n $coeff2")

    spark.stop()
  }
}
```
## Hypothesis Testing. 

### Programa Ejemplo.
```scala
import org.apache.spark.ml.linalg.{Vector, Vectors}
import org.apache.spark.ml.stat.ChiSquareTest
import org.apache.spark.sql.SparkSession

object ChiSquareTestExample {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("ChiSquareTestExample")
      .getOrCreate()
    import spark.implicits._

    val data = Seq(
      (0.0, Vectors.dense(0.5, 10.0)),
      (0.0, Vectors.dense(1.5, 20.0)),
      (1.0, Vectors.dense(1.5, 30.0)),
      (0.0, Vectors.dense(3.5, 30.0)),
      (0.0, Vectors.dense(3.5, 40.0)),
      (1.0, Vectors.dense(3.5, 40.0))
    )

    val df = data.toDF("label", "features")
    val chi = ChiSquareTest.test(df, "features", "label").head
    println(s"pValues = ${chi.getAs[Vector](0)}")
    println(s"degreesOfFreedom ${chi.getSeq[Int](1).mkString("[", ",", "]")}")
    println(s"statistics ${chi.getAs[Vector](2)}")

    spark.stop()
  }
}
```

## Summerizer.
### Resumen de estadísticas
Estadística de resumen de columnas vectoriales
## Uso 
 *La Tabla de salida constará de campos que contienen el resultado de la operación estadística.
 *Con esta herramienta están disponibles las siguientes operaciones estadísticas: suma, valor medio, máximo, mínimo, rango, desviación estándar, recuento, primero y último.
 *Los valores nulos se excluyen de todos los cálculos estadísticos
 ## Metricas 
*SUM: Agrega el valor total para el campo especificado.
*MEAN: Calcula el promedio para el campo especificado.
*MIN: Busca el valor más pequeño para todos los registros del campo especificado.
*MAX: Busca el valor más grande para todos los registros del campo especificado.
*STD: Busca la desviación estándar de los valores en el campo especificado.
*Varianza: Busca la varianza entre ambos valores 
Metricas Extras 
*COUNT: Busca la cantidad de valores incluidos en los cálculos estadísticos. Esto cuenta todos los valores excepto los valores nulos.
*FIRST: Busca el primer registro de la entrada y utiliza el valor de campo especificado.
*LAST: Busca el último registro de la entrada y utiliza el valor de campo especificado.

### Programa Ejemplo.
```scala
import org.apache.spark.ml.linalg.{Vector, Vectors}
import org.apache.spark.ml.stat.Summarizer
import org.apache.spark.sql.SparkSession

object SummarizerExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("SummarizerExample")
      .getOrCreate()

    import spark.implicits._
    import Summarizer._

    val data = Seq(
      (Vectors.dense(2.0, 3.0, 5.0), 1.0),
      (Vectors.dense(4.0, 6.0, 7.0), 2.0)
    )

    val df = data.toDF("features", "weight")

    val (meanVal, varianceVal) = df.select(metrics("mean", "variance")
      .summary($"features", $"weight").as("summary"))
      .select("summary.mean", "summary.variance")
      .as[(Vector, Vector)].first()

    println(s"with weight: mean = ${meanVal}, variance = ${varianceVal}")

    val (meanVal2, varianceVal2) = df.select(mean($"features"), variance($"features"))
      .as[(Vector, Vector)].first()

    println(s"without weight: mean = ${meanVal2}, sum = ${varianceVal2}")

    spark.stop()
  }
}
```
### Fuentes.
https://www.questionpro.com/blog/es/coeficiente-de-correlacion-de-pearson/
