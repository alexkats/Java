function Const(number) {
    this.number = number;
}

Const.prototype.evaluate = function(x, y, z) {
    return this.number;
}

Const.prototype.diff = function() {
    return new Const(0);
}

Const.prototype.toString = function() {
    return String(this.number) + " ";
}

function Variable(name) {
    this.name = name;
}

Variable.prototype.evaluate = function(x, y, z) {
    switch (this.name) {
        case "x":
            return x;
            break;
        case "y":
            return y;
            break;
        case "z":
            return z;
            break;
    }
}

Variable.prototype.diff = function(name) {
    if (this.name == name) {
        return new Const(1);
    } else {
        return new Const(0);
    }
}

Variable.prototype.toString = function() {
    return this.name + " ";
}

function BinaryOperation() {
}

BinaryOperation.prototype.evaluate = function(x, y, z) {
    return this.get(this.first.evaluate(x, y, z), this.second.evaluate(x, y, z));
}

BinaryOperation.prototype.toString = function() {
    return String(this.first.toString()) + " " + String(this.second.toString()) + " " + this.id + " ";
}

function Add(first, second) {
    this.first = first;
    this.second = second;
}

Add.prototype = new BinaryOperation();

Add.prototype.get = function(a, b) {
    return a + b;
}

Add.prototype.diff = function(name) {
    return new Add(this.first.diff(name), this.second.diff(name));
}

Add.prototype.id = "+"

function Subtract(first, second) {
    this.first = first;
    this.second = second;
}

Subtract.prototype = new BinaryOperation();

Subtract.prototype.get = function(a, b) {
    return a - b;
}

Subtract.prototype.diff = function(name) {
    return new Subtract(this.first.diff(name), this.second.diff(name));
}

Subtract.prototype.id = "-"

function Multiply(first, second) {
    this.first = first;
    this.second = second;
}

Multiply.prototype = new BinaryOperation();

Multiply.prototype.get = function(a, b) {
    return a * b;
}

Multiply.prototype.diff = function(name) {
    return new Add(new Multiply(this.first.diff(name), this.second), new Multiply(this.first, this.second.diff(name)));
}

Multiply.prototype.id = "*";

function Divide(first, second) {
    this.first = first;
    this.second = second;
}

Divide.prototype = new BinaryOperation();

Divide.prototype.get = function(a, b) {
    return a / b;
}

Divide.prototype.diff = function(name) {
    return new Divide(new Subtract(new Multiply(this.first.diff(name), this.second), new Multiply(this.first, this.second.diff(name))), new Multiply(this.second, this.second));
}

Divide.prototype.id = "/"

function UnaryOperation() {
}

UnaryOperation.prototype.evaluate = function(x, y, z) {
    return this.get(this.arg.evaluate(x, y, z));
}

UnaryOperation.prototype.toString = function() {
    return String(this.arg.toString()) + " " + this.id + " ";
}

function Sin(arg) {
    this.arg = arg;
}

Sin.prototype = new UnaryOperation();

Sin.prototype.get = function(a) {
    return Math.sin(a);
}

Sin.prototype.diff = function(name) {
    return new Multiply(new Cos(this.arg), this.arg.diff(name));;
}

Sin.prototype.id = "sin";

function Cos(arg) {
    this.arg = arg;
}

Cos.prototype = new UnaryOperation();

Cos.prototype.get = function(a) {
    return Math.cos(a);
}

Cos.prototype.diff = function(name) {
    return new Multiply(new Subtract(new Const(0), new Sin(this.arg)), this.arg.diff(name));
}

Cos.prototype.id = "cos";

var isDigit = function(c) {
    return c >= '0' && c <= '9';
}
