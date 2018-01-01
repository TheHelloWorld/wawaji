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

    <script src="/common/js/fileUpload.js" type="text/javascript"></script>

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
                <span name = "toyRoomImg">
                    <input type="hidden" id="toyRoomImg" />
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

                            // 上传图片处理controller
                            var url = "/toiletCat/fileUpload/toyImgUpload.action";

                            // input file id
                            var inputId = "uploadToyImg";

                            // 要保存返回值的元素id
                            var toyImg = "toyImg";

                            // 类型
                            var type = "normal";

                            // 玩具主图片上传
                            imgUpload(inputId, url, toyImg, type);

                        </script>
                    </form>
                </div>
                <div class="row">
                    <form id="uploadToyRoomImgForm" method="post" enctype="multipart/form-data">
                        <div class="col-xs-5">
                            玩具房间图片上传:
                            <input id="uploadToyRoomImg" name="file" multiple type="file" data-show-caption="true">
                        </div>

                        <script type="text/javascript">

                            // 上传图片处理controller
                            var url = "/toiletCat/fileUpload/toyImgUpload.action";

                            // input file id
                            var inputId = "uploadToyRoomImg";

                            // 要保存返回值的元素id
                            var toyRoomImg = "toyRoomImg";

                            // 类型
                            var type = "normal";

                            // 玩具房间图片上传
                            imgUpload(inputId, url, toyRoomImg, type);

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

                            // 上传图片处理controller
                            var url = "/toiletCat/fileUpload/toyImgUpload.action";

                            // input file id
                            var inputId = "uploadToyDescImg";

                            // 要保存返回值的元素id
                            var toyDesc = "toyDesc";

                            // 类型
                            var type = "add";

                            // 玩具描述图片上传
                            imgUpload(inputId, url, toyDesc, type);

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
