package com.zhilingsd.base.zk.confcenter.listener;

import com.zhilingsd.base.zk.zkclient.IZkDataListener;

public class DefaultDataListener implements IZkDataListener {

    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        System.out.println(dataPath + "====" + data);
    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        System.out.println(dataPath);
    }

}
