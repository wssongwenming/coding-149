<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>训练准备</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <jsp:include page="/common/page.jsp"/>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>
<div class="page-header">
    <h1>
        照靶终端设备维护
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护靶纸拍照设备
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-xs-12">
        <div class="table-header">
            拍照设备列表&nbsp;&nbsp;
            <a class="green" href="#">
                <i class="ace-icon fa fa-plus-circle orange bigger-130  camera-add"></i>
            </a>
        </div>
        <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                <div class="row">
                    <div class="col-xs-6">
                        <div class="dataTables_length align-middle" id="dynamic-table_length" ><label>
                            展示
                            <select id="pageSize" name="dynamic-table_length" aria-controls="dynamic-table" class="form-control input-sm">
                                <option value="10">10</option>
                                <option value="25">25</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                            </select> 条记录 </label>

                        </div>
                    </div>
                    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                           aria-describedby="dynamic-table_info" style="font-size:14px">
                        <thead>
                        <tr role="row">
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                设备名称
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                MAC地址
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                IP地址
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                靶位编号
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                设备状态
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                备注
                            </th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                        </tr>
                        </thead>
                        <tbody id="cameraList"></tbody>
                    </table>
                    <div class="row" id="cameraPage">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="dialog-camera-form" style="display: none;">
    <form id="cameraForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="device_name">设备名称</label></td>
                <input type="hidden" name="id" id="cameraId"/>
                <td style="width: 200px;"><input type="text" name="name" id="device_name" value="照靶终端" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="device_mac">MAC地址</label></td>
                <td><input type="text" name="mac" id="device_mac" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="device_ip">IP地址</label></td>
                <td><input type="text" name="ip" id="device_ip" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>

            <tr>
                <td><label for="device_number">所处靶位编号</label></td>
                <td><input type="text" name="number" id="device_number" value=""  class="text ui-widget-content ui-corner-all"></td>
            </tr>

            <tr>
                <td><label for="device_status">设备状态</label></td>
                <td>
                    <select id="device_status" name="status" data-placeholder="选择状态" style="width: 150px;">
                        <option value="0">正常</option>
                        <option value="1">异常</option>
                    </select>
                </td>

            </tr>
            <tr>
                <td><label for="camera_memo">备注</label></td>
                <td><textarea name="memo" id="camera_memo" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<script id="cameraListTemplate" type="x-tmpl-mustache">
{{#cameraList}}
<tr role="row"  data-id="{{id}}"><!--even -->
    <td><a href="#" class="camera-edit" data-id="{{id}}">{{name}}</a></td>
    <td>{{mac}}</td>
    <td>{{ip}}</td>
    <td>{{number}}</td>
    <td>{{showStatus}}</td>
    <td>{{memo}}</td>
    <td>
        <div class="hidden-sm hidden-xs action-buttons">
            <a class="green camera-edit" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-pencil bigger-100"></i>
            </a>
            &nbsp;
            <a class="red camera-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                <i class="ace-icon fa fa-trash-o bigger-100"></i>
            </a>
         </div>
    </td>
</tr>
{{/cameraList}}
</script>
<script type="application/javascript">
    $(function() {

        var cameraMap = {}; // 存储map格式的训练计划信息
        var cameraListTemplate = $('#cameraListTemplate').html();
        Mustache.parse(cameraListTemplate);

        loadCameraList();

         $(".camera-add").click(function() {
            $("#dialog-camera-form").dialog({
                modal: true,
                minWidth: 450,
                title: "新增拍照设备",
                open: function(event, ui) {
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    $("#cameraForm")[0].reset();
                },
                buttons : {
                    "添加": function(e) {
                        e.preventDefault();
                        updateCamera(true, function (data) {
                            $("#dialog-camera-form").dialog("close");
                            loadCameraList();
                        }, function (data) {
                            showMessage("新增拍照设备", data.msg, false);
                        })
                    },
                    "取消": function () {
                        $("#dialog-camera-form").dialog("close");
                    }
                }
            });
        });

        function updateCamera(isCreate, successCallback, failCallback) {
            $.ajax({
                url: isCreate ? "/sys/camera/save.json" : "/sys/camera/update.json",
                data: $("#cameraForm").serializeArray(),
                type: 'POST',
                position: {
                    my: 'center',
                    at: 'center',
                    of: window,
                    collision: 'fit',
                    // ensure that the titlebar is never outside the document
                    using: function(pos) {
                        var topOffset = $(this).css(pos).offset().top;
                        if (topOffset < 0) {
                            $(this).css('top', pos.top - topOffset);
                        }
                    }
                },
                success: function(result) {
                    if (result.ret) {
                        if (successCallback) {
                            successCallback(result);
                        }
                    } else {
                        if (failCallback) {
                            failCallback(result);
                        }
                    }
                }
            })
        }
        function loadCameraList(){
            var pageSize = $("#pageSize").val();
            var url = "/sys/camera/page.json";
            var pageNo = $("#cameraPage .pageNo").val() || 1;
            $.ajax({
                url : url,
                data: {
                    pageSize: pageSize,
                    pageNo: pageNo
                },
                success: function (result) {
                    renderCameraListAndPage(result, url);
                }
            })
        }

        function renderCameraListAndPage(result, url) {
            if (result.ret) {
                if (result.data.total > 0){
                    var rendered = Mustache.render(cameraListTemplate, {
                        cameraList: result.data.data,
                        "showStatus": function() {
                            return this.status == 0 ? '正常' : '异常';
                        }
                    });
                    $("#cameraList").html(rendered);
                    bindCameraClick();
                    $.each(result.data.data, function(i, camera) {
                        cameraMap[camera.id] = camera;
                    })
                } else {
                    $("#cameraList").html('');
                }
                var pageSize = $("#pageSize").val();
                var pageNo = $("#cameraPage .pageNo").val() || 1;
                renderPage(url, result.data.total, pageNo, pageSize, result.data.total > 0 ? result.data.data.length : 0, "cameraPage", renderCameraListAndPage);
            } else {
                showMessage("获取照靶设备列表", result.msg, false);
            }
        }

        function bindCameraClick() {

            $(".camera-edit").click(function(e) {
                e.preventDefault();
                e.stopPropagation();
                var cameraId = $(this).attr("data-id");
                $("#dialog-camera-form").dialog({
                    modal: true,
                    minWidth: 450,
                    title: "编辑照靶设备",
                    open: function(event, ui) {
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        $("#cameraForm")[0].reset();
                        var targetCamera = cameraMap[cameraId];
                        if (targetCamera) {
                            $("#cameraId").val(targetCamera.id);
                            $("#device_name").val(targetCamera.name);
                            $("#device_mac").val(targetCamera.mac);
                            $("#device_ip").val(targetCamera.ip);
                            $("#device_status").val(targetCamera.status);
                            $("#device_number").val(targetCamera.number);
                            $("#device_memo").val(targetCamera.memo);
                        }
                    },
                    buttons : {
                        "更新": function(e) {
                            e.preventDefault();
                            updateCamera(false, function (data) {
                                $("#dialog-camera-form").dialog("close");
                                loadCameraList();
                            }, function (data) {
                                showMessage("更新照靶设备", data.msg, false);
                            })
                        },
                        "取消": function () {
                            $("#dialog-camera-form").dialog("close");
                        }
                    }
                });
            });

            $(".camera-delete").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var cameraId = $(this).attr("data-id");
                var cameraName = $(this).attr("data-name");
                if (confirm("确定要删除照靶设备[" + cameraName + "]吗?")) {
                    $.ajax({
                        url: "/sys/camera/delete.json",
                        data: {
                            id: cameraId
                        },
                        type: 'POST',
                        success: function (result) {
                            if (result.ret) {
                                showMessage("删除设备[" + cameraName + "]", "操作成功", true);
                                loadCameraList();
                            } else {
                                showMessage("删除设备[" + cameraName + "]", result.msg, false);
                            }
                        }
                    });
                }
            })
        }



        //时间空间Laydate
        lay('#version').html('-v'+ laydate.v);
        //执行一个laydate实例
        laydate.render({
            elem: '#training_dot' //指定元素
        });

    })
</script>
</body>
</html>