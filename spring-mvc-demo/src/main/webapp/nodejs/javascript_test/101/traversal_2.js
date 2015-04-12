/**
 * Created by patrick on 14/12/13.
 * @version: $ $ $
 */

$(document).ready(function() {
    var ajQuery = {};

    function dir(elem, dir, until) {
        var matched = [],
            truncate = until !== undefined;
        while ((elem = elem[dir]) && elem.nodeType !== 9) {
            if (elem.nodeType === 1) {
                if (truncate) {
                    if (elem.nodeName.toLowerCase() == until || elem.className == until) {
                        break;
                    }
                }
                matched.push(elem);
            }
        }
        return matched;
    }

    jQuery.each({
        parent: function (elem) {
            var parent = elem.parentNode;
            return parent && parent.nodeType !== 11 ? parent : null;
        },
        parents: function (elem) {
            return dir(elem, "parentNode");
        },
        parentsUntil: function (elem, until) {
            return dir(elem, "parentNode", until);
        }
    }, function (name, fn) {
        ajQuery[name] = function (until, selector) {
            return  fn(until, selector);
        };
    });


    $("#test1").click(function () {
        var item = $('.item-1');
        alert(item.parent()[0])
        alert(item.parents().length)
        alert(item.parentsUntil('body').length)
    })

    $("#test2").click(function () {
        var item = document.querySelectorAll('.item-1')[0]
        alert(ajQuery.parent(item))
        alert(ajQuery.parents(item).length)
        alert(ajQuery.parentsUntil(item, 'body').length)
    });
});
