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

    <script type="text/javascript" src="/js/back/bannerImg/bannerImgDetailPage.js"></script>
</head>
<body>
    <div id="dataInfo">
        <div id="bannerImgDefine">
            <div class="modal-header">
                <h4 class="modal-title">
                    Banner图页
                </h4>
            </div>
            <div id="bannerImg-body" class="modal-body">
                <span name = "id">
                    <input type = 'hidden' id = "id" />
                </span>
                <span name = "imgUrl">
                    <input type = 'hidden' id = "imgUrl" />
                </span>
                <div class="row">
                    <div class="col-xs-5">
                        轮播顺序:
                        <span name = "bannerOrder">
                            <input class="form-control" id = "bannerOrder">
                        </span>
                    </div>
                    <div class="col-xs-5">
                        banner类型:
                        <span name = "bannerType">
                            <select id="bannerType" class="form-control">
                                <option value="1">游戏房间</option>
                                <option value="0">娃娃机房间</option>
                            </select>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-xs-5">
                        点击url:
                        <span name = "clickUrl">
                            <input class="form-control" id = "clickUrl">
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <form id="uploadBannerImgForm" method="post" enctype="multipart/form-data">
                        <div class="col-xs-5">
                            Banner图片上传:
                            <input id="uploadBannerImg" name="file" multiple type="file" data-show-caption="true">
                        </div>

                        <script type="text/javascript">

                                // 上传图片处理controller
                                var url = "/toiletCat/fileUpload/bannerImgUpload.action";

                                // input file id
                                var inputId = "uploadBannerImg";

                                // 要保存返回值的元素id
                                var imgUrl = "imgUrl";

                                // 类型
                                var type = "normal";

                                // 玩具主图片上传
                                imgUpload(inputId, url, imgUrl, type);
                        </script>
                    </form>
                </div>
            </div>
        </div>
        <div class="text-center">
            <button type="button" class="btn btn-danger btn-lg" onclick="cancelThis()">取 消</button>
            &nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-primary btn-lg" onclick="updateOrSaveBannerImg()">
                提 交
            </button>
        </div>
    </div>
</body>
</html>
