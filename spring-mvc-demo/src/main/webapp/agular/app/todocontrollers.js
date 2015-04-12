/**
 * Created by patrick on 15/3/21.
 */

function ToDoListController($scope,$location,Todo){
  $scope.todos = Todo.query();
  $scope.gotoNewPage=function(){
      $location.path('/ToDoList/new');
  };
  $scope.deleteTodo=function(){
     Todo.delete({'id':Todo.id},function(){
         $location.path("/ToDoList");
     });
  } ;
};

function TodoDetailController($scope,$routeParams,$location,Todo){
    $scope.Todo =Todo.get({id:$routeParams.id},function(Todo){

    });

    $scope.gotoToDoListPage = function(){
        $location.path("/");
        };
};

function ToDoNewController($scope,$location,Todo){
    $scope.submit=function(){
      Todo.save($scope.Todo,function(){
          $location.path("/ToDoList");
      });
      $scope.gotoToDoListPage=function(){
          $location.path("/ToDoList");
      };
    };
}