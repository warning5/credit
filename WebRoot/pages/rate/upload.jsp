<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<link rel="stylesheet" href="fweb/dwzres/upload/fineuploader.css">
<script src="fweb/dwzres/upload/fineuploader-3.0.min.js"></script>
<style>
.btn {
	-moz-border-bottom-colors: none;
	-moz-border-image: none;
	-moz-border-left-colors: none;
	-moz-border-right-colors: none;
	-moz-border-top-colors: none;
	background-color: #F5F5F5;
	background-image: -moz-linear-gradient(center top, #FFFFFF, #E6E6E6);
	background-repeat: repeat-x;
	border-color: #E6E6E6 #E6E6E6 #A2A2A2;
	border-radius: 4px 4px 4px 4px;
	border-style: solid;
	border-width: 1px;
	box-shadow: 0 1px 0 rgba(255, 255, 255, 0.2) inset, 0 1px 2px
		rgba(0, 0, 0, 0.05);
	color: #333333;
	cursor: pointer;
	display: inline-block;
	font-size: 14px;
	line-height: 20px;
	margin-bottom: 0;
	padding: 4px 14px;
	text-align: center;
	text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
	vertical-align: middle;
}

.btn-primary {
	background-color: #006DCC;
	background-image: -moz-linear-gradient(center top, #0088CC, #0044CC);
	background-repeat: repeat-x;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	color: #FFFFFF;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
}
</style>
<script>
	$(document).ready(function() {
		var manualuploader = new qq.FineUploader({
			element : $('#manual-fine-uploader')[0],
			request : {
				endpoint : 'upload.action',
				params : {
					'folder' : '${param.folder}'
				},
				forceMultipart : true
			},
			autoUpload : false,
			text : {
				uploadButton : '<i class="icon-plus icon-white"></i> 选择文件'
			},
			debug : false,
			callbacks : {
				onComplete : function(id, fileName, responseJSON) {
					if (responseJSON.success) {
						$('#manual-fine-uploader .qq-upload-button').hide();
						$('#triggerUpload').hide();
						$('#fileName').val(responseJSON.fileName);
						alertMsg.correct('上传成功！');
					}

				}
			},
			onError : function(id, fileName, reason) {
				alertMsg.error('上传失败！');
			}
		});

		$('#triggerUpload').click(function() {
			manualuploader.uploadStoredFiles();
		});
	});
</script>
<div class="page">
	<div class="pageContent">
		<form name="form" action="" method="POST"
			enctype="multipart/form-data" class="a_form">
			<div class="pageFormContent" layoutH="58">
				<div id="manual-fine-uploader"></div>
				<input id="fileName" type="hidden"/>
				<div id="triggerUpload" class="btn btn-primary"
					style="margin-top: 10px;">
					<i class="icon-upload icon-white"></i> 上传
				</div>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button"
								onclick="javascript:$.bringBack({id:'1', ${param.folder}:$('#fileName').val()})"
								href="javascript:void(0);">完成</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
		</form>
	</div>
</div>