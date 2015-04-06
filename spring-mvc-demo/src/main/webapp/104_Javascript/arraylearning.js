/**
 * Created by patrick on 14/11/29.
 */

//array 是object
var arr1= [];
console.log(typeof(arr1));

//array也是array
console.log(Array.isArray(arr1));

// 遍历array
var arr2 =["cat","dog","pig"];
for (i = 0 ; i<arr2.length;i++){
    console.log(arr2[i]);
}

// array operation
arr2.push("wolve");
console.log(arr2);
//LIFO last in first out
arr2.pop();
console.log(arr2);
//delelet
arr2.push("wolve");
delete arr2[3];
//index of 3 还是存在的
console.log(arr2);
//use slice, start index to end index
console.log(arr2.slice(1,2));
console.log(arr2);
// start index and delete number
console.log(arr2.splice(1,2));
console.log(arr2);

// shift, unshift,join
arr2.unshift("wolve");
console.log(arr2);
arr2.shift();
console.log(arr2);
console.log(arr2.join("__"));

//sort
var number_list = [1,23,4,5,6,54,4435];
var sorted_number = number_list.sort();
//字母顺序
console.log(number_list);
console.log(sorted_number);
console.log(number_list.sort(function(a,b){
    return a-b ;
}));

