import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/* Class containing left and right child of current
   node and key value*/
class Node {
    int ID;
    List<Node> children;
    Node parent;
    int parentID;
    int depth;
    String name;
    String path;
    boolean isHidden;
    String prefix;

    public Node() {
        InitializeRoot();
    }

    public Node(int ID, String name, String path, boolean isHidden, int parent) {

        this.parentID = parent;
        this.ID = ID;
        this.name = name;
        this.path = path;
        this.isHidden = isHidden;
        this.parent.addChildren(this);

    }

    public void InitializeRoot() {
        this.ID = 0;
        this.name = "ROOT";
        this.depth = 0;
        this.path = "";
        this.children = new ArrayList<>();
        this.isHidden = true;
        this.parent = null;
        this.prefix = "";
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<Node> getSiblings() {
        if (this.name == "ROOT") return new ArrayList<>();
        return (ArrayList<Node>) this.parent.children;
    }

    public void addChildren(Node child) {
        if (this.children != null) {
            this.children.add(child);
            return;
        }
        this.children = new ArrayList<>();
        this.children.add(child);
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return prefix + name;
    }
}

class Tree {
    Node root;
    HashMap<Integer, Node> nodeArray;

    public Tree() {
        this.root = new Node();
        nodeArray = new HashMap<>();
    }

    public void setNode(Node node) {
        nodeArray.put(node.ID, node);
    }

    public Node getNode(int ID) {
        System.out.println(nodeArray.get(ID));
        return nodeArray.get(ID);
    }

    public void pasteInorder() {
        Stack<Node> stack = new Stack<>();
        boolean visited[] = new boolean[treeSize()];
        stack.add(this.root);
        while (!stack.isEmpty()) {
            ArrayList<Node> list = stack.peek().getSiblings();
            if (list.size() == 0) {
                stack.pop();
            }
            boolean f = false;
            for (Node n : list) {
                if (!visited[n.getID()]) {
                    if (!n.isHidden)
                        System.out.println(n);
                    stack.push(n);
                    f = true;
                    break;
                }
            }
            if (f)
                stack.pop();
        }
    }

    private int treeSize() {
        return nodeArray.size();
    }

    public void addNode(Node n) {
        nodeArray.put(n.ID, n);
    }


}


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("C:\\Users\\MarinGligorov\\Desktop\\Navigation.csv");
        Scanner scan = new Scanner(new FileReader(f));
        scan.nextLine();
        StringBuilder sb = new StringBuilder();
        int lines = 0;
        while (scan.hasNext()) {
            lines++;
        }
        String text = sb.toString();
        scan.close();
        scan = new Scanner(text);
        HashMap<Integer, Node> map = new HashMap<>();
        for (int i = 0; i < lines; i++) {
            String str[] = scan.nextLine().split(";");
            int parent = -1;
            if(!str[2].equals("NULL")) parent = Integer.parseInt(str[2]);
            int ID = Integer.parseInt(str[0]);
            String name = str[1];
            boolean isHidden = Boolean.parseBoolean(str[3]);
            String path = str[4];
            Node n = new Node(ID, name, path, isHidden, parent);
            map.put(ID, n);
        }
        Tree tree = new Tree();
        for(Node n: map.values())
        {
            n.parent = map.get(n.parentID);
            tree.setNode(n);
        }
        tree.pasteInorder();
    }
}