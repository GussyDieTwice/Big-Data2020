//Algoritmo 1 Versión recursiva descendente
def fib1 (n:Int): Int = {
    if (n<2)
        {return n}
    else
        {return fib1(n-1)+fib1(n-2)}
}

//Algoritmo 3 Versión iterativa
def fib3 (n:Int): Int = {
    var a = 0;
    var b = 1;
    var c = 0;

    for(k <- 1 to n)
    {
        c = a + b;
        a = b;
        b = c;
    }

    return a
}

//Algoritmo 5 Versión iterativa vector
def fib5 (n:Int): Int = {
    if (n<2)
        {return n}
    else 
        {
            var vector = new Array[Int](n+1);
            vector(0) = 0;
            vector(1) = 1;

            for(k <- Range(2,n+1))
            {
                vector(k) = vector(k-1) + vector(k-2);
            }

            return vector(n);
        }

}