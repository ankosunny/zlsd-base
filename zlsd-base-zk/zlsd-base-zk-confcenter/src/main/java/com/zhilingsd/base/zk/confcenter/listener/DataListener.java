package com.zhilingsd.base.zk.confcenter.listener;

import com.zhilingsd.base.zk.zkclient.IZkDataListener;

public interface DataListener extends IZkDataListener {
    void doDataChange(String dataPath, Object data);

    void doDataDelete(String dataPath);
}
