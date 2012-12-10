<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type='text/javascript' src='fweb/core/fweb-core.js'></script>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);"
		action="agcFinacial.action" method="post" name="searchForm" id="searchForm">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<c:choose>
						<c:when test="${empty identity}">
							<td>&nbsp;&nbsp;<strong>机构名称：</strong>
								<input name="listAgcFinacial.agcCnName" type="text" id="listAgcFinacial_agcCnName" />
							</td>
							<td>&nbsp;&nbsp;<strong>报表年份：</strong>
								<input type="text" name="listAgcFinacial.agcRptYear"
								id="listAgcFinacial_agcRptYear" />
							</td>
						</c:when>
						<c:otherwise>
						<td>&nbsp;&nbsp;<strong>报表年份：</strong></td>
							<td><input type="text" name="listAgcFinacial.agcRptYear"
								id="listAgcFinacial_agcRptYear" />
							</td>
						</c:otherwise>
					</c:choose>
					<td>
						<div class="subBar" style="margin-top: 0px;">
							<ul style="float: left;">
								<li><div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div>
								</li>
								<li><a class="button" href="agcFinacial_showAdvanceSearch.action"
									target="dialog" mask="true" title="查询框" height="350"><span>高级检索</span> </a>
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
			<li><a class="add" href="agcFinacial_showAdd.action" target="dialog"
				title="添加" width="1190" height="500" mask="true" rel="dlg_addfinacial"><span>添加</span> </a>
			</li>
			<li><a class="edit" href="agcFinacial_showEdit.action?agcFinaceId={agcFinaceId}"
				target="dialog" warn="请选择一条记录" width="1200" height="500" mask="true"
				rel="dlg_editfinacial"><span>编辑</span>
			</a>
			</li>
			<li><a title="确定要删除吗？" warn="您没有选择任何项目！请选择列表中的数据，选中项将会以蓝色高亮显示！"
				target="selectedTodo" rel="agcFinacials" href="agcFinacial_deleteAgcFinacials.action"
				class="delete"><span>删除</span> </a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="113" id="agcFinacials">
		<thead>
			<tr>
				<th width="30">序号</th>
				<th width="30"><input type="checkbox" group="agcFinacials"
					class="checkboxCtrl">
				</th>
				<th width="100">机构名称</th>
				<th width="50">机构租住机构代码</th>
				<th width="120">年度报表年份</th>
				<th width="100">评级业务收入</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr FWebTable="head" target="agcFinaceId" rel="agcFinaceId">
				<td id="squence"></td>
				<td FWebTable="ids"><input id="agcFinaceId" name="agcFinacials"
					type="checkbox">
				</td>
				<td id="agcCnName"></td>
				<td id="agcOrgId"></td>
				<td id="agcRptYear"></td>
				<td id="agcRatIncm"></td>
				<td><a title="确定要删除财务数据吗?" target="ajaxTodo"
					href="agcFinacial_deleteAgcFinacials.action" class="btnDel">删除</a> <a title="编辑"
					target="dialog" href="agcFinacial_showEdit.action" class="btnEdit"
					width="1190" height="500" mask="true" rel="dlg_editfinacial">编辑</a>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>每页</span> 			
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="1">调整</option>
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<div FWebTable="datainfo"></div>
		</div>

		<div class="pagination" targetType="navTab" totalCount=""
			numPerPage="" pageNumShown="10" currentPage=""></div>
		<form id="pagerForm" method="post" action="agcFinacial.action"
			name="searchForm">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage" value="" />
		</form>
	</div>
</div>
