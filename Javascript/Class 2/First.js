/*var obj = {
    "x": 10,
    "y": 20
}*/
/*obj["x"] = 100;
obj["y"] = 200;
obj["hello, world"] = 30;
obj[1] = 40;
obj["undefined"] = 50;

println(obj["x"]);
println(obj["y"]);
println(obj["hello, world"]);
println(obj["1"]);
println(obj[undefined]);

println(obj["x"]); //equals
println(obj.x); //equals*/

var point = {
    x: 10,
    y: 20
}

var shiftedPoint = Object.create(point);
shiftedPoint.dx = 2;
shiftedPoint.dy = 3;
shiftedPoint.getX = function() { return this.x + this.dx; };
shiftedPoint.getY = function() { return this.y + this.dy; };

println(shiftedPoint.getX());

var sp = shiftedPoint;

for (var name in sp) {
    if (typeof(sp[name]) == "function") {
        println("    " + name + "() = " + sp[name]/*.name - одно поле с именем name*/);
    } else {
        println("    " + name + " = " + sp[name]/*.name - одно поле с именем name*/);
    }
}

//var colorPoint = Object.create(point/*null*/);
/*println(Object.getPrototypeOf(colorPoint));
colorPoint.color = "red";

println(colorPoint.color);
println(colorPoint.x);
println(colorPoint.y);

/*colorPoint*//*point.x = 100;
println(colorPoint.x);
println(point.x);

/*var point2 = point; // copies link
point.x = 1000;
println(point2.x);*/

/*Object.prototype.hello = "world";
println(colorPoint.hello);
println("dafadf".hello);
2*/
