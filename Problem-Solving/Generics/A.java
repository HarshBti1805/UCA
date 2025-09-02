// FALSE :: since a1 and a2 are diff objects and stored in diff places in memory in JAVA they show false. 
public class A {
    public int x;

    public A(int x){
        this.x = x;
    }
    public static void main(String[] args) {
        A a1 = new A(5);
        A a2 = new A(5);

        System.out.println(a1.equals(a2)); // false 
        System.out.println(a1 == a2); // false : diff refernces 
    }

}

