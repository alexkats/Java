function cnst(number) {
    return function(x) {
        return number;
    }
}

function variable(name) {
    return function(x) {
        return x;
    }
}

function binaryOperation(first, second, f) {
    return function(x) {
        return f(first(x), second(x));
    }
}

function add(first, second) {
    return binaryOperation(first, second, function(a, b) {
        return a + b;
    });
}

function subtract(first, second) {
    return binaryOperation(first, second, function(a, b) {
        return a - b;
    });
}

function multiply(first, second) {
    return binaryOperation(first, second, function(a, b) {
        return a * b;
    });
}

function divide(first, second) {
    return binaryOperation(first, second, function(a, b) {
        return a / b;
    });
}

var expression = add(subtract(multiply(variable("x"), variable("x")), multiply(cnst(2), variable("x"))), cnst(1));

for (var i = 0; i <= 10; i++) {
    println(i + ": " + expression(i));
}

function parse(input) {
    var a = [];

    for (var i = 0; i < input.length; i++) {
        if (input[i] == 'x') {
            a.push(variable("x"));
        } else if (input[i] >= '0' && input[i] <= '9') {
            var ind = i;

            while (input.length > (i + 1) && input[i + 1] >= '0' && input[i + 1] <= '9') {
                i++;
            }

            a.push(cnst(parseInt(input.substring(ind, i + 1))));
        } else {
            var second = a.pop();
            var first = a.pop();

            switch (input[i]) {
                case '+':
                    a.push(add(first, second));
                    break;
                case '-':
                    a.push(subtract(first, second));
                    break;
                case '*':
                    a.push(multiply(first, second));
                    break;
                case '/':
                    a.push(divide(first, second));
                    break;
            }
        }
    }

    var ans = a.pop();
    return ans;
}

println("");
println(parse("xx2-*x*1+")(5));
