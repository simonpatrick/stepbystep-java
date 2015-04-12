/**
 * Created by patrick on 14/12/13.
 * @version: $ $ $
 */


$("#test1").click(function() {
    var div = document.querySelectorAll('div')[0];
    div.innerHTML = "<script>alert('慕课网')";
    div.innerHTML = '代码不执行'
})

//代码执行
$("#test2").click(function() {
    var div = $('div')
    div.append("<script>alert('慕课网')").append('代码执行了')
})


$("#test3").click(function() {
    append(document.querySelectorAll('div')[0],"<script>alert('慕课网')" )
})



//==========模拟实现script加载================

function buildFragment(elems, context) {
    var fragment = context.createDocumentFragment(),
        nodes = [],
        i = 0,
        elem,
        l = elems.length;

    for (; i < l; i++) {
        elem = elems[i];
        //创一个元素div做为容器
        tmp = fragment.appendChild(context.createElement("div"));
        //放到文档碎片中
        tmp.innerHTML = elem;
    }
    return fragment;
}

//关闭脚本执行
function disableScript(elem) {
    elem.type = (elem.getAttribute("type") !== null) + "/" + elem.type;
    return elem;
}

//还原脚本
function restoreScript(elem) {
    elem.removeAttribute("type");
    return elem;
}

function domManip(parentEles, target, callback) {
    var l = parentEles.length;
    var iNoClone = l - 1;
    var scripts;
    var hasScripts;
    if (l) {
        var fragment = buildFragment([target], parentEles[0].ownerDocument);
        var first = fragment.firstChild.firstChild
        if (first) {
            //"<script type="false/">alert('慕课网')
            //增加false标记
            scripts = disableScript(first);
            hasScripts = true
            callback.call(parentEles, scripts);
        }

        //执行脚本加载
        if(hasScripts){
            //去掉type锁定
            restoreScript(scripts);
            //开始执行脚本
            var code = scripts.textContent.replace(/^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g, "");
            eval(code)
        }

    }

}

function append(parentEles, target) {
    return domManip([parentEles], target, function(elem) {
        parentEles.appendChild(elem)
    });
}

