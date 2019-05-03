package com.zhilingsd.base.common.utils;

import com.zhilingsd.base.common.utils.jaxb.CDataXMLStreamWriter;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.AbstractUnmarshallerImpl;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.io.*;

public class JAXBUtil {
    public static final String GBK = "GBK";
    public static final String UTF8 = "UTF-8";
    // 输出 的编码
    private static final String ECODING = "UTF-8";


    private JAXBUtil() {
    }

    /**
     * 将对象序列化到本地?
     *
     * @param jaxbEntity 可通过JAXB序列化的对象
     * @param file       必须的形�?./xx.xml，并且要保证xx.xml文件的父路径存在
     * @throws JAXBException
     * @throws IOException
     */
    public static void marshallerTolocal(Object jaxbEntity, File file) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(jaxbEntity.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, JAXBUtil.ECODING);
        marshaller.marshal(jaxbEntity, file);
    }

    /**
     * 使用JAXB方式解决CDATA问题
     *
     * @throws Exception
     */
    public static <T> String ojbectToXmlWithCDATA(T t) throws Exception {

        JAXBContext context = JAXBContext.newInstance(t.getClass());
        ByteArrayOutputStream op = null;
        CDataXMLStreamWriter cdataStreamWriter;
        try {
            op = new ByteArrayOutputStream();

            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLStreamWriter streamWriter = xof.createXMLStreamWriter(op);
            cdataStreamWriter = new CDataXMLStreamWriter(streamWriter);

            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(t, cdataStreamWriter);
            cdataStreamWriter.flush();
        } finally {
            if (op != null) {
                op.close();
            }
        }
        cdataStreamWriter.close();
        return op.toString("UTF-8");
    }

    /**
     * 进行列化
     *
     * @param jaxbEntity 可�?过JAXB序列化的对象
     * @return String
     * @throws JAXBException
     * @throws IOException
     */
    public static <T> String marshaller(T jaxbEntity) throws JAXBException, IOException {
        if (jaxbEntity == null) {
            return null;
        }
        ByteArrayOutputStream outMesg = null;
        String result = "";
        try {
            outMesg = new ByteArrayOutputStream();
            JAXBContext context = JAXBContext.newInstance(jaxbEntity.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, JAXBUtil.ECODING);
            marshaller.marshal(jaxbEntity, outMesg);
        } finally {
            if (outMesg != null) {
                outMesg.close();
            }
        }
        result = outMesg.toString(JAXBUtil.ECODING);
        return result;
    }

    /**
     * 进行列化
     *
     * @param jaxbEntity 可�?过JAXB序列化的对象
     * @return String
     * @throws JAXBException
     * @throws IOException
     */
    public static <T> String marshaller(T jaxbEntity, String encode) throws JAXBException, IOException {
        if (jaxbEntity == null) {
            return null;
        }
        ByteArrayOutputStream outMesg = null;
        String result = "";
        try {
            outMesg = new ByteArrayOutputStream();
            JAXBContext context = JAXBContext.newInstance(jaxbEntity.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encode);
            marshaller.marshal(jaxbEntity, outMesg);
        } finally {
            if (outMesg != null) {
                outMesg.close();
            }
        }
        result = outMesg.toString(encode);
        return result;
    }


    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    private static boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 对XML进行反序列化
     *
     * @param clazz
     * @param xml   必须是xml格式的字符串
     * @return T
     * @throws JAXBException
     * @throws IOException
     */
    public static <T> T unmarshal(Class<T> clazz, String xml) throws JAXBException, IOException {
        if (JAXBUtil.isBlank(xml)) {
            return null;
        }

        StringReader reader = null;
        T unmarshal = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            AbstractUnmarshallerImpl unmarshaller = (AbstractUnmarshallerImpl) context.createUnmarshaller();
            reader = new StringReader(xml);
            SAXParserFactory sax = SAXParserFactory.newInstance();
            XMLReader xmlReader = null;
            try {
                sax.setFeature("http://xml.org/sax/features/external-general-entities", false);
                sax.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
                sax.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
                xmlReader = sax.newSAXParser().getXMLReader();
            } catch (Exception e) {
                throw new JAXBException(e);
            }
            Source source = new SAXSource(xmlReader, new InputSource(reader));
            unmarshal = (T) unmarshaller.unmarshal(source);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return unmarshal;
    }


    public static <T> T convertXmlInputStream(Class<T> entityClass, String context) throws JAXBException, IOException {
        InputStream inputStrem = null;
        T unmarshal = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(entityClass);
            Unmarshaller u = jc.createUnmarshaller();
            inputStrem = Thread.currentThread().getContextClassLoader().getResourceAsStream(context);
            unmarshal = (T) u.unmarshal(inputStrem);
        } finally {
            if (inputStrem != null) {
                inputStrem.close();
            }
        }
        return unmarshal;
    }

    /**
     * 忽略命名空间
     *
     * @param clazz
     * @param xml
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T unmarshalIgnoreNSP(Class<T> clazz, String xml) throws Exception {
        if (JAXBUtil.isBlank(xml)) {
            return null;
        }

        StringReader reader = null;
        T unmarshal = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            reader = new StringReader(xml);
            SAXParserFactory sax = SAXParserFactory.newInstance();
            sax.setNamespaceAware(false);
            sax.setFeature("http://xml.org/sax/features/external-general-entities", false);
            sax.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            sax.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            XMLReader xmlReader = sax.newSAXParser().getXMLReader();
            Source source = new SAXSource(xmlReader, new InputSource(reader));
            unmarshal = (T) unmarshaller.unmarshal(source);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return unmarshal;
    }

}
