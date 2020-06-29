package com.frame.webview.main.webview.config.component;

import android.webkit.WebView;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.frame.webview.main.webview.config.web.WebViewFragment;

public class UserCenterComponent implements IComponent {
    @Override
    public String getName() {
        return "webview";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case "BaseWebViewFragment":
                CCResult result = new CCResult();
                result.addData("fragment", new WebViewFragment());
                CC.sendCCResult(cc.getCallId(), result);
                return false;
            default:
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }

    }
}
