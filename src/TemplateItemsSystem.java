import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class TemplateItemsSystem {

    public static void main(String[] args) {

        try {
            Scanner reader = new Scanner(System.in);
            System.out.println("Podaj ścieżkę do folderu");
            String path = reader.next();

            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            ObjectMapper mapper = new ObjectMapper();
            ListOfItems item1 = mapper.readValue(listOfFiles[0], ListOfItems.class);
            for(int i=1; i<listOfFiles.length; i++)
            {
                if (listOfFiles[i].isFile()) {
                    ListOfItems itemToAdd = mapper.readValue(listOfFiles[i], ListOfItems.class);
                    item1.getItems().addAll(itemToAdd.getItems());
                }
            }


            List<Items> redundantItems1 = new ArrayList<>(item1.getItems()); //an array of items containing redundant items
                                                                            //to make every courtCases a key in the HashMap below

            for (Items item: item1.getItems())
            {
                if (item.getCourtCases().size() > 1)
                {
                    for (int i = 1; i<item.getCourtCases().size(); i++) {
                        Items newItem = new Items();
                        newItem.setCourtCases(item.getCourtCases().subList(i, item.getCourtCases().size()));
                        //newItem will have less CourtCases than the original item, but we won't use their amount
                        newItem.setCourtType(item.getCourtType());
                        newItem.setJudges(item.getJudges());
                        newItem.setJudgmentDate(item.getJudgmentDate());
                        newItem.setReferencedRegulations(item.getReferencedRegulations());
                        newItem.setTextContent(item.getTextContent());
                        redundantItems1.add(newItem);
                    }
                }
            }
            Map<String,Items> redundantItems = new HashMap<>();
            for (Items item: redundantItems1) redundantItems.put(item.getCourtCases().get(0).getCaseNumber(),item);

            //Map<String,Items> items = new HashMap<>();
            //for (Items item: item1.getItems()) items.put(item.getCourtCases().get(0).getCaseNumber(),item);

            for(int i=0; i<item1.getItems().size(); i++) //setNumberOfOrders for judges
            {
                for(int j=0; j<item1.getItems().get(i).getJudges().size(); j++)
                    item1.getItems().get(i).getJudges().get(j).setNumberOfOrders(item1);
            }

            for(int i=0; i<item1.getItems().size(); i++) //setNumberOfOrders for referencedRegulations
            {
                for(int j=0; j<item1.getItems().get(i).getReferencedRegulations().size(); j++)
                    item1.getItems().get(i).getReferencedRegulations().get(j).setNumberOfOrders(item1);
            }

            System.out.println("Aby wyświetlić informacje o programie, wpisz: info. Aby zakończyć, wpisz: quit");
            String start = reader.next();
            if (start.equals("info")) {
                String info = "Aby wyświetlić rubrum orzeczenia o wybranej sygnaturze, wpisz: 1" + '\n'
                        + "Aby wyświetlić uzasadnienie orzeczenia o określonej sygnaturze, wpisz: 2" + '\n'
                        + "Aby wyświetlić liczbę orzeczeń dla wybranego sędziego, wpisz: 3" + '\n'
                        + "Aby wyświetlić 10 sędziów, którzy wydali największą liczbę orzeczeń, wpisz: 4" + '\n'
                        + "Aby wyświetlić rozkład statystyczny wyroków ze względu na miesiąc, wpisz: 5" + '\n'
                        + "Aby wyświetlić rozkład statystyczny wyroków ze względu na rodzaj sądu, wpisz: 6" + '\n'
                        + "Aby wyświetlić 10 ustaw, które są najczęściej przywoływane w orzeczeniach, wpisz: 7" + '\n'
                        + "Aby wyświetlić rozkład statystyczny liczby sędziów przypadających na orzeczenie, wpisz: 8" + '\n';
                System.out.println(info);

                while (!start.equals("quit")) {
                        start = reader.nextLine();
                        switch (start) {
                            case "1":
                                System.out.println("Podaj sygnaturę orzeczenia");
                                start = reader.nextLine();
                                System.out.println(new Rubrum(start, redundantItems).toString());
                                break;
                            case "2":
                                System.out.println("Podaj sygnaturę orzeczenia");
                                start = reader.nextLine();
                                System.out.println(redundantItems.get(start).getTextContent());
                                System.out.println("\n");
                                break;
                            case "3":
                                System.out.println("Podaj imię i nazwisko sędziego");
                                start = reader.nextLine();
                                int counter = 0;
                                for(int i=0; i<item1.getItems().size(); i++) {
                                    if (counter > 0) break;
                                    for(int j=0; j<item1.getItems().get(i).getJudges().size(); j++) {
                                        if(item1.getItems().get(i).getJudges().get(j).getName().equals(start)) {
                                            System.out.println("Liczba orzeczeń: " +
                                                    item1.getItems().get(i).getJudges().get(j).getNumberOfOrders());
                                            counter++;
                                            break;
                                        }
                                    }
                                }
                                System.out.println("\n");
                                break;
                            case "4":
                                new Top10Statistics(item1).tenJudges();
                                System.out.println("\n");
                                break;
                            case "5":
                                new StatisticalDistributions().sentencesPerMonth(item1);
                                System.out.println("\n");
                                break;
                            case "6":
                                new StatisticalDistributions().sentencesPerCourtType(item1);
                                System.out.println("\n");
                                break;
                            case "7":
                                new Top10Statistics(item1).tenReferencedRegulations();
                                System.out.println("\n");
                                break;
                            case "8":
                                new StatisticalDistributions().judgesPerOrder(item1);
                                System.out.println("\n");
                                break;
                            case "info":
                                System.out.println(info);
                                break;
                            case "quit":
                                return;
                            default:
                                System.out.println("Podaj numer z zakresu 1 - 8 lub wpisz: info");
                                break;
                        }
                }
            }
        }
        catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
