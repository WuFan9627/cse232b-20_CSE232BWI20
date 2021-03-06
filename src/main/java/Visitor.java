import org.antlr.v4.runtime.tree.TerminalNode;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;
//update global & use returned res
class Key {
    ArrayList<Node> keyNodes;

    Key() {
        keyNodes = new ArrayList<>();
    }
    @Override
    public boolean equals(Object object){
        if(object == this){
            return true;
        }
        Key kobj = (Key)object;
        if(keyNodes.size() != kobj.keyNodes.size()){
            return false;
        }
        for(int i = 0; i < keyNodes.size(); i++){

            if (!keyNodes.get(i).getFirstChild().isEqualNode(kobj.keyNodes.get(i).getFirstChild())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode(){
        String s = "";
        for(Node n : keyNodes){
            s += n.getFirstChild().getTextContent();
        }
        return s.hashCode();
    }

}
class Visitor extends XPathBaseVisitor<Object>{
        List<Node> curNodes = new ArrayList<>();
        Map<String, List<Node>> globalVar = new HashMap<>();
        Document docNode = null;
        Document textNode = null;
        Node documentNode = null;
        //text and attribute
        //only store ducu and element
        public LinkedList<Node> getChildren(Node n){
            LinkedList<Node> nodes = new LinkedList<>();
            NodeList children = n.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                nodes.add(children.item(i));
            }
            return nodes;
        }

        public Node makeElem(String tag, List<Node> list){
            Node result = docNode.createElement(tag);
            for (Node node : list) {
                if (node != null) {
                    Node newNode = docNode.importNode(node, true);
                    result.appendChild(newNode);
                }
            }
            return result;
        }

        public Visitor(){
            try {
                DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance();
                DocumentBuilder docB = docBF.newDocumentBuilder();
                docNode = docB.newDocument();
                textNode = docB.newDocument();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }

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
                documentNode = doc;
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

            Deque<Node> queue = new ArrayDeque<>();
            if(documentNode!=null){
                queue.offerFirst(documentNode);
                //children.add(documentNode);
            }
            //store all the ele and root
            while(!queue.isEmpty()){
                Node cur = queue.pollLast();
                List<Node> nxt = getChildren(cur);
                for(Node n: nxt){
                    if(n.getNodeType()== Node.ELEMENT_NODE || n.getNodeType() == Node.DOCUMENT_NODE){//
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
                    if (children.item(i).getNodeType() == Node.ELEMENT_NODE || children.item(i).getNodeType() == Node.DOCUMENT_NODE
                    || children.item(i).getNodeType() == Node.TEXT_NODE) {
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
            Deque<Node> queue = new ArrayDeque<>(rpOne);
            //store all the ele and root
            while(!queue.isEmpty()){
                Node cur = queue.pollLast();
                List<Node> nxt = getChildren(cur);
                for(Node n: nxt){
                    if(n.getNodeType()== Node.ELEMENT_NODE || n.getNodeType() == Node.DOCUMENT_NODE){//
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
            if (rpOne== null || rpTwo == null || rpOne.isEmpty() || rpTwo.isEmpty()) { return false;}
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
            if (rpOne== null || rpTwo == null || rpOne.isEmpty() || rpTwo.isEmpty()) { return false;}
            for (Node i : rpOne) {
                for (Node j : rpTwo) {
                    if (i.isSameNode(j)) {
                        return true;
                    }
                }
            }
            return false;
        }
        @Override public List<Node> visitXqAp(XPathParser.XqApContext ctx){
            return (List<Node>) visit(ctx.ap());
        }
        @Override public List<Node> visitStringConst(XPathParser.StringConstContext ctx){
            String textName = ctx.StringConstant().getText().substring(1, ctx.StringConstant().getText().length()-1);
            Text textEle = textNode.createTextNode(textName);
            List<Node> result = new ArrayList<>();
            result.add(textEle);
            curNodes = result;
            return curNodes;
        }
        @Override public List<Node> visitXqwithP(XPathParser.XqwithPContext ctx) {
            return (List<Node>) visit(ctx.xq());
        }

        @Override public List<Node> visitXqRpSingleSlash(XPathParser.XqRpSingleSlashContext ctx) {
            curNodes = (List<Node>) visit(ctx.xq());
            return (List<Node>)visit(ctx.rp());
        }
        @Override public List<Node> visitXqRpDoubleSlash(XPathParser.XqRpDoubleSlashContext ctx) {

            //List<Node> temp = (List<Node>) visit(ctx.xq(0));
            List<Node> temp = (List<Node>) visit(ctx.xq());
            List<Node> result = new ArrayList<>(temp);
            Deque<Node> queue = new ArrayDeque<>(temp);
            //store all the ele and root
            while(!queue.isEmpty()){
                Node cur = queue.pollLast();
                List<Node> nxt = getChildren(cur);
                for(Node n: nxt){
                    if(n.getNodeType()== Node.ELEMENT_NODE || n.getNodeType() == Node.DOCUMENT_NODE){//
                        queue.offerFirst(n);
                        result.add(n);
                    }
                }
            }
            curNodes = result;
            visit(ctx.rp());
            return curNodes;
        }
        @Override public List<Node> visitXqConstructor(XPathParser.XqConstructorContext ctx) {
            List<Node> result = new ArrayList<>();
            List<Node> xqRes = (List<Node>)visit(ctx.xq());
            //if(xqRes.size()!=0){
                String tag = ctx.NAME(0).getText();
                result.add(makeElem(tag, xqRes));
                curNodes = result;

            //}
            return result;
        }

        @Override public List<Node> visitVariable(XPathParser.VariableContext ctx) {
            return globalVar.get(ctx.getText());
        }
        @Override public List<Node> visitTwoXqConcat(XPathParser.TwoXqConcatContext ctx){
            List<Node> temp = new ArrayList<>(curNodes);
            List<Node> rpOne = (List<Node>) visit(ctx.xq(0));
            curNodes = temp;
            List<Node> rpTwo = (List<Node>) visit(ctx.xq(1));
            rpOne.addAll(rpTwo);
            curNodes = rpOne;
            return rpOne;
        }

        @Override
        public List<Node> visitForClause(XPathParser.ForClauseContext ctx){
            return null;
        }
        public void flwrHelper(XPathParser.FLWRContext ctx, int level, List<Node> res, Map<String, XPathParser.XqContext> forResult){
            //base case
            if(level==ctx.forClause().var().size()){
                Map<String, List<Node>> oldContext = new HashMap<>(globalVar);
                if (ctx.letClause() != null) {
                    visit(ctx.letClause());
                }
                if (ctx.whereClause() ==null || ctx.whereClause() != null && (Boolean)visit(ctx.whereClause())) {
                    List<Node> c = (List<Node>) visit(ctx.returnClause());
                    if (c != null) {
                        res.addAll(c);
                    }
                }
                globalVar = oldContext;
                return;
            }
            else{
                String var = ctx.forClause().var(level).getText();
                XPathParser.XqContext  xqCon= forResult.get(ctx.forClause().var(level).getText());
                List<Node> varNodes = (List<Node>) visit(xqCon);
                //check docu
//                for(int i = 0;i< varNodes.size();i++){
//                    if(varNodes.get(i).getNodeType() == Node.DOCUMENT_NODE) varNodes.remove(varNodes.get(i));
//                }
                for (int i = 0;i<varNodes.size();i++){
                    Node n = varNodes.get(i);
                    List<Node> nList = new ArrayList<>();
                    nList.add(n);
                    globalVar.put(var, nList);
                    flwrHelper(ctx, level + 1, res,forResult);
                    globalVar.remove(var);

                }

            }
        }

        @Override public List<Node> visitFLWR(XPathParser.FLWRContext ctx){
            List<Node> result = new ArrayList<>();
            HashMap<String, List<Node>> oldContext = new HashMap<>(globalVar);
            Map<String, XPathParser.XqContext> forResult = new HashMap<>();
            int size = ctx.forClause().var().size();
            for(int i = 0;i<size;i++){
                XPathParser.VarContext var = ctx.forClause().var(i);
                XPathParser.XqContext xq = ctx.forClause().xq(i);
                forResult.put(var.getText(),xq);
            }
            flwrHelper(ctx, 0, result, forResult);
            globalVar = new HashMap<>(oldContext);
            curNodes = result;
            return result;
        }

        @Override public List<Node> visitReturnClause(XPathParser.ReturnClauseContext ctx) {
            return (List<Node>) visit(ctx.xq());
        }
        @Override public Boolean visitWhereClause(XPathParser.WhereClauseContext ctx) {
            return (Boolean) visit(ctx.cond());
        }
        @Override public List<Node> visitLetClause(XPathParser.LetClauseContext ctx){
            for (int i = 0; i < ctx.var().size(); i++) {
                globalVar.put(ctx.var(i).getText(), (List<Node>)visit(ctx.xq(i)));
            }
            return null;
        }

        @Override public Boolean visitXqEqual(XPathParser.XqEqualContext ctx){
            List<Node> temp = new ArrayList<>(curNodes);
            List<Node> rpOne = (List<Node>) visit(ctx.xq(0));
            curNodes = temp;
            List<Node> rpTwo = (List<Node>) visit(ctx.xq(1));
            curNodes = temp;
            if (rpOne== null || rpTwo == null || rpOne.isEmpty() || rpTwo.isEmpty()) { return false;}
            for (Node i : rpOne) {
                for (Node j : rpTwo) {
                    if (i.isEqualNode(j)) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public Boolean visitXqIs(XPathParser.XqIsContext ctx) {
            List<Node> temp = new ArrayList<>(curNodes);
            List<Node> rpOne = (List<Node>) visit(ctx.xq(0));
            curNodes = temp;
            List<Node> rpTwo = (List<Node>) visit(ctx.xq(1));
            curNodes = temp;
            if (rpOne== null || rpTwo == null || rpOne.isEmpty() || rpTwo.isEmpty()) { return false;}
            for (Node i : rpOne) {
                for (Node j : rpTwo) {
                    if (i.isSameNode(j)) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public Boolean visitXqCondOr(XPathParser.XqCondOrContext ctx) {
            List<Node> temp = new ArrayList<>(curNodes);
            Boolean l = (Boolean)visit(ctx.cond(0));
            curNodes = temp;
            Boolean r = (Boolean)visit(ctx.cond(1));
            return l||r;
        }

        @Override
        public Boolean visitXqCondNot(XPathParser.XqCondNotContext ctx) {
            return !(Boolean)visit(ctx.cond());
        }

        @Override
        public Boolean visitXqCondAnd(XPathParser.XqCondAndContext ctx) {
            Boolean l = (Boolean) visit(ctx.cond(0));//cur = {ABC,ABC,AB,ABB}
            Boolean r = (Boolean) visit(ctx.cond(1));
            return l&&r;
        }

        @Override
        public Boolean visitXqEmpty(XPathParser.XqEmptyContext ctx){
            List<Node> xqResult = (List<Node>)visit(ctx.xq());
            if(xqResult.isEmpty()){
                return true;
            }
            return false;
        }

        @Override
        public Boolean visitXqCondwithP(XPathParser.XqCondwithPContext ctx){
            return (Boolean)visit(ctx.cond());
        }

        @Override
        public Boolean visitXqSome(XPathParser.XqSomeContext ctx){
//        Map<String, List<Node>> oldContext = new HashMap<>(globalVar);
//        for (int i = 0; i < ctx.var().size(); i++) {
//            globalVar.put(ctx.var(i).getText(),(List<Node>)visit(ctx.xq(i)));
//        }
//        Boolean r = (Boolean)visit(ctx.cond());
//        globalVar = new HashMap<>(oldContext);
//        return r;
            Map<String, List<Node>> oldContext = new HashMap<>(globalVar);
            Boolean r = someHelper(ctx,0);
            globalVar = new HashMap<>(oldContext);
            return r;

        }
        public Boolean someHelper(XPathParser.XqSomeContext ctx, int level){
            //base case
            if(level==ctx.var().size()){
                Boolean r = (Boolean) visit(ctx.cond());
                return r;
            }
            else{
                String var = ctx.var(level).getText();
                List<Node> varNodes = (List<Node>) visit(ctx.xq(level));
                for (int i = 0;i<varNodes.size();i++){
                    Map<String, List<Node>> oldContext = new HashMap<>(globalVar);
                    Node n = varNodes.get(i);
                    List<Node> nList = new ArrayList<>();
                    nList.add(n);
                    globalVar.put(var, nList);
                    Boolean t = someHelper(ctx, level + 1);
                    globalVar = new HashMap<>(oldContext);
                    if(t){
                        return true;
                    }
                }

            }
            return false;
        }
        @Override
        public List<Node> visitNames(XPathParser.NamesContext ctx) { return null; }


        public String createKey(Node e, int i) throws TransformerException{
            List<Node> r = getChildren(e);
            Node newEle = makeElem("attr"+i, r);
            newEle.setNodeValue(e.getTextContent());
            StringWriter writer = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.transform(new DOMSource(newEle), new StreamResult(writer));
            return writer.toString();
        }
        @Override
        public List<Node> visitXqJoin(XPathParser.XqJoinContext ctx) {
            return (List<Node>) visit(ctx.joinClause());
        }

//        @Override
//        public List<Node> visitJoinClause(XPathParser.JoinClauseContext ctx) {
//            List<Node> res = new ArrayList<>();
//            List<Node> temp = new ArrayList<>(curNodes);
//            List<Node> left = (List<Node>) visit(ctx.xq(0));
//            curNodes = temp;
//            List<Node> right = (List<Node>) visit(ctx.xq(1));
//            List<String> lAttrs = new ArrayList<>();
//            for (TerminalNode n : ctx.names(0).NAME()) {
//                lAttrs.add(n.getText());
//            }
//            List<String> rAttrs = new ArrayList<>();
//            for (TerminalNode n : ctx.names(1).NAME()) {
//                rAttrs.add(n.getText());
//            }
//
//            //one attrs is empty
//            if (lAttrs.isEmpty() || rAttrs.isEmpty()) {
//                return res;
//            }
//
//            //for leftTuple : get Key and store in the hashMap
//            Map<String, List<Node>> map = new HashMap<>();
//
//            for (Node l : left) {
//                String key = "";
//                NodeList children = l.getChildNodes();
//                for (int i = 0; i < lAttrs.size(); i++) {
//                    String ls = lAttrs.get(i);
//                    for (int j = 0; j < children.getLength(); j++) {
//                        if (children.item(j).getNodeType() == Node.ELEMENT_NODE && children.item(j).getNodeName().equals(ls)) {
//                            // key = key + i + children.item(j).getTextContent();
//                            try {
//                                key += createKey(children.item(j), i);
//                            } catch (TransformerException ex) {
//                                ex.printStackTrace();
//                            }
//                            break;
//                        }
//                    }
//                }
//                map.putIfAbsent(key, new ArrayList<>());
//                map.get(key).add(l);
//            }
//            for (Node r : right) {
//                String key = "";
//                NodeList children = r.getChildNodes();
//                for (int i = 0; i < rAttrs.size(); i++) {
//                    String rs = rAttrs.get(i);
//                    for (int j = 0; j < children.getLength(); j++) {
//                        if (children.item(j).getNodeType() == Node.ELEMENT_NODE && children.item(j).getNodeName().equals(rs)) {
//                            try {
//                                key += createKey(children.item(j), i);
//                            } catch (TransformerException ex) {
//                                ex.printStackTrace();
//                            }
//                            break;
//                        }
//                    }
//                if (map.containsKey(key)) {
//                    for (Node l : map.get(key)) {
//                      List<Node> join = new ArrayList<>();
//                      join.addAll(getChildren(l));
//                      join.addAll(getChildren(r));
//                      res.add(makeElem(l.getNodeName(), join));
//
//                     }
//                  }
//                }
//
//            }
//            this.curNodes = res;
//            return res;
//        }
    @Override public List<Node> visitJoinClause(XPathParser.JoinClauseContext ctx) {
            List<Node> res = new ArrayList<>();
            List<Node> temp = new ArrayList<>(curNodes);
            List<Node> left = (List<Node>) visit(ctx.xq(0));
            curNodes = temp;
            List<Node> right = (List<Node>) visit(ctx.xq(1));
            List<String> lAttrs = new ArrayList<>();
            for (TerminalNode n : ctx.names(0).NAME()) {
                lAttrs.add(n.getText());
            }
            List<String> rAttrs = new ArrayList<>();
            for (TerminalNode n : ctx.names(1).NAME()) {
                rAttrs.add(n.getText());
            }

            if (lAttrs.isEmpty() && rAttrs.isEmpty()) {
                for (Node l : left) {
                    for (Node r : right) {
                        List<Node> join = new ArrayList<>();
                        join.addAll(getChildren(l));
                        join.addAll(getChildren(r));
                        res.add(makeElem(l.getNodeName(), join));
                    }
                }
                this.curNodes = res;
                return res;
            }

            //for leftTuple : get Key and store in the hashMap
            Map<Key, List<Node>> map = new HashMap<>();
            for (Node l : left) {
                Key key = new Key();
                NodeList children = l.getChildNodes();
                for (String ls: lAttrs) {
                    for (int i = 0; i < children.getLength(); i++) {
                        if (children.item(i).getNodeType() == Node.ELEMENT_NODE && children.item(i).getNodeName().equals(ls)) {
                            key.keyNodes.add(children.item(i));
                            break;
                        }
                    }
                }
                map.putIfAbsent(key, new ArrayList<>());
                map.get(key).add(l);
            }
            for (Node r : right) {
                Key key = new Key();
                NodeList children = r.getChildNodes();
                for (String rs: rAttrs) {
                    for (int i = 0; i < children.getLength(); i++) {
                            if (children.item(i).getNodeType() == Node.ELEMENT_NODE && children.item(i).getNodeName().equals(rs)) {
                                key.keyNodes.add(children.item(i));
                                break;
                            }
                    }
                }
                if (map.containsKey(key)){
                    for (Node l : map.get(key)) {
                           List<Node> join = new ArrayList<>();
                           join.addAll(getChildren(l));
                           join.addAll(getChildren(r));
                           res.add(makeElem(l.getNodeName(), join));
                    }
                }
//                if (map.containsKey(key)) {
//                    for (Node l : map.get(key)) {
//                        Node clone = l.cloneNode(true);
//                        for (int j = 0; j < children.getLength(); j++) {
//                            Node curElement = children.item(j);
//                            Node newNode = l.getOwnerDocument().importNode(curElement, true);
//                            clone.appendChild(newNode);
//                        }
//                        res.add(clone);
//                    }
//                }
            }
            this.curNodes = res;
            return res;
        }


    }