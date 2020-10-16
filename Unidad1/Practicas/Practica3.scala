//3
def balance(list:List[Int]): Boolean={
    var primera = 0
    var segunda = 0

    segunda = list.sum

    for(i <- Range(0,list.length)){
        primera = primera + list(i)
        segunda = segunda - list(i)

        if(primera == segunda){
            return true
        }
    }
    return false 
}

val bl = List(3,2,1)
val bl2 = List(2,3,3,2)
val bl3 = List(10,30,90)

balance(bl)
balance(bl2)
balance(bl3)

/***
1. Se declara una funcion llamada balance que tendra como parametro una Lista conformada por Enteros y retornara un Booleano.
2. Se declaran las variables primera y segunda con valor 0.
3. Se asigna la suma de toda la Lista a la variable segunda.
4. En un ciclo for con rango de 0 hasta el largo de la lista se declara una sumatoria en la variable primaria de los valores en 
    la lista y en la variable secundaria proceden a restarse.
5. Al ultimo de esto si la variable primera y segunda resultan ser iguales la funcion retornara un True.
6. Sino son iguales la funcion retornara un False.

Pseudocodigo.
1. Funcion balance (list).
    primero <- 0, segunda <- 0.
    segunda <- Sumatoria de list.

    Por cada i desde 0 hasta el largo de list.
        primera <- primera + list(i).
        segunda <- segunda - list(i).

        Si primera = segunda entonces
            verdadero
        Fin si.
    Fin for.
    Falso
2. Fin.

***/

//4
def palindromo(palabra:String):Boolean ={
    return (palabra == palabra.reverse)
}

val palabra = "OSO"
val palabra2 = "ANNA"
val palabra3 = "JUAN"

println(palindromo(palabra))
println(palindromo(palabra2))
println(palindromo(palabra3))

/*** 
1. Se declara una funcion llamada palindromo que tendra como parametro un String palabra y devolvera un Bolean.
2. Devolvera el resultado de comparar el valor palabra con el mismo pero en reverso (para comprobar si es palindromo o no)
3. Al mandar a imprimir el resultado de la funcion con el valor palabra como parametro este nos devolvera un Booleado,
    True si este es palindromo (osea si es igual el valor palabra a ella misma en reverso) o un False si esta no es palindromo.

Pseudocodigo.
1. Funcion Palindromo (palabra)
    Regresa si Palanbra es igual a Palabra (alreves)
2. Fin

***/ 


