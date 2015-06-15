class DBZComplexException extends Exception { 
    ...
    ...
    ...
}

Complex divide(Complex c) {
    throws DBZComplexException;
    ...
    ...
}

...
if (Math.abs(d) < EPS) {
    throw new DBZComplexException(d);
}
...






try {
    Complex c = a.divide(b);
    //Do something - OK
}

catch (DBZComplexException e) {
    //Обработаем ошибку
}

OR

Complex calculate(...) throws DBZComplexException { //Кидает исключение дальше наверх
    Complex c = a.divide(b);
    //Do something - OK
}

AssertionError /*часто используется*/ {
    throw new AssertionError(message) or assert statement [:message];
}

//1 - недостижимое состояние, 2 - проверка утверждения




ComplexException extends Exception {
    public ComplexException(String message) {
        super(message);
    }

    public ComplexException(
        String message,
        Throwable cause
    );



}



try {
    // Действия
} catch (*Exception e) {
    // Обработка исключения
} catch (*Exception e) {
    // Обработка исключения
} finally { // finally - действия будут обязательно выполнены, даже если есть return в try это все равно исполнится
    // Действия при выходе
}




//Применение:

try {
    f();
} catch (*Exception e) {
}

f() { ... g(); ... }

g() { ... throw new *Exception(...); ... }


//Управление русерсами (например, сетевое соединение)

// Получение ресурса
try {
    // Действия с ресурсом
} finally {
    // Освобождение ресурса
}





Нельзя так делать!!!:
try {
    int index = 0;

    while (true) {
        System.out.println(a[index++]);
    }
} catch (IndexOutOfBoundsException e) {
}
// Exception - очень дорогая операция!


// Полное игнорирование
try {



} catch (Exception e) {}


// Запись в лог
try {


} catch (Exception e) {
    e.printStackTrace();
}

// Exception надо обрабатывать там, где его могут обработать.

// Ловить просто Exception плохо!!! (ловушки базовых исключений)
try {



} catch (Exception e) {
    // Что-то полезное
}

try {
} catch (IOException e) {
    throw new APISpecificException(e);
}
//OK

try {
} catch (IOException e) {
    e.printStackTrace();
    throw new APISpecificException();
}
//Not OK, так как выводим лишнюю ненужную информацию

10
