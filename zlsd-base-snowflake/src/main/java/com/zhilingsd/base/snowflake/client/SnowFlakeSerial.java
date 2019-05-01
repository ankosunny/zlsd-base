/**
 * Software License Declaration.
 * <p>
 * zhilingsd.com, Co,. Ltd.
 * Copyright © 2019 All Rights Reserved.
 * <p>
 * Copyright Notice
 * This documents is provided to zhilingsd contracting agent or authorized programmer only.
 * This source code is written and edited by zhilingsd Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 * <p>
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to zhilingsd Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise zhilingsd will charge the fee according to the programme itself.
 * <p>
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of wandaph. If Any problem cannot be solved in the
 * procedure of programming should be feedback to zhilingsd Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.base.snowflake.client;

import com.zhilingsd.base.snowflake.common.SnowFlakeEntityEnum;
import com.zhilingsd.base.snowflake.common.SnowFlakeException;
import com.zhilingsd.bebinca.client.BebincaClient;
import com.zhilingsd.bebinca.common.BebincaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author ZhangRong
 * @version Id: com.zhilingsd.base.snowflake.client.SnowFlakeSerial, v 0.1 2019/5/1 16:32 ZhangRong Exp $$
 */
public class SnowFlakeSerial implements Serial {
    private static final Logger log = LoggerFactory.getLogger(SnowFlakeSerial.class);

    private BebincaClient bebincaClient;

    public SnowFlakeSerial(String connectString, int clientTimeout) throws IOException {
        bebincaClient = new BebincaClient(connectString, clientTimeout);
    }

    @Override
    public Long getLongSerialNum() throws SnowFlakeException {
        try {
            return bebincaClient.getSnowFlakeId(SnowFlakeEntityEnum.COMMON.getEntityId());
        } catch (BebincaException | InterruptedException e) {
            log.error("Get snow lake id error: ", e);
            throw new SnowFlakeException(e);
        }
    }

    @Override
    public Long getLongSerialNum(SnowFlakeEntityEnum entityEnum) throws SnowFlakeException {
        try {
            return bebincaClient.getSnowFlakeId(entityEnum.getEntityId());
        } catch (BebincaException | InterruptedException e) {
            log.error("Get snowflake id error: ", e);
            throw new SnowFlakeException(e);
        }
    }

    @Override
    public String getStringSerialNum() throws SnowFlakeException {
        long snowFlakeId = 0;
        try {
            snowFlakeId = bebincaClient.getSnowFlakeId(SnowFlakeEntityEnum.COMMON.getEntityId());
        } catch (BebincaException | InterruptedException e) {
            log.error("Get snowflake id error: ", e);
            throw new SnowFlakeException(e);
        }
        return String.valueOf(snowFlakeId);
    }

    @Override
    public String getStringSerialNum(SnowFlakeEntityEnum entityEnum) throws SnowFlakeException {
        long snowFlakeId = 0;
        try {
            snowFlakeId = bebincaClient.getSnowFlakeId(entityEnum.getEntityId());
        } catch (BebincaException | InterruptedException e) {
            log.error("Get snowflake id error: ", e);
            throw new SnowFlakeException(e);
        }
        return String.valueOf(snowFlakeId);
    }
}
