# Basic Statistics (Estadísticas Básicas)

## Correlation (Correlacion).
### Correlacion de Pearson.
El coeficiente de correlación de Pearson es una prueba que mide la relación estadística entre dos variables continuas. Si la asociación entre los elementos no es lineal, entonces el coeficiente no se encuentra representado adecuadamente.

El coeficiente de correlación puede tomar un rango de valores de +1 a -1. Un valor de 0 indica que no hay asociación entre las dos variables. Un valor mayor que 0 indica una asociación positiva. Es decir, a medida que aumenta el valor de una variable, también lo hace el valor de la otra. Un valor menor que 0 indica una asociación negativa; es decir, a medida que aumenta el valor de una variable, el valor de la otra disminuye.

**¿Cómo se calcula el coeficiente de correlación de Pearson?**
La fórmula del coeficiente de correlación de Pearson es la siguiente:
![Pearson Formula](https://www.webyempresas.com/wp-content/uploads/2018/05/formula.jpg)
Donde:
“x” es igual a la variable número uno, “y” pertenece a la variable número dos, “Zx” es la desviación estándar de la variable uno, “Zy” es la desviación estándar de la variable dos y “N” es es número de datos.
![Desviacion Estandar Formula](https://economipedia.com/wp-content/uploads/Formula-Desviacion-Tipica.jpg)

**Ejemplo.**
![Paso1](https://www.uv.es/webgid/Descriptiva/Img11.jpg)

![Paso2](https://www.uv.es/webgid/Descriptiva/Img12.jpg)

![Paso3](https://www.uv.es/webgid/Descriptiva/Img13.jpg)

### Correlacion de Spearman.
El coeficiente de correlación de Spearman es una medida no paramétrica de la correlación de rango (dependencia estadística del ranking entre dos variables). Se utiliza principalmente para el análisis de datos.

(Las pruebas no paramétricas son aquellas que se encargan de analizar datos que no tienen una distribución particular y se basan una hipótesis, pero los datos no están organizados de forma normal. Aunque tienen algunas limitaciones, cuentan con resultados estadísticos ordenados que facilita su comprensión.)

Mide la fuerza y la dirección de la asociación entre dos variables clasificadas. Pero antes de hablar de la correlación de Spearman, es importante entender la correlación de Pearson, la cual es una medida estadística de la fuerza de una relación lineal entre datos emparejados.



**¿Cómo calcular el coeficiente de correlación de Spearman?**
![Formula Spearman](https://www.questionpro.com/blog/wp-content/uploads/2019/02/2.png)
n= número de puntos de datos de las dos variables

di= diferencia de rango del elemento “n”

El Coeficiente Spearman,⍴, puede tomar un valor entre +1 y -1 donde:

* Un valor de +1 en ⍴ significa una perfecta asociación de rango
* Un valor 0 en ⍴ significa que no hay asociación de rangos
* Un valor de -1 en ⍴ significa una perfecta asociación negativa entre los rangos.
* Si el valor de ⍴ se acerca a 0, la asociación entre los dos rangos es más débil.

Debemos ser capaces de clasificar los datos antes de proceder con el coeficiente de correlación de Spearman. Es importante observar que si se incrementa una variable, la otra sigue una relación monótona.

**Ejemplo.**
| X 	| Y 	| d 	| d^2 	|
|:-:	|:-:	|:-:	|:---:	|
| 3 	| 5 	| 2 	|  4  	|
| 5 	| 3 	| 2 	|  4  	|
| 1 	| 2 	| 1 	|  1  	|
| 6 	| 6 	| 0 	|  0  	|
| 7 	| 8 	| 1 	|  1  	|
| 2 	| 1 	| 1 	|  1  	|
| 8 	| 7 	| 1 	|  1  	|
| 9 	| 9 	| 0 	|  0  	|
| 4 	| 4 	| 0 	|  0  	|
|   	|   	|   	|  12 	|

* Agrega una tercera columna “d” a tu conjunto de datos, “d” aquí denota la diferencia entre los rangos. Por ejemplo, si el rango de física del primer estudiante es 3 y el rango de matemáticas es 5, entonces la diferencia en el rango es 3. En la cuarta columna, cuadrar sus valores “d”.
* Sumar todos los valores del cuadrado “d” que es 12 (∑d cuadrada).
* Insertar estos valores en la fórmula.
=1-(6*12)/(9(81-1))

=1-72/720

=1-01

=0.9

El coeficiente de correlación de Spearman para estos datos es de 0.9 y como se mencionó anteriormente si el valor de ⍴ se acerca a +1 entonces tienen una asociación perfecta de rango.

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
### ¿Qué es la prueba de hipótesis?
La prueba de hipótesis es un acto en estadística mediante el cual un analista prueba 
una suposición con respecto a un parámetro de población. La metodología empleada por el analista 
depende de la naturaleza de los datos utilizados y el motivo del análisis.
### Objetivo 
* El objetivo principal de la estadística es probar una hipótesis.
* La prueba de hipótesis se utiliza para evaluar la plausibilidad de una hipótesis utilizando datos de muestra.
* La prueba proporciona evidencia sobre la plausibilidad de la hipótesis, dados los datos.
* Los analistas estadísticos prueban una hipótesis midiendo y examinando una muestra aleatoria de la población que se analiza.
### Pasos para crear la Hipostesis 
Todas las hipótesis se prueban mediante un proceso de cuatro pasos:
* El primer paso es que el analista enuncie las dos hipótesis de modo que solo una pueda ser correcta.
* El siguiente paso es formular un plan de análisis, que describe cómo se evaluarán los datos.
* El tercer paso es llevar a cabo el plan y analizar físicamente los datos de la muestra.
* El cuarto y último paso es analizar los resultados y rechazar la hipótesis nula o afirmar que la hipótesis nula es plausible, dados los datos.
### Hipotesis Nula 
### ¿Qué es una hipótesis nula?
Una hipótesis nula es un tipo de hipótesis utilizada en estadística que propone que no existe diferencia entre determinadas características de una población (o proceso de generación de datos).
### Conclusiones Claves 
* Una hipótesis nula es un tipo de conjetura utilizada en estadística que propone que no existe diferencia entre ciertas características de una población o proceso de generación de datos.
* La hipótesis alternativa propone que hay una diferencia.
* La prueba de hipótesis proporciona un método para rechazar una hipótesis nula dentro de un cierto nivel de confianza. (Sin embargo, las hipótesis nulas no se pueden probar).
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
### Uso 
* La Tabla de salida constará de campos que contienen el resultado de la operación estadística.
* Con esta herramienta están disponibles las siguientes operaciones estadísticas: suma, valor medio, máximo, mínimo, rango, desviación estándar, recuento, primero y último.
* Los valores nulos se excluyen de todos los cálculos estadísticos
 ### Metricas 
* SUM: Agrega el valor total para el campo especificado.
* MEAN: Calcula el promedio para el campo especificado.
* MIN: Busca el valor más pequeño para todos los registros del campo especificado.
* MAX: Busca el valor más grande para todos los registros del campo especificado.
* STD: Busca la desviación estándar de los valores en el campo especificado.
* Varianza: Busca la varianza entre ambos valores 
Metricas Extras 
* COUNT: Busca la cantidad de valores incluidos en los cálculos estadísticos. Esto cuenta todos los valores excepto los valores nulos.
* FIRST: Busca el primer registro de la entrada y utiliza el valor de campo especificado.
* LAST: Busca el último registro de la entrada y utiliza el valor de campo especificado.

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
* https://www.questionpro.com/blog/es/coeficiente-de-correlacion-de-pearson/
* https://www.uv.es/webgid/Descriptiva/31_coeficiente_de_pearson.html 
* https://www.investopedia.com/terms/h/hypothesistesting.asp
* https://www.statisticshowto.com/probability-and-statistics/hypothesis-testing/?fbclid=IwAR1Lk1PU64bshZdMFep_LjFhUCzjtHfV3AuKTgGQnleXcXrB-mqOykzbTWQ
* https://desktop.arcgis.com/es/arcmap/latest/tools/analysis-toolbox/summary-statistics.html
* https://spark.apache.org/docs/2.4.7/ml-statistics.html#summarizer
* https://www.investopedia.com/terms/n/null_hypothesis.asp
### Video de Apoyo
* https://www.youtube.com/watch?v=V4SRgabFbz0&list=LL&index=2&t=93s&fbclid=IwAR04LN57q9hehBDwgVhQanmiEhyykaWG--ld7f3IsBz6jTyAa6Tvb1JZcVQ
