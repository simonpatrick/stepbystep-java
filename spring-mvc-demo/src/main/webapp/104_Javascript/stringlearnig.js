/**
 * Created by patrick on 14/11/29.
 */

//string function

var x = "test.test";
var index = x.indexOf("test");
console.log(index);
var index = x.indexOf("test",2);
console.log(index);

var y = "  test  ";
console.log(y.trim());
console.log(y.substr(2));
console.log(y.substr(0,2));
console.log(y.substr(1,4));
console.log(y.slice(2,3));
console.log(y.split(""));
console.log(y.split(" "));
console.log(y.replace("te","et"));
console.log(y)
console.log("\n\n test  \n\n".trim())

//regular expression

var temp = "aaoo";
var temp1 = "a00";
var temp2 = "aaa00";
var exp = new RegExp("[Aa]{2,}");
console.log(temp.replace(exp,"b"));
console.log(temp1.replace(exp,"b"));
console.log(temp2.replace(exp,"b"));
console.log(temp2.replace( /[Aa]{2,}/ ,"b"));

