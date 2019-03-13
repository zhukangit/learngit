package com.mg.app.util;

import java.lang.String;

import freemarker.template.utility.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.XMLFormatter;

/**
 * 类名描述
 *
 * @author ideal
 * @version Revision 版本号
 * @版权： 版权所有 (c) 2018
 * @创建日期： 2019/2/2 10:30
 * @功能说明： 功能描述
 */
public class XmlUtil {

  static   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 将XML文件输出到指定的路径
     *
     * @param doc
     * @param fileName
     * @throws Exception
     */
    public static void outputXml(Document doc, String fileName)
            throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(doc);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
        StreamResult result = new StreamResult(pw);
        transformer.transform(source, result);
        System.out.println("生成XML文件成功!");
    }

    /**
     * 生成XML
     *
     * @param ip
     * @return
     */
    public static Document generateXml(String ip) {
        Document doc = null;
        Element root = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
            root = doc.createElement("errorDevices");
            doc.appendChild(root);
        } catch (Exception e) {
            e.printStackTrace();
            return null;// 如果出现异常，则不再往下执行
        }

        Element element;
        element = doc.createElement("errorDevice");
        element.setAttribute("ip", ip);
        element.setAttribute("date",
                format.format(new Date()));
        element.setAttribute("status", "1");
        root.appendChild(element);
        return doc;
    }

    /**
     * 新增Xml节点
     *
     * @param ip
     * @param filename
     * @return
     * @throws FileNotFoundException
     * @throws TransformerException
     */
    public static void toWrite(String filename, String ip)
            throws FileNotFoundException, TransformerException {
        String date = format.format(new Date());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(filename));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NodeList links = doc.getElementsByTagName("errorDevice");
        if (links.getLength() > 0) {
            for (int i = 0; i < links.getLength(); i++) {
                Node nd = links.item(i);
                Node catParent = nd.getParentNode();
                Element ele = (Element) nd;
                String url = ele.getAttribute("ip");
                if (url.equals(ip)) {
                    // ele.setAttribute("date", date);
                    catParent.removeChild(nd);
                }
            }
        }
        Element element = doc.createElement("errorDevice");
        element.setAttribute("ip", ip);
        element.setAttribute("date",
                format.format(new Date()));
        element.setAttribute("status", "1");
        doc.getDocumentElement().appendChild(element);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(doc);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
        StreamResult result = new StreamResult(pw);
        transformer.transform(source, result);
        System.out.println("新增XML节点成功!");
    }

    /**
     * 读取XML
     *
     * @param filename
     * @return
     */
    public static List<Map> readXml(String filename){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(filename));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NodeList links = doc.getElementsByTagName("errorDevice");
        List<Map> list = new ArrayList<Map>();
        for(int i = 0; i< links.getLength() ; i ++){
            Element node = (Element)links.item(i);
            Map map = new HashMap();
            map.put(node.getAttribute("ip"), node.getAttribute("date"));
            list.add(map);
        }
        return list;
    }
}