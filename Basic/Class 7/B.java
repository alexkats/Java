// Exceptions in Java 7

try (
    Type var1 = ...;
    Type var2 = ...; // Можно инициализировать некоторые переменные
    ...
) { ... }

// Example:
try (InputStream is = new FileInputStream("in")) {
    System.out.println(is.read());
}

// В круглые скобки можно реализовывать любые типы-наследники, реализующие интерфейс AutoCloseable

Метод close() throws Exception

Throwable.addSuppressed(...)

//Was
try { ...
} catch (SQLException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
}

//Now
try { ...
} catch (SQLException | IOException e) {
    e.printStackTrace();
}

// Безопасность

/*1*/class CloneablePair {
    Cloneable first;
    Cloneable second;
}

...

/*2*/void copyOf(CloneablePair p) {
    first = p.first.clone();
    second = p.second.clone();
}

/*3*/void copyOf(CloneablePair p) {
    Cloneable temp = p.first.clone();
    second = p.second.clone();
    first = temp;
}

/*4*/// Должно быть temp.destroy(); вместо first.destroy();

/*5*///Может произойти ошибка при присваивании (в реальности почти никогда не надо писать)

/*6*/
