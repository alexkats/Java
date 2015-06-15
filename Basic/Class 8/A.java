drawWithColor(Shape shape, Color color) {
    shape.setColor(color);
    shape.draw();
}

// Вызов setColor && draw для наследников

void draw(Shape shape) {
    if (shape instanceof Rect) ...
    if (shape ...) ...
} // полиморфизм времени исполнения - не очень красиво, так как надо будет рисование всех отдельно писать

//<c++>
template <typename T>

bool contains (T x, T a [], int size)
{
    for (int i = 0; i < size; i++)
        if (x == a [i])
            return true;

    return false;
}
//</c++> - можно написать любую программу с помощью template, долго компилится из-за всего этого

//Java
<T> boolean contains(T item, T[] array) {
    for (int i = 0; i < array.length; i++) {
        if (item.equals(array[i])) {
            return true;
        }
    }

    return false;
}

public interface List<E> extends Collection<E> {
    E get(int i);
    set(int i, E e);
    add(E e);

    Iterator<E> iterator();

    ...
}

List list = new List();
list.add(new Integer(1));
Integer a = (Integer) list.get(0); //Без Integer вернет Object

List<Integer> list = new List<Integer>();
list.add(1);
Integer a = list.get(0);

List<Integer> li = new ArrayList<Integer>();
List<Object> lo = li; //Так нельзя, даже учитывая, что Object выше Integer

//Troubles of realisation
//#1
void dump(Collection<Object> c) {
    for (Iterator<Object> i = c.iterator(); i.hasNext(); ) {
        Object o = i.next();
        System.out.println(o);
    }
}

//Solution - wildcard
void dump(Collection<?> c) {
    for (Iterator<?> i = c.iterator(); i.hasNext(); ) {
        Object o = i.next();
        System.out.println(o);
    }
}

//#2
void draw(List<Shape> c) {
    for (Iterator<Shape> i = c.iterator(); i.hasNext(); ) {
        Shape s = i.next();
        s.draw();
    }
}

//Solution - bounded wildcard
void draw(List<? extends Shape> c) {
    for (Iterator<? extends Shape> i = c.iterator(); i.hasNext(); ) {
        Shape s = i.next();
        s.draw();
    }
}

//#3

