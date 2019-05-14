package com.zhilingsd.base.zk.confcenter.listener;

import com.zhilingsd.base.zk.zkclient.ZkClient;

public abstract class AbstractGoonWatchDataListener implements DataListener {
    private ZkClient _zk;

    public AbstractGoonWatchDataListener(ZkClient zk) {
        _zk = zk;
    }

    public void set_zk(ZkClient _zk) {
        this._zk = _zk;
    }

    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        doDataChange(dataPath, data);
        _zk.subscribeDataChanges(dataPath, this);
    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        doDataDelete(dataPath);
        _zk.subscribeDataChanges(dataPath, this);
    }

}
