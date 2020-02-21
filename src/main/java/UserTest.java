import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.Iterator;
import java.util.List;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CommonTokenStream;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import javax.swing.*;
import javax.xml.transform.TransformerException;

public class UserTest  {
    public static void main(String[] args) throws IOException, TransformerException {
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

        CharStream stream = CharStreams.fromStream(in);
        XPathLexer lexer  = new XPathLexer(stream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        XPathParser parser = new XPathParser(tokenStream);
        ParseTree tree = parser.xq();

        //show AST in console
        System.out.println(tree.toStringTree(parser));

        //show AST in GUI
        JFrame frame = new JFrame("Antlr AST");
        JPanel panel = new JPanel();
        TreeViewer viewer = new TreeViewer(Arrays.asList(
                parser.getRuleNames()),tree);
        viewer.setScale(1); // Scale a little
        panel.add(viewer);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);




//        CharStream stream = CharStreams.fromStream(in);
//        XPathLexer lexer = new XPathLexer(stream);
//        CommonTokenStream cts = new CommonTokenStream(lexer);
//        XPathParser parser = new XPathParser(cts);
//        ParseTree tree = parser.xq();
//        Visitor vi = new Visitor();  //
//        List<Node> list = (List<Node>)vi.visit(tree);
//        System.out.println("The size of the result is " + list.size());
//
//        Iterator iterator = list.iterator();
//
//        while(iterator.hasNext()){
//            Node node = (Node)iterator.next();
//            System.out.println(toString(node));
//        }



    }
    private static String toString(Node node) throws IOException, TransformerException
    {
        DOMImplementationLS lsImpl = (DOMImplementationLS)node.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
        LSSerializer serializer = lsImpl.createLSSerializer();
        serializer.getDomConfig().setParameter("xml-declaration", false);
        String str = serializer.writeToString(node);
        return str;
    }

}
