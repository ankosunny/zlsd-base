package com.zhilingsd.base.zk.confcenter;

import com.zhilingsd.base.zk.zkclient.IZkDataListener;
import com.zhilingsd.base.zk.zkclient.ZkClient;

/**
 * 配置中心API
 *
 * @author zhangrong
 * @version $Id: ConfigurationCenter.java, v 1.0 Feb 21, 2017 8:05:48 PM sunjm13534 Exp $
 */
public interface ConfigurationCenter {
    /**
     * 设置配置中心值
     *
     * @param path  配置路径
     * @param value 配置值
     */
    void setupConfValue(String path, Object value);

    /**
     * 获得配置中心值
     *
     * @param path 配置路径
     * @return
     */
    <T> T getConfValue(String path);

    <T> T getConfValue(String path, IZkDataListener listener);

    /**
     * 判断指定的节点是否已经存在
     * @param path
     * @return
     */
    Boolean exists(String path);

    /**
     * 节点存在情况下，写数据
     * @param path
     * @param value
     */
    void setNodeData(String path, Object value);




    ZkClient getZkClient();
}
