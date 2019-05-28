import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

class Node implements Comparable<Node>
{
    String name;
    String path;
    boolean isHidden = false;
    String prefix;
    public Node(String path, String name, boolean isHidden){
        this.name = name;
        this.path = path;
        this.prefix = setPrefix(path);
        this.isHidden = isHidden;
}
    public static String setPrefix(String path)
    {
        int num = path.split("/").length -2;
        StringBuilder sb = new StringBuilder();
        sb.append(".");
        for (int i = 0; i < num; i++) {
            sb.append("...");
        }
        return sb.toString();
    }
    public String toString()
    {
        return prefix + name;
    }

    @Override
    public int compareTo(Node o) {
        return this.path.compareTo(o.path);
    }
}


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("C:\\Users\\MarinGligorov\\Desktop\\Navigation.csv");
        Scanner scan = new Scanner(new FileReader(f));
        scan.nextLine();
        TreeSet<Node> nodes = new TreeSet<Node>(Node::compareTo);
        while (scan.hasNext())
        {
            String s = scan.nextLine();
            String str[] = s.split(";");
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(str));
            String name = list.get(1);
            String path = list.get(4);
            boolean b = Boolean.getBoolean(str[3]);
            Node node = new Node(path, name, b);
            nodes.add(node);
        }
        for(Node n: nodes)
        {
            if(!n.isHidden)
            System.out.println(n);
        }
    }
}