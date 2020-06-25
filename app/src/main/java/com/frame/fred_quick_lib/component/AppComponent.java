package com.frame.fred_quick_lib.component;

import android.app.Application;
import android.content.Context;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.frame.baselib.application.BaseApplication;

public class AppComponent implements IComponent {
    @Override
    public String getName() {
        return "AppComponent";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case "AppContext":
                Context context = BaseApplication.sAppContext;
                CC.sendCCResult(cc.getCallId(), CCResult.success("AppContext", context));
                return false;
            default:
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }
    }
}
