
// 图片上传
function imgUpload(inputId, uploadUrl, imgInputId, type) {
    $("#"+inputId).fileinput({
        language: 'zh', //设置语言
        uploadUrl:uploadUrl, //上传的地址
        allowedFileExtensions: ['jpg','jpeg', 'gif', 'png','doc','docx','xls','xlsx','pdf'],//接收的文件后缀
        //uploadExtraData:{"id": 1, "fileName":'123.mp3'},
        uploadAsync: true, //默认异步上传
        showUpload:true, //是否显示上传按钮
        showRemove :true, //显示移除按钮
        showPreview :true, //是否显示预览
        showCaption:false,//是否显示标题
        browseClass:"btn btn-primary", //按钮样式
        dropZoneEnabled: false,//是否显示拖拽区域
        //minImageWidth: 50, //图片的最小宽度
        //minImageHeight: 50,//图片的最小高度
        //maxImageWidth: 1000,//图片的最大宽度
        //maxImageHeight: 1000,//图片的最大高度
        //maxFileSize:0,//单位为kb，如果为0表示不限制文件大小
        //minFileCount: 0,
        maxFileCount:10, //表示允许同时上传的最大文件个数
        enctype:'multipart/form-data',
        validateInitialCount:true,
        previewFileIcon: "<i class='glyphicon glyphicon-file'></i>",
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"

        //上传中
    }).on('filepreupload', function(event, data, previewId, index) {
        var form = data.form;
        var files = data.files;
        var extra = data.extra;
        var response = data.response;
        var reader = data.reader;

        //一个文件上传成功
    }).on("fileuploaded", function (event, data, previewId, index) {

        var res = data.response;

        if(typeof(res) == "string") {
            res = eval("("+res+")");
        }

        var fileName = res["fileName"];

        if(type == "add") {
            var val = $("#"+imgInputId).val();

            if(val == "null") {
                val = "";
            }

            val += fileName + ";";

            $("#"+imgInputId).val(val);
        } else {
            $("#"+imgInputId).val(fileName);
        }


    }).on('fileerror', function(event, data, msg) {  //一个文件上传失败
        alert("上传图片失败")
    });
}