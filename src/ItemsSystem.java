import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ItemsSystem {

    public static void main(String[] args) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            ListOfItems item1 = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-1117.json"),
                    ListOfItems.class);
            ListOfItems item2 = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-348.json"),
                    ListOfItems.class);
            ListOfItems item3 = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-356.json"),
                    ListOfItems.class);
            ListOfItems item4 = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-520.json"),
                    ListOfItems.class);
            ListOfItems item5 = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-924.json"),
                    ListOfItems.class);
            ListOfItems item6 = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-995.json"),
                    ListOfItems.class);
            ListOfItems item7 = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-1287.json"),
                    ListOfItems.class);
            ListOfItems item8 = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-1324.json"),
                    ListOfItems.class);
            ListOfItems item9 = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-1338.json"),
                    ListOfItems.class);
            ListOfItems item10 = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-1912.json"),
                    ListOfItems.class);

            item1.getItems().addAll(item2.getItems());
            item1.getItems().addAll(item3.getItems());
            item1.getItems().addAll(item4.getItems());
            item1.getItems().addAll(item5.getItems());
            item1.getItems().addAll(item6.getItems());
            item1.getItems().addAll(item7.getItems());
            item1.getItems().addAll(item8.getItems());
            item1.getItems().addAll(item9.getItems());
            item1.getItems().addAll(item10.getItems());

            ListOfItems item1redundantt = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-1117.json"),
                    ListOfItems.class);
            ListOfItems item2redundantt = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-348.json"),
                    ListOfItems.class);
            ListOfItems item3redundantt = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-356.json"),
                    ListOfItems.class);
            ListOfItems item4redundantt = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-520.json"),
                    ListOfItems.class);
            ListOfItems item5redundantt = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-924.json"),
                    ListOfItems.class);
            ListOfItems item6redundantt = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-995.json"),
                    ListOfItems.class);
            ListOfItems item7redundantt = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-1287.json"),
                    ListOfItems.class);
            ListOfItems item8redundantt = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-1324.json"),
                    ListOfItems.class);
            ListOfItems item9redundantt = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-1338.json"),
                    ListOfItems.class);
            ListOfItems item10redundantt = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-1912.json"),
                    ListOfItems.class);

            item1redundantt.getItems().addAll(item2redundantt.getItems());
            item1redundantt.getItems().addAll(item3redundantt.getItems());
            item1redundantt.getItems().addAll(item4redundantt.getItems());
            item1redundantt.getItems().addAll(item5redundantt.getItems());
            item1redundantt.getItems().addAll(item6redundantt.getItems());
            item1redundantt.getItems().addAll(item7redundantt.getItems());
            item1redundantt.getItems().addAll(item8redundantt.getItems());
            item1redundantt.getItems().addAll(item9redundantt.getItems());
            item1redundantt.getItems().addAll(item10redundantt.getItems());

            List<Items> item1redundant = new ArrayList<>(item1.getItems()); //an array of items containing redundant items
                                                                            //to make every courtCases a key in the HashMap below

            for (Items item: item1.getItems())
            {
                if (item.getCourtCases().size() > 1)
                {
                    for (int i = 1; i<item.getCourtCases().size(); i++) {
                        Items newItem = new Items();
                        newItem.setCourtCases(item.getCourtCases().subList(i, item.getCourtCases().size()));
                        //newItem will have less CourtCases than the original item, but we won't use this field
                        newItem.setCourtType(item.getCourtType());
                        newItem.setJudges(item.getJudges());
                        newItem.setJudgmentDate(item.getJudgmentDate());
                        newItem.setReferencedRegulations(item.getReferencedRegulations());
                        newItem.setTextContent(item.getTextContent());
                        item1redundant.add(newItem);
                    }
                }
            }

            Map<String,Items> redundantItems = new HashMap<>();
            for (Items item: item1redundant) redundantItems.put(item.getCourtCases().get(0).getCaseNumber(),item);

            Map<String,Items> items = new HashMap<>();
            for (Items item: item1.getItems()) items.put(item.getCourtCases().get(0).getCaseNumber(),item);

            //String id = "KIO 321/14";

            //System.out.println(items.get(id).getCourtCases().get(0).getCaseNumber());
            //System.out.println(redundantItems.get(id).getCourtCases().get(0).getCaseNumber());
            //System.out.println(item1.getItems().get(8).getCourtCases().get(0).getCaseNumber());

            /*for(Items item: items.values()) //szukanie orzeczenia z kilkoma courtcases
            {
                if (item.getCourtCases().size()>1) System.out.println(item.getCourtCases().get(0).getCaseNumber());
            }*/

            //System.out.println(item1.getItems().get(8).getJudges().get(1).numberOfOrders(item1)); //sprawdzanie ilosci orzeczen

            for(int i=0; i<item1.getItems().size(); i++) //setNumberOfOrders for judges
            {
                for(int j=0; j<item1.getItems().get(i).getJudges().size(); j++)
                    item1.getItems().get(i).getJudges().get(j).setNumberOfOrders(item1);
            }

            for(int i=0; i<item1.getItems().size(); i++) //setNumberOfOrders for referencesregulations
            {
                for(int j=0; j<item1.getItems().get(i).getReferencedRegulations().size(); j++)
                    item1.getItems().get(i).getReferencedRegulations().get(j).setNumberOfOrders(item1);
            }
            //System.out.println(item1.getItems().get(4).getJudges().get(0).getNumberOfOrders());
            System.out.println(item1.getItems().get(5).getReferencedRegulations().get(2).getNumberOfOrders());

            /*System.out.println(new JudgesStatistics(item1).tenJudgestoString());

            new StatisticalDistributions().judgesPerOrder(item1); //rozklad stat liczby sedziow na orzeczenie

            //System.out.println(items.get("KIO 276/14").getCourtCases().get(1).getCaseNumber());
            //System.out.println(redundantItems.get("KIO 302/14").getJudges().get(0).getName());

            System.out.println(new Rubrum("IV CZ 130/13", redundantItems).toString());

            new StatisticalDistributions().sentencesPerMonth(item1);
            new StatisticalDistributions().sentencesPerCourtType(item1);*/


        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
