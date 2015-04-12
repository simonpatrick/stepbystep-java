/**
 * Created by patrick on 14/11/29.
 */

console.log(234==234);
console.log(234=="234");
console.log(234==="234");
console.log("cat"=="CAT");
console.log("cat".toUpperCase()=="CAT");
console.log("cat".toUpperCase()==="CAT");
console.log("CAT"==="CAT");

//boolean
console.log(''==false==null==undefined==0); //true
console.log(null===undefined); //false

//validation input
function test(parm){
    if(parm==null||parm==undefined||parm==''){
        throw new Error("invalid input")
    }
};

//test();

//构造器

var x= 234;
var y = new Number(234);
console.log(x===y); //false
console.log(x==y); //true