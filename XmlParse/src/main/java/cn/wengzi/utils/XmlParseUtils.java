package cn.wengzi.utils;

import cn.wengzi.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arley
 * @email qx_leizige@163.com
 * Xml解析工具类
 */
@Slf4j
@SuppressWarnings("all")
public class XmlParseUtils {

    /**
     * xml解析返回List集合
     *
     * @param filePath 文件路径
     * @param clazz    要返回的集合实体
     * @param <T>      clazz
     * @return List<T>
     */
    public static List<Student> parseXmlToList(String filePath) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document document = null;
        List<Student> list = new ArrayList<>();
        try {
            builder = factory.newDocumentBuilder();
            /* 解析为一个Java可以处理的document对象 */
            document = builder.parse(new FileInputStream(filePath));
            /* 获取所有文档节点 */
            Element element = document.getDocumentElement();
            /* 获取文档子节点(student节点) */
            NodeList nodeList = element.getElementsByTagName("student");
            /* 遍历Xml中三个student节点*/
            for (int i = 0; i < nodeList.getLength(); i++) {
                Student student = new Student();
                /* 每一个student */
                Element node = (Element) nodeList.item(i);
                Long id = Long.valueOf(node.getAttribute("id"));
                /* 先拿到Id */
                student.setId(id);
                /* 拿到每一个student的子节点*/
                NodeList childNodes = node.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node item = childNodes.item(j);
                    /* 只拿<xx>这种形式的子节点,不拿空格和文字*/
                    if (item.getNodeType() == Node.ELEMENT_NODE) {
                        switch (item.getNodeName()) {
                            case "name":
                                student.setName(item.getFirstChild().getNodeValue());
                                break;
                            case "age":
                                student.setAge(Integer.parseInt(item.getFirstChild().getNodeValue()));
                                break;
                            default:
                                break;
                        }
                    }
                }
                list.add(student);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            log.error("XmlToList解析失败,{}", e.toString());
        }
        return list;
    }
}
