package com.zhilingsd.base.common.utils.collection;

import com.zhilingsd.base.common.bean.KeyValueBean;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description
 * @createTime 2019年04月28日 11:49*
 * log.info()
 */
public class KeyValueBeanUtil {

    /**
     * 将双参数枚举转换成 KeyValueBean
     */
    public static List<KeyValueBean> enumToConditionBeans(Class c, String field1, String field2) {
        List<KeyValueBean> conditionBeans = new ArrayList<KeyValueBean>();
        if (c.isEnum()) {
            try {
                Object[] objs = c.getEnumConstants();
                for (Object obj : objs) {
                    Method m = obj.getClass().getDeclaredMethod("values", null);
                    Object[] result = (Object[]) m.invoke(obj, null);
                    List list = Arrays.asList(result);
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        Object objOne = it.next();
                        Field code = objOne.getClass().getDeclaredField(field1);
                        Field codeDesc = objOne.getClass().getDeclaredField(field2);
                        Field priority = null;
                        try {
                            priority = objOne.getClass().getDeclaredField("priority");
                        } catch (Exception e) {
                            priority = null;
                        }
                        code.setAccessible(true);
                        codeDesc.setAccessible(true);
                        if (priority != null) {
                            priority.setAccessible(true);
                        }
                        KeyValueBean conditionBean = new KeyValueBean();
                        conditionBean.setCode(GO.string(code.get(objOne)));
                        conditionBean.setName(GO.string(codeDesc.get(objOne)));
                        conditionBeans.add(conditionBean);
                    }
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conditionBeans;
    }

    /**
     * 将list<string>列表转换成 KeyValueBean
     */
    public static List<KeyValueBean> listStringToConditionBeans(List<String> stringList) {
        if (CollectionUtils.isNotEmpty(stringList)) {
            List<KeyValueBean> conditionBeans = new ArrayList<>();
            for (String string : stringList) {
                KeyValueBean keyValueBean = new KeyValueBean(string, string);
                conditionBeans.add(keyValueBean);
            }
            return conditionBeans;
        }
        return null;
    }

    public static List<KeyValueBean> listMapToConditionBeans(List<Map<String, String>> stringList) {
        if (CollectionUtils.isNotEmpty(stringList)) {
            List<KeyValueBean> conditionBeans = new ArrayList<>();
            for (Map<String, String> map : stringList) {
                for (String k : map.keySet()) {
                    KeyValueBean keyValueBean = new KeyValueBean(k, map.get(k));
                    conditionBeans.add(keyValueBean);
                }
            }
            return conditionBeans;
        }
        return null;
    }

    public static void main(String[] args) {
    }

}
