# Practice 2.
1. Create a list called "list" with the elements "rojo", "blanco", "negro".
```scala
val lista = List("rojo", "blanco", "Negro")
```

2. Add 5 more items to "list" "verde", "amarillo", "azul", "naranja", "perla".
```scala
val lista2 = lista ::: List("verde", "amarillo", "azul", "naranja", "perla")
```

3. Bring the items from "list" "verde", "amarillo", "azul".
```scala
val lista3 = lista2.slice(3,6)
```

4. Create an array of numbers in the range 1-1000 in steps of 5 by 5.
```scala
Array.range(1, 1000,5)
```

5. What are the unique elements of the list List (1,3,3,4,6,7,3,7) use conversion to sets.
```scala
val elementos = List(1,3,3,4,6,7,3,7)
elementos.toSet
val nuevoselementos = elementos.toSet
```

6. Create a mutable map named names that contains the following "Jose", 20, "Luis", 24, "Ana", 23, "Susana", "27".
```scala
val mimapa = collection.mutable.Map(("Jose", 20), ("Luis", 24), ("Ana", 23), ("Susana","27"))
```
a. Print all keys on the map.
```scala
mimapa.keys
```
b. Add the following value to the map ("Miguel", 23)
```scala
mimapa += ("Miguel" -> 23)
```

