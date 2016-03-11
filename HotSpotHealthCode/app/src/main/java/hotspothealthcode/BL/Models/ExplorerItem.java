package hotspothealthcode.BL.Models;

/**
 * Created by Giladl on 08/03/2016.
 */
public class ExplorerItem implements Comparable<ExplorerItem>{
    private String name;
    private String data;
    private String path;
    private String image;

    public ExplorerItem(String n,String d, String p, String img)
    {
        name = n;
        data = d;
        path = p;
        image = img;
    }

    public String getName()
    {
        return name;
    }

    public String getExtension(){
        return this.name.substring(this.name.lastIndexOf('.'));
    }

    public String getData()
    {
        return data;
    }

    public String getPath()
    {
        return path;
    }

    public String getImage() {
        return image;
    }

    public int compareTo(ExplorerItem o) {
        if(this.name != null)
            return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
        else
            throw new IllegalArgumentException();
    }
}