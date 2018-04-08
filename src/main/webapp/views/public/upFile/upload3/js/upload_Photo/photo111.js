$(function(){
var obUrl = ''
//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
$("#clipArea").photoClip({
    width: 280,
    height: 280,
    file: "#file",
    view: "#view",
    ok: "#clipBtn",
    loadStart: function() {
        console.log("照片读取中");
    },
    loadComplete: function() {
        console.log("照片读取完成");
    },
    clipFinish: function(dataURL) {
        console.log(dataURL);
    }
});

$("#logoBox,#s_dpage").click(function(){
$(".htmleaf-container").fadeIn(300);
$("#dpage").addClass("show");
})
    $("#clipBtn").click(function(){
        $("#logoBox").empty();
        $('#logoBox').append('<img src="' + imgsource + '" align="absmiddle" style=" width:100%;">');
        $(".htmleaf-container").hide();
        $("#dpage").removeClass("show");
    })



    jQuery.divselect = function(divselectid,inputselectid) {
        var inputselect = $(inputselectid);
        $(divselectid+" small").click(function(){
            $("#divselect ul").toggle();
            $(".mask").show();
        });
        $(divselectid+" ul li a").click(function(){
            var txt = $(this).text();
            $(divselectid+" small").html(txt);
            var value = $(this).attr("selectid");
            inputselect.val(value);
            $(divselectid+" ul").hide();
            $(".mask").hide();
            $("#divselect small").css("color","#333")
        });
    };
    $.divselect("#divselect","#inputselect");


    jQuery.divselectx = function(divselectxid,inputselectxid) {
        var inputselectx = $(inputselectxid);
        $(divselectxid+" small").click(function(){
            $("#divselectx ul").toggle();
            $(".mask").show();
        });
        $(divselectxid+" ul li a").click(function(){
            var txt = $(this).text();
            $(divselectxid+" small").html(txt);
            var value = $(this).attr("selectidx");
            inputselectx.val(value);
            $(divselectxid+" ul").hide();
            $(".mask").hide();
            $("#divselectx small").css("color","#333")
        });
    };
    $.divselectx("#divselectx","#inputselectx");



    jQuery.divselecty = function(divselectyid,inputselectyid) {
        var inputselecty = $(inputselectyid);
        $(divselectyid+" small").click(function(){
            $("#divselecty ul").toggle();
            $(".mask").show();
        });
        $(divselectyid+" ul li a").click(function(){
            var txt = $(this).text();
            $(divselectyid+" small").html(txt);
            var value = $(this).attr("selectyid");
            inputselecty.val(value);
            $(divselectyid+" ul").hide();
            $(".mask").hide();
            $("#divselecty small").css("color","#333")
        });
    };
    $.divselecty("#divselecty","#inputselecty");

   $(".mask").click(function(){
       $(".mask").hide();
       $(".all").hide();
   })
    $(".right input").blur(function () {
        if ($.trim($(this).val()) == '') {
            $(this).addClass("place").html();
        }
        else {
            $(this).removeClass("place");
        }
    })



$("#file0").change(function(){
    var objUrl = getObjectURL(this.files[0]) ;
     obUrl = objUrl;
    console.log("objUrl = "+objUrl) ;
    if (objUrl) {
        $("#img0").attr("src", objUrl).show();
    }
    else{
        $("#img0").hide();
    }
}) ;
function qd(){
   var objUrl = getObjectURL(this.files[0]) ;
   obUrl = objUrl;
   console.log("objUrl = "+objUrl) ;
   if (objUrl) {
       $("#img0").attr("src", objUrl).show();
   }
   else{
       $("#img0").hide();
   }
}
function getObjectURL(file) {
    var url = null ;
    if (window.createObjectURL!=undefined) { // basic
        url = window.createObjectURL(file) ;
    } else if (window.URL!=undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file) ;
    } else if (window.webkitURL!=undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file) ;
    }
    return url ;
}

var subUrl = "";

    $(".file-3").bind('change',function(){
        subUrl = $(this).val()
        $(".yulan").show();
        $(".file-3").val("");
    });

    $(".file-3").each(function(){
        if($(this).val()==""){
            $(this).parents(".uploader").find(".filename").val("营业执照");
        }
    });
$(".btn-3").click(function(){
$("#img-1").attr("src", obUrl);
$(".yulan").hide();
$(".file-3").parents(".uploader").find(".filename").val(subUrl);
})
    $(".btn-2").click(function(){
        $(".yulan").hide();
    })



function setImagePreview() {
    var preview, img_txt, localImag, file_head = document.getElementById("file_head"),
            picture = file_head.value;
    if (!picture.match(/.jpg|.gif|.png|.bmp/i)) return alert("您上传的图片格式不正确，请重新选择！"),
            !1;
    if (preview = document.getElementById("preview"), file_head.files && file_head.files[0]) preview.style.display = "block",
            preview.style.width = "100px",
            preview.style.height = "100px",
            preview.src = window.navigator.userAgent.indexOf("Chrome") >= 1 || window.navigator.userAgent.indexOf("Safari") >= 1 ? window.webkitURL.createObjectURL(file_head.files[0]) : window.URL.createObjectURL(file_head.files[0]);
    else {
        file_head.select(),
                file_head.blur(),
                img_txt = document.selection.createRange().text,
                localImag = document.getElementById("localImag"),
                localImag.style.width = "100px",
                localImag.style.height = "100px";
        try {
            localImag.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)",
                    localImag.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = img_txt
        } catch(f) {
            return alert("您上传的图片格式不正确，请重新选择！"),
                    !1
        }
        preview.style.display = "none",
                document.selection.empty()
    }
    return document.getElementById("DivUp").style.display = "block",
            !0
}
});
