public interface Stack {
    default public int clear() throws Exception {
        while (!isEmpty()) {
            pop();
        }
        //
    }
    /*
    public static void clear(Stack s) {
        while (!s.isEmpty()) {
            s.pop();
        }
    }
    */
}

// clear return int | Exception
// String | IOException | DBZE (DivisionByZeroException)

public class LinkedStack implements Stack {
    /// ....

    public void clear() {
        head = null;
    }
}

public class B {
    void q() {
        class A extends ArrayStack {

        }

        Stack s = new ArrayStack() { /*static*/ /*не компилится*/ {
            push(1);
            push(2);
            push(3);
        }};
    }
}

/* Work:
super(...) - конструктор по умолчанию
все инициализаторы, потом все конструкторы
*/
