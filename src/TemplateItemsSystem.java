import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class TemplateItemsSystem {

    public static void main(String[] args) {

        try {
            Scanner reader = new Scanner(System.in);

            ListOfItems item1 = new Helper().prepareListOfItems(reader);

            List <Items> redundantItems1 = new Helper().prepareRedundantItems(item1);

            Map<String,Items> redundantItems = new HashMap<>();
            for (Items item: redundantItems1) redundantItems.put(item.getId(),item);


            for(Items item: item1.getItems()) //setNumberOfOrders for judges
            {
                for(Judges judge: item.getJudges())
                    judge.setNumberOfOrders(item1);
            }

            for(Items item: item1.getItems()) //setNumberOfOrders for referencedRegulations
            {
                for(ReferencedRegulation regulation: item.getReferencedRegulations())
                    regulation.setNumberOfOrders(item1);
            }

           new Helper().printAndSplit(reader, redundantItems, item1);
        }
        catch(IllegalArgumentException ex){
            System.out.println(ex.toString());
        }
    }
}
