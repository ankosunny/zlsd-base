/**
 * Software License Declaration.
 * <p>
 * wandaph.com, Co,. Ltd.
 * Copyright © 2017 All Rights Reserved.
 * <p>
 * Copyright Notice
 * This documents is provided to wandaph contracting agent or authorized programmer only.
 * This source code is written and edited by wandaph Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 * <p>
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to wandaph Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise wandaph will charge the fee according to the programme itself.
 * <p>
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of wandaph. If Any problem cannot be solved in the
 * procedure of programming should be feedback to wandaph Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.collection.common.support;


import com.zhilingsd.collection.common.util.ReflectUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author luziliang1
 * @version Id: SnowfakeInterceptor.java, v 0.1 2018/5/3 16:36 luziliang1 Exp $$
 */
@Intercepts({@Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})})
public class SnowflakeInterceptor implements Interceptor {

    private final static Logger log = LoggerFactory.getLogger(SnowflakeInterceptor.class);

    private SnowflakeRestTemplate restTemplate;

    private String url;

    public SnowflakeInterceptor(SnowflakeRestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object object = args[1];
        SqlCommandType sqlCommandType = ms.getSqlCommandType();
        String[] keyProperties = ms.getKeyProperties();
        if (sqlCommandType == SqlCommandType.INSERT && keyProperties!=null && keyProperties.length == 1) {
            String keyName = keyProperties[0];
            if (object instanceof Map) {
                for (Object obj : ((Map) object).values()) {
                    if (obj instanceof Collection) {
                        for (Object target : (Collection) obj) {
                            setSnowflakeId(target, keyName);
                        }
                        break;
                    } else if (obj.getClass().isArray()) {
                        for (Object target : (Object[]) obj) {
                            setSnowflakeId(target, keyName);
                        }
                        break;
                    }

                }
            } else {
                setSnowflakeId(object, keyName);
            }

        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private long getSnowflakeId() {
        return restTemplate.getForObject(url, long.class);
    }

    private void setSnowflakeId(Object target, String keyName) {
        Field field = ReflectUtil.getField(target, keyName);
        Object fieldValue = ReflectUtil.getFieldValue(target, keyName);
        if (fieldValue != null) {
            log.warn("主键字段非空，将被雪花id替代。");
        }
        if (field.getType() == Long.class || field.getType() == long.class) {
            ReflectUtil.setFieldValue(target, keyName, getSnowflakeId());
        } else if (field.getType() == String.class) {
            ReflectUtil.setFieldValue(target, keyName, getSnowflakeId());
        } else {
            log.warn("雪花id暂不支持用于非Long、String类型的主键。");
        }
    }
}