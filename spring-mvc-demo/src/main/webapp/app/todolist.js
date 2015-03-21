/**
 * Created by patrick on 15/3/21.
 */

angular.module('todoApp',['todoService'])
    .config(['$routeProvider',function($routeProvider){
    $routeProvider.
        when('/ToDoList/list',{templateUrl:'views/todolist-list.html',controller:ToDoListController}).
        when('/ToDoList',{templateUrl:'views/todolist-list.html',controller:ToDoListController}).
        when('/ToDoList/new',{templateUrl:'views/todolist-new.html',controller:ToDoListNewController}).
        when('/ToDoList/:id',{templateUrl:'views/todolist-detail.html',controller:ToDoListDetailController}).
        otherwise({redirectTo:'/ToDoList'})
    }]);