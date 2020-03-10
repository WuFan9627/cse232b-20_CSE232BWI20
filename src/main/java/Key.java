import org.w3c.dom.*;
import java.util.ArrayList;

public class Key {
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
