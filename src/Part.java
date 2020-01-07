/*Contains part's id, name, price, type, and best use*/
import java.util.ArrayList;

class Part {
    private Integer id;
    private String type;
    private String name;
    private Integer price;
    private String bestUse;

    private static ArrayList<Part> allParts = new ArrayList<>();

    Part(Integer id, String type, String name, Integer price, String bestUse) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.bestUse = bestUse;

        allParts.add(this);

    }

    static ArrayList<Part> getPartList() {
        return allParts;
    }

    Integer getPartID() { return id; }

    String getPartName() { return name; }

    Integer getPartPrice() { return price; }

    String getPartType() { return type; }

    String getPartBestUse() { return bestUse; }
}
