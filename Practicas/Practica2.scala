//Practice 2
// 1. Crea una lista llamada "lista" con los elementos "rojo", "blanco", "negro"
val lista = List("rojo", "blanco", "Negro")
// 2. Añadir 5 elementos mas a "lista" "verde" ,"amarillo", "azul", "naranja", "perla"
val lista2 = lista ::: List("verde", "amarillo", "azul")
// 3. Traer los elementos de "lista" "verde", "amarillo", "azul"
val lista3 = lista2.slice(3,6)