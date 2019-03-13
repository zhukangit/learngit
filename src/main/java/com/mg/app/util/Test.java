package com.mg.app.util;




import java.io.File;
import java.util.List;

public class Test {

//    static Document doc;
//
//    static Element svg;

//    public static void parseSVG(String path) throws IOException {
//        // Parse the barChart.svg file into a Document.
//        String parser = XMLResourceDescriptor.getXMLParserClassName();
//        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
//        URL url = new URL(path);
//        doc = f.createDocument(url.toString());
//
//        svg = doc.getDocumentElement();
//
//        // Change the document viewBox.
//        svg.setAttributeNS(null, "viewBox", "40 95 370 265");
//
//        // Make the text look nice.
//        svg.setAttributeNS(null, "text-rendering", "geometricPrecision");
//
//        // Remove the xml-stylesheet PI.
//        for (Node n = svg.getPreviousSibling();
//             n != null;
//             n = n.getPreviousSibling()) {
//            if (n.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE) {
//                doc.removeChild(n);
//                break;
//            }
//        }
//
//        // Remove the Batik sample mark 'use' element.
//        for (Node n = svg.getLastChild();
//             n != null;
//             n = n.getPreviousSibling()) {
//            if (n.getNodeType() == Node.ELEMENT_NODE
//                    && n.getLocalName().equals("use")) {
//                svg.removeChild(n);
//                break;
//            }
//        }
//    }
//    // 解析svg
//    public static String parseSVG(String svgURI) throws Exception {
//
//        File file = new File(svgURI);
//        String parser = XMLResourceDescriptor.getXMLParserClassName();
//        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
//        Document doc = f.createDocument(file.toURI().toString());
//
//        Element element = doc.getElementById("path");
//
//        String elementStr = convertElemToSVG(element);
//
//        System.out.println(elementStr);
//
//        return elementStr;
//    }
//
//    // 将element转换成字符串
//    public static String convertElemToSVG(Element element) {
//        TransformerFactory transFactory = TransformerFactory.newInstance();
//        Transformer transformer = null;
//        try {
//            transformer = transFactory.newTransformer();
//        } catch (TransformerConfigurationException e) {
//            e.printStackTrace();
//        }
//        StringWriter buffer = new StringWriter();
//        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//        try {
//            transformer.transform(new DOMSource(element), new StreamResult(buffer));
//        } catch (TransformerException e) {
//            e.printStackTrace();
//        }
//        String elementStr = buffer.toString();
//        return elementStr;
//    }

    public static void main(String[] args) throws Exception {
        File path = new File("C:\\cus\\app");
        File[] files = path.listFiles();
        StringBuilder s = new StringBuilder();
        System.out.println("1");
        for (File file : files) {

            s.append("<module>../" + file.getName() + "</module>");
//            System.out.println(file.getName());

        }
        System.out.println(s.toString());
//        SAXReader reader = new SAXReader();
//        File file = new File("C:\\Users\\Troy\\Desktop\\captcha.svg");
////        parseSVG("C:\\Users\\Troy\\Desktop\\captcha.svg");
//        Document document = reader.read(file);
//        Element root = document.getRootElement();
//        List<Element> childElements = root.elements();
//
//        for (Element child : childElements) {
//            if ("path".equals(child.getQName().getName())) {
//                List<Element> gElements = child.elements();
//                for (Element gEle : gElements) {
//                    if ("g".equals(gEle.getQName().getName())) {
//                        List<Element> elements = gEle.elements();
//                        for (Element e : elements) {
//                            if ("desc".equals(e.getQName().getName())) {
//                                System.out.println(e.getText());
//                            }
//                        }
//
//                    }
//                }
//            }

//        }
//        String path = getSourcePath() + "/target\\rap201810230227518";
//
//        System.out.println(deleteDir(path));
//        System.out.println(getSourcePath());
    }
}
