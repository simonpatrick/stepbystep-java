/**
 * Created by patrick on 14/11/29.
 */

//js 基本都通过function还实现面向对象
function Shape() {
    this.X = 0;
    this.Y = 0;
    this.move = function (x, y) {
        this.X += x;
        this.Y += y;
    };
    this.distance_of_original = function (){
      return Math.sqrt(this.X*this.X+this.Y*this.Y);
    };
}

var s = new Shape();
s.move(2,3);
console.log(s.distance_of_original());


