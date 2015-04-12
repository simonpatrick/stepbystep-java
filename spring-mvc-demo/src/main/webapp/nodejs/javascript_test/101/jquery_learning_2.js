/**
 * Created by patrick on 14/12/13.
 * @version: $ $ $
 */

function a1(){
    var time1= (+new Date());

    for(var i =0 ; i<5000;i++){
        var op = document.createElement("span");
        var oText = document.createTextNode(i);
        op.appendChild(oText);
        document.body.appendChild(op);
    }

    document.getElementById("test").innerHTML='<br><div>普通方式创建耗时'+ ( +new Date() - time1);

}

//fragmenet build elemenet
function a2(){
    var oFragment= document.createDocumentFragment();
    var time2=(+new Date());
    for(var i =0 ; i<5000;i++){
        var op = document.createElement("span");
        var oText = document.createTextNode(i);
        op.appendChild(oText);
        oFragment.appendChild(op);
    }

    document.body.appendChild(oFragment);
    document.getElementById('test1').innerHTML = '<br><div>文档碎片创建耗时'+ ( +new Date() - time2);
}