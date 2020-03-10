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

public class UserTest  {
    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException {
//        Scanner input = new Scanner(System.in);
//        System.out.print("Enter query file name : ");
//        String file = input.next();
//        if(args.length==0){
//            System.out.println("Need input file");
//        }
//        String file = args[0];
        String file = "XPathTest.txt";
        InputStream in = System.in;
        if(file != null) {
            in = new FileInputStream(file);
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
        reWriteVisitor rw = new reWriteVisitor();
        System.out.println(rw.reWrite(in));
//        CharStream stream = CharStreams.fromString(rw.reWrite(in));
//        //CharStream stream = CharStreams.fromStream(in);
//        XPathLexer lexer = new XPathLexer(stream);
//        CommonTokenStream cts = new CommonTokenStream(lexer);
//        XPathParser parser = new XPathParser(cts);
//        ParseTree tree = parser.xq();
//        Visitor vi = new Visitor();  //
//        List<Node> list = (List<Node>)vi.visit(tree);
//        System.out.println("The size of the result is " + list.size());
//        Iterator iterator = list.iterator();
//        while(iterator.hasNext()){
//            Node node = (Node)iterator.next();
//           printNode(node,"");
//        }
    }
    private static void printNode(Node node, String tab) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println(tab + "<" + node.getNodeName() + ">");
            NamedNodeMap nodeMap = node.getAttributes();
            if (nodeMap != null) {
                for (int i = 0; i < nodeMap.getLength(); i++) {
                    printNode(nodeMap.item(i), tab + "  ");
                }
            }
            NodeList nodeList = node.getChildNodes();
            if (nodeList != null) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    printNode(nodeList.item(i), tab + "  ");
                }
            }
            System.out.println(tab + "<" + node.getNodeName() + "/>");

        } else if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
            System.out.println(tab + "<@" + node.getNodeName() + "=" + node.getNodeValue() + ">");
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            System.out.println(tab + node.getTextContent());
        }
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
