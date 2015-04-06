/**
 * Created by patrick on 15/4/6.
 */
mainApp.controller("studentController",function($scope){
    $scope.student={
        firstName: "simon",
        lastName:  "patrick",
        fees: 500,
        subjects:[
            {name:'physics',marks:80},
            {name:'math',marks:90}
        ],
        fullName: function(){
            var studentObject;
            studentObject = $scope.student;
            return studentObject.firstName+":"+studentObject.lastName;
        }
    };
});