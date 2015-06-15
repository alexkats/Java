var cnst = function(number) {
    return function(x, y, z) {
        return number;
    }
}

var unaryOperation = function(first) {
    return function(f) {
        return function(x, y, z) {
            return f(first(x, y, z));
        }                           
    }
}

var abs = function(first) {
    var f = function(a) {
        return Math.abs(a);
    }

    return unaryOperation(first)(f);
}

var log = function(first) {
    var f = function(a) {
        return Math.log(a);
    }

    return unaryOperation(first)(f);
}

var variable = function(name) {
    return function(x, y, z) {  
        if (name == 'x')
            return x;
        if (name == 'y')
            return y;
        if (name == 'z')
            return z;
    }
}

var binaryOperation = function(first, second) {
    return function(f) {
        return function(x, y, z) {
            return f(first(x, y, z), second(x, y, z));
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

//var expression = add(subtract(multiply(variable("x"), variable("x")), multiply(cnst(2), variable("x"))), cnst(1));

/*for (var i = 0; i <= 10; i++) {
    println(i + ": " + expression(i));
} */

var parse = function(input) {
    var a = [];
    var n = input.length;

    for (var i = 0; i < n; i++) {
        if (input[i] == ' ') {
            continue;
        }

        if (input[i] == 'x') {
            a.push(variable("x"));
            continue;
        }
        if (input[i] == 'y') {
            a.push(variable("y"));
            continue;
        }
        if (input[i] == 'z') {
            a.push(variable("z"));
            continue;
        }
        
        if (input[i] >= '0' && input[i] <= '9') {
            var ind = i;
            while ((n > (i + 1) && input[i + 1] >= '0' && input[i + 1] <= '9') || input[i] == '.') {
                i++;
            }
        a.push(cnst(parseFloat(input.substring(ind, i + 1))));
        }
        else if(input[i] == 'l' || input[i] == 'a') {
            var first = a.pop();
            
            switch(input[i]) {
                case 'l':
                    a.push(log(first));
                    i += 2;
                    break;
                case 'a':
                    a.push(abs(first));
                    i += 2;
                    break;
            }
        }
        else {
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

//println(parse("abs -7")(0, 0, 0));
