package com.zhilingsd.base.swagger.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: 智灵时代广州研发中心
 * @description: swagger配置文件类
 * @author: 吞星(yangguojun)
 * @create: 2019-04-30 17:32
 **/
@ConfigurationProperties(prefix = SwaggerProperties.SWAGGER_PREFIX)
public class SwaggerProperties {

    public static final String SWAGGER_PREFIX ="zlsd.swagger";


    /**
     * swagger注解扫描范围
     */
    private String scanPackage ="";
    /**
     * swagger在线文档名称
     */
    private String tile;

    /**
     * swagger在线文档描述
     */
    private String description;

    /**
     * 项目维护人员 example 吞星，林梦怀
     */
    private String names;

    /**
     * 是否开启swagger true:开启；false：关闭
     */
    private String flag;

    public String getScanPackage() {
        return scanPackage;
    }

    public void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }

    public static String getSwaggerPrefix() {
        return SWAGGER_PREFIX;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
