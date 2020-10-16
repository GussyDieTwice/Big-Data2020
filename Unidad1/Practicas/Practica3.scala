//1
def listEvens(list:List[Int]): String ={
    for(n <- list){
        if(n%2==0){
            println(s"$n is even")
        }else{
            println(s"$n is odd")
        }
    }
    return "Done"
}
val l = List(1,2,3,4,5,6,7,8)
val l2 = List(4,3,22,55,7,8)
listEvens(l)
listEvens(l2)
/***
1.Se declara una Lista llamada listEvens conformada por Enteros y retornara un String.
2.En un ciclo for mientras el valor este menor a la lista 
3.Si el residuo de N es igual  a 0 se imprimira que el numero de la lista es par
4.Si el residuo de N es diferente a 0 se imprimira que el numero de la lista es imprimira
5.Se retorna la respuesta

Pseudocodigo
listEvens(list)
    Por cada n desde 0 hasta el largo de list.
        si (n%2 es igual a 0)
            imprime (n,"es par")
        Si no 
            imprime (n,"es Impar")
        Fin Condicion
    Fin ciclo
regresa respuesta     
***/        
            
//2
def afortunado(list:List[Int]): Int={
    var res=0
    for(n <- list){
        if(n==7){
            res = res + 14
        }else{
            res = res + n
        }
    }
    return res
}
val af= List(1,7,7)
println(afortunado(af))
/***
1.Se declara una Lista llamada afortunado conformada por Enteros al igual que su retorno
2.se genera una variable llamada res asignandole una valor inicial de 0
3.En un cilo for mientras el valor de n sea menor que la lista
4.Si n es igual a 7 esta imprimira el valor que de la operacion de res+14
4.Si no imprimira el valor de res+n
5.Retorna el res

Pseudocodigo
afortunado(list)
        Por cada n desde 0 hasta el largo de list.
            si (n=7)
                imprime el resultado de res+14
            Si no 
                imprime el resultad de res+n
            Fin Condicion
        Fin ciclo
    regresa res
***/

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


