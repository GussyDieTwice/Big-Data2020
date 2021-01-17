# Test Project

## Introduction.
Machine learning is a form of AI that enables a system to learn from data rather than learn through explicit programming.
As the algorithm ingests training data, it is possible to produce more accurate models based on data. 
A machine learning model is the information output that is generated when you train your machine learning algorithm with data. 
After training, providing a model with an input will give it an output [1].

abstract- In this project and research we will make a comparison with different Machine Learning 
techniques such as SVM, Decision Trees, Logistic Regression and Perceptron, making use of
the same csv called calle bank with approximately 40,000 records.

## ALGORITHMS
## Multilayer Perceptron

Multilayer perceptron classifier. The multilayer perceptron classifier (MLPC) is a classifier based on the
feedforward artificial neural network. MLPC consists of multiple layers of nodes. Each layer is completely connected to the next layer in the network. 
The nodes in the input layer represent the input data. All other nodes map inputs to outputs performing a linear combination of the inputs with the node 
weights and bias and applying a trigger function.

As described in the Image, MLPC consists of multiple layers of nodes, including the input layer, hidden layers (also called middle layers), and output layers. 
Each layer is completely connected to the next layer in the network. Where the input layer, the intermediate layers and the output layer can be defined as follows:

* The input layer is the neurons that accept the input values, the nodes are those that represent the input data.
* The hidden layers are found between the input and output layers and their function depends on those assigned by the input layer to the output layer of a node.
* The output layer is the final layer of a neural network that returns the result to the user's environment.

![capaz](https://austingwalters.com/wp-content/uploads/2018/12/mlp.png)

A multilayer perceptron has three distinctive characteristics:

1. The model of each neuron in the network includes a non-linearity in the final output. The important point to emphasize here is that nonlinearity 
is smooth (that is, differentiable). A commonly used way for nonlinearity to satisfy this requirement is to use a sigmoidal nonlinearity defined by the following function:

yj = 1/1+exp(-vj)

where yj is the output of neuron, and vj is the internal activity level of neuron j. The presence of non-linearities is important because otherwise the 
input-output relationship of the lattice could be reduced to that of a single-layer perceptron. Furthermore, the use of the function described in has biological motivations.

2. The network contains one or more hidden layers that are not part of the input or output of the network. These hidden layers activate
the network to learn complex tasks by extracting more meaningful features from the input patterns.

3. The network exhibits a high degree of connectivity, determined by the synapses of the network. A
The change in network connectivity requires a change in the synaptic connections or their weights [2].

## Decision tree

A decision tree is a schematic representation that facilitates decision-making by visually representing the different possibilities that exist in a scenario, 
as well as the possible consequences that each scenario could bring. Its name is given due to the similarity that the scheme has with the branches of a tree;
In addition, a decision tree can be used in any aspect of daily life, from difficult decisions in the family, to complex applications in business and artificial intelligence.

![arbol](https://upload.wikimedia.org/wikipedia/commons/0/08/Decision_stream.gif)

Decision trees are especially useful when:
* The alternatives or courses of action are well defined (for example: accept or reject a proposal, increase production capacity or not, build or not a new warehouse, etc.)
* The uncertainties can be quantified (for example: probability of success of an advertising campaign, probable effect on sales, probability of passing stages, etc.)
* The objectives are clear (for example: increase sales, maximize profits, minimize costs, etc.) [3].

## Support Vector Machines

Support Vector Machine (SVM) is a supervised machine learning algorithm that can be used for classification or regression challenges. However, it is mainly used in classification problems. In the SVM algorithm, we plot each data item as a point in n-dimensional space (where n is the number of features it has)
and the value of each feature is the value of a particular coordinate. Then, we perform the classification by finding the hyperplane that differentiates 
the two classes very well (see snapshot below) [4].

![svm](https://www.analyticsvidhya.com/wp-content/uploads/2015/10/SVM_1.png)

## Logistic regression

Logistic regression is the appropriate regression analysis to perform when the dependent variable is dichotomous (binary). Like all regression analyzes, 
logistic regression is a predictive analysis. Logistic regression is used to describe data and explain the relationship between a
dependent binary variable and one or more nominal, ordinal, interval, or ratio independent variables [5].

![lg](https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logistic-curve.svg/320px-Logistic-curve.svg.png)

## TECHNOLOGIES

* Scala

Scala is a modern multi-paradigm programming language designed to express common programming patterns in a concise, elegant, and securely typed way. 
Easily integrate features of functional and object-oriented languages.

* Spark

Apache Spark is a distributed data processing programming framework designed to be fast and general-purpose. As its name suggests, 
it has been developed within the framework of the Apache project, which guarantees its Open Source license.
In addition, we can count on its maintenance and evolution to be carried out by highly prestigious work groups, and there will be great flexibility and interconnection with other Apache modules such as Hadoop, Hive or Kafka.
Part of the essence of Spark is its general character. It consists of different APIs and modules that allow it to be used by a wide variety of professionals at all stages of the data life cycle.
These stages can include from support for interactive data analysis with SQL to the creation of complex pipelines of machine learning and streaming processing, all using the same processing engine and the same APIs.

## RESULTS
A program run was carried out with the algorithms, the accuracy of their performance cannot always be tested with just one run, so we set the objective of performing 5 of them and comparing them.
For this comparison we take into account the time it takes to execute the entire program, the accuracy of the algorithm and the probability of error it has.

The specifications of the equipment where the executions were carried out are:
* CPU: Intel Core i7 7700 3.60 GHz 4 cores 8 threads.
* RAM: Kingston HyperX 16 GB 2133 Mhz DDR4.
* HDD: WD Blue HDD 1TB 3.5 "7200RPM.
* SSD: Kingstone SSD 480GB 2.5 ".
* OS: Windows 10 Home.

Although these practices were carried out in a virtual machine, which its specifications were:
* OS: Ubuntu 20.04.1 LTS.
* RAM: 8GB 2133 Mhz.
* HDD: 7200 RPM.
* CPU: 3.60 Ghz 4 cores 8 threads.

It should be declared that the computer is in the High Performance state, therefore there is no limitation when using the hardware.

* SVM.

|   	| Time  	| Accuracy 	| Error 	|
|---	|-------	|----------	|-------	|
| 1 	| 24.96 	| 0.88     	| 0.11  	|
| 2 	| 13.76 	| 0.88     	| 0.11  	|
| 3 	| 13.53 	| 0.88     	| 0.11  	|
| 4 	| 13.16 	| 0.88     	| 0.11  	|
| 5 	| 12.96 	| 0.88     	| 0.11  	|

* Decision Tree.

|   	| Time  	| Accuracy 	| Error 	|
|---	|-------	|----------	|-------	|
| 1 	| 19.74 	| 0.88     	| 0.10  	|
| 2 	| 11.24 	| 0.88     	| 0.10  	|
| 3 	| 10.77 	| 0.88     	| 0.10  	|
| 4 	| 10.61 	| 0.89     	| 0.10  	|
| 5 	| 10.95 	| 0.89     	| 0.10  	|

* Logistic Regression.

|   	| Time  	| Accuracy 	| Error 	|
|---	|-------	|----------	|-------	|
| 1 	| 17.04 	| 0.88     	| 0.11  	|
| 2 	| 8.31  	| 0.88     	| 0.11  	|
| 3 	| 7.93  	| 0.88     	| 0.11  	|
| 4 	| 7.32  	| 0.88     	| 0.11  	|
| 5 	| 7.26  	| 0.88     	| 0.11  	|

* MLPC.

|   	| Time  	| Accuracy 	| Error 	|
|---	|-------	|----------	|-------	|
| 1 	| 20.07 	| 0.88     	| 0.11  	|
| 2 	| 19.32 	| 0.88     	| 0.11  	|
| 3 	| 18.64 	| 0.88     	| 0.11  	|
| 4 	| 19.29 	| 0.88     	| 0.11  	|
| 5 	| 19.72 	| 0.88     	| 0.11  	|

* Average.

| Algorithm 	| Time      	| Accuracy 	| Error 	|
|-----------	|-----------	|----------	|-------	|
| SVM       	| 15.67 sec 	| 88%      	| 11%   	|
| Dec.Th    	| 12.66 sec 	| 88%      	| 11%   	|
| Log.Reg   	|  9.57 sec 	| 88%      	| 11%   	|
| MLPC      	| 19,40 sec 	| 88%      	| 11%   	|

## CONCLUSION
Taking into account the previous results, it could be seen that the probability of accuracy and error is similar in all the algorithms, the running time of these is what varied, being the first time to execute the one that takes the longest, and decreasing each time.
Regarding average time we can see that MLPC is the one that takes the longest to execute and Logistic Regression the least, this does not mean that one is better than another only because of its speed, but it is not a reason to choose one of these techniques, either. although we can take it into account. The correct thing to do would be to take into account what you want to do when doing a study, with that starting and choosing the Machine Learning technique that best suits the objective of the research.
Leaving all this out, we can see that Logistic Regression is the fastest machine learning method to execute, at least in this research.

Project Video: https://youtu.be/bAc7EJp_Q7U

Original Document: https://docs.google.com/document/d/1RksZHNVrs09aW7gCDetoxNk0NIokzpGgnSm5X_doV2E/edit?usp=sharing

## REFERENCE SOURCES

[1] IBM. (June 22, 2020). What is Machine Learning ?. January 14, 2021, from IBM Corporation Website: https://www.ibm.com/mx-es/analytics/machine-learning

[2] Francisco J. Palacios Burgos .. (February 21, 2017). The multilayer perceptron (MLP). January 15, 2021, from ibiblio.org Website: https://www.ibiblio.org/pub/linux/docs/LuCaS/Presentaciones/200304curso-glisa/redes_neuronales/curso-glisa-redes_neuronales-html/x105.html

[3] Jorge Montiel Romero. (February 13, 2020). What are and how to make decision trees? January 14, 2021, from Profesionalistar.org Website: https://profesionistas.org.mx/que-son-y-como-hacer-arboles-de-decisiones/

[4] Sunil Ray. (September 13, 2017). Understand the Support Vector Machine (SVM) algorithm. January 15, 2021, from AnalyticsVidhya Website: https://www.analyticsvidhya.com/blog/2017/09/understaing-support-vector-machine-example-code/

[5] StatisticSolution. (-). What is Logistic Regression ?. January 15, 2021, from statisticssolution Website: https://www.statisticssolutions.com/what-is-logistic-regression/

[6] Scala. (-). Introduction Scala. January 15, 2021, from Scala Website: https://docs.scala-lang.org/es/tour/tour-of-scala.html#:~:text=Scala%20es%20un%20lenguaje%20de, oriented% 20 to% 20 functional objects% 20 and% 20.

[7] ESIC BUSINESS & MARKETING SCHOOL. (October 20, 2020). Apache Spark. January 15, 2021, from ESIC Website: https://www.esic.edu/rethink/tecnologia/apache-spark-introduccion-que-es-y-como-funciona
