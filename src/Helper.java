import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class Helper {


    public ListOfItems prepareListOfItems (Scanner reader) throws IllegalArgumentException {

        System.out.println("Podaj ścieżkę do folderu");
        String path = reader.next();

        File folder = new File(path);
        if(folder.isDirectory()) {
            File[] listOfFiles = folder.listFiles();
            ObjectMapper mapper = new ObjectMapper();
            if (listOfFiles != null) {
                ListOfItems item1 = new ListOfItems();
                item1.setItems(new LinkedList<>());
                for (File file : listOfFiles) {
                    if (file.isFile() && Files.getFileExtension(file.getName()).equals("json")) {
                        ListOfItems itemToAdd = null;
                        try {
                            itemToAdd = mapper.readValue(file, ListOfItems.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        item1.getItems().addAll(itemToAdd.getItems());
                    }
                }
                if(item1.getItems().isEmpty())
                    throw new IllegalArgumentException("Podany folder nie zawiera plików w odpowiednim formacie");
                return item1;
            }
        }
        throw new IllegalArgumentException("Podano niepoprawną ścieżkę do folderu");
    }

    public void printAndSplit(Scanner reader, Map<String, Items> redundantItems, ListOfItems item1) {

        System.out.println("Aby wyświetlić listę dostępnych komend, wpisz: help. Aby zakończyć, wpisz: quit" + "\n");
        String start = reader.next();
        if (start.equals("help")) {
            String info = "Aby wyświetlić rubrum jednego lub wielu orzeczeń, wpisz: rubrum" + "\n"
                    + "Aby wyświetlić uzasadnienie orzeczenia o określonej sygnaturze, wpisz: content" + "\n"
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
                        System.out.println(new StatisticalDistributions().rubrumResult(redundantItems, a));
                        break;
                    case "content":
                        System.out.println("Podaj sygnaturę orzeczenia");
                        start = reader.nextLine();
                        System.out.println(new StatisticalDistributions().printTextContent(redundantItems, start));
                        break;
                    case "judge":
                        System.out.println("Podaj imię i nazwisko sędziego");
                        start = reader.nextLine();
                        System.out.println(new StatisticalDistributions().numberOfOrdersPerJudge(item1, start));
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

    public List<Items> prepareRedundantItems (ListOfItems item1){

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
        return redundantItems1;
    }
}
