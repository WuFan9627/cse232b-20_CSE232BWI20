import org.antlr.v4.runtime.tree.ParseTree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.annotation.ElementType;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;


//update global & use returned res
class Visitor extends XPathBaseVisitor<Object>{
    List<Node> curNodes = new ArrayList<>();
    //text and attribute
    //only store ducu and element
    public static LinkedList<Node> getChildren(Node n){
        LinkedList<Node> nodes = new LinkedList<>();
        NodeList children = n.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            nodes.add(children.item(i));
        }
        return nodes;
    }

    @Override public Document visitFileName(XPathParser.FileNameContext ctx){
        try{
            String fileName = ctx.NAME().toString();
            DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = docBF.newDocumentBuilder();
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            fileName = s + File.separator + fileName + ".xml";
            File xmlFile = new File(fileName);
            Document doc = docBuild.parse(xmlFile);

            if (doc != null) {
                doc.getDocumentElement().normalize();
            }
            curNodes.add(doc);
            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override public List<Node> visitApGOToChildren(XPathParser.ApGOToChildrenContext ctx) {
        //put root in curNodes
        visit(ctx.fileName());
        return (List<Node>) visit(ctx.rp());
    }
    @Override public List<Node> visitApGoTODescent(XPathParser.ApGoTODescentContext ctx){
        visit(ctx.fileName());
        List<Node> children = new ArrayList<>();
        Node root = curNodes.get(0);
        Deque<Node> queue = new ArrayDeque<>();
        queue.offerFirst(root);
        children.add(root);
        //store all the ele and root
        while(!queue.isEmpty()){
            Node cur = queue.pollLast();
            List<Node> nxt = getChildren(cur);
            for(Node n: nxt){
                if(n.getNodeType()== Node.ELEMENT_NODE || n.getNodeType() == Node.DOCUMENT_NODE){
                    queue.offerFirst(n);
                    children.add(n);
                }
            }
        }
        curNodes = children;
        return (List<Node>) visit(ctx.rp());
    }

    @Override public List<Node> visitAllChildren(XPathParser.AllChildrenContext ctx){
        Set<Node> set = new HashSet<>();
        for(Node n: curNodes){
            NodeList children =n.getChildNodes();
            for (int i = 0;i<children.getLength();i++) {
                if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    set.add(children.item(i));
                }
            }

        }
        curNodes = new ArrayList<>(set);
        return curNodes;
    }

    @Override public List<Node> visitTagName(XPathParser.TagNameContext ctx) {
        List<Node> res = new ArrayList<>();
        String tagName = ctx.getText();

        for(Node n:curNodes){
            List<Node> children = getChildren(n);
            for(Node child : children){
                if(child.getNodeType()== Node.ELEMENT_NODE  && child.getNodeName().equals(tagName)){
                    res.add(child);
                }
            }
        }
        curNodes = res;
        return res;
    }
    @Override public List<Node> visitParent(XPathParser.ParentContext ctx) {
        List<Node> res = new ArrayList<>();
        for(Node n : curNodes) {
            if(n!=null && !res.contains(n.getParentNode())) res.add(n.getParentNode());
        }
        curNodes = res;
        return res;
    }
    // one node may have multiple attributes
    @Override public List<Node> visitAttribute(XPathParser.AttributeContext ctx) {
        String attr = ctx.NAME().getText();
        List<Node> res = new ArrayList<>();
        for (Node node : curNodes){
            if(node!=null) {
                Node att = null;
                if(node.getAttributes()!=null){
                    att = node.getAttributes().getNamedItem(attr);
                }
                if (att != null) res.add(att);
            }

        }
        curNodes = res;
        return res;
    }
    @Override public List<Node> visitRpWithP(XPathParser.RpWithPContext ctx) {
        return (List<Node>) visit(ctx.rp());
    }
    @Override public List<Node> visitRpGoToChildren(XPathParser.RpGoToChildrenContext ctx){
        visit(ctx.rp(0));
        List<Node> res = (List<Node>) visit(ctx.rp(1));
        curNodes = res;
        return res;
    }
    //rp1[rp2] A[B]
    @Override public List<Node> visitRpWithFilter(XPathParser.RpWithFilterContext ctx){
        visit(ctx.rp());//cur = {A_B,A_B,A,A}
        List<Node> res = new ArrayList<>();
        List<Node> temp = new ArrayList<>(curNodes);
        for(Node n : temp){
            curNodes = new ArrayList<>();
            curNodes.add(n);
            if((Boolean)visit(ctx.filter())){
                res.add(n);
            }
        }
        //ctx.filter(); get cur = {A_B,A_B,A,A}
        curNodes = res; //res = {A_B,A_B}
        return res;
    }

    @Override public List<Node> visitRpGoToDescent(XPathParser.RpGoToDescentContext ctx){
        List<Node> rpOne = (List<Node>) visit(ctx.rp(0));
        List<Node> children = new ArrayList<>(rpOne);
        // for(Node curNode: rpOne){
        // 	children.addAll(getChildren(curNode));
        // }
        // curNodes = children;
        // return visit(ctx.rp(1));

        Deque<Node> queue = new ArrayDeque<>(rpOne);
        //store all the ele and root
        while(!queue.isEmpty()){
            Node cur = queue.pollLast();
            List<Node> nxt = getChildren(cur);
            for(Node n: nxt){
                if(n.getNodeType()== Node.ELEMENT_NODE || n.getNodeType() == Node.DOCUMENT_NODE){
                    queue.offerFirst(n);
                    children.add(n);
                }
            }
        }
        curNodes = children;
        visit(ctx.rp(1));
        return curNodes;


    }
    @Override public List<Node> visitRpWithConcat(XPathParser.RpWithConcatContext ctx){
        List<Node> temp = new ArrayList<>(curNodes);
        List<Node> rpOne = (List<Node>) visit(ctx.rp(0));
        curNodes = temp;
        List<Node> rpTwo = (List<Node>) visit(ctx.rp(1));
        rpOne.addAll(rpTwo);
        curNodes = rpOne;
        return rpOne;
    }
    @Override public List<Node> visitText(XPathParser.TextContext ctx){
       Set<Node> set = new HashSet<>();
        for(Node cur: curNodes){
            List<Node> children = getChildren(cur);
            for(Node child: children){
                if(child.getNodeType() == Node.TEXT_NODE && child.getTextContent() != null){
                    set.add(child);
                }
            }
        }
        curNodes = new ArrayList<>(set);
        return curNodes;
    }

//self not included
    @Override public List<Node> visitCurrent(XPathParser.CurrentContext ctx){
        return curNodes;
    }
    // suppose no return
    //{A_B, A,A,A_B}---cur = {B,B}
    //rp1[rp2]
    @Override public Boolean visitFilRp(XPathParser.FilRpContext ctx){ //filter = rp
        List<Node> temp = new ArrayList<>(curNodes);
        visit(ctx.rp());//cur = {B_C,B_C,B,B}---cur = {C,C}
        if(!curNodes.isEmpty()){
            curNodes = temp;
            return true;
        }
        //update global
        curNodes = temp;
        return false;
    }
    //rp1[rp3 and rp2]
    //{ABC,ABC,AB,ABB,AC,AC}
    @Override public Boolean visitFilAnd(XPathParser.FilAndContext ctx){
        Boolean l = (Boolean) visit(ctx.filter(0));//cur = {ABC,ABC,AB,ABB}
        Boolean r = (Boolean) visit(ctx.filter(1));
//        if (!curNodes.isEmpty()) return true;
//        else return false;
        return l&&r;
    }
    @Override public Boolean visitFilOr(XPathParser.FilOrContext ctx){
//        return (Boolean)visit(ctx.filter(0)) ||(Boolean) visit(ctx.filter(1));
        List<Node> temp = new ArrayList<>(curNodes);
        Boolean l = (Boolean)visit(ctx.filter(0));
        curNodes = temp;
        Boolean r = (Boolean)visit(ctx.filter(1));
//        if (!curNodes.isEmpty()) return true;
////        else return false;
        return l||r;
    }

    @Override public Boolean visitFilWithP(XPathParser.FilWithPContext ctx) {
        return (Boolean)visit(ctx.filter());
//        if(!curNodes.isEmpty()) return true;
//        else return false;
//

    }
    @Override public Boolean visitFilNot(XPathParser.FilNotContext ctx){
        return !(Boolean)visit(ctx.filter());
//       if(!curNodes.isEmpty()) return false;
//       else return true;

    }
    @Override public Boolean visitFilEqual(XPathParser.FilEqualContext ctx){
        List<Node> temp = new ArrayList<>(curNodes);
        List<Node> rpOne = (List<Node>) visit(ctx.rp(0));
        curNodes = temp;
        List<Node> rpTwo = (List<Node>) visit(ctx.rp(1));
        curNodes = temp;
        for (Node i : rpOne) {
            for (Node j : rpTwo) {
                if (i.isEqualNode(j)) {
                    return true;
                }
            }
        }
        return false;


    }
    @Override public Boolean visitFilIs(XPathParser.FilIsContext ctx){
        List<Node> temp = new ArrayList<>(curNodes);
        List<Node> rpOne = (List<Node>) visit(ctx.rp(0));
        curNodes = temp;
        List<Node> rpTwo = (List<Node>) visit(ctx.rp(1));
        curNodes = temp;
        for (Node i : rpOne) {
            for (Node j : rpTwo) {
                if (i.isSameNode(j)) {
                    return true;
                }
            }
        }
        return false;
    }


}
