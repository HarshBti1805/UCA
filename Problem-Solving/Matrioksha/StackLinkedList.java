class Node {
    int data;
    Node next;
    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}
class Stack {
    private Node top;
    public Stack() {
        this.top = null;
    }
    public void push(int data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
    }
    public int pop() {
        if (isEmpty()) {
            System.out.println("Stack Underflow! Cannot pop.");
            return -1;
        }
        int popped = top.data;
        top = top.next;
        return popped;
    }
    public int peek() {
        if (isEmpty()) {
            System.out.println("Stack is Empty.");
            return -1;
        }
        return top.data;
    }
    public boolean isEmpty() {
        return top == null;
    }
    public void display() {
        if (isEmpty()) {
            System.out.println("Stack is Empty.");
            return;
        }
        Node current = top;
        System.out.print("Stack: ");
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("NULL");
    }
}
public class StackLinkedList {
    public static void main(String[] args) {
        Stack stack = new Stack();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        stack.display(); 
        System.out.println("Top element is: " + stack.peek()); 
        System.out.println("Popped element: " + stack.pop());  
        stack.display();  
        System.out.println("Is Stack Empty? " + stack.isEmpty()); 
        stack.pop();
        stack.pop();
        stack.pop();  // Underflow Case
    }
}
