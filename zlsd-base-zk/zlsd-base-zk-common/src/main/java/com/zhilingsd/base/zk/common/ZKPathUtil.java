package com.zhilingsd.base.zk.common;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import java.util.List;

public class ZKPathUtil {

    public static List<String> analysisZkPath(String path) {
        boolean b = Strings.isNullOrEmpty(path);
        if (b) {
            throw new NullPointerException("path is null !");
        }
        List<String> list = Splitter.on("/").omitEmptyStrings().trimResults().splitToList(path);
        return list;
    }

    public static String createZkPath(List<String> list) {
        String s = "";
        if (list.size() > 1) {
            list = list.subList(0, list.size() - 1);
            s = "/" + Joiner.on("/").join(list);
//            if (!_zk.exists(s)) {
//                _zk.createPersistent(s, true);
//            }
        }
        return s;
    }

    public static void main(String[] args) {
        new ZKPathUtil().analysisZkPath("a/ab/sdf/fdsf/");
    }
}
