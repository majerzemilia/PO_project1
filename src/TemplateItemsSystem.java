import jline.console.ConsoleReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;


public class TemplateItemsSystem {

    public static void main(String[] args) {

        try {
            ConsoleReader reader = new ConsoleReader(System.in, System.out);

            ListOfItems items = new Preparer().prepareListOfJsonItems(reader);
            ListOfItems items2 = new Preparer().prepareListOfHtmlItems(reader);
            items.getItems().addAll(items2.getItems());
            BufferedWriter writer = new Preparer().prepareBufferedReader(reader);

            Map<String, Items> redundantItems = new Preparer().prepareRedundantItems(items);

            for (Items item : items.getItems()) { //setNumberOfOrders for judges

                if (item.getJudges() != null) {
                    for (Judges judge : item.getJudges())
                        judge.setNumberOfOrders(items);
                }
            }

            for (Items item : items.getItems()) { //setNumberOfOrders for referencedRegulations

                if (item.getReferencedRegulations() != null) {
                    for (ReferencedRegulation regulation : item.getReferencedRegulations())
                        regulation.setNumberOfOrders(items);
                }
            }

            new Helper().printAndSplit(reader, redundantItems, items, writer);
            if (writer != null) writer.close();
        } catch (IllegalArgumentException | IOException ex) {
            System.out.println(ex.toString());
        }
    }
}
