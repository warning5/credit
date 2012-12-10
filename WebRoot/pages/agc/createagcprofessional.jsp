<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page">
	<div class="pageContent">
		<form method="post" action="agcPro_createAgcProfessional.action"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);"
			name="agcProfessional">
			<div class="pageFormContent" layoutH="56">
			
				<p>
					<label>评级机构名称：</label>
					<input type="hidden" name="agcProfessional.agcId" value="${identity}"/> 
					<c:choose>
						<c:when test="${empty identity}">
							<input type="text" class="required"	name="agcProfessional.agcCnName" readonly="readonly" /> <a
								class="btnLook" href="agc_lookup.action" lookupGroup="agcProfessional"
								target="dialog" width="750" height="360" mask="true">查找带回</a>
						</c:when>
						<c:otherwise>
							<input type="text" class="required"	name="agcProfessional.agcCnName" readonly="readonly" value="${agcProfessional_agcCnName}"/>
						</c:otherwise>
					</c:choose>
				</p>
				
				<p>
					<label>专业评级人员姓名：</label> <input name="agcProfessional.agcProName"
						type="text" size="30" class="required" />
				</p>
				<p>
					<label>证件类型 ：</label> <select name="agcProfessional.agcProIDType"
						class="required combox">
						<option value="">请选择</option>
						<c:forEach var="item" items="${agcProIDType}">
							<option value="${item.code}">${item.name}</option>
						</c:forEach>
					</select>
				</p>
				<p>
					<label>证件号码 ：</label> <input name="agcProfessional.agcProIDNum"
						size="30" alt="请输入数字" class="digits" type="text" />
				</p>
				<p>
					<label> 专业评级人员性别 ：</label> <select
						name="agcProfessional.agcProGender" class="required combox">
						<option value="0">男</option>
						<option value="1">女</option>
					</select>
				</p>
				<p>
					<label>出生日期：</label> <input
						name="agcProfessional.agcProBirthday" type="text" size="30"
						class="required date" /><a
						class="inputDateButton" href="javascript:;">选择</a>
				</p>
				<p>
					<label> 最高学历：</label> <select
						name="agcProfessional.agcProHighestEdu" class="required combox">
						<option value="">请选择</option>
						<c:forEach var="item" items="${agcProHighestEdu}">
							<option value="${item.code}">${item.name}</option>
						</c:forEach>
					</select>
				</p>
				<p>
					<label>从业年限：</label> <input
						name="agcProfessional.agcProWorkingLife" type="text" size="30"
						alt="请输入数字" class="digits required" />
				</p>
				<p>
					<label>信用评级资格类别 ：</label> <select
						name="agcProfessional.agcProCertiType" class="required combox">
						<option value="">请选择</option>
						<c:forEach var="item" items="${agcProCertiType}">
							<option value="${item.code}">${item.name}</option>
						</c:forEach>
					</select>
				</p>
				<p>
					<label>资格证书编号 ：</label> <input
						name="agcProfessional.agcProCertiNum" type="text" size="30"
						class="required" />
				</p>

				<p>
					<label>资格证书颁发机构 ：</label> <input
						name="agcProfessional.agcProCertiGrantAuth" type="text" size="30"
						class="required" />
				</p>

				<p>
					<label>资格证书颁发时间：</label> <input
						name="agcProfessional.agcProCertiObtDate" type="text" size="30"
						class="required date" /><a
						class="inputDateButton" href="javascript:;">选择</a>
				</p>

				<p>
					<label>资格证书当前状态：</label> <select
						name="agcProfessional.agcProCertiStatus" class="required combox">
						<option value="">请选择</option>
						<c:forEach var="item" items="${agcProCertiStatus}">
							<option value="${item.code}">${item.name}</option>
						</c:forEach>
					</select>
				</p>

				<div class="unit">
					<label>其它技能及获得时间 :</label>
					<textarea name="agcProfessional.agcProOtherCertis" cols="155" rows="5"></textarea>
				</div>

				<div class="unit">
					<label> 个人奖惩情况：</label>
					<textarea name="agcProfessional.agcProRewdPunish" cols="155" rows="5"></textarea>
				</div>

				<div class="unit">
					<label> 工作简历：</label>
					<textarea name="agcProfessional.agcProResume" cols="155" rows="5"></textarea>
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