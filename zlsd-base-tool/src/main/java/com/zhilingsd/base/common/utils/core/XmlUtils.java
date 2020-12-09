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
package com.zhilingsd.base.common.utils.core;

import com.alibaba.fastjson.JSONObject;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将xml string 转化为map
 *
 * @author zhangrong67
 * @version Id: XmlUtils.java, v 0.1 2018/2/1 下午5:41 zhangrong67 Exp $$
 */
public class XmlUtils {
    /**
     * 将xml string 转化为map
     *
     * @param xmlDoc
     * @return
     * @throws IOException
     * @throws JDOMException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> xmlToMap(String xmlDoc) throws JDOMException, IOException {
        // 创建一个新的字符串
        StringReader read = new StringReader(xmlDoc);
        // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        // 创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();

        Map<String, Object> xmlMap = new HashMap<String, Object>();

        // 通过输入源构造一个Document
        Document doc = sb.build(source);
        // 取的根元素
        Element root = doc.getRootElement();

        // 得到根元素所有子元素的集合(根元素的子节点，不包括孙子节点)
        List<Element> cNodes = root.getChildren();
        Element et;
        for (int i = 0; i < cNodes.size(); i++) {
            // 循环依次得到子元素
            et = cNodes.get(i);
            xmlMap.put(et.getName(), et.getText());
        }
        return xmlMap;
    }

    /**
     * xml转对象
     * @param xmlDoc
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T xmlToObject(String xmlDoc, Class<T> clazz) {
        try {
            Map<String, Object> map = xmlToMap(xmlDoc);
            return JSONObject.parseObject(JSONObject.toJSONString(map), clazz);
        } catch (JDOMException | IOException e) {
          throw new RuntimeException(e);
        }
    }

    /**
     * 将对象直接转换成String类型的 XML输出
     *
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

}