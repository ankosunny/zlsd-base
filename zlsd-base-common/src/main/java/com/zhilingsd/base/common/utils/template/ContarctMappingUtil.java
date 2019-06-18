package com.zhilingsd.base.common.utils.template;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.Field;

/**
 * **className CollectionTypeUtils
 *
 */
@Slf4j
public class ContarctMappingUtil {

    private String[] templateBaseFields = {"联系人姓名", "联系人关系", "联系人电话", "联系人性别", "联系人证件类型", "联系人证件号码", "联系人单位", "联系人地址", "联系人联系类型", "联系人固话","邮编"};
    private String[] baseFieldCodes = {"contactName","contactReationship","contactPhone","contactGender","contactCredentiaType","contactCredentiaCode","contactCompany","contactAddr","contactType","contactCall","zipCode"};

    public int getContarct(String dto){
        if(StringUtils.isBlank(dto)){
            return -1;
        }
        for (int i = 0; i < baseFieldCodes.length; i++) {
            if(dto.equals(baseFieldCodes[i])){
                return i;
            }
        }
        return -1;
    }

    /**
     * @param dto 模板字段
     * @return
     */
    public int getTemplateFields(String dto){
        if(StringUtils.isBlank(dto)){
            return -1;
        }
        for (int i = 0; i < templateBaseFields.length; i++) {
            if(dto.equals(templateBaseFields[i])){
                return i;
            }
        }
        return -1;
    }
    /**
     *
     */
    public String getTemplateBaseFieldCode(int i){
        return templateBaseFields[i];
    }
    public String getFieldCode(int i){
        return baseFieldCodes[i];
    }

    public Object getProperticeVlaue(Object o,String field){
        Class<?> aClass = o.getClass();
        Field declaredField = null;
        Object targetValue = null;
        try {
            declaredField = aClass.getDeclaredField(field);
            declaredField.setAccessible(true);
            targetValue =   declaredField.get(o);
        } catch (NoSuchFieldException e) {
            log.error("反射异常");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.error("反射异常");
        }
        return targetValue;
    }

    /**
     *
     * 设置属性
     * @param o
     */
    public void setPropertice(Object o,String field,String value){
        Class<?> aClass = o.getClass();
        try {
            Field declaredField = aClass.getDeclaredField(field);
            declaredField.setAccessible(true);
             declaredField.set(o,value);
        } catch (NoSuchFieldException e) {
            log.error("编辑联系人映射：设置属性异常");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.error("编辑联系人映射：设置属性异常");
        }
    }
}
