import java.util.*;
public class Main {
    public static void main(String[] args){
        Integer i = Integer.valueOf(10);
        System.out.println(i.hashCode());

        Integer j = Integer.valueOf(10);
        System.out.println(j.hashCode());
        
        System.out.println(i == j);
        System.out.println(i.hashCode() == j.hashCode());
        System.out.println(i.equals(j));
    }

}
