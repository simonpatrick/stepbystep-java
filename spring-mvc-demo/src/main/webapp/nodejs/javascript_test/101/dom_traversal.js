/**
 * Created by patrick on 14/12/13.
 * @version: $ $ $
 */

$(document).ready(function(){

    $("#span1").parent().css({"color":"red","border":"2px solid red"});
    $("#span1").parentsUntil("body").css({"color":"red","border":"2px solid red"});
    //$("span").parents().css({"color":"red","border":"2px solid red"});
    $("#test1").click(function(event) {
        $('li').eq(2).css('background-color', 'red');
    })
    $("#test2").click(function(event) {
        $('li').filter(':even').css('background-color', 'blue');
    })
    $("#test3").click(function(event) {
        console.log(event);
        $('li').not(':even').css('background-color', 'red');
    })
    $("#test4").click(function(event) {
        $('ul.level-2').children().css('background-color', 'yellow');
    })
    $("#test5").click(function(event) {
        $('li.item-a').closest('ul')
            .css('background-color', 'red');
    })
    $("#test6").click(function(event) {
        $('li.item-ii').find('li').css('background-color', 'blue');
    })

    $("div#div2 h2").nextUntil("h6").css({"color":"red","border":"2px solid red"});


});
