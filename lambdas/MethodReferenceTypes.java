package java8.Expert.and.Java70.cursoJavaAvancado.lambdas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MethodReferenceTypes {
    public static void main(String[] args) {    
    	//boundMethodReferences();        // bound
    	//unboundMethodReferences();      // unbound
        //constructorMethodReferences();  // constructor
        staticMethodReferences();       // static
    }
    
    //Uma referência a método é considerada "bound" quando está associada a uma instância específica de uma classe.
    public static void boundMethodReferences(){
    	//Isso transforma em Bound
        String name = "Mr. Joe Bloggs";
        
        
        // Supplier<T>   Um Supplier é uma interface funcional que representa um fornecedor de resultados.   
        //      T get()  Possui um único método T get() que não recebe argumentos e retorna um resultado do tipo T.
        Supplier<String> lowerL   = () -> name.toLowerCase();   // lambda
        Supplier<String> lowerMR  = name::toLowerCase;          // method reference

        // No need to say which instance to call it on - the supplier is bound to name            
        System.out.println(lowerL.get()); // mr. joe bloggs
        System.out.println(lowerMR.get());// mr. joe bloggs
        
        // Predicate<T> Um Predicate é uma interface funcional que representa um predicado (função que retorna um valor booleano) de um argumento.
        //    boolean test(T t) Possui um único método boolean test(T t) que retorna true ou false com base no argumento de entrada.
       
        // Mesmo que startsWith esteja sobrecarregado, boolean startWith(String) e
        // boolean startsWith(String, int), porque estamos criando um Predicate que
        // possui um método funcional de test(T t), o startWith(String) é usado.
        // É aqui que o "contexto" é importante.
        Predicate<String> titleL  = (title) -> name.startsWith(title);
        Predicate<String> titleMR = name::startsWith;

        System.out.println(titleL.test("Mr.")); // true
        System.out.println(titleMR.test("Mr."));// true
    }
    
    //Uma referência a método é considerada "unbound" quando não está associada a uma instância específica, e a instância à qual o método se refere é fornecida 
    public static void unboundMethodReferences(){
        //   Function<T, R> Uma Function é uma interface funcional que representa uma função que aceita um argumento do tipo T e retorna um resultado do tipo R.
        //      R apply(T) Possui um único método R apply(T t) que realiza a transformação.
        //          String apply(String) Como usaremos abaixo
    	
        Function<String, String> upperL  = s -> s.toUpperCase();
        Function<String, String> upperMR = String::toUpperCase; 
        
        System.out.println(upperL.apply("sean"));   // SEAN
        System.out.println(upperMR.apply("sean"));  // SEAN

        //   BiFunction<T, U, R> Uma BiFunction é uma interface funcional que representa uma função que aceita dois argumentos (T e U) e retorna um resultado (R).
        //      R apply(T t, U u) Possui um único método R apply(T t, U u) que realiza a operação.
        //          String apply(String, String)
        BiFunction<String, String, String> concatL   = (s1, s2) -> s1.concat(s2);
        BiFunction<String, String, String> concatMR  = String::concat;
        System.out.println(concatL.apply("Sean ", "Kennedy"));// Sean Kennedy

        // 1st parameter is used for executing the instance method
        // "Sean ".concat("Kennedy")
        System.out.println(concatMR.apply("Sean ", "Kennedy"));// Sean Kennedy


    }
    
    //precisa de uma referencia (new)
    public static void constructorMethodReferences(){
        // Supplier<T>  Um Supplier é uma interface funcional que representa um fornecedor de resultados.    
        //      T get() Possui um único método T get() que não recebe argumentos e retorna um resultado do tipo T.
        Supplier<StringBuilder> sbL   = () -> new StringBuilder();  // lambda
        Supplier<StringBuilder> sbMR  = StringBuilder::new;         // method reference
        StringBuilder sb1 = sbL.get(); sb1.append("lambda version"); System.out.println(sb1);
        StringBuilder sb2 = sbMR.get(); sb2.append("method reference version"); System.out.println(sb2);
        
        //  Function<T, R> Uma Function é uma interface funcional que representa uma função que aceita um argumento do tipo T e retorna um resultado do tipo R.
        //      R apply(T) Possui um único método R apply(T t) que realiza a transformação.
        //          List<String> apply(Integer) Como usaremos (Retornara uma lista de string) e recebe um inteiro
        
        Function<Integer, List<String>> alL  = x -> new ArrayList(x);
        Function<Integer, List<String>> alMR = ArrayList::new; 
        List<String> ls1 = alL.apply(10);  // size 10
        ls1.add("21"); 
        
        System.out.println(ls1);//[21]
        List<String> ls2 = alMR.apply(5);  // size 5
        ls2.add("88"); 
        System.out.println(ls2);//[88]
    }
    
    //bound que não precisa instancia
    public static void staticMethodReferences(){
        
    	// Referências de métodos estáticos também são consideradas UNBOUND. Um exemplo de método estático  é Collections.sort(Lista)
        //  Consumer<T> 				 Um Consumer é uma interface funcional que representa uma operação que aceita um argumento do tipo T e não retorna resultado.
        //		void accept(T t)		 Possui um único método void accept(T t) que realiza a operação.
        //  void accept(List<Integer>)   (Receberemos o Tipo Lista de Inteiros)
    	
        // NB: O consumidor usa um parâmetro => sort(List)  em oposição a sort(List, Comparator)
        Consumer<List<Integer>> sortL  = list -> Collections.sort(list);
        Consumer<List<Integer>> sortMR = Collections::sort;

        List<Integer> listOfNumbers = Arrays.asList(2,1,5,4,9);
        sortL.accept(listOfNumbers);// execution
        System.out.println(listOfNumbers);  // [1, 2, 4, 5, 9]

        listOfNumbers = Arrays.asList(8,12,4,3,7);
        sortMR.accept(listOfNumbers);// execution
        System.out.println(listOfNumbers);  // [3, 4, 7, 8, 12]
    }    
}

