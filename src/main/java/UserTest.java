import java.io.*;
import java.util.Iterator;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CommonTokenStream;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;

public class UserTest {
    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException {

        //String file = "XPathTest.txt";

        if(args.length==0){
            System.out.println("The argument format is: java -jar <jar> <Query.txt> <flag>");
        }
        String file = args[0];
        String flag = args[1];
        Boolean f = true;

        if(flag.equals("-L")) f = true;
        else if(flag.equals("-B")) f = false;
        else System.out.println("please enter the correct flag");

        InputStream in = System.in;
        InputStream in2 = System.in;
        if(file != null){
            in = new FileInputStream(file);
            in2 = new FileInputStream(file);
        }
        //below to show the picture of xquery
//
//        CharStream stream = CharStreams.fromStream(in);
//        XPathLexer lexer  = new XPathLexer(stream);
//        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
//        XPathParser parser = new XPathParser(tokenStream);
//        ParseTree tree = parser.xq();
//
//        //show AST in console
//        System.out.println(tree.toStringTree(parser));
//
//        //show AST in GUI
//        JFrame frame = new JFrame("Antlr AST");
//        JPanel panel = new JPanel();
//        TreeViewer viewer = new TreeViewer(Arrays.asList(
//                parser.getRuleNames()),tree);
//        viewer.setScale(1); // Scale a little
//        panel.add(viewer);
//        frame.add(panel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);


        //---below do the milestone3---
        reWriteVisitor rw = new reWriteVisitor();
        String rew = rw.reWrite(in,f);//true = left, false = bushy
        PrintWriter out = new PrintWriter("rewriteQuery.txt");
        PrintWriter result = new PrintWriter("result.txt");


        //System.out.println(rew);
        //System.out.println("----------------------");
        StringBuilder sb = new StringBuilder();
        if(rew==null){
            out.println("rewrite failed");
            CharStream stream = CharStreams.fromStream(in2);
            XPathLexer lexer = new XPathLexer(stream);
            CommonTokenStream cts = new CommonTokenStream(lexer);
            XPathParser parser = new XPathParser(cts);
            ParseTree tree = parser.xq();
            Visitor vi = new Visitor();  //
            List<Node> list = (List<Node>) vi.visit(tree);
            result.println("The size of the result is " + list.size());
            //System.out.println("The size of the result is " + list.size());
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Node node = (Node) iterator.next();
                sb.append(printNode(node, ""));
            }
        }
        else {
            out.println(rew);
            CharStream stream = CharStreams.fromString(rew);
            XPathLexer lexer = new XPathLexer(stream);
            CommonTokenStream cts = new CommonTokenStream(lexer);
            XPathParser parser = new XPathParser(cts);
            ParseTree tree = parser.xq();
            Visitor vi = new Visitor();  //
            List<Node> list = (List<Node>) vi.visit(tree);
            result.println("The size of the result is " + list.size());
            //System.out.println("The size of the result is " + list.size());
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                Node node = (Node) iterator.next();
                sb.append(printNode(node, ""));
            }
        }
        result.println(sb);
        out.close();
        result.close();

    }
    private static StringBuilder printNode(Node node, String tab) {
        StringBuilder sb = new StringBuilder();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            sb.append(tab).append("<").append( node.getNodeName()).append(">").append("\n");
            //System.out.println(tab + "<" + node.getNodeName() + ">");
            NamedNodeMap nodeMap = node.getAttributes();
            if (nodeMap != null) {
                for (int i = 0; i < nodeMap.getLength(); i++) {
                    sb.append(printNode(nodeMap.item(i), tab + "  ")).append("\n");
                }
            }
            NodeList nodeList = node.getChildNodes();
            if (nodeList != null) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    sb.append(printNode(nodeList.item(i), tab + "  ")).append("\n");
                }
            }
            //System.out.println(tab + "<" + node.getNodeName() + "/>");
            sb.append(tab).append("<").append(node.getNodeName()).append("/>").append("\n");

        } else if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
            sb.append(tab).append("<@").append(node.getNodeName()).append("=").append(node.getNodeValue()).append(">").append("\n");
            //System.out.println(tab + "<@" + node.getNodeName() + "=" + node.getNodeValue() + ">");
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            sb.append(tab).append(node.getTextContent()).append("\n");
            //System.out.println(tab + node.getTextContent());
        }
        return sb;
    }

//    private static String toString(Node node) throws IOException, TransformerException
//    {
//        DOMImplementationLS lsImpl = (DOMImplementationLS)node.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
//        LSSerializer serializer = lsImpl.createLSSerializer();
//        serializer.setNewLine("\n");
//        serializer.getDomConfig().setParameter("xml-declaration", false);
//        String str = serializer.writeToString(node);
//        return str;
//
//    }
//    public static String formatXml(String xml){
//        try{
//            Transformer serializer= SAXTransformerFactory.newInstance().newTransformer();
//            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
//            serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
//            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");
//            Source xmlSource=new SAXSource(new InputSource(new ByteArrayInputStream(xml.getBytes())));
//            StreamResult res =  new StreamResult(new ByteArrayOutputStream());
//            serializer.transform(xmlSource, res);
//            return new String(((ByteArrayOutputStream)res.getOutputStream()).toByteArray());
//        }catch(Exception e){
//            return xml;
//        }
//    }


}
