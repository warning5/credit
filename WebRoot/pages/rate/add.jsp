<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page">
	<div class="pageContent">
		<form method="post" action="ratingBiz_addRatingBiz.action"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);"
			name="ratingBiz">
			<input value="0" name="ratingBiz.state" type="hidden">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>评级机构代码：</label>
					<c:choose>
						<c:when test="${empty identity}">
							<input name="ratingBiz.agcId" type="hidden" />
							<input type="text" class="required" name="ratingBiz.agcCnName"
								readonly="readonly" />
							<a class="btnLook" href="agc_lookup.action"
								lookupGroup="ratingBiz" target="dialog" width="750" height="360"
								mask="true">评级机构列表</a>
						</c:when>
						<c:otherwise>
							<input name="ratingBiz.agcId" type="hidden" value="${identity}"/>
							<input name="agcName" class="required" type="text" size="30"
								readonly="readonly" value="${agc_agcCnName}" />
						</c:otherwise>
					</c:choose>
				</p>
				<p>
					<label>被评企业机构名称：</label> <input type="hidden"
						name="ratingBiz.firmId" /> <input type="text" class="required"
						name="ratingBiz.firmCnName" readonly="readonly" /> <a
						class="btnLook" href="firm_lookup.action" lookupGroup="ratingBiz"
						target="dialog" width="750" height="360" mask="true">企业列表</a>
				</p>
				<p>
					<label>主体评级业务类别：</label> <select name="ratingBiz.rateBizType"
						class="required combox" id="industryType" FWebSelectLabel="name"
						FWebSelectValue="code">
						<option value="">请选择</option>
					</select>
				</p>
				<p>
					<label>评级业务所属行业：</label> <select name="ratingBiz.rateHy"
						class="required combox">
						<option value="">请选择</option>
						<c:forEach var="item" items="${industryType}">
							<option value="${item.code}">${item.name}</option>
						</c:forEach>
					</select>
				</p>
				<p>
					<label>发生日期：</label> <input type="text" name="ratingBiz.rateDate"
						class="date required" size="30" dateFmt="yyyy-MM-dd HH:mm:ss"
						readonly="readonly" /> <a class="inputDateButton"
						href="javascript:;">选择</a>
				</p>
				<p>
					<label>合同金额：</label> <input name="ratingBiz.rateMoney"
						class="digits required" type="text" size="30" alt="请输入数字" />
				</p>
				<p>
					<label>区域：</label> <select class="combox required"
						name="ratingBiz.rateRegionProvince" ref="w_combox_city"
						refUrl="dic_city.action?value={value}" class="required">
						<option value="">所有省市</option>
						<c:forEach var="item" items="${province}">
							<option value="${item.code}">${item.name}</option>
						</c:forEach>
					</select> <select class="combox required" name="ratingBiz.rateRegionCity"
						id="w_combox_city" ref="w_combox_area"
						refUrl="dic_area.action?value={value}" class="required">
						<option value="">所有城市</option>
					</select> <select class="combox required" name="ratingBiz.rateRegionArea"
						id="w_combox_area" class="required">
						<option value="">所有区县</option>
					</select>

				</p>
				<p>
					<label>收费日期：</label> <input type="text"
						name="ratingBiz.rateChargeDate" class="date required" size="30"
						dateFmt="yyyy-MM-dd HH:mm:ss" readonly="readonly" /><a
						class="inputDateButton" href="javascript:;">选择</a>
				</p>
				<p>
					<label>相关第三方：</label> <input name="ratingBiz.rateThird" type="text"
						size="30" />
				</p>
				<p>
					<label>跟踪评级标识:</label> <input type="radio"
						name="ratingBiz.rateGzId" checked="checked" value="1" />跟踪 <input
						type="radio" name="ratingBiz.rateGzId" value="0" />不跟踪
				</p>
				<div class="unit">
					<label>收据扫描件：</label> <input type="text" size="60"
						name="ratingBiz.rateScanReceipt" id="ratingBiz_rateScanReceipt"
						readonly="readonly" /> <a class="btnAttach"
						href="pages/rate/upload.jsp?folder=rateScanReceipt"
						target="dialog" title="文件上传" width="600" height="200"
						lookupGroup="ratingBiz"><span>上传</span> </a>
				</div>
				<div class="unit">
					<label>跟踪评级安排扫描件：</label> <input type="text"
						name="ratingBiz.rateGzApScanFile" size="60"
						id="ratingBiz_rateGzApScanFile" readonly="readonly" /> <a
						class="btnAttach"
						href="pages/rate/upload.jsp?folder=rateGzApScanFile"
						target="dialog" title="文件上传" width="600" height="200"
						lookupGroup="ratingBiz"><span>上传</span> </a>
				</div>

				<div class="unit">
					<label>信用评级协议扫描件：</label> <input type="text" size="60"
						name="ratingBiz.rateScanFile" id="ratingBiz_rateScanFile"
						readonly="readonly" /> <a class="btnAttach"
						href="pages/rate/upload.jsp?folder=rateScanFile" target="dialog"
						title="文件上传" width="600" height="200" lookupGroup="ratingBiz"><span>上传</span>
					</a>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">提交</button>
							</div>
						</div></li>
					<li><div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div></li>
				</ul>
			</div>
		</form>
	</div>
</div>