<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page">
	<div class="pageContent">
		<form method="post" action="firm_createFirm.action"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);" name="firm"
			id="firm">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>企业中文名称：</label> <input name="firm.firmCnName" type="text"
						size="30" class="required" />
				</p>
				<p>
					<label>企业英文英文名称：</label> <input name="firm.firmEnName" type="text"
						size="30" class="required" />
				</p>
				<p>
					<label>主体组织机构代码：</label> <input name="firm.firmOrgId"
						class="required" type="text" size="30" />
				</p>
				<p>
					<label>企业性质：</label> <select name="firm.firmNature"
						class="required combox" id="firm_firmNature">
						<c:forEach var="item" items="${firmNature}">
							<option value="${item.code}">${item.name}</option>
						</c:forEach>
					</select>
				</p>
				<p>
					<label>企业注册地址：</label> <input name="firm.firmRegisAddr" type="text"
						size="30" class="required" />
				</p>
				<p>
					<label>工商注册登记登记类型：</label> <select name="firm.firmRegisType"
						class="required combox">
						<option value="">请选择</option>
						<c:forEach var="item" items="${firmRegisType}">
							<option value="${item.code}">${item.name}</option>
						</c:forEach>
					</select>
				</p>
				<p>
					<label>行业分类 ：</label> <select name="firm.firmIndustryType"
						class="required combox" id="firm_firmIndustryType">
						<c:forEach var="item" items="${industryType}">
							<option value="${item.code}">${item.name}</option>
						</c:forEach>
					</select>
				</p>
				<p>
					<label>工商注册登记日期：</label> <input type="text"
						name="firm.firmRagisDate" class="date required" size="30" /> <a
						class="inputDateButton" href="javascript:;">选择</a>
				</p>
				<p>
					<label>营业执照到期日期：</label> <input type="text"
						name="firm.firmBizLicnsExpDate" class="date required" size="30" />
					<a class="inputDateButton" href="javascript:;">选择</a>
				</p>
				<p>
					<label>注册登记号：</label> <input name="firm.firmRagisNum" type="text"
						size="30" class="required" />
				</p>
				<p>
					<label>企业法人代表姓名：</label> <input name="firm.firmLegleRepre"
						type="text" size="30" class="required" />
				</p>
				<p>
					<label>税务登记证号（国税）：</label> <input name="firm.firmNTaxCertiNum"
						type="text" size="30" class="required" />
				</p>
				<p>
					<label>税务登记证号（地税）：</label> <input name="firm.firmLTaxCertiNum"
						type="text" size="30" class="required" />
				</p>
				<p>
					<label>行政区划：</label> <select class="required combox"
						name="firm.firmMunicpPrv" ref="w_combox_city"
						refUrl="dic_city.action?value={value}">
						<option value="">所有省市</option>
						<c:forEach var="item" items="${province}">
							<option value="${item.code}">${item.name}</option>
						</c:forEach>
					</select> <select class="required combox" name="firm.firmMunicpCity"
						id="w_combox_city" ref="w_combox_area"
						refUrl="dic_area.action?value={value}">
						<option value="">所有城市</option>
					</select> <select class="required combox" name="firm.firmMunicpDistr"
						id="w_combox_area">
						<option value="">所有区县</option>
					</select>

				</p>
				<p>
					<label>主体贷款卡编码：</label> <input name="firm.firmLoanCardCode"
						type="text" size="30" class="required" />
				</p>
				<p>
					<label>企业负责人人姓名：</label> <input name="firm.firmPrincipalName"
						type="text" size="30" class="required" />
				</p>
				<p>
					<label>联系电话：</label> <input name="firm.firmContactNumber"
						type="text" size="30" class="required" />
				</p>
				<p>
					<label>传真号码：</label> <input name="firm.firmFax" type="text"
						size="30" class="required" />
				</p>
				<p>
					<label>企业从业人数：</label> <input name="firm.firmCrewSize"
						class="digits required" type="text" size="30" alt="请输入数字" />
				</p>
				<p>
					<label>企业网址：</label> <input name="firm.firmWebsite" type="text"
						size="30" />
				</p>
				<p>
					<label>上市地：</label> <input name="firm.firmListedLoac" type="text"
						size="30" />
				</p>
				<p>
					<label>上市代码：</label> <input name="firm.firmListedCode" type="text"
						size="30" />
				</p>
				<p>
					<label>企业通信地址：</label> <input name="firm.firmPostalAddr"
						type="text" size="30" class="required" />
				</p>

				<p>
					<label>企业特征：</label> <input name="firm.firmPostalAddr" type="text"
						size="30" />
				</p>
				<p>
					<label>经营范围：</label> <input name="firm.firmBizScope" type="text"
						size="30" />

				</p>
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