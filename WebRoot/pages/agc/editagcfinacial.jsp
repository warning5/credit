<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type='text/javascript' src='fweb/core/fweb-core.js'></script>
<div class="page">
	<div class="pageContent">
		<form method="post" action="agcFinacial_editAgcFinacial.action"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);"
			name="agcFinacial" id="agcFinacial">
			<div class="pageFormContent" layoutH="56">
				<input type="hidden" name="agcFinacial.agcFinaceId" id="agcFinacial_agcFinaceId"/>
				<p>
					<label>评级机构名称：</label>
					<input type="hidden" name="agcFinacial.agcId" id="agcFinacial_agcId"/>
					<c:choose>
						<c:when test="${empty identity}">
							<input type="text" class="required"	name="agcFinacial.agcCnName" readonly="readonly" id="agcFinacial_agcCnName"/>
							<a class="btnLook" href="agc_lookup.action" lookupGroup="agcFinacial" target="dialog" 
							width="750" height="360" mask="true">查找带回</a>
						</c:when>
						<c:otherwise>
							<input type="text" class="required"	name="agcFinacial.agcCnName" id="agcFinacial_agcCnName" readonly="readonly"/>
						</c:otherwise>
					</c:choose>
				</p>
				<p>
					<label>评级机构组织机构代码：</label> <input name="agcFinacial.agcOrgId"
						type="text" size="30" class="required" id="agcFinacial_agcOrgId" />
				</p>
				<p>
					<label>年度报表年份：</label> <input name="agcFinacial.agcRptYear"
						type="text" size="30" class="required" id="agcFinacial_agcRptYear" />
				</p>
				<p>
					<label>评级机构资产总计：</label> <input name="agcFinacial.agcTotalAssets"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcTotalAssets" />
				</p>
				<p>
					<label>货币资金：</label> <input name="agcFinacial.agcMoneyFunds"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcMoneyFunds" />
				</p>
				<p>
					<label>固定资产：</label> <input name="agcFinacial.agcFixedAssets"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcFixedAssets" />
				</p>
				<p>
					<label>评级机构所有者权益：</label> <input name="agcFinacial.agcOwnerRights"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcOwnerRights" />
				</p>
				<p>
					<label>未分配利润：</label> <input name="agcFinacial.agcUndisProfits"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcUndisProfits" />
				</p>
				<p>
					<label>评级机构业务总收入：</label> <input name="agcFinacial.agcTotalRatIncm"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcTotalRatIncm" />
				</p>
				<p>
					<label>评级业务收入：</label> <input name="agcFinacial.agcRatIncm"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcRatIncm" />
				</p>

				<p>
					<label> 借款企业评级业务收入：</label> <input
						name="agcFinacial.agcBorVentRatIncm" class="required digits"
						type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcBorVentRatIncm" />
				</p>

				<p>
					<label>担保机构评级业务收入：</label> <input
						name="agcFinacial.agcGuaraRatIncm" class="required digits"
						type="text" size="30" alt="请输入数字" id="agcFinacial_agcGuaraRatIncm" />
				</p>

				<p>
					<label>金融机构评级业务收入：</label> <input
						name="agcFinacial.agcFinancRatIncm" class="required digits"
						type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcFinancRatIncm" />
				</p>

				<p>
					<label>长期债评级业务收入 ：</label> <input
						name="agcFinacial.agcTBoundsRatIncm" class="required digits"
						type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcTBoundsRatIncm" />
				</p>

				<p>
					<label>结构融资债评级业务收入 ：</label> <input
						name="agcFinacial.agcStrctFinancRatIncm" class="required digits"
						type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcStrctFinancRatIncm" />
				</p>

				<p>
					<label>短期债评级业务收入 ：</label> <input name="agcFinacial.agcSBndRatIncm"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcSBndRatIncm" />
				</p>

				<p>
					<label> 主营业务成本 ：</label> <input name="agcFinacial.agcMainBizCost"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcMainBizCost" />
				</p>

				<p>
					<label>主营业务利润 ：</label> <input name="agcFinacial.agcMainBizProfit"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcMainBizProfit" />
				</p>

				<p>
					<label>财务费用 ：</label> <input name="agcFinacial.agcFinaExpense"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcFinaExpense" />
				</p>

				<p>
					<label>管理费用 ：</label> <input name="agcFinacial.agcAdmExpense"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcAdmExpense" />
				</p>

				<p>
					<label>营业税费及附加 ：</label> <input name="agcFinacial.agcBizTaxAndAnn"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcBizTaxAndAnn" />
				</p>

				<p>
					<label>评级机构利润总额 ：</label> <input name="agcFinacial.agcTotalProfit"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcTotalProfit" />
				</p>

				<p>
					<label> 所得税 ：</label> <input name="agcFinacial.agcIncmTax"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcIncmTax" />
				</p>
				<p>
					<label>净利润 ：</label> <input name="agcFinacial.agcNetProfit"
						class="required digits" type="text" size="30" alt="请输入数字"
						id="agcFinacial_agcNetProfit" />
				</p>
				<div class="unit">
					<label>会计师事务所名称 ：</label> <input name="agcFinacial.agcAccFirmName"
						class="required" type="text" size="30" id="agcFinacial_agcAccFirmName"/>
				</div>
				<div class="unit">
					<label>会计师事务所审计意见：</label>
					<textarea id="agcFinacial_agcAccFirmOpin" name="agcFinacial.agcAccFirmOpin" class="required" cols="155" rows="7"></textarea>
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
${data}
