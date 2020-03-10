import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.w3c.dom.Node;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

class TreeNode{
    List<TreeNode> children = new ArrayList<>();
    String TreeRoot; //  = appear index if it is a root
    String forXQ;
    String name;
    String parent;
}

class reWriteVisitor{
    Map<String,TreeNode> varToRoot = new HashMap<>();
    Map<String,TreeNode> varToNode = new HashMap<>();
    Map<String,String> varToStringEQ = new HashMap<>(); // al1 : "John"
    Map<List<String>,List<List<String>>> varToVarEQ = new HashMap<>();
    private void sortHelper(Set<String> set,List<List<String>> res){
        for(Map.Entry<List<String>, List<List<String>>> entry : varToVarEQ.entrySet()){
            String val1 = entry.getKey().get(0); String val2 = entry.getKey().get(1);
            if(res.contains(entry.getKey())) continue;
            else if(!set.contains(val1) && !set.contains(val2)) continue;
            else{
                res.add(entry.getKey());
                set.add(val1);
                set.add(val2);
            }
        }
    }
    public String findParent(String input){
        if(varToRoot.get(input)!=null) return input;
        else{
            return findParent(varToNode.get(input).parent);
        }
    }
    public List<List<String>> sort(){
        Set<String> set = new HashSet<>();
        List<List<String>> res = new ArrayList<>();
        Deque<List<String>> queue = new ArrayDeque<>(varToVarEQ.keySet());
        Iterator<List<String>> iterator = varToVarEQ.keySet().iterator();
        List<String> key = null;
        if(iterator.hasNext()){
            key = iterator.next();
        }
        set.add(key.get(0)); set.add(key.get(1));
        while(!queue.isEmpty()){
            List<String> node = queue.poll();
            if(res.contains(node)) continue;
            if(!set.contains(node.get(0)) && !set.contains(node.get(1))) {
                queue.offer(node);
                continue;
            }
            if(!res.contains(node) && (set.contains(node.get(0))|| set.contains(node.get(1)))){
                res.add(node);
                set.add(node.get(0));set.add(node.get(1));
                sortHelper(set,res);
            }

        }
        return res;
    }
    public String reWrite(InputStream in) throws IOException {
        CharStream stream = CharStreams.fromStream(in);
        XPathLexer lexer = new XPathLexer(stream);
        CommonTokenStream cts = new CommonTokenStream(lexer);
        XPathParser parser = new XPathParser(cts);
        XPathParser.XqContext  context = parser.xq();
        XPathParser.ForClauseContext forClauseContext = (XPathParser.ForClauseContext) context.getChild(0);
        XPathParser.WhereClauseContext whereClauseContext = (XPathParser.WhereClauseContext) context.getChild(1);
        XPathParser.ReturnClauseContext returnClauseContext = (XPathParser.ReturnClauseContext) context.getChild(2);
        //no join
        if(whereClauseContext==null) return null;

        //create varToNode and varToMap;
        for (int i = 0; i < forClauseContext.var().size(); ++i){
            String var = forClauseContext.var(i).getText().substring(1);
            String xq = forClauseContext.xq(i).getText();
            //create node & update map
            TreeNode tNode = new TreeNode();
            tNode.forXQ = xq;
            tNode.name = var;
            if (xq.startsWith("doc")) {
                tNode.TreeRoot = String.valueOf(i);
                tNode.parent = "root";
                varToRoot.put(var,tNode);
            }
            else{
                String parent = xq.substring(1,xq.indexOf("/"));
                tNode.parent = parent;
                if(varToRoot.get(parent)!=null) tNode.TreeRoot = parent;
                else tNode.TreeRoot = findParent(parent);
                //put itself to parent's children list
                Boolean isRoot = varToRoot.containsKey(parent);
                if(isRoot==true) varToRoot.get(parent).children.add(tNode);
                else varToNode.get(parent).children.add(tNode);
                varToNode.put(var,tNode);
            }
        }
        //create where: varToString and varToVar
        evalWhere(whereClauseContext.cond());
        StringBuilder res = new StringBuilder();
        res.append("for $tuple in ").append(getResult());
        //return clause
        res.append("\n").append(returnClauseContext.getText().replaceAll("\\$([a-zA-Z0-9_]+)", "\\$tuple/$1/*"));
        return res.toString();
    }

    public StringBuilder appendEQ(List<String> input){
        StringBuilder sb = new StringBuilder();
        List<List<String>> list = appendEQHelper(input);
        List<String> l1 = list.get(0);
        List<String> l2 = list.get(1);
        sb.append("[");
        for(String s: l1){
           sb.append(s).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("],");
        sb.append("[");
        for(String s: l2){
            sb.append(s).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb;
    }
    public List<List<String>> appendEQHelper(List<String> input){
        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();
        for(List<String> val : varToVarEQ.get(input)){
            l1.add(val.get(0));  l2.add(val.get(1));
        }
        List<List<String>> list = new ArrayList<>();
        list.add(l1); list.add(l2);
        return list;
    }

    public StringBuilder getResult(){
        Set<String> visited = new HashSet<>();
        StringBuilder joinAll = new StringBuilder();
        //no join
        if(varToRoot.size()<2) return null;
        List<List<String>> sequence = sort();
        for(int i = 0;i<sequence.size();i++){
            List<String> entry = sequence.get(i);
            String tree1 = entry.get(0);
            String tree2 = entry.get(1);
            if(tree1.equals(tree2)) continue;
            TreeNode n1 = varToNode.get(tree1)==null? varToRoot.get(tree1) : varToNode.get(tree1);
            TreeNode n2 = varToNode.get(tree2)==null? varToRoot.get(tree2) : varToNode.get(tree2);
            //both empty need flwr
            if(!visited.contains(tree1) && !visited.contains(tree2)){
                StringBuilder tree1Res = flwrRes(n1);
                StringBuilder tree2Res = flwrRes(n2);
                joinAll.append("join (\n").append(tree1Res).append(", ")
                        .append(tree2Res).append(", ")
                        .append(appendEQ(entry)).append(")");
                visited.add(tree1);
                visited.add(tree2);
            }
            else if(visited.contains(tree1) && !visited.contains(tree2)){
                visited.add(tree2);
                joinAll.insert(0,"join (\n")
                        .append(",\n").append(flwrRes(n2)).append(",")
                        .append(appendEQ(entry)).append(")");
            }
            else if(!visited.contains(tree1) && visited.contains(tree2)){
                visited.add(tree1);
                joinAll.insert(0,",").insert(0,flwrRes(n1))
                        .insert(0,"join (\n").append(",")
                        .append(appendEQ(entry)).append(")");
            }
            else if(visited.contains(tree1) && visited.contains(tree1)){//add eq
                List<List<String>> appendee = appendEQHelper(entry);
                int idx = joinAll.toString().lastIndexOf("}</tuple>");
                String appender =  joinAll.substring(idx+11,joinAll.toString().length()-2);
                joinAll.delete(idx + 10,joinAll.length());
                String[] parts = appender.split("],");
                String part1 = parts[0].substring(1); // a1 ,a2
                String part2 = parts[1].substring(1,parts[1].length()-1); // b1 ,b2
                List<String> part1List = new ArrayList<>(Arrays.asList(part1.split(",")));
                List<String> part2List = new ArrayList<>(Arrays.asList(part2.split(",")));
                part1List.addAll(appendee.get(0));
                part2List.addAll(appendee.get(1));
                joinAll.append("[");
                for(String s: part1List){
                    joinAll.append(s).append(",");
                }
                joinAll.deleteCharAt(joinAll.length()-1).append("],").append("[");
                for(String s: part2List){
                    joinAll.append(s).append(",");
                }
                joinAll.deleteCharAt(joinAll.length()-1).append("])");
            }
            else {
                System.out.println("No join happen");
            }
        }
        //handel not joined flwr, go to varToRoot and check if it's in set
        for(String t : varToRoot.keySet()){
            if(!visited.contains(t)) joinAll.append(",\n").append(flwrRes(varToRoot.get(t)));
        }
        return new StringBuilder(joinAll);
    }

    public StringBuilder flwrRes(TreeNode n1){ //n1 = root
        StringBuilder sb = new StringBuilder();
        Deque<TreeNode> queue = new ArrayDeque<>();
        List<TreeNode> children = new ArrayList<>();
        //string const in tree
        Map<String,String> rootToJoin = new HashMap<>();
        children.add(n1);
        queue.offer(n1);
        while(!queue.isEmpty()){
            TreeNode cur = queue.pollLast();
            List<TreeNode> nxt = cur.children;
            for(TreeNode n: nxt){
                    queue.offerFirst(n);
                    children.add(n);
                    if(varToStringEQ.get(n.name)!=null){
                        rootToJoin.put(n.name,varToStringEQ.get(n.name));
                    }
            }
        }
        sb.append("for ");
        for(TreeNode child: children){
            sb.append("$").append(child.name).append(" in ").append(child.forXQ).append(",\n");
        }
        sb.deleteCharAt(sb.length()-2); //delete "," and \n
        //add where
        if(rootToJoin.size()!=0){
            sb.append("where ");
            for(Map.Entry<String,String> entry:rootToJoin.entrySet()){
               sb.append("$").append(entry.getKey()).append(" eq ").append(entry.getValue()).append("and");
            }
            sb.delete(sb.length()-3,sb.length()).append("\n");
        }
        //add return
        sb.append("return <tuple>{");
        for(TreeNode child: children){
            sb.append("<").append(child.name).append(">{$").append(child.name).append("}</").append(child.name).append(">,\n");
        }
        sb.deleteCharAt(sb.length()-2); //delete "," and \n
        sb.append("}</tuple>");
        return sb;
    }

    public void evalWhere(XPathParser.CondContext condContext){
        if (condContext.getChild(1).getText().equals("and")) {
            this.evalWhere((XPathParser.CondContext) condContext.getChild(0));
            this.evalWhere((XPathParser.CondContext) condContext.getChild(2));
        } else if (condContext.getChild(1).getText().equals("=") || condContext.getChild(1).getText().equals("eq")) {
            String str1 = condContext.getChild(0).getText(); // af1
            String str2 = condContext.getChild(2).getText(); //af2
            // var to var
            if(str1.startsWith("$") && str2.startsWith("$") ){
                str1 = str1.substring(1); str2 = str2.substring(1);
                String str1Tree; String str2Tree; int idx1; int idx2;
                if(varToNode.get(str1)!=null){
                    str1Tree = varToNode.get(str1).TreeRoot;
                }
                else{
                    str1Tree =str1;
                }
                idx1 = Integer.parseInt(varToRoot.get(str1Tree).TreeRoot);
                if(varToNode.get(str2)!=null){
                    str2Tree = varToNode.get(str2).TreeRoot;
                }
                else{
                    str2Tree  = str2;
                }
                idx2 = Integer.parseInt(varToRoot.get(str2Tree).TreeRoot);
                //swap str1 & str2, str1Tree & str2Tree
                if(idx1 > idx2){
                    String temp = str1;
                    str1 = str2;
                    str2 = temp;
                    temp = str1Tree;
                    str1Tree = str2Tree;
                    str2Tree = temp;
                }
                List<String> key = new ArrayList<>();
                key.add(str1Tree);key.add(str2Tree);
                List<String> val = new ArrayList<>();
                val.add(str1); val.add(str2);
                if(varToVarEQ.containsKey(key)){
                    varToVarEQ.get(key).add(val);
                }
                else{
                    List<List<String>> r = new ArrayList<>();
                    r.add(val);
                    varToVarEQ.put(key,r);
                }
            }
            else {
                if(!str1.startsWith("$")) varToStringEQ.put(str2.substring(1),str1);
                else varToStringEQ.put(str1.substring(1),str2);
            }
        }
    }
}