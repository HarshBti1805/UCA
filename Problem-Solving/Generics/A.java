// FALSE :: since a1 and a2 are diff objects and stored in diff places in memory in JAVA they show false. 
public class A {
    public int x;

    public A(int x){
        this.x = x;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        A other = (A) o;  // cast to your class
        return this.x == other.x;
    }

    // @Override 
    // public int hashCode(){ 
    //
    //
    // }
    //
    public static void main(String[] args) {
        A a1 = new A(5);
        A a2 = new A(5);

        System.out.println(a1.equals(a2)); // true because of custom implementation of the object class otherwise natively it is false 
        System.out.println(a1 == a2); // false : diff refernces even after implementing custom class since they we are comparing references 
        System.out.println(a1.hashCode() == a2.hashCode()); // natively not equal but after custom implementaiton.
                                                            //
        System.out.println(a1.hashCode());
    }

}

// HASHCODE :: INTEGER REPRESNATION OF AN OBJECT 

