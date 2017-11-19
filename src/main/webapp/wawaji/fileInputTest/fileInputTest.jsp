<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>文件上传</title>
    <%@ include file="../../include/header.jsp"%>
</head>

    <body>
        <link href="/dist/css/fileInput/fileinput.css" media="all"rel="stylesheet" type="text/css" />
        <script src="/dist/js/fileInput/fileinput.js" type="text/javascript"></script>

        <script src="/dist/js/fileInput/locales/zh.js" type="text/javascript"></script>

        <form id="form" method="post" enctype="multipart/form-data">
            <div class="row form-group">
                <label class="col-md-4">图片上传:</label>
                <div class="col-sm-12">
                    <input id="uploadFile" name="file" multiple type="file" data-show-caption="true">
                </div>
            </div>
            <div style = "text-align:center">
                <div id = "images">
                    <h3>图片展示</h3>

                </div>
                <div id = "files">
                    <h3>文件展示</h3>

                </div>
            </div>

            <script type="text/javascript">
                $("#images").hide();
                $("#files").hide();
                var width = $(document.body).width()/4;
                var url = "/wawaji/fileUpload/uploadFile.action";
                var inputId = "uploadFile";
                initFileInput(inputId,url);

                // 文件上传初始化
                function initFileInput(inputId,uploadUrl) {
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
                        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
                    }).on('filepreupload', function(event, data, previewId, index) {     //上传中
                        var form = data.form, files = data.files, extra = data.extra,
                        response = data.response, reader = data.reader;

                    }).on("fileuploaded", function (event, data, previewId, index) {    //一个文件上传成功
                        console.info(data.response);
                        if(data.response.split(".")[1] == 'docx'|| data.response.split(".")[1] == 'doc'|| data.response.split(".")[1] == 'xls'|| data.response.split(".")[1] == 'xlsx'|| data.response.split(".")[1] == 'pdf') {
                            var file_link = "<a href='/"+data.response+"'>文件</a>";
                            $("#files").append(file_link);
                            $("#files").show();
                        } else {
                            var img_link = "<div class='col-xs-5' height='200' width='"+width+"' ><img src='/image/"+data.response+"' width=80%  /></div>"
                            $("#images").append(img_link);
                            $("#images").show();
                        }


                    }).on('fileerror', function(event, data, msg) {  //一个文件上传失败

                    });
                }

            </script>
        </form>
    </body>
</html>