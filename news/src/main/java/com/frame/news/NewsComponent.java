package com.frame.news;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;

public class NewsComponent implements IComponent {

    private static final String name = "news";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getComponentName();
        switch (actionName) {
            case "BlankFragment":
                CCResult ccResult = new CCResult();
//                ccResult.addData("fragment", new NewsFragment());
                CC.sendCCResult(cc.getCallId(), ccResult);
                return false;
            default:
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }
    }
}
