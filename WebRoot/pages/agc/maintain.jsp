<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type='text/javascript' src='fweb/core/fweb-core.js'></script>
<div class="page">
	<div class="pageContent">
		<form method="post" action="agc_editAgc.action"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, navTabAjaxDone);" name="agc"
			id="agc">
			<input name="agc.agcId" id="agc_agcId" type="hidden" />
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>评级机构中文名称：</label> <input name="agc.agcCnName" type="text"
						size="30" class="required" id="agc_agcCnName" readonly="readonly"
						value="${agc_agcCnName}" />
				</p>
				<p>
					<label>评级机构英文名称：</label> <input name="agc.agcEnName" type="text"
						size="30" class="required" id="agc_agcEnName" />
				</p>
				<p>
					<label>评级机构组织代码：</label> <input name="agc.agcOrgId"
						class="required" type="text" size="30" id="agc_agcOrgId" />
				</p>
				<p>
					<label>评级机构性质：</label> <select name="agc.agcNature"
						class="required combox" id="agc_agcNature" FWebSelectLabel="name"
						FWebSelectValue="code">
					</select>
				</p>
				<p>
					<label>成立日期：</label> <input type="text" name="agc.agcFoundingDate"
						class="date required" size="30" id="agc_agcFoundingDate"
						dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" /> <a
						class="inputDateButton" href="javascript:;">选择</a>
				</p>
				<p>
					<label>评级机构登记注册类型</label> <select name="agc.agcRegisType"
						class="required combox" id="agc_agcRegisType"
						FWebSelectLabel="name" FWebSelectValue="code">
					</select>
				</p>
				<p>
					<label>评级机构登记注册号</label> <input name="agc.agcRagisNum"
						class="required" type="text" size="30" id="agc_agcRagisNum" />
				</p>

				<p>
					<label>评级机构登记日期：</label> <input type="text" name="agc.agcRagisDate"
						class="date required" size="30" id="agc_agcRagisDate"
						dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" /> <a
						class="inputDateButton" href="javascript:;">选择</a>
				</p>

				<p>
					<label>评级机构营业执照到期日：</label> <input type="text"
						name="agc.agcBizLicsExpDate" class="date required" size="30"
						id="agc_agcBizLicsExpDate" dateFmt="yyyy-MM-dd HH:mm:ss"
						readonly="true" /> <a class="inputDateButton" href="javascript:;">选择</a>
				</p>
				<p>
					<label>注册地址：</label> <input name="agc.agcRegisAddr" type="text"
						size="30" class="required" id="agc_agcRegisAddr" />
				</p>

				<p>
					<label>行政区划：</label> <select class="combox" name="agc.agcMunicpPrv"
						ref="agc_agcMunicpCity" refUrl="dic_city.action?value={value}"
						id="agc_agcMunicpPrv" FWebSelectLabel="name"
						FWebSelectValue="code">
					</select> <select class="combox" name="agc.agcMunicpCity"
						id="agc_agcMunicpCity" ref="agc_agcMunicpDistr"
						refUrl="dic_area.action?value={value}" FWebSelectLabel="name"
						FWebSelectValue="code">
					</select> <select class="combox" name="agc.agcMunicpDistr"
						id="agc_agcMunicpDistr" FWebSelectLabel="name"
						FWebSelectValue="code">
					</select>
				</p>
				<p>
					<label>境外投资人登记注册号：</label> <input name="agc.agcAbrdInvstRegisNum"
						type="text" size="30" class="required"
						id="agc_agcAbrdInvstRegisNum" />
				</p>
				<p>
					<label>税务登记证号（国税）：</label> <input name="agc.agcNTaxCertiNum"
						type="text" size="30" class="required" id="agc_agcNTaxCertiNum" />
				</p>
				<p>
					<label>税务登记证号（地税）：</label> <input name="agc.agcLTaxCertiNum"
						type="text" size="30" class="required" id="agc_agcLTaxCertiNum" />
				</p>
				<p>
					<label>贷款卡编码：</label> <input name="agc.agcLoanCardCode" type="text"
						size="30" id="agc_agcLoanCardCode" />
				</p>
				<p>
					<label>违约率系统编码：</label> <input name="agc.agcDefaultRateCode"
						type="text" size="30" id="agc_agcDefaultRateCode" />
				</p>
				<p>
					<label>法人代表姓名：</label> <input name="agc.agcLegleRepr" type="text"
						size="30" class="required" id="agc_agcLegleRepr" />
				</p>
				<p>
					<label>法人代表身份标识：</label> <input name="agc.agcLegleReprIDType"
						type="text" size="30" id="agc_agcLegleReprIDType" />
				</p>
				<p>
					<label>法人代表证件号码：</label> <input name="agc.agcLegleReprIDNum"
						type="text" size="30" class="required" id="agc_agcLegleReprIDNum" />
				</p>
				<p>
					<label>评级机构资质：</label> <input name="agc.agcQualification"
						type="text" size="30" id="agc_agcQualification" />
				</p>
				<p>
					<label>业务种类：</label> <select name="agc.agcBizScope"
						class="required combox" FWebSelectLabel="name"
						FWebSelectValue="code" id="agc_agcBizScope">
					</select>
				</p>
				<p>
					<label>联系人姓名：</label> <input name="agc.agcContactName" type="text"
						size="30" id="agc_agcContactName" />
				</p>
				<p>
					<label>联系电话：</label> <input name="agc.agcContactNumber" type="text"
						size="30" id="agc_agcContactNumber" />
				</p>
				<p>
					<label>传真号码：</label> <input name="agc.agcFax" type="text" size="30"
						id="agc_agcFax" />
				</p>
				<p>
					<label>办公地址（总部）：</label> <input name="agc.agcHQAddress" type="text"
						size="30" id="agc_agcHQAddress" />
				</p>
				<p>
					<label>邮政编码（总部）：</label> <input name="agc.agcHQCode" type="text"
						size="30" id="agc_agcHQCode" />
				</p>
				<p>
					<label>通信地址：</label> <input name="agc.agcPostalAddr" type="text"
						size="30" id="agc_agcPostalAddr" />
				</p>
				<p>
					<label>电子邮件：</label> <input name="agc.agcEmail" type="text"
						size="30" id="agc_agcEmail" />
				</p>
				<p>
					<label>网址：</label> <input name="agc.agcWebsite" type="text"
						size="30" id="agc_agcWebsite" />
				</p>

				<p>
					<label>上市地：</label> <input name="agc.agcListedLoacation"
						type="text" size="30" id="agc_agcListedLoacation" />
				</p>

				<p>
					<label>注册资金（万元）：</label> <input name="agc.agcRegisCapital"
						class="digits" type="text" size="30" alt="请输入数字"
						id="agc_agcRegisCapital" />
				</p>

				<p>
					<label>营业场地面积：</label> <input name="agc.agcBizArea" type="text"
						size="30" id="agc_agcBizArea" />
				</p>
				<p>
					<label>从业人数：</label> <input name="agc.agcCrewSize" type="text"
						size="30" alt="请输入数字" class="digits" id="agc_agcCrewSize" />
				</p>
				<p>
					<label>评级人员数：</label> <input name="agc.agcRatingProfes" type="text"
						size="30" alt="请输入数字" class="digits" id="agc_agcRatingProfes" />
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