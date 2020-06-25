package com.frame.usercenter.component;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.frame.usercenter.main.UserCenterFragment;

public class UserCenterComponent implements IComponent {
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
                result.addData("UserCenterFragment()", new UserCenterFragment());
                CC.sendCCResult(cc.getCallId(), result);
                return false;
            default:
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }

    }
}
