/**
 * Created by patrick on 14/11/29.
 */

function hello(name){
    console.log("hello "+name);
}

hello("patrick");
//throw out additional parameters
hello("patrick","simon");
//use undefined if no parameter passed
hello()

function init_cache(){
    var init_cache ={
        cache_size: 10,
        location: "/etc/cache",
        algorithm: "hash",
        item_size: 1024,
        type: "btree+"
    };

    var args = arguments;
    for(var i =0;i< args.length;i++){
        if(typeof(a[i])=='object'){
            init_cache=a[i];
        }else if (typeof a[i] =='number'){
            init_cache["cache_size"]=a[i];
            break;
        }else if (typeof(a[i])=='string'){
            init_cache["location"]=a[i];
        }else{
            throw new Error("bad cache parameters"+args);
        }
    };

}

init_cache();

//function definition
var f = function (a,b){
    return a+b;
}

console.log(f(3,4));

//function scope
var pet = "pet";
function play_with_pet(){
    var pet = "dog";
    console.log(pet);
};

play_with_pet();
console.log(pet);

var height = 3;
var radius = 4;
var volume;
(function(){
    //didn't change the global variant
   volume = Math.PI*radius*radius*height;
    console.log(volume);
});

console.log(volumn);


