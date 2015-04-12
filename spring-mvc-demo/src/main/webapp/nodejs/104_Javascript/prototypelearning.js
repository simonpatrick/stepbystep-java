/**
 * Created by patrick on 14/11/29.
 */
//prototype Shape
function Shape(){

};

Shape.prototype.X=0;
Shape.prototype.Y=0;
Shape.prototype.move = function(x,y){
    this.X =x;
    this.Y=y;
};
Shape.prototype.distance_of_original=function(){
    console.log("prototype function");
    return Math.sqrt(this.X*this.X+this.Y*this.Y);
};

var s2 = new Shape();
s2.move(3,4)
s2.distance_of_original();

//prototype and inheritance

Shape.prototype.area = function (){
    return this.X*this.Y;
}

function Square(){

};

Square.prototype = new Shape();
Square.prototype.__proto__=Shape.prototype;
Square.prototype.Width =0;
Square.prototype.area = function(){
    return this.Width*this.Width;
}

var sq = new Square();
sq.move(-5,-5);
sq.Width = 5;
console.log(sq.distance_of_original());
console.log(sq.area());

function Rectangle(){

};

Rectangle.prototype = new Shape();
Rectangle.prototype.__proto__=Shape.prototype;
Rectangle.prototype.Height =0;
Rectangle.prototype.area = function(){
    return this.Width*this.Height;
}

var rec = new Rectangle();
rec.move(5,6);
rec.distance_of_original();
rec.Width=10;
rec.Height=10;
console.log(rec.area());
