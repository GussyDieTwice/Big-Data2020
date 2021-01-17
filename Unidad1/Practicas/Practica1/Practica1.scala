//1. Desarrollar un algoritmo en scala que calcule el radio de un circulo

def Diame=2000
def Radio = Diame/2
Radioc

//2. Desarrollar un algoritmo en scala que me diga si un numero es primo
def numeroprimo(num:Int): Boolean = {
    for(n <- Range(2, num)){
        if(num%n == 0){
            return false
        }
    }
    return true
}

println(numeroprimo())

//3. Dada la variable bird = "tweet", utiliza interpolacion de string para imprimir "Estoy ecribiendo un tweet"
val bird = "Tweet, " + "Estoy escribiendo un Tweet!"

//4. Dada la variable mensaje = "Hola Luke yo soy tu padre!" utiliza slilce para extraer la
//   secuencia "Luke"
var mensaje = "Hola Luke yo soy tu padre!" 
mensaje slice (5,9)

//5. Cual es la diferencia entre value y una variable en scala?
//El objeto asignado a Val no puede ser remplazado y el asignado a Var si puede reemplazarse

//6. Dada la tupla (2,4,5,1,2,3,3.1416,23) regresa el numero 3.1416 
val my_tup = (2,4,5,1,2,3,3.1416,23)
my_tup._7

