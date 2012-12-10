<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="pageContent">
	<form method="post" action="agc.action" class="pageForm"
		onsubmit="return navTabSearch(this);" name="searchForm"
		id="searchForm">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>名称：</label> <input type="text" size="30"
					name="listAgcBase.agcCnName" /> <span class="inputInfo">关键字或全称</span>
			</div>
			<div class="unit">
				<label>评级机构性质：</label> <select name="listAgcBase.agcNature"
					class="required combox">
					<option value="-1">请选择</option>
					<c:forEach var="item" items="${agcNature}">
						<option value="${item.code}">${item.name}</option>
					</c:forEach>
				</select> <span class="inputInfo">关键字或全称</span>
			</div>
			<div class="unit">
				<label>注册地址：</label> <input type="text" size="30"
					name="listAgcBase.agcRegisAddr" class="lettersonly" /> <span
					class="inputInfo">关键字或全称</span>
			</div>
			<div class="unit">
				<label>行政区划：</label> <select class="combox"
					name="listAgcBase.agcMunicpPrv" ref="w_combox_city"
					refUrl="dic_city.action?value={value}&all=1">
					<option value="-1">所有省市</option>
					<c:forEach var="item" items="${province}">
						<option value="${item.code}">${item.name}</option>
					</c:forEach>
				</select> <select class="combox" name="listAgcBase.agcMunicpCity"
					id="w_combox_city" ref="w_combox_area"
					refUrl="dic_area.action?value={value}&all=1">
					<option value="-1">所有城市</option>
				</select> <select class="combox" name="listAgcBase.agcMunicpDistr"
					id="w_combox_area">
					<option value="-1">所有区县</option>
				</select>
			</div>
			<div class="unit">
				<label>联系电话：</label> <input type="text" size="30"
					name="listAgcBase.agcContactNumber" /> <span class="inputInfo">关键字或全称</span>
			</div>
			<div class="unit">
				<label>组织机构代码：</label> <input type="text" size="30"
					name="listAgcBase.agcOrgId" /> <span class="inputInfo">完整代码</span>
			</div>
			<div class="unit">
				<label>法人姓名：</label> <input type="text" size="30"
					name="listAgcBase.agcLegleRepr" /> <span class="inputInfo">关键字或全名</span>
			</div>
			<div class="unit">
				<label>专业评级人员数：</label> <input type="text" size="30"
					name="listAgcBase.agcRatingProfes" /> <span class="inputInfo">数字</span>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">开始检索</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="reset">清空重输</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
