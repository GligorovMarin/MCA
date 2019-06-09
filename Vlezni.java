import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

class Item {
    int ID, ParentID;
    String URL;
    String Name;
    boolean isHidden;

    public Item(int ID, int parentID, String URL, String Name, boolean isHidden) {
        this.ID = ID;
        ParentID = parentID;
        this.URL = URL;
        this.Name = Name;
        this.isHidden = isHidden;
    }


    public String getItemName()
    {
        return Name;
    }

}

class Tree {
    java.util.Map<Integer, TreeSet<Item>> Map;

    public Tree(){
        Map=new HashMap<>();

    }

    public void addChild(int parentID, Item child){
        Map.computeIfAbsent(parentID,k->new TreeSet<Item>(Comparator.comparing(Item::getItemName)));
        Map.computeIfPresent(parentID,(k,v)->{v.add(child);return v;});
    }

    public void printItem(Integer parentID, int level){

        TreeSet<Item> temp = Map.getOrDefault(parentID, null);
        if(temp!=null)
            temp.stream().forEach(e->{if(!e.isHidden){System.out.print(".");for(int i=0; i<level; i++)System.out.print("...");System.out.print(e.Name+"\n");printItem(e.ID,level+1);}});

    }

    public void printItem()
    {
        printItem(0,0);
    }
}



public class Vlezni {

    public static void main(String[] args) throws IOException {

        Path path= Paths.get("Navigation.csv");
        BufferedReader reader=new BufferedReader(new FileReader(new File(path.toAbsolutePath().toString())));

        String line=reader.readLine();

        Tree tree=new Tree();


        while((line=reader.readLine())!=null){
            String[] part=line.split(";");

            Integer parentID;
            if(part[2].equals("NULL"))
                parentID=0;
            else
                parentID=Integer.parseInt(part[2]);

            Item temp=new Item(Integer.parseInt(part[0]),parentID,part[4],part[1],Boolean.parseBoolean(part[3].toLowerCase()));


            tree.addChild(parentID,temp);

        }

        tree.printItem();
    }

}
