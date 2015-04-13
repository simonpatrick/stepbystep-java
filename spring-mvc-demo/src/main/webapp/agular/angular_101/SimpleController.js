/**
 * Created by patrick on 15/4/12.
 */
var demoApp = angular.module('demoApp',[]);
demoApp.controller('SimpleController',
function SimpleController($scope){
    $scope.customers=[
        {name:'patrick',city:'shanghai'},
        {name:'simon',city:'beijing'},
        {name:'kevin',city:'sina'}
    ];
    $scope.addCustomer =function(){
        $scope.customers.push({name: $scope.newCustomer.name
            ,city:$scope.newCustomer.city});
        console.log($scope.customers)
    }
});
