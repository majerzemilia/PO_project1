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
            for(File file: listOfFiles)
            {
                if (file.isFile()) {
                    ListOfItems itemToAdd = mapper.readValue(file, ListOfItems.class);
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

            System.out.println("Aby wyświetlić listę dostępnych komend, wpisz: help. Aby zakończyć, wpisz: quit");
            String start = reader.next();
            if (start.equals("help")) {
                String info = "Aby wyświetlić rubrum jednego lub wielu orzeczeń, wpisz: rubrum" + '\n'
                        + "Aby wyświetlić uzasadnienie orzeczenia o określonej sygnaturze, wpisz: content" + '\n'
                        + "Aby wyświetlić liczbę orzeczeń dla wybranego sędziego, wpisz: judge" + '\n'
                        + "Aby wyświetlić 10 sędziów, którzy wydali największą liczbę orzeczeń, wpisz: judges" + '\n'
                        + "Aby wyświetlić rozkład statystyczny orzeczeń ze względu na miesiąc, wpisz: months" + '\n'
                        + "Aby wyświetlić rozkład statystyczny orzeczeń ze względu na rodzaj sądu, wpisz: courts" + '\n'
                        + "Aby wyświetlić 10 ustaw, które są najczęściej przywoływane w orzeczeniach, wpisz: regulations" + '\n'
                        + "Aby wyświetlić rozkład statystyczny liczby sędziów przypadających na orzeczenie, wpisz: jury" + '\n';
                System.out.println(info);

                while (!start.equals("quit")) {
                        start = reader.nextLine();
                        switch (start) {
                            case "rubrum":
                                System.out.println("Podaj sygnatury orzeczeń oddzielone przecinkiem i spacją");
                                String[] a = reader.nextLine().split(", ");
                                System.out.println('\n');
                                for(int i = 0; i < a.length; i++){
                                    System.out.println(new Rubrum(a[i], redundantItems).toString());
                                }
                                break;
                            case "content":
                                System.out.println("Podaj sygnaturę orzeczenia");
                                start = reader.nextLine();
                                System.out.println(redundantItems.get(start).getTextContent());
                                System.out.println("\n");
                                break;
                            case "judge":
                                System.out.println("Podaj imię i nazwisko sędziego");
                                start = reader.nextLine();
                                int counter = 0;
                                for(Items item: item1.getItems()) {
                                    if (counter > 0) break;
                                    for(Judges judge: item.getJudges()) {
                                        if(judge.getName().equals(start)) {
                                            System.out.println("Liczba orzeczeń: " +
                                                    judge.getNumberOfOrders());
                                            counter++;
                                            break;
                                        }
                                    }
                                }
                                System.out.println("\n");
                                break;
                            case "judges":
                                System.out.println(new Top10Statistics(item1).tenJudges());
                                break;
                            case "months":
                                System.out.println(new StatisticalDistributions().sentencesPerMonth(item1));
                                break;
                            case "courts":
                                System.out.println(new StatisticalDistributions().sentencesPerCourtType(item1));
                                break;
                            case "regulations":
                                System.out.println(new Top10Statistics(item1).tenReferencedRegulations());
                            break;
                            case "jury":
                                System.out.println(new StatisticalDistributions().judgesPerOrder(item1));
                                break;
                            case "help":
                                System.out.println(info);
                                break;
                            case "quit":
                                return;
                            default:
                                System.out.println("Podaj odpowiednią komendę lub wpisz: help" + "\n");
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
