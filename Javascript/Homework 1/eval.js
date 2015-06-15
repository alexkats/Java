var cnst = function(number) {
    return function(x, y, z) {
        return number;
    }
}

var pi = function() {
    return function(x, y, z) {
        return Math.PI;
    }
}

var variable = function(name) {
    return function(x, y, z) {
        if (name == "x") {
            return x;
        }

        if (name == "y") {
            return y;
        }

        if (name == "z") {
            return z;
        }
    }
}

var binaryOperation = function(first, second) {
    return function(f) {
        return function(x, y, z) {
            return f(first(x, y, z), second(x, y, z));
        }
    }
}

var unaryOperation = function(oper) {
    return function(f) {
        return function(x, y, z) {
            return f(oper(x, y, z));
        }
    }
}

var add = function(first, second) {
    var f = function(a, b) {
        return a + b;
    }

    return binaryOperation(first, second)(f);
}

var subtract = function(first, second) {
    var f = function(a, b) {
        return a - b;
    }

    return binaryOperation(first, second)(f);
}

var multiply = function(first, second) {
    var f = function(a, b) {
        return a * b;
    }

    return binaryOperation(first, second)(f);
}

var divide = function(first, second) {
    var f = function(a, b) {
        return a / b;
    }

    return binaryOperation(first, second)(f);
}

var pow = function(first, second) {
    var f = function(a, b) {
        return Math.pow(a, b);
    }

    return binaryOperation(first, second)(f);
}

var abs = function(oper) {
    var f = function(a) {
        return Math.abs(a);
    }

    return unaryOperation(oper)(f);
}

var log = function(oper) {
    var f = function(a) {
        return Math.log(a);
    }

    return unaryOperation(oper)(f);
}

var sin = function(oper) {
    var f = function(a) {
        return Math.sin(a);
    }

    return unaryOperation(oper)(f);
}

/*
var expression = add(subtract(multiply(variable("x"), variable("x")), multiply(cnst(2), variable("x"))), cnst(1));

for (var i = 0; i <= 10; i++) {
    println(i + ": " + expression(i));
}
*/

var parse = function(input) {
    var a = [];
    var n = input.length;

    for (var i = 0; i < n; i++) {
        if (input[i] == ' ') {
            continue;
        }

        if (input[i] == 'x') {
            a.push(variable("x"));
        } else if (input[i] == 'y') {
            a.push(variable("y"));
        } else if (input[i] == 'z') {
            a.push(variable("z"));
        } else if ((input[i] == '-' && input[i + 1] >= '0' && input[i + 1] <= '9') || (input[i] >= '0' && input[i] <= '9')) {
            var ind = i;

            while (n > (i + 1) && (input[i + 1] == '.' || (input[i + 1] >= '0' && input[i + 1] <= '9'))) {
                i++;
            }

            a.push(cnst(parseFloat(input.substring(ind, i + 1))));
        } else if (input[i] == 'a' || input[i] == 'l') {
            var oper = a.pop();

            switch(input.substring(i, i + 3)) {
                case "abs":
                    i += 2;
                    a.push(abs(oper));
                    break;
                case "log":
                    i += 2;
                    a.push(log(oper));
                    break;
            }
        } else {
            var second = a.pop();
            var first = a.pop();

            switch(input[i]) {
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

/*
println("");
println(parse("xx2-*x*1+")(5));

println(parse("5 3 /")(0, 0, 0));
var expr = divide(cnst(5), cnst(3));
println(expr(0));
println(parse("-23 5 +")(0, 0, 0));
*/
