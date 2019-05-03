/**
 * Software License Declaration.
 *
 * wandaph.com, Co,. Ltd.
 * Copyright © 2017 All Rights Reserved.
 *
 * Copyright Notice
 * This documents is provided to wandaph contracting agent or authorized programmer only.
 * This source code is written and edited by wandaph Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 *
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to wandaph Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise wandaph will charge the fee according to the programme itself.
 *
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of wandaph. If Any problem cannot be solved in the
 * procedure of programming should be feedback to wandaph Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.collection.common.utils;

import com.zhilingsd.collection.common.emuns.BaseResultCodeEnum;
import com.zhilingsd.collection.common.exception.ServiceException;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * 
 * @author linguangliang
 * @version Id: BeanUtils.java, v 0.1 2018年4月26日 上午9:20:16 linguangliang Exp $
 */
public class BeanUtils extends  org.springframework.beans.BeanUtils{


    public static <T> List<T> copyArray(Collection sourceList, Class<T> clz){
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.EMPTY_LIST;
        }
        List<T> list = new ArrayList<>(sourceList.size());
        for (Object source : sourceList) {
            T target = null;
            try {
                target = clz.newInstance();
            } catch (Exception e) {
                throw new ServiceException(BaseResultCodeEnum.SYSTEM_ERROR.getCode(),BaseResultCodeEnum.SYSTEM_ERROR.getMsg()+e);
            }
            org.springframework.beans.BeanUtils.copyProperties(source, target);
            list.add(target);
        }

        return list;
    }

    public static <T> T copyBean(Object source, Class<T> clz){
        T target = null;
        try {
           target  = clz.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new ServiceException(BaseResultCodeEnum.SYSTEM_ERROR.getCode(),BaseResultCodeEnum.SYSTEM_ERROR.getMsg()+e);
        }
        return target;
    }
    
}
