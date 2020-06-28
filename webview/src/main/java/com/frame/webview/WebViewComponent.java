package com.frame.webview;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;


public class WebViewComponent implements IComponent {
    @Override
    public String getName() {
        return "webviewComponent";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case "webViewFragment":
                CCResult result = new CCResult();
                result.addData("UserCenterFragment()", new WebViewFragment());
                CC.sendCCResult(cc.getCallId(), result);
                return false;
            default:
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }

    }
}
