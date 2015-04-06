/**
 * Created by patrick on 14/12/13.
 */

$(document).ready(function(){
    $("#hide").click(function(){
        $("p").hide(1000,function(){
            alert("hide p");
        });
    });
    $("#show").click(function(){
        $("p").show(1000);
    });

    $("#fade").click(function(){
        $("#div1").fadeToggle();
        $("#div2").fadeToggle("slow");
        $("#div3").fadeToggle(3000,function(){
            console.log("start fading ");
        });

        $("#div1").fadeTo("slow",0.15);
        $("#div2").fadeTo("slow",0.4);
        $("#div3").fadeTo("slow",0.7);
        $("#div3").css("background-color","red").fadeTo("slow",0.1);
    });
//    $("button").click(function(){
//        $("p").toggle(1000);
//    });

    $(".flip").click(function(){

        $(".panel").slideToggle("slow");
       // $(".panel").slideUp("slow");
        // $(".panel").slideDown("slow");
    });

    $("#button_animation").click(function(){
        var div=$("#div_animation");
        div.animate({height:'300px',opacity:'0.4'},"slow",function(){
            console.log("move hight 300 px");
        });
        div.animate({width:'300px',opacity:'0.8'},"slow");
        div.animate({height:'100px',opacity:'0.4'},"slow");
        div.animate({width:'100px',opacity:'0.8'},"slow");
        div.animate({left:'+=100px'},"slow");
        div.animate({fontSize:'2em'},"slow");
    });
});