# Practice 1.

1. Develop a scala algorithm that calculates the radius of a circle.
```scala
def Diame=2000
def Radio = Diame/2
Radioc
```
2. Develop a scala algorithm that tells me if a number is prime.
```scala
def numeroprimo(num:Int): Boolean = {
    for(n <- Range(2, num)){
        if(num%n == 0){
            return false
        }
    }
    return true
}

println(numeroprimo())
```

3. Given the variable bird = "tweet", use string interpolation to print "Estoy escribiendo un tweet".
```scala
val bird = "Tweet, " + "Estoy escribiendo un Tweet!"
```

4. Given the variable message = "Hola Luke yo soy tu padre!" use slilce to extract the sequence "Luke".
```scala
var mensaje = "Hola Luke yo soy tu padre!" 
mensaje slice (5,9)
```

5. What is the difference between value and a variable in scala?
The object assigned to Val cannot be replaced and the one assigned to Var can be replaced

6. Given the tuple (2,4,5,1,2,3,3.1416,23) returns the number 3.1416
```scala
val my_tup = (2,4,5,1,2,3,3.1416,23)
my_tup._7
```
