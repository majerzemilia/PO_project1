import java.util.Map;
import java.util.Scanner;


public class TemplateItemsSystem{

    public static void main(String[] args){

        try{
            Scanner reader = new Scanner(System.in);

            ListOfItems items = new Helper().prepareListOfJsonItems(reader);
            ListOfItems items2 = new HtmlParser().prepareListOfHtmlItems(reader);
            items.getItems().addAll(items2.getItems());

            Map<String,Items> redundantItems = new Helper().prepareRedundantItems(items);

            for(Items item: items.getItems()){ //setNumberOfOrders for judges

                if(item.getJudges() != null) {
                    for (Judges judge : item.getJudges())
                        judge.setNumberOfOrders(items);
                }
            }

            for(Items item: items.getItems()){ //setNumberOfOrders for referencedRegulations

                if(item.getReferencedRegulations() != null) {
                    for (ReferencedRegulation regulation : item.getReferencedRegulations())
                        regulation.setNumberOfOrders(items);
                }
            }

           new Helper().printAndSplit(reader, redundantItems, items);
        }
        catch(IllegalArgumentException ex){
            System.out.println(ex.toString());
        }
    }
}
