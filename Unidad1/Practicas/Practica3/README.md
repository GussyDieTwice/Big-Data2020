# Practice 3.

Perform algorithm and pseudocode of each code presented

1. First code
```scala
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
```
```
Algorithm: 
1. Declare a List called listEvens made up of Integers and return a String.
2.In a for loop as long as the value is less than the list
3.If the remainder of N is equal to 0, it will print that the number in the list is even
4.If the remainder of N is different from 0 it will print that the number in the list is it will print
5.The answer is returned
```
```
Pseudocode: 
listEvens (list)
    For every n from 0 to the length of list.
        yes (n% 2 is equal to 0)
            print (n, "is even")
        If not
            print (n, "is Odd")
        End Condition
    End of cycle
return answer
```
2. Second code
```scala
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
```
```
Algorithm:
1.A list named lucky is declared made up of Integers as well as its return
2. a variable called res is generated, assigning it an initial value of 0
3.In a for loop as long as the value of n is less than the list
4.If n is equal to 7 this will print the value of the operation of res + 14
4.If not it will print the value of res + n
5.Return the beef
```
```
Pseudocode:
lucky (list)
        For every n from 0 to the length of list.
            yes (n = 7)
                print the result of res + 14
            If not
                print the result of res + n
            End Condition
        End of cycle
    come back res
```
3. Third code
```scala
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
```
```
Algorithm:
1. A function called balance is declared that will have as a parameter a List made up of Integers and will return a Boolean.
2. The first and second variables are declared with value 0.
3. The sum of the entire List is assigned to the second variable.
4. In a for loop with a range from 0 to the length of the list, a summation is declared in the primary variable of the values ​​in
    the list and the secondary variable proceed to be subtracted.
5. At the last of this, if the first and second variables turn out to be equal, the function will return True.
6. If they are not equal, the function will return a False.
```
```
Pseudocode:
Balance (list) function.
    first <- 0, second <- 0.
    second <- Sum of list.
    For every i from 0 to the length of list.
        first <- first + list (i).
        second <- second - list (i).
        If first = second then
            true
        End yes.
    End for.
    False
 End.
```
4. Fourth code
```scala
def palindromo(palabra:String):Boolean ={
    return (palabra == palabra.reverse)
}

val palabra = "OSO"
val palabra2 = "ANNA"
val palabra3 = "JUAN"

println(palindromo(palabra))
println(palindromo(palabra2))
println(palindromo(palabra3))
```
```
Algorithm:
1. Declare a function called palindrome that will have a String word as a parameter and return a Bolean.
2. It will return the result of comparing the word value with the same but in reverse (to check if it is palindrome or not)
3. When sending to print the result of the function with the word value as a parameter, it will return a Boolean,
    True if this is palindrome (that is, if the word value is equal to itself in reverse) or a False if it is not palindrome.
```
```
Pseudocode:
 Palindrome function (word)
    Returns if Palanbra equals Word (reverse)
 End
```
