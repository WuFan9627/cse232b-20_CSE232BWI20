
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

class TreeNode{
    List<TreeNode> children = new ArrayList<>();
    String TreeRoot; //  = appear index if it is a root
    String forXQ;
    String name;
    String parent;
    int height;//valid if it is treeRoot;
    int cp;
    List<List<String>> joinCon = new ArrayList<>();
}


class reWriteVisitor{
//    Map<String, String> joinList = new HashMap<>();
//    Map<String, StringBuilder> resList = new HashMap<>();
//    List<List<String>> left = new ArrayList<>();
//    List<List<String>> unrelated = new ArrayList<>();
    Map<String,TreeNode> varToRoot = new HashMap<>();
    Map<String,TreeNode> varToNode = new HashMap<>();
    Map<String,String> varToStringEQ = new HashMap<>(); // al1 : "John"
    Map<List<String>,List<List<String>>> varToVarEQ = new HashMap<>();
//            (new Comparator<List<String>>(){
//        @Override
//        public int compare(List<String> o1, List<String> o2){
//            int idxl10 = Integer.parseInt(varToNode.get(o1.get(0)).TreeRoot);
//            int idxl11 = Integer.parseInt(varToNode.get(o1.get(1)).TreeRoot);
//            int idxl20 = Integer.parseInt(varToNode.get(o2.get(0)).TreeRoot);
//            int idxl21 = Integer.parseInt(varToNode.get(o2.get(1)).TreeRoot);
//            if (idxl10<idxl20) return -1;
//            else if(idxl10>idxl20) return 1;
//            else if(idxl11 < idxl21) return -1;
//            else return 1;
//        }
//    });
    int getHeight(TreeNode n){
        //basecase
        if(varToRoot.get(n.name)!=null) return 0;
        int childHeight = 0;
        for(TreeNode child: n.children){
            int temp = getHeight(child);
            if(temp>childHeight) childHeight = temp;
        }
        return childHeight+1;
    }
    private void sortHelper(Set<String> set,List<List<String>> res,Map<List<String>,List<List<String>>> inputMap){
        for(Map.Entry<List<String>, List<List<String>>> entry : inputMap.entrySet()){
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
    public List<List<String>> sort(Map<List<String>,List<List<String>>> inputMap){
        Set<String> set = new HashSet<>();
        List<List<String>> res = new ArrayList<>();
        Deque<List<String>> queue = new ArrayDeque<>(inputMap.keySet());
        Iterator<List<String>> iterator = inputMap.keySet().iterator();
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
                sortHelper(set,res,inputMap);
            }

        }
        return res;
    }
//    public void separate(){
//        int maxNum = 0;
//        List<List<List<String>>> sequence = new ArrayList<>();
//        for(List<String> list: varToVarEQ.keySet()){
//            List<List<List<String>>> res =  separateHelper(list);
//            if(res.get(0).size()>maxNum){
//                maxNum = res.get(0).size();
//                sequence = new ArrayList<>(res);
//
//            }
//        }
//        unrelated = sequence.get(0);
//        left = sequence.get(1);
//    }
//    public List<List<List<String>>> separateHelper(List<String> input){
//        List<List<List<String>>> res = new ArrayList<>();
//        Set<String> set = new HashSet<>();
//        List<List<String>> temp_unrelated = new ArrayList<>();
//        List<List<String>> temp_left = new ArrayList<>();
//        set.add(input.get(0));set.add(input.get(1));
//        temp_unrelated.add(input);
//        for(List<String> list: varToVarEQ.keySet()){
//            String s1 = list.get(0);
//            String s2 = list.get(1);
//            if(!set.contains(s1) &&!set.contains(s2) ){
//                temp_unrelated.add(list);
//                set.add(s1); set.add(s2);
//            }
//            else if(set.contains(s1) &&set.contains(s2) ){
//                continue;
//            }
//            else{
//                temp_left.add(list);
//            }
//        }
//        res.add(temp_unrelated);res.add(temp_left);
//      return res;
//    }

//    Set<List<List<String>>> separate(){
//        Set<List<List<String>>> groups = new HashSet<>();
//        Map<List<String>,List<List<String>>> unjoinedTrees = new HashMap<>(varToVarEQ);
//        while(unjoinedTrees.size()!=0){
//            List<List<String>> sequence = sort(unjoinedTrees);
//            List<List<String>> g = new ArrayList<>();
//            for(List<String> s: sequence){
//                g.add(s);
//            }
//            groups.add(g);
//            for(List<String> s: sequence) {
//                unjoinedTrees.remove(s);
//            }
//        }
//        return groups;
//    }
    public String reWrite(InputStream in, Boolean flag) throws IOException{
        CharStream stream = CharStreams.fromStream(in);
        XPathLexer lexer = new XPathLexer(stream);
        CommonTokenStream cts = new CommonTokenStream(lexer);
        XPathParser parser = new XPathParser(cts);
        XPathParser.XqContext  context = parser.xq();
        XPathParser.ForClauseContext forClauseContext = (XPathParser.ForClauseContext) context.getChild(0);
        //check if xq = join
        for (int i = 0; i < forClauseContext.var().size(); ++i){
            String xq = forClauseContext.xq(i).getText();
            if(xq.indexOf("join")!=-1) return null;
        }
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
            tNode.cp = 0;
            if (xq.startsWith("doc")) {
                tNode.TreeRoot = String.valueOf(i);
                tNode.parent = "root";
                tNode.height = 0;
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
        StringBuilder getRes = new StringBuilder();
        if(flag) getRes = getResult();
        else getRes = getResultBushy();
        if(getRes==null) return null;
        res.append("for $tuple in ").append(getRes);
        //return clause
        res.append("\n").append(returnClauseContext.getText().replaceAll("\\$([a-zA-Z0-9_]+)", "\\$tuple/$1/*"));
        return res.toString();
    }

    public StringBuilder appendEQ(List<String> input, Boolean reverse){
        StringBuilder sb = new StringBuilder();
        List<List<String>> list = new ArrayList<>();
        if(reverse==false) list = appendEQHelper(input);
        else {
            List<List<String>> temp  = appendEQHelper(input);
            list.add(temp.get(1));
            list.add(temp.get(0));
        }
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
    int getIndex(List<String> input, Map<String, Integer> weights){
        int w = 0;
        for(String s: input){
            w += weights.get(s);
        }
        return w;
    }
    TreeNode getMinHeightTree(List<List<String>> joinTreeList){
        //get tree set
        Set<String> treeSet = new HashSet<>();
        for(List<String> tree: joinTreeList){
            treeSet.add(tree.get(0));
            treeSet.add(tree.get(1));
        }
        int size = treeSet.size();
        TreeNode[] dp = new TreeNode[(int) Math.pow(2,size)];
        //create tree weight map
        Map<String, Integer> weights = new HashMap<>();
        int count = 0;
        for(String s: treeSet){
            weights.put(s, (int) Math.pow(2,count));
            count++;
        }
        //initialization dp array
        for(String s: treeSet){
            List<String> singleList = new ArrayList<>();
            singleList.add(s);
            int index = getIndex(singleList,weights);
            dp[index] = varToRoot.get(s);
        }
        List<String> setList = new ArrayList<>(treeSet);

        for(int i = 2;i<Math.pow(2,size);i++){
            List<String> S = new ArrayList<>();
            for(int j = 1; j <= size;j++){
                int idx = (int) Math.floor((i/Math.pow(2,j-1)));
                if(idx%2==1){
                    S.add(setList.get(j-1));
                }
            }
            if(S.size()==0) continue;
            List<List<String>> subsets = getSubsets(S);
            for(List<String> s1 : subsets){
                List<String> s2 = new ArrayList<>(S);
                for(String s : s1){
                    s2.remove(s);
                }
                if(s1.size()==0 || s2.size()==0) continue;

                int index1 = getIndex(s1,weights);
                int index2 = getIndex(s2,weights);
                if(dp[index1] == null || dp[index2] == null){
                    continue;
                }
                TreeNode joinedRoot = new TreeNode();
                TreeNode n1 = dp[index1];
                TreeNode n2 = dp[index2];
                joinedRoot.name = n1.name + n2.name;
                joinedRoot.children.add(n1);
                joinedRoot.children.add(n2);
                joinedRoot.height = getHeight(joinedRoot);
                joinedRoot.cp = n1.cp + n2.cp;

                if(noJoinCon(n1,n2).size()==0){
                        joinedRoot.cp++;
                }
                else{
                    joinedRoot.joinCon = noJoinCon(n1,n2);
                }
                int subsetIdx = getIndex(S,weights);
                if(dp[subsetIdx]==null || (dp[subsetIdx].height > joinedRoot.height && dp[subsetIdx].cp >= joinedRoot.cp)){
                        dp[subsetIdx] = joinedRoot;
                }
            }
        }

        return dp[getIndex(setList,weights)];
    }
    public List<List<String>> noJoinCon(TreeNode n1, TreeNode n2){
        List<List<String>> res = new ArrayList<>();
        List<TreeNode> descent1 = new ArrayList<>();
        List<TreeNode> descent2 = new ArrayList<>();
        if(varToRoot.get(n1.name)!=null && varToRoot.get(n2.name)!=null){
            List<String> pair1 = new ArrayList<>();
            List<String> pair2 = new ArrayList<>();
            pair1.add(n1.name);pair1.add(n2.name);
            pair2.add(n2.name);pair2.add(n1.name);
            if(varToVarEQ.get(pair1)!= null){
                res.add(pair1);
                return res;
            }
            else if(varToVarEQ.get(pair2)!= null){
                res.add(pair2);
                return res;
            }
            else return new ArrayList<>();
        }
        else if(varToRoot.get(n1.name)!=null || varToRoot.get(n2.name)!=null){
            List<TreeNode> descent = new ArrayList<>();
            TreeNode leaf = new TreeNode();
            if(varToRoot.get(n1.name)==null ){
                descent = getDescent(n1); leaf = n2;
            }
            else {
                descent = getDescent(n2); leaf = n1;
            }
            for(TreeNode child_d : descent){
                List<String> pair1 = new ArrayList<>();
                List<String> pair2 = new ArrayList<>();
                pair1.add(child_d.name);pair1.add(leaf.name);
                pair2.add(leaf.name);pair2.add(child_d.name);
                if(varToVarEQ.get(pair1)!= null){
                    res.add(pair1);
                }
                else if(varToVarEQ.get(pair2)!= null){
                    res.add(pair2);
                }
            }
            return res;

        }
        else{
            descent1 = getDescent(n1);
            descent2 = getDescent(n2);
            for(TreeNode child1 : descent1){
                for(TreeNode child2 : descent2){
                    List<String> pair1 = new ArrayList<>();
                    List<String> pair2 = new ArrayList<>();
                    pair1.add(child1.name);pair1.add(child2.name);
                    pair2.add(child2.name);pair2.add(child1.name);
                    if(varToVarEQ.get(pair1)!= null){
                        res.add(pair1);
                    }
                    else if(varToVarEQ.get(pair2)!= null){
                        res.add(pair2);
                    }
                }
            }
            return res;
        }

    }
    public  List<TreeNode> getDescent(TreeNode n){
        List<TreeNode> des = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(n); des.add(n);
        while(!queue.isEmpty()){
            TreeNode p = queue.poll();
            for(TreeNode child: p.children){
                queue.offer(child);
                des.add(child);
            }
        }
        return des;
    }
    public List<List<String>> getSubsets(List<String> treeSet) {
        List<List<String>> output = new ArrayList();
        output.add(new ArrayList<String>());

        for (String s : treeSet) {
            List<List<String>> newSubsets = new ArrayList();
            for (List<String> curr : output) {
                newSubsets.add(new ArrayList<String>(curr){{add(s);}});
            }
            for (List<String> curr : newSubsets) {
                output.add(curr);
            }
        }
        return output;
    }
//    public void replace(String a, String b ){
//        for(String s: joinList.keySet()){
//            String v = joinList.get(s);
//            if (v.equals(a)){
//                joinList.put(s,b);
//            }
//        }
//    }

    public StringBuilder getResultBushy(){

        List<List<String>> joinTreeList = new ArrayList<>();
        for(List<String> key : varToVarEQ.keySet()){
            joinTreeList.add(key);
        }
        return bushyHelper(getMinHeightTree(joinTreeList));
    }
    public StringBuilder bushyHelper(TreeNode n) {

        if (varToRoot.get(n.name) != null) {
            return flwrRes(n);
        }
        TreeNode n1 = n.children.get(0);
        TreeNode n2 = n.children.get(1);
        StringBuilder sb1 = bushyHelper(n1);
        StringBuilder sb2 = bushyHelper(n2);
        StringBuilder sb = new StringBuilder();
        sb.append("join (\n").append(sb1).append(", ")
                    .append(sb2).append(", ");
        List<String> leftNodeName = new ArrayList<>();
        for(TreeNode leftNode: getDescent(n.children.get(0))){
            leftNodeName.add(leftNode.name);
        }
        List<String> left = new ArrayList<>();
        List<String> right = new ArrayList<>();
        List<String> leftList = new ArrayList<>();
        List<String> rightList = new ArrayList<>();
        if(n.joinCon.size()==0){
            sb.append("[],[])");
            return sb;
        }
        for(List<String> entry : n.joinCon){
            if(leftNodeName.contains(entry.get(0))){
                left.add(entry.get(0));right.add(entry.get(1));
            }
            else{
                left.add(entry.get(1));right.add(entry.get(0));
            }
        }
        for(int i = 0;i<left.size();i++){
            List<String> temp = new ArrayList<>();
            temp.add(left.get(i)); temp.add(right.get(i));
            if(varToVarEQ.get(temp) != null){
                List<List<String>> eq = appendEQHelper(temp);
                for(String s : eq.get(0)){
                    leftList.add(s);
                }
                for(String s : eq.get(1)){
                    rightList.add(s);
                }
            }
            else{
                temp = new ArrayList<>();
                temp.add(right.get(i)); temp.add(left.get(i));
                List<List<String>> eq = appendEQHelper(temp);
                for(String s : eq.get(1)){
                    leftList.add(s);
                }
                for(String s : eq.get(0)){
                    rightList.add(s);
                }

            }
        }
        sb.append("[");
        for(String s: leftList){
            sb.append(s).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("],");
        sb.append("[");
        for(String s: rightList){
            sb.append(s).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("] )");
//        List<String> entry = new ArrayList<>();
//        if(varToRoot.get(n1.name)!=null && varToRoot.get(n2.name)!=null ){
//            int idx1 = Integer.parseInt(n1.TreeRoot);
//            int idx2 = Integer.parseInt(n2.TreeRoot);
//            if(idx1 < idx2){
//                entry.add(n1.name); entry.add(n2.name);
//            }
//            else{
//                entry.add(n2.name); entry.add(n1.name);
//            }
//            if(varToVarEQ.get(entry) != null){
//                sb.append(appendEQ(entry,false)).append(")");
//            }
//            else{
//                sb.append("[],[] ").append(")");
//            }
//        }
//        else if(varToRoot.get(n1.name)!=null || varToRoot.get(n2.name)!=null ){
//
//        }


        return sb;
    }


//    public StringBuilder getResultBushy(){
//        //no join
//        if(varToVarEQ.size()==0) return null;
//        separate();
//
//        for(List<String> l : unrelated){
//            String tree1 = l.get(0);
//            String tree2 = l.get(1);
//            if(tree1.equals(tree2)) continue;
//            StringBuilder joinAll = new StringBuilder();
//            TreeNode n1 = varToNode.get(tree1)==null? varToRoot.get(tree1) : varToNode.get(tree1);
//            TreeNode n2 = varToNode.get(tree2)==null? varToRoot.get(tree2) : varToNode.get(tree2);
//            StringBuilder tree1Res = flwrRes(n1);
//            StringBuilder tree2Res = flwrRes(n2);
//            joinAll.append("join (\n").append(tree1Res).append(", ")
//                    .append(tree2Res).append(", ")
//                    .append(appendEQ(l,false)).append(")");
//            resList.put(tree1, joinAll);
//            joinList.put(tree2, tree1);
//        }
//        for(List<String> l : left){
//            String s1 = l.get(0); String s2 = l.get(1);
//            if((resList.containsKey(s1) && joinList.containsKey(s2)) || (resList.containsKey(s2) && joinList.containsKey(s1))){
//                Boolean rev = false;
//                if(resList.containsKey(s2) && joinList.containsKey(s1)){
//                    String temp = s1;s1 = s2;s2 = temp; //swap, s1 in resList, s2 in joinList
//                    rev = true;
//                }
//                if(s1.equals(joinList.get(s2))){
//                    StringBuilder sb = resList.get(s1);
//                    sb = appendEQToRes(sb,l);
//                    resList.put(s1, sb);
//                }
//                else{
//                    StringBuilder sb = resList.get(s1);
//                    sb.insert(0,"join (\n").append(",\n").append(resList.get(joinList.get(s2))).append(",")
//                            .append(appendEQ(l,rev)).append(")");
//                    String t = joinList.get(s2);
//                    resList.put(s1, sb);
//                    resList.remove(t);
//                    joinList.put(joinList.get(s2),s1);
//                    //find all joinList.get(s2) in joinList and replace them with s1
//                    replace(t,s1);
//                }
//
//            }
//            else if((!resList.containsKey(s1) && !joinList.containsKey(s1) && resList.containsKey(s2)) ||
//                    (!resList.containsKey(s2) && !joinList.containsKey(s2) && resList.containsKey(s1)) ){
//                Boolean rev = false;
//                if(!resList.containsKey(s1) && !joinList.containsKey(s1)){
//                    String temp = s1;s1 = s2;s2 = temp; //swap, s1 in resList, s2 is empty
//                    rev = true;
//                }
//                TreeNode n2 = varToNode.get(s2)==null? varToRoot.get(s2) : varToNode.get(s2);
//                StringBuilder sb = resList.get(s1);
//                sb.insert(0,"join (\n").append(",\n").append(flwrRes(n2)).append(",")
//                        .append(appendEQ(l,rev)).append(")");
//                resList.put(s1, sb);
//                joinList.put(s2,s1);
//
//            }
//            else if((!resList.containsKey(s1) && !joinList.containsKey(s1) && joinList.containsKey(s2)) ||
//                    (!resList.containsKey(s2) && !joinList.containsKey(s2) && joinList.containsKey(s1)) ){
//                Boolean rev = false;
//                if(!resList.containsKey(s1) && !joinList.containsKey(s1)){
//                    String temp = s1;s1 = s2;s2 = temp; //swap, s1 in joinList, s2 is empty
//                    rev = true;
//                }
//                TreeNode n2 = varToNode.get(s2)==null? varToRoot.get(s2) : varToNode.get(s2);
//                StringBuilder sb =resList.get(joinList.get(s1));
//                sb.insert(0,"join (\n").append(",\n").append(flwrRes(n2)).append(",")
//                        .append(appendEQ(l,rev)).append(")");
//                resList.put(joinList.get(s1), sb);
//                joinList.put(s2,joinList.get(s1));
//
//
//            }
//            else if(resList.containsKey(s1) && resList.containsKey(s2)){
//                StringBuilder sb =resList.get(s1);
//                sb.insert(0,"join (\n").append(",\n").append(resList.get(s2)).append(",")
//                        .append(appendEQ(l,false)).append(")");
//                resList.put(s1, sb);
//                replace(s2,s1);
//                joinList.put(s2,s1);
//                resList.remove(s2);
//            }
//            else if(joinList.containsKey(s1) && joinList.containsKey(s2)){
//                if(joinList.get(s1).equals(joinList.get(s2))){
//                    //append eq
//                    StringBuilder sb =resList.get(joinList.get(s1));
//                    sb = appendEQToRes(sb,l);
//                    resList.put(joinList.get(s1), sb);
//                }
//                else{
//                    StringBuilder sb =resList.get(joinList.get(s1));
//                    StringBuilder sb2 =resList.get(joinList.get(s2));
//                    sb.insert(0,"join (\n").append(",\n").append(sb2).append(",")
//                            .append(appendEQ(l,false)).append(")");
//                    joinList.put(s2, joinList.get(s1));
//                    resList.remove(joinList.get(s2));
//                    resList.put(joinList.get(s1),sb);
//                }
//            }
//        }
//        if(resList.size()==1){
//            for(Map.Entry<String, StringBuilder> entry: resList.entrySet()){
//                StringBuilder value = entry.getValue();
//                return value;
//            }
//        }else{
//            return unionRes();
//        }
//        return null;
//    }
//    public StringBuilder unionRes(){
//        StringBuilder sb = new StringBuilder();
//        List<StringBuilder> list = new ArrayList<>();
//        for(StringBuilder sbEle : resList.values()){
//            list.add(sbEle);
//        }
//        for(int i = 0; i <list.size();i = i+2){
//            if(i==0){
//                sb.append("join (\n").append(list.get(i)).append(",\n").append(list.get(i+1)).append(",")
//                        .append("[],[] ").append(")");
//            }
//            else if(i+1==list.size()){
//                sb.append(",").append(list.get(i)).append("[],[] ").append(")");
//                sb.insert(0,"join (\n").append("[],[] ").append(")");
//            }
//            else{
//                sb.append(",").append("join (\n").append(list.get(i)).append(",\n").append(list.get(i+1)).append(",")
//                        .append("[],[] ").append(")");
//            }
//        }
//        if(list.size() %2 ==0){
//            sb.insert(0,"join (\n").append(", [],[] ").append(")");
//        }
//        return sb;
//    }
    public StringBuilder appendEQToRes(StringBuilder sb, List<String> l){
        List<List<String>> appendee = appendEQHelper(l);
        int idx = sb.toString().lastIndexOf("}</tuple>");
        String appender =  sb.substring(idx+11,sb.toString().length()-2);
        sb.delete(idx + 10,sb.length());
        String[] parts = appender.split("],");
        String part1 = parts[0].substring(0, parts[0].length()); // a1 ,a2
        String part2 = parts[1].substring(1,parts[1].length()); // b1 ,b2
        List<String> part1List = new ArrayList<>(Arrays.asList(part1.split(",")));
        List<String> part2List = new ArrayList<>(Arrays.asList(part2.split(",")));
        part1List.addAll(appendee.get(0));
        part2List.addAll(appendee.get(1));
        sb.append("[");
        for(String s: part1List){
            sb.append(s).append(",");
        }
        sb.deleteCharAt(sb.length()-1).append("],").append("[");
        for(String s: part2List){
            sb.append(s).append(",");
        }
        sb.deleteCharAt(sb.length()-1).append("])");
        return sb;
    }
    public StringBuilder getResult(){
        Set<String> visited = new HashSet<>();
        StringBuilder joinAll = new StringBuilder();
        //no join
        if(varToVarEQ.size()==0) return null;
        List<List<String>> sequence = sort(varToVarEQ);
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
                        .append(appendEQ(entry,false)).append(")");
                visited.add(tree1);
                visited.add(tree2);
            }
            else if(visited.contains(tree1) && !visited.contains(tree2)){
                visited.add(tree2);
                joinAll.insert(0,"join (\n")
                        .append(",\n").append(flwrRes(n2)).append(",")
                        .append(appendEQ(entry,false)).append(")");
            }
            else if(!visited.contains(tree1) && visited.contains(tree2)){
                //reverse the eq list
                visited.add(tree1);
//                joinAll.insert(0,",").insert(0,flwrRes(n1))
//                        .insert(0,"join (\n").append(",")
//                        .append(appendEQ(entry,false)).append(")");
                joinAll.insert(0,"join (\n")
                        .append(",\n").append(flwrRes(n1)).append(",")
                        .append(appendEQ(entry,true)).append(")");
            }
            else if(visited.contains(tree1) && visited.contains(tree1)){//add eq
                 joinAll = appendEQToRes(joinAll,entry);
            }
            else {
                System.out.println("No join happen");
            }
        }
        //handel not joined flwr
        for(String t : varToRoot.keySet()){
            if(!visited.contains(t)) return null;
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
            this.evalWhere((XPathParser.CondContext) condContext.getChild(2));}
        else if (condContext.getChild(1).getText().equals("=") || condContext.getChild(1).getText().equals("eq")) {
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