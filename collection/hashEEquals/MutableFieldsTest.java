package java8.Expert.and.Java70.cursoJavaAvancado.collection.hashEEquals;

import java.util.HashMap;
import java.util.Map;

class Dog{
    private String name;

    Dog(String name){this.name = name;}
    public void setName(String name) {this.name = name;}

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Dog){
            Dog otherDog = (Dog)obj;
            return this.name.equals(otherDog.name);
        }else{
            return false;
        }
    }
    @Override
    public int hashCode() {
        int hash = 5;
        // simple algorithm
        hash = 83 * hash + this.name.length();
        return hash;
    }
}
public class MutableFieldsTest {
    public static void main(String[] args) {
        // Dog has a hashCode() method
        Dog spot                = new Dog("Spot");
        Map<Dog, String> dogs   = new HashMap<>();
        
        dogs.put(spot, "Great Dane");
        System.out.println("spotInMap "+spot.hashCode()+
                " in map? : "+dogs.containsKey(spot)); // 419, true
        
        /* API: "Note: great care must be exercised if mutable objects are used as map keys. 
        The behavior of a map is not specified if the value of an object is changed in a manner 
        that affects equals comparisons while the object is a key in the map."*/

        // change the object state (the dogs name); note that the dogs name IS USED in the
        // calculation of the hash value (and is used in equals() also).
        spot.setName("Rex"); 
        
        // assuming we have overridden hashcode(), it is false as the object state has changed 
        // i.e. the hash code is re-calculated and is going to be different because the 'name' is 
        // now different - this is dangerous!
        System.out.println("spotInMap "+spot.hashCode()+
                " in map? : "+dogs.containsKey(spot)); // 418, false        
    }
    
    
   
    
    
    
    
    
    
    
}

