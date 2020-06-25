package com.frame.webview;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.frame.webview.main.WebViewFragment;


public class WebViewComponent implements IComponent {
    @Override
    public String getName() {
        return "usercenter";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case "UserCenterFragment":
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
