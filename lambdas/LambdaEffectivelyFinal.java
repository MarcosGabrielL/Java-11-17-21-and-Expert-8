package java8.Expert.and.Java70.cursoJavaAvancado.lambdas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * A classe LambdaEffectivelyFinal possui uma variável de instância chamada name e um método main que demonstra o uso de lambdas.
 * @author marcos_l
 *
 */
public class LambdaEffectivelyFinal {
    String name="";
    
    public static void main(String[] args) {
    	
    	//Criação da Lista e Variável Local: 
    	//Aqui, uma lista de strings é criada e um inteiro x é inicializado com o valor 12.
        ArrayList<String> al = new ArrayList<>();
        al.add ("John");

        int x=12; // final or effectively final

        //Definição da Expressão Lambda:
        //Lambdas tiram um print de variáveis locais
        //essas variáveis NÃO DEVEM mudar.
        /**
         * Uma expressão lambda é criada usando a interface funcional Predicate. 
         * A expressão lambda captura a variável local x e a variável de instância name.
         *  A mensagem x == 12 é impressa.
         */
        Predicate<String> lambda = s -> {
            //x++;
            new LambdaEffectivelyFinal().name = "Kennedy"; // instância/variáveis de classe são aceitáveis
            System.out.println("x == "+x);
            return s.isEmpty() && x%2 == 0; 
        };
        
        //Chamada do Método filterData:
        /**
         * O método filterData é chamado com a lista e a expressão lambda 
         * como argumentos. O método remove elementos da lista com base na 
         * condição especificada pela lambda.
         */
        filterData(al, lambda);// lambda views 'x' as 12
        System.out.println(al);
        
        new LambdaEffectivelyFinal().name = "Sean"; // instance/class vars are ok
        
        
        // x++; 
        /**
         * A variável x é considerada "efetivamente final" na expressão lambda, 
         * mesmo que não seja explicitamente marcada como final. Isso significa 
         * que o valor de x não deve ser modificado após a captura pela expressão 
         * lambda para garantir consistência. Se você tentasse descomentar //x++;, 
         * isso resultaria em um erro de compilação, indicando que a variável x não é efetivamente final.
         */
       
        
        
        //O método filterData é chamado novamente com a mesma expressão lambda, que ainda vê a variável x como 12.
        filterData(al, lambda);// lambda views 'x' as 12
        // some code...
        
    }
    public static void filterData(List<String> list, Predicate<String> lambda){
       Iterator<String> i = list.iterator();
       while(i.hasNext()){
            if(lambda.test(i.next())){  // executing lambda here
                 i.remove();
            }
       }
    }
    
}
