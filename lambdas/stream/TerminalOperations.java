package java8.Expert.and.Java70.cursoJavaAvancado.lambdas.stream;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TerminalOperations {
    public static void main(String[] args) {
//        doMinAndMax();
       // doReduce3();
    	//doCollect1();
    	doReduce1();
    }
    public static void doCollect1(){
        
    	// StringBuilder collect(Supplier<StringBuilder> fornecedor,
        // Acumulador BiConsumer<StringBuilder,String>
        // combinador BiConsumer<StringBuilder,StringBuilder>)
        // Esta versão é usada quando você deseja controle total sobre
        // como a coleta deve funcionar. O acumulador adiciona um elemento
        // para a coleção, por exemplo. a próxima String do StringBuilder.
        // O combinador pega duas coleções e as mescla. É útil
        // em processamento paralelo.
        StringBuilder word = Stream.of("ad", "jud", "i", "cate",  "i")
                .collect(() -> new StringBuilder(),         // StringBuilder::new
                         (sb, str) -> sb.append(str),       // StringBuilder::append
                         (sb1, sb2) -> sb1.append(sb2));    // StringBuilder::append
        
        StringBuilder word1 = Stream.of("ad", "jud", "i", "cate",  "i")
                .collect(StringBuilder::new,         
                         StringBuilder::append,      
                         StringBuilder::append);  
        
        System.out.println(word);// adjudicate
    }
    public static void doReduce3(){
        
    	// <U> U reduz (U identidade,
        // Acumulador BiFunction,
        // combinador BinaryOperator)
        // Usamos esta versão quando estamos lidando com tipos diferentes,
        // nos permitindo criar reduções intermediárias e depois combinar
        // eles no final. Isto é útil ao trabalhar com paralelo
        // streams - os streams podem ser decompostos e remontados por
        // threads separados. Por exemplo, se quiséssemos contar o comprimento
        //de quatro strings de 1000 caracteres, os 2 primeiros valores e o último
        // dois valores podem ser calculados independentemente. O intermediário
        // os resultados (2000) seriam então combinados em um valor final (4000).
        // Exemplo: queremos contar o número de caracteres em cada String
        Stream<String> stream = Stream.of("car", "bus", "train", "aeroplane");
        int length = stream.reduce( 0,  // identity
                                    (n, str) -> n + str.length(), // n is Integer
                                    (n1, n2) -> n1 + n2); // both are Integers
        System.out.println(length);// 20
    }
    public static void doReduce2(){

    	// Opcional<T> reduzir(BinaryOperator<T> acumulador)
        // Quando você omite a identidade, um Opcional é
        // retornado porque pode não haver nenhum dado (todos os
        // os elementos poderiam ter sido filtrados anteriormente). Há
        // 3 resultados possíveis:
        // a) fluxo vazio => vazio Opcional retornado
        // b) um elemento no stream => esse elemento é retornado
        // c) múltiplos elementos no stream => acumulador é aplicado
        BinaryOperator<Integer> op = (a,b) -> a+b;
        Stream<Integer> empty               = Stream.empty();
        Stream<Integer> oneElement          = Stream.of(6);
        Stream<Integer> multipleElements    = Stream.of(3, 4, 5);
        empty.reduce(op).ifPresent(System.out::println);            // 
        oneElement.reduce(op).ifPresent(System.out::println);       // 6
        multipleElements.reduce(op).ifPresent(System.out::println); // 12
     // Por que não apenas exigir a identidade e remover este método?
        // Às vezes é bom saber se o stream está vazio, ao contrário
        // para o caso onde existe um valor retornado do acumulador
        // isso corresponde à identidade (embora improvável).
        Integer val = Stream.of(1,1,1)
                //     .filter(n -> n > 5)      // val is 1 this way
                       .reduce(1, (a, b) -> a );// val is 1 this way too
        System.out.println(val);// 1
    }
    public static void doReduce1(){
        
    	// O método reduz() combina um fluxo em um único objeto.
        // É uma redução, o que significa que processa todos os elementos.
        // A maneira mais comum de fazer uma redução é começar com
        // um valor inicial e continue mesclando-o com o próximo valor.

        // T reduz(T identidade, BinaryOperator<T> acumulador)
        // Método funcional BinaryOperator<T>:
        //T aplicar(T, T);
        // A “identidade” é o valor inicial da redução e também
        // o que é retornado se o stream estiver vazio. Isto significa que há
        // sempre será um resultado e, portanto, Opcional não é o tipo de retorno
        // (nesta versão de reduzir()).
        // O "acumulador" combina o resultado atual com o
        // valor atual no fluxo.
        String name = Stream.of("s", "e", "a", "n")
//                        . filter(s -> s.length()>2)
//                       .reduce("nothing", (s, c) -> s + c);
                       .reduce("", (s, c) -> s + c);
        System.out.println(name);// sean

        Integer product = Stream.of(2,3,4)
                       .reduce(1, (a, b) -> a * b);
        System.out.println(product);// 24
        
    }
    public static void doForEach(){
        
        // void forEach(Consumer)
        // As there is no return value, forEach() is not a reduction.
        // As the return type is 'void', if you want something to
        // happen, it has to happen inside the Consumer (side-effect).
        Stream<String> names = Stream.of("Cathy", "Pauline", "Zoe");
        names.forEach(System.out::print);//CathyPaulineZoe
        
        // Notes: forEach is also a method in the Collection interface.
        //        Streams cannot be the source of a for-each loop 
        //        because streams do not implement the Iterable interface.
        Stream<Integer> s = Stream.of(1);
 //       for(Integer i : s){}// error: required array or Iterable
        
    }
    public static void doMatches(){
        
        // boolean anyMatch(Predicate)
        // boolean allMatch(Predicate)
        // boolean noneMatch(Predicate)
        List<String> names = Arrays.asList("Alan", "Brian", "Colin");
        Predicate<String> pred = name -> name.startsWith("A");
        System.out.println(names.stream().anyMatch(pred)); // true (one does)
        System.out.println(names.stream().allMatch(pred)); // false (two don't)
        System.out.println(names.stream().noneMatch(pred));// false (one does)
    }
    public static void doFindAnyFindFirst(){
        
        // Optional<T> findAny()
        // Optional<T> findFirst()
        // These are terminal operations but not reductions 
        // as they sometimes return without processing all 
        // the elements in the stream. Reductions reduce the 
        // entire stream into one value.
        Optional<String> any = Stream.of("John", "Paul")
                            .findAny();
        any.ifPresent(System.out::println);// John (usually)

        Optional<String> first = Stream.of("John", "Paul")
                            .findFirst();
        first.ifPresent(System.out::println);// John
  
    }
    
    public static void doCount(){
        
        long count = Stream.of("dog", "cat")
                        .count();
        System.out.println(count); // 2
        
    }
    
    public static void doMinAndMax(){ 
        
    	// Opcional<T> min(Comparador)
        // Opcional<T> max(Comparador)
        // Introdução opcional no Java 8 para substituir 'null'. Se o fluxo for
        // vazio então o Opcional ficará vazio (e não precisaremos
        //lidar com nulo).
        Optional<String> min = Stream.of("deer", "horse", "pig")
                                .min((s1, s2) -> s1.length()-s2.length());
        min.ifPresent(System.out::println);// pig
        
        Optional<Integer> max = Stream.of(4,6,2,12,9)
                                .max((i1, i2) -> i1-i2);
        max.ifPresent(System.out::println);// 12
        
    }
}

/*
    public static void doMinAndMax(){ 
        
        // Optional<T> min(Comparator)
        // Optional<T> max(Comparator)
        // Optional introduce in Java 8 to replace 'null'. If the stream is
        // empty then the Optional will be empty (and we won't have to
        // deal with null).
        Optional<String> min = Stream.of("cow", "horse", "pig")
                                .min((s1, s2) -> s1.length()-s2.length());
        min.ifPresent(System.out::println);// cow
        
        Optional<Integer> max = Stream.of(4,6,2,9,9)
                                .max((i1, i2) -> i1-i2);
        max.ifPresent(System.out::println);//9
        
    }

*/
