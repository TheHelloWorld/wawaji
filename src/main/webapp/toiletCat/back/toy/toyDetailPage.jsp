<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="zikun.liu">

    <title>玩具页</title>

    <%@ include file="../../../include/header.jsp"%>

    <link href="/dist/css/fileInput/fileinput.css" media="all"rel="stylesheet" type="text/css" />

    <script src="/dist/js/fileInput/fileinput.js" type="text/javascript"></script>

    <script src="/dist/js/fileInput/locales/zh.js" type="text/javascript"></script>

    <script type="text/javascript" src="/js/back/toy/toyDetailPage.js"></script>
</head>
<body>
    <div id="dataInfo">
        <div id="toyDefine">
            <div class="modal-header">
                <h4 class="modal-title">
                    玩具页
                </h4>
            </div>
            <div id="toy-body" class="modal-body">
                <span name = "id">
                    <input type = 'hidden' id = "id" />
                </span>
                <span name = "toyImg">
                    <input type = 'hidden' id = "toyImg" />
                </span>
                <span name = "toyDesc">
                    <input type="hidden" id="toyDesc" value="null" />
                </span>
                <div class="row">
                    <div class="col-xs-5">
                        玩具编号:
                        <span name = "toyNo">
                            <input class="form-control" id = "toyNo">
                        </span>
                    </div>
                    <div class="col-xs-5">
                        是否可用:
                        <span name = "currentState">
                            <select id="currentState" class="form-control">
                                <option value="1">可用</option>
                                <option value="0">禁用</option>
                            </select>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-xs-5">
                        娃娃名称:
                        <span name = "toyName">
                            <input class="form-control" id = "toyName">
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-xs-5">
                        娃娃可兑换游戏币数:
                        <span name = "toyForCoin">
                            <input class="form-control" id = "toyForCoin">
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-xs-4">
                        娃娃当前游戏币数:
                        <span name = "toyNowCoin">
                           <input class="form-control" id="toyNowCoin" />
                        </span>
                    </div>
                    <div class="col-xs-4">
                        娃娃原本游戏币数:
                        <span name = "toyOriginCoin">
                            <input class="form-control" id="toyOriginCoin" />
                        </span>
                    </div>
                    <div class="col-xs-4">
                        娃娃成本:
                        <span name = "toyCost">
                            <input class="form-control" id="toyCost" />
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <form id="uploadToyImgForm" method="post" enctype="multipart/form-data">
                        <div class="col-xs-5">
                            玩具主图片上传:
                            <input id="uploadToyImg" name="file" multiple type="file" data-show-caption="true">
                        </div>

                        <script type="text/javascript">

                                var url = "/toiletCat/fileUpload/uploadFile.action";
                                var inputId = "uploadToyImg";
                                // 玩具主图片上传
                                toyImgUpload(inputId,url);

                                // 玩具主图片上传
                                function toyImgUpload(inputId, uploadUrl) {
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
                                        var form = data.form;
                                        var files = data.files;
                                        var extra = data.extra;
                                        var response = data.response;
                                        var reader = data.reader;

                                    }).on("fileuploaded", function (event, data, previewId, index) {    //一个文件上传成功
                                        console.info(data);
                                        var res = data.response;
                                        if(typeof(res) == "string") {
                                            res = eval("("+res+")");
                                        }
                                        var fileName = res["fileName"];
                                        $("#toyImg").val(fileName);


                                    }).on('fileerror', function(event, data, msg) {  //一个文件上传失败
                                        alert("上传图片失败")
                                    });
                                }
                        </script>
                    </form>
                </div>
                <div class="row">
                    <form id="form" method="post" enctype="multipart/form-data">
                        <div class="col-xs-5">
                            玩具描述图片上传:
                            <input id="uploadToyDescImg" name="file" multiple type="file" data-show-caption="true">
                        </div>

                        <script type="text/javascript">

                            var url = "/toiletCat/fileUpload/uploadFile.action";
                            var inputId = "uploadToyDescImg";
                            // 玩具描述图片上传
                            toyDescUpload(inputId,url);

                            // 玩具描述图片上传
                            function toyDescUpload(inputId, uploadUrl) {
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
                                    var form = data.form;
                                    var files = data.files;
                                    var extra = data.extra;
                                    var response = data.response;
                                    var reader = data.reader;

                                }).on("fileuploaded", function (event, data, previewId, index) {    //一个文件上传成功
                                    console.info(data);
                                    var res = data.response;
                                    if(typeof(res) == "string") {
                                        res = eval("("+res+")");
                                    }
                                    var fileName = res["fileName"];

                                    var toyDesc = $("#toyDesc").val();

                                    if(toyDesc == "null") {
                                        toyDesc = "";
                                    }

                                    toyDesc += fileName + ";";

                                    $("#toyDesc").val(toyDesc);


                                }).on('fileerror', function(event, data, msg) {  //一个文件上传失败
                                    alert("上传图片失败")
                                });
                            }
                        </script>
                    </form>
                </div>
            </div>
        </div>
        <div class="text-center">
            <button type="button" class="btn btn-danger btn-lg" onclick="cancelThis()">取 消</button>
            &nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-primary btn-lg" onclick="updateOrSaveToy()">
                提 交
            </button>
        </div>
    </div>
</body>
</html>
