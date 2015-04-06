/**
 * Created by patrick on 14/11/29.
 */

//json format
var user ={
  first_name:"simon",
  second_name:"patrick",
  age:18,
  male:"MALE"
};

for(key in user){
    console.log(key+":"+user[key]);
}

console.log(typeof(user));
// access object member
console.log(user["first_name"]);
//add new member
user["location"]="shanghai";
console.log(user);

//delete attribute
delete user["location"];
console.log(user);

// get length of an object
console.log(Object.keys(user).length);