#!/bin/bash
# fold name
echo "create folder $1"
# is $1 existing then create mkdir $1
rm -rf $1
mkdir $1

echo "cd folder $1"
cd $1
path ='pwd'
echo $path

npm install express
touch app.js
