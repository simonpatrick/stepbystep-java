//first express

var express = require('express')
var app = express()

app.get('/',function(req,res){
   res.send('Hello World!')
});

app.listen(3000,function(){
   console.log('app is listening at pot 3000');
});


