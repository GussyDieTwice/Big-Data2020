//Algoritmo 1 Versión recursiva descendente
def fib1 (n:Int): Int = {
    if (n<2)
        {return n}
    else
        {return fib1(n-1)+fib1(n-2)}
}

//Algoritmo 2 Versión con fórmula explícita
n=2
for(n <- Array.range(0,n+1))
{
var fibo =(Math.pow(((1+Math.sqrt(5))/2),n)-Math.pow(((1-Math.sqrt(5))/2),n))/Math.sqrt(5)
return fibo
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

//Algoritmo 4 Versión iterativa 2 variables 
def fibo(n:Int): Int = {
    var a = 0
    var b = 1
    for (j<-1 to n)
    {
        b = b+a
        a = b-a
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

//Algotimo 6 Versión Divide y Vencerás
import scala.math.pow
def fibo(n :Double):Double={
    if(n<=0){
        return 0
    }

    var i = n-1
    var aux1 =0.00
    var aux2=1.00

    var a=(aux2,aux1)
    var b=(aux1,aux2)

    while(i>0){
        if((i%2)!=0){
            aux1=(b._2*a._2)+(b._1*a._1)
            aux2=(b._2*(a._2+a._1)+b._1*a._2)
            a=(aux1,aux2)
        }
        aux1=pow(b._1,2)+pow(b._2,2)
        aux2=(b._2*(2*b._1+b._2))
        b=(aux1,aux2)
        i=i/2
    }
    return (a._1+a._2)
}
