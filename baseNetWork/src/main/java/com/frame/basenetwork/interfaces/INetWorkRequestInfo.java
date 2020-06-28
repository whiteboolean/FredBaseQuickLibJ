package com.frame.basenetwork.interfaces;

import java.util.HashMap;

public interface INetWorkRequestInfo {

    HashMap<String,String> getRequestHeaderMap();

    boolean isDebug();
}
