import java.util.Map;
import java.util.Scanner;


public class TemplateItemsSystem {

    public static void main(String[] args) {

        try {
            Scanner reader = new Scanner(System.in);

            ListOfItems items = new Helper().prepareListOfItems(reader);

            Map<String,Items> redundantItems = new Helper().prepareRedundantItems(items);

            for(Items item: items.getItems()) //setNumberOfOrders for judges
            {
                for(Judges judge: item.getJudges())
                    judge.setNumberOfOrders(items);
            }

            for(Items item: items.getItems()) //setNumberOfOrders for referencedRegulations
            {
                for(ReferencedRegulation regulation: item.getReferencedRegulations())
                    regulation.setNumberOfOrders(items);
            }

           new Helper().printAndSplit(reader, redundantItems, items);
        }
        catch(IllegalArgumentException ex){
            System.out.println(ex.toString());
        }
    }
}
