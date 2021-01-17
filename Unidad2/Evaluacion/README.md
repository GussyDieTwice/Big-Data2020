# Test Unit-2

## Introduction.

What is Machine Learning? 
Machine Learning is a scientific discipline in the field of Artificial 
Intelligence that creates systems that learn automatically. 
Learning in this context means identifying complex patterns in millions of data. 
The machine that really learns is an algorithm that reviews data and is capable of 
predicting future behavior. Automatically, also in this context, implies that these systems are
improved autonomously over time, without human intervention.

![Graphic](https://github.com/CinthiaBV/EXPODM/blob/master/Image/rn.jpg?raw=true)

As described in the Image, MLPC consists of multiple layers of nodes, including the input layer, 
hidden layers (also called intermediate layers), and output layers. Each layer is completely connected 
to the next layer in the network. Where the input layer, the intermediate layers and the output layer can be defined as follows:
* The input layer is the neurons that accept the input values, the nodes are the ones that represent the input data.
* The hidden layers are found between the input and output layers and their function depends on those assigned by the 
input layer to the output layer of a node.
* The output layer is the final layer of a neural network that returns the result to the user's environment.

![Graphic2](https://austingwalters.com/wp-content/uploads/2018/12/mlp.png)

## Instructions.

Develop the following instructions in Spark with the Scala programming language, using only the documentation of the Machine Learning Mllib
library from Spark and Google.
In the matter of massive data the students were asked to carry out an evaluative practice, this is based on the functions of Spark 
with the Scala language for the Dataframes section, in this case the Machine Learning Multilayer Perceptron Classifier method will be applied :

**1. Load into an Iris.csv dataframe found at https://github.com/jcromerohdz/iris, elaborate the necessary data cleaning to be processed by the following algorithm (Important, this cleaning must be through a Scala script in Spark).
Use the Spark Mllib library the Machine Learning algorithm corresponding to multilayer perceptron.**

Solution:
```scala 
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorAssembler


val spark = SparkSession.builder.appName("Evaluacion2").getOrCreate()
val df = spark.read.option("header","true").option("inferSchema","true")csv("/home/gussy/git_workspace/Big-Data2020/Unidad2/Evaluacion/iris.csv")
val df = spark.read.option("header","true").option("inferSchema","true")csv("C:/Data2020/Big-Data2020/Unidad2/Evaluacion/iris.csv")
```

Different libraries that will be necessary when carrying out our practice were imported, MultilayerPerceptronClassifier which is the 
one of the machine learning method that we will use, MultiClassificationEvaluator which will help us when calculating the accuracy of our model,
StringIndexer which will be the one with which we transform the categorical data into numeric, and VectorAssembler which is to join different
columns or vectors.

**2. What are the column names?**

Solution:
```scala
df.columns
```
In this line of code the name of the columns of our csv file is printed.
Result:
```
Array[String] = Array(sepal_length, sepal_width, petal_length, petal_width, species)
```

**3. How is the scheme?**

Solution: 
```scala
df.printSchema
```
With the .printSchema it helps us to know more about our csv file regarding columns.
Result:
```
    |-- sepal_length: double (nullable = true)
    |-- sepal_width: double (nullable = true)
    |-- petal_length: double (nullable = true)
    |-- petal_width: double (nullable = true)
    |-- species: string (nullable = true) */
    esquema: Unit = ()
 ```

**4. Print the first 5 columns.**

Solution:
```scala
df.head(5)
```
The .head (5) operation will display the first 5 columns (or those that we mark in parentheses) with the first 5 lines in an array.

Result:
```scala
Array([5.1,3.5,1.4,0.2,setosa],[4.9,3.0,1.4,0.2,setosa], [4.7,3.2,1.3,0.2,setosa],[4.6,3.1,1.5,0.2,setosa], [5.0,3.6,1.4,0.2,setosa])
```
**5. Use the describe () method to learn more about the data in the DataFrame.**

Solution:
```scala
df.describe().show()
```
The .describe () helps us to know more about our csv in a general way.

Result:
```
+-------+------------------+-------------------+------------------+------------------+---------+
|summary|      sepal_length|        sepal_width|      petal_length|       petal_width|  species|
+-------+------------------+-------------------+------------------+------------------+---------+
|  count|               150|                150|               150|               150|      150|
|   mean| 5.843333333333335| 3.0540000000000007|3.7586666666666693|1.1986666666666672|     null|
| stddev|0.8280661279778637|0.43359431136217375| 1.764420419952262|0.7631607417008414|     null|
|    min|               4.3|                2.0|               1.0|               0.1|   setosa|
|    max|               7.9|                4.4|               6.9|               2.5|virginica|
+-------+------------------+-------------------+------------------+------------------+---------+ 
```

**6. Make the pertinent transformation for the categorical data which will be our labels to be classified.**

Solution:
```scala
val indexer = new StringIndexer().setInputCol("species").setOutputCol("indexedLabel").fit(df)
val indexed = indexer.transform(df).drop("species").withColumnRenamed("indexedLabel", "label")
indexed.show()
val assembler = new VectorAssembler().setInputCols(Array("sepal_length","sepal_width","petal_length","petal_width")).setOutputCol("features")
val data = assembler.transform(indexed)
data.show()
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(indexed)
println(s"Labels: ${indexer.labels.mkString("[", ", ", "]")}")
println(s"indexedLabels: ${labelIndexer.labels.mkString("[", ", ", "]")}")
```

In the indexer value we take the species column from the dataframe and with StringIndexer we convert it from categorical 
to numeric data, adding it to the dataframe as a new column called indexedLabels.
In the indexed value we alter our dataframe by removing the species column and adding the new indexedLabel column renaming 
it as label and showing the indexed value with a .show ().
The assembler value will use VectorAssembler to join the columns of the dataframe into a single vector called features.
In the data value we alter the indexed value and add the assembler vector as a column.
Finally, the categories are printed both in string and in numerical data.

Result:
```
+------------+-----------+------------+-----------+-----+
|sepal_length|sepal_width|petal_length|petal_width|label|
+------------+-----------+------------+-----------+-----+
|         5.1|        3.5|         1.4|        0.2|  2.0|
|         4.9|        3.0|         1.4|        0.2|  2.0|
|         4.7|        3.2|         1.3|        0.2|  2.0|
|         4.6|        3.1|         1.5|        0.2|  2.0|
|         5.0|        3.6|         1.4|        0.2|  2.0|
|         5.4|        3.9|         1.7|        0.4|  2.0|
|         4.6|        3.4|         1.4|        0.3|  2.0|
|         5.0|        3.4|         1.5|        0.2|  2.0|
|         4.4|        2.9|         1.4|        0.2|  2.0|
|         4.9|        3.1|         1.5|        0.1|  2.0|
|         5.4|        3.7|         1.5|        0.2|  2.0|
|         4.8|        3.4|         1.6|        0.2|  2.0|
|         4.8|        3.0|         1.4|        0.1|  2.0|
|         4.3|        3.0|         1.1|        0.1|  2.0|
|         5.8|        4.0|         1.2|        0.2|  2.0|
|         5.7|        4.4|         1.5|        0.4|  2.0|
|         5.4|        3.9|         1.3|        0.4|  2.0|
|         5.1|        3.5|         1.4|        0.3|  2.0|
|         5.7|        3.8|         1.7|        0.3|  2.0|
|         5.1|        3.8|         1.5|        0.3|  2.0|
+------------+-----------+------------+-----------+-----+



+------------+-----------+------------+-----------+-----+-----------------+
|sepal_length|sepal_width|petal_length|petal_width|label|         features|
+------------+-----------+------------+-----------+-----+-----------------+
|         5.1|        3.5|         1.4|        0.2|  2.0|[5.1,3.5,1.4,0.2]|
|         4.9|        3.0|         1.4|        0.2|  2.0|[4.9,3.0,1.4,0.2]|
|         4.7|        3.2|         1.3|        0.2|  2.0|[4.7,3.2,1.3,0.2]|
|         4.6|        3.1|         1.5|        0.2|  2.0|[4.6,3.1,1.5,0.2]|
|         5.0|        3.6|         1.4|        0.2|  2.0|[5.0,3.6,1.4,0.2]|
|         5.4|        3.9|         1.7|        0.4|  2.0|[5.4,3.9,1.7,0.4]|
|         4.6|        3.4|         1.4|        0.3|  2.0|[4.6,3.4,1.4,0.3]|
|         5.0|        3.4|         1.5|        0.2|  2.0|[5.0,3.4,1.5,0.2]|
|         4.4|        2.9|         1.4|        0.2|  2.0|[4.4,2.9,1.4,0.2]|
|         4.9|        3.1|         1.5|        0.1|  2.0|[4.9,3.1,1.5,0.1]|
|         5.4|        3.7|         1.5|        0.2|  2.0|[5.4,3.7,1.5,0.2]|
|         4.8|        3.4|         1.6|        0.2|  2.0|[4.8,3.4,1.6,0.2]|
|         4.8|        3.0|         1.4|        0.1|  2.0|[4.8,3.0,1.4,0.1]|
|         4.3|        3.0|         1.1|        0.1|  2.0|[4.3,3.0,1.1,0.1]|
|         5.8|        4.0|         1.2|        0.2|  2.0|[5.8,4.0,1.2,0.2]|
|         5.7|        4.4|         1.5|        0.4|  2.0|[5.7,4.4,1.5,0.4]|
|         5.4|        3.9|         1.3|        0.4|  2.0|[5.4,3.9,1.3,0.4]|
|         5.1|        3.5|         1.4|        0.3|  2.0|[5.1,3.5,1.4,0.3]|
|         5.7|        3.8|         1.7|        0.3|  2.0|[5.7,3.8,1.7,0.3]|
|         5.1|        3.8|         1.5|        0.3|  2.0|[5.1,3.8,1.5,0.3]|
+------------+-----------+------------+-----------+-----+-----------------+

Labels: [versicolor, virginica, setosa]
indexedLabels: [1.0, 0.0, 2.0]
```

**7. Build the classification model and explain its architecture.**

Solution:

```scala
val splits = data.randomSplit(Array(0.7, 0.3), seed = 1234L)
val train = splits(0)
val test = splits(1)
val layers = Array[Int](4, 5, 4, 3)
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(1
28).setSeed(1234L).setMaxIter(100)
val model = trainer.fit(train)
val result = model.transform(test)
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
```

With the splits value it allows us to store our training data with 70% and test 30%.
Once we have the value, we create two new values ​​train (0) with 70% and test (1) with 30%.
In the layers value we are going to create the layers, one input with a size of 4 characters, two hidden or intermediate 
with a size of 5 and 4, and the final layer with a size of 3.
We start with the training data and call the MultilayerPerceptronClassifier function, giving it our layers value as a parameter.
In the model value we give me what our trainer value contains and we give it a fit.
In our result value we give it what we have in our model value and we give it a transform sending it our test data
After creating a predictionandLabel value we make a select to our result value.
Finally we generate a value called evaluator doing in a new MulticlassificationEvaluator to be able to generate the accuracy and 
send us the prediction.

**8. Print the results of the model.**

Solution:

```scala
println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")
```
With println we print our result with a Test set Accuracy comment showing the percentage of our prediction.

Result:
```
Test set accuracy = 0.95
```

## Conclusion.

In conclusion, the practice was simple, the only problem was cleaning the data and changing the categorical data to numeric
But thanks to what we saw in class and the activities carried out by our classmates, we were able to carry out the practice successfully, 
although with some doubts. The model could be made without any difficulty thanks to the Spark documentation in Scala.
This practice aims to see the precision that the model had, and all the times that the code of corrio gave the result of 95% precision, 
which means that it is reliable but there may be other more precise classification methods, it is up to us to experiment and find them.

## Sources.

* https://spark.apache.org/docs/2.4.7/ml-features.html

* https://spark.apache.org/docs/latest/ml-features

Video: https://www.youtube.com/watch?v=GShAOwnUB_Q&feature=youtu.be




