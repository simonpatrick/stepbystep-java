/**
 * Created by patrick on 15/4/12.
 */

demoApp.config(function($routeProvider){
   $routeProvider
       .when("/",{
           controller:'SimpleController',
           templateUrl:'View1.html'
       })
       .when('/partial2',{
           controller:'SimpleController',
           templateUrl:'View2.html'
       })
       .otherwise({redirectTo:"/"})
});