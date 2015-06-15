/*var point = {
    x: 10,
    y: 20
}

var sp = Object.create(point);
sp.dx = 2;
sp.dy = 3;
sp.getX = function() { return this.x + this.dx; }
sp.getY = function() { return this.y + this.dy; }

var spp = {x: -1, y: -2, dx: -3, dy: -4};
println(sp.getX.apply(spp));
*/
function Point(x, y) {
    this.x = x;
    this.y = y;
    //this.getX = Point.getX;
    //this.getY = Point.getY;
    //return this; - по факту делает new
}

Point.prototype = {
    constructor: Point, 
    getX: function() { return this.x; },
    getY: function() { return this.y; }
}

dump(Point.prototype);

//Point.prototype.getX = function() { return this.x; }
//Point.prototype.getY = function() { return this.y; }
//Point.getX = function() { return this.x; }
//Point.getY = function() { return this.y; }

var point = new Point(10, 20);
//Point.call(Object.create(Point.prototype), 10, 20); - equals to previous

println(Point.prototype.constructor);
println(point.constructor);

function spFactory(point, dx, dy) {
    var sp = Object.create(point);
    sp.dx = dx;
    sp.dy = dy;
    sp.getX = function() { return this.x + this.dx; }
    sp.getY = function() { return this.y + this.dy; }
    return sp;
}

var sp = spFactory(point, -1, -2);

println(sp.getX());
dump(point);
dump(sp);

function dump(sp) {
    for (var name in sp) {
        if (typeof(sp[name]) == "function") {
            println("    " + name + "() = " + sp[name]);
        } else {
            println("    " + name + " = " + sp[name]);
        }
    }
}
