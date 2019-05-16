/**  
 * All rights Reserved, Designed By www.mujinkeji.com
 * @Title:  LocalPrincipal.java   
 * @Package com.mujin.constants.websocket   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: Administrator     
 * @date:   2018年7月27日 下午1:58:26   
 * @version V1.0 
 * @Copyright: 2018 www.mujinkeji.com Inc. All rights reserved. 
 * 
 */
package com.zhilingsd.base.common.websocket;

import java.security.Principal;

/**   
 * @ClassName:  LocalPrincipal   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: Administrator  
 * @date:   2018年7月27日 下午1:58:26   
 *    
 * @Copyright: 2018 www.mujinkeji.com Inc. All rights reserved. 
 * 
 */
public class LocalPrincipal implements Principal {

	private String username;

	public LocalPrincipal(String username) {
        this.username = username;
    }
	
    public LocalPrincipal() {
    }

	@Override
	public String getName() {
		return username;
	}

}
