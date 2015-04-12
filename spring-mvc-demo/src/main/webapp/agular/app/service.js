/**
 * Created by patrick on 15/3/21.
 */
angular.module('todoService',['ngResource']).
    factory('Todo',function($resource){
        return $resource('ToDoList/:id',{},{
            'save': {method: 'PUT'}
        });
    });