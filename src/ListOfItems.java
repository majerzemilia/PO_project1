import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(value = { "links", "queryTemplate", "info"})

public class ListOfItems{

    private List <Items> items;

    public List<Items> getItems(){
        return items;
    }

    public void setItems(List<Items> items){
        this.items = items;
    }
}
