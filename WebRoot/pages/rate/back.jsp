<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page">
	<div class="pageContent">
		<form method="post" action="${action}"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone)"
			name="handleBiz">
			<input type="hidden" value="${rateId}" name="rateId" /> <input
				type="hidden" value="${state}" name="state" />
			<div class="pageFormContent" layoutH="58">
				<label>退回原因</label>
				<textarea id="backReason" class="required" name="backReason"
					style="width: 666px; height: 215px;">${backReason}</textarea>
			</div>
			<div class="formBar">
				<ul>
					<c:if test="${action!=null}">
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">退回</button>
								</div>
							</div>
						</li>
					</c:if>
					<li>
						<div class="button">
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