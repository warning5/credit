<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
$(document).ready(function(){
		var tabid = navTab._getTabs().eq(navTab._currentIndex).attr("tabid");
		$("#add").attr("href",$("#add").attr("href")+"&tabId="+tabid);
		$("tr a").each(function(i){
			$(this).attr("href",$(this).attr("href")+"&tabId="+tabid);
		});
	});
</script>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="beforeHandleBiz_beforeApprovalList.action"
		method="post" name="searchForm" id="searchForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>&nbsp;&nbsp;
						企业名称：
						<input name="biz.firmCnName" id="biz_firmCnName" type="text" value="${firmCnName}"/>
					</td>
					<td>&nbsp;&nbsp;
						发生日期：
						<fmt:formatDate value="${rateDate}" var="date" pattern="yyyy-MM-dd"/>
						<input name="biz.rateDate" id="biz_rateDate" class="date" type="text" readonly="readonly" 
						value="${date}"/>
					</td>
					<td>&nbsp;&nbsp;
						是否跟踪：
						<input type="radio" name="biz.rateGzId" value="1" <c:if test="${rateGzId==1}">checked="checked"</c:if>/>跟踪 
						<input type="radio" name="biz.rateGzId" value="0" <c:if test="${rateGzId==0}">checked="checked"</c:if>/>不跟踪
					</td>
					<td>
						<div class="subBar">
							<ul style="float: left;">
								<li><div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div>
								</li>
							</ul>
						</div></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" id="add"
				href="afterRatingBiz_showAddAfter.action?bizIds={bizIds}"
				target="dialog" warn="请选择一条记录" width="850" height="580" mask="true"
				rel="dlg_addReport"><span>添加后报备业务</span> </a></li>
			<li><a class="detail"
				href="ratingBiz_brower.action?rateId={rateId}" target="dialog"
				warn="请选择一条记录" width="850" height="365" rel="rateBizs" mask="true"><span>查看</span>
			</a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="112" id="rateBizs">
		<thead>
			<tr>
				<th width="30">序号</th>
				<th width="30"><input type="checkbox" group="rateBizs"
					class="checkboxCtrl"></th>
				<th width="100">被评企业组织机构代码</th>
				<th width="100">被评级企业名称</th>
				<th width="50">所属行业</th>
				<th width="100">所属区域</th>
				<th width="100">收费金额</th>
				<th width="100">业务发生日期</th>
				<th width="100">跟踪评级</th>
				<th width="100">批准日期</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(data)==0}">
				<tr>
					<td><center>抱歉，没有任何记录。</center>
					</td>
				</tr>
			</c:if>
			<c:forEach var="dataItem" items="${data}">
				<tr target="bizIds" rel="${dataItem.rateId}:${dataItem.firmId}:${dataItem.agcId}">
					<td>${dataItem.squence}</td>
					<td><input id="${dataItem.rateId}" name="ids"
						value="${dataItem.rateId}" type="checkbox">
					</td>
					<td>${dataItem.firmOrgId}</td>
					<td>${dataItem.firmCnName}</td>
					<td>${dataItem.rateHy}</td>
					<td>${dataItem.rateRegion}</td>
					<td>${dataItem.rateMoney}</td>
					<td>${dataItem.rateDate}</td>
					<td>${dataItem.rateGz}</td>
					<td>${dataItem.handledRatingBizTime}</td>
					<td><a title="查看" target="dialog"
						href="ratingBiz_brower.action?rateId=${dataItem.rateId}"
						class="btnInfo" width="850" height="365">查看</a> <c:if
							test="${empty dataItem.reportId}">
							<a title="添加后报备业务" target="dialog" href="afterRatingBiz_showAddAfter.action?bizIds=${dataItem.rateId}:${dataItem.firmId}:${dataItem.agcId}"
								width="850" height="580" class="btnAdd" mask="true" rel="dlg_addReport">添加后报备</a>
						</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>每页</span> <select class="combox" name="numPerPage"
				onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="1">调整</option>
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共${paging.allCount}条记录，每页${paging.numPerPage}条，当前第${paging.currentPage}/${paging.pageCount}页</span>
		</div>

		<div class="pagination" targetType="navTab"
			totalCount="${paging.allCount}" numPerPage="${paging.numPerPage}"
			pageNumShown="10" currentPage="${paging.currentPage}"></div>
		<form id="pagerForm" method="post"
			action="beforeHandleBiz_beforeApprovalList.action"
			name="searchForm">
			<input type="hidden" name="pageNum" value="1" /> 
			<input type="hidden" name="numPerPage" value="${paging.numPerPage}" />
		</form>
	</div>
</div>
