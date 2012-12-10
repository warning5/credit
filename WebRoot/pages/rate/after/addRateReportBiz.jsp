<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type='text/javascript' src='fweb/core/fweb-core.js'></script>
<div class="page">
	<div class="pageContent">
		<form method="post" action="afterRatingBiz_addRatingReportBiz.action"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);"
			name="ratingReportBiz">
			<input type="hidden" value="${rateId}" name="ratingReportBiz.rateId" />
			<input type="hidden" value="${tabId}" name="ratingReportBiz.tabId" />
			<input type="hidden" value="${firmId}" name="ratingReportBiz.firmId" />
			<input type="hidden" value="${agcId}" name="ratingReportBiz.agcId" />
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>评级报告编号：</label> <input name="ratingReportBiz.reportId"
						type="text" size="30" class="required" />
				</p>
				<p>
					<label>信用等级：</label> <select name="ratingReportBiz.xyLevel"
						class="required combox">
						<option value="">请选择</option>
						<c:forEach var="item" items="${rateLevel}">
							<option value="${item.code}">${item.name}</option>
						</c:forEach>
					</select>
				</p>
				<p>
					<label>参考授信建议：</label> <input name="ratingReportBiz.xySuggestion"
						type="text" size="30" class="required" />
				</p>
				<p>
					<label>信用等级展望：</label> <input name="ratingReportBiz.xyProspect"
						type="text" size="30" class="required" />
				</p>
				<p>
					<label>分析师标识号：</label> 
					<input type="hidden" name="ratingReportBiz.analystNum" class="required"/>
					<input type="text" size="25" class="required"	name="ratingReportBiz.agcProName" readonly="readonly"/>
					<a class="btnLook" href="agcPro_lookup.action" lookupGroup="ratingReportBiz"
								target="dialog" width="750" height="360" mask="true">查找带回</a>
						
				</p>
				<p>
					<label>信用等级出具日期：</label> <input name="ratingReportBiz.xyOutDate"
						class="date required" type="text" size="30" /> <a
						class="inputDateButton" href="javascript:;">选择</a>
				</p>
				<p>
					<label>信用等级生效日期：</label> <input type="text"
						name="ratingReportBiz.xyStartDate" class="date required" size="30" /><a
						class="inputDateButton" href="javascript:;">选择</a>
				</p>
				<p>
					<label>信用等级终止日期：</label> <input name="ratingReportBiz.xyEndDate"
						type="text" class="date required" size="30" /><a
						class="inputDateButton" href="javascript:;">选择</a>
				</p>
				<p>
					<label>信用等级撤销日期:</label> <input type="text"
						name="ratingReportBiz.xyRevokeDate" class="date required"
						size="30" /><a class="inputDateButton" href="javascript:;">选择</a>
				</p>
				<p>
					<label>信用等级变更生效日期：</label> <input type="text" size="30"
						class="date required" name="ratingReportBiz.xyChangeDate" /> <a
						class="inputDateButton" href="javascript:;">选择</a>
				</p>
				<p>
					<label>被评级企业资产总计：</label> <input
						name="ratingReportBiz.entAssetsTotal" class="digits required"
						type="text" size="30" alt="请输入数字" />
				</p>
				<p>
					<label>被评级企业贷款余额：</label> <input type="text" size="30" alt="请输入数字"
						name="ratingReportBiz.entLoanBalance" class="digits required" />
				</p>
				<div class="unit">
					<label>评级报告摘要：</label>
					<textarea name="ratingReportBiz.reportDigestSummary" rows="7"
						cols="93"></textarea>
				</div>
				<div class="unit">
					<label>评级报告风险点：</label>
					<textarea name="ratingReportBiz.reportDigestRisk" rows="7"
						cols="93"></textarea>
				</div>
				<div class="unit">
					<label>评级报告扫描件：</label> <input type="text" size="80"
						name="ratingReportBiz.reportScanFile"
						id="ratingReportBiz_reportScanFile" readonly="readonly" /> <a
						class="btnAttach"
						href="pages/rate/upload.jsp?folder=reportScanFile" target="dialog"
						title="文件上传" width="600" height="200"
						lookupGroup="ratingReportBiz"><span>上传</span> </a>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">提交</button>
							</div>
						</div>
					</li>
					<li><div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>