package com.credit.util;

import org.apache.commons.lang.StringUtils;

import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultMessageWidget;

public class MessageUtils {

	public static final int ERROR = 1;
	public static final int SUCCESS = 2;

	public static MessageWidget build(String message, String navTabId, int type, boolean close) {
		DefaultMessageWidget defaultMessageWidget = new DefaultMessageWidget(message);
		if (StringUtils.isNotEmpty(navTabId)) {
			defaultMessageWidget.setNavTabId(navTabId);
		}
		switch (type) {
		case 1:
			defaultMessageWidget.fail();
			break;
		}
		if (close) {
			defaultMessageWidget.setCallbackType(DefaultMessageWidget.CALLBACKTYPE_CLOSECURRENT);
		}
		return defaultMessageWidget;
	}

}
