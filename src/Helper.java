import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class Helper{


    public ListOfItems prepareListOfJsonItems (Scanner reader) throws IllegalArgumentException{

        System.out.println("Podaj ścieżkę do folderu zawierającego pliki w formacie JSON. Jeśli nie chcesz wczytywać" +
                " plików w formacie JSON, wpisz: next");
        String path1 = reader.next();
        if(!path1.equals("next")){
            File folder = new File(path1);
            if (folder.isDirectory()){
                File[] listOfFiles = folder.listFiles();
                ObjectMapper mapper = new ObjectMapper();
                if (listOfFiles != null){
                    ListOfItems item1 = new ListOfItems();
                    item1.setItems(new LinkedList<>());
                    for (File file : listOfFiles){
                        if (file.isFile() && Files.getFileExtension(file.getName()).equals("json")) {
                            ListOfItems itemToAdd;
                            try{
                                itemToAdd = mapper.readValue(file, ListOfItems.class);
                            } catch (IOException e) {
                                throw new IllegalArgumentException(file.getName() + " zawiera niepoprawne dane. " +
                                        "Uruchom program ponownie, podając ścieżkę do folderu zawierającego poprawne pliki."
                                        + "\n");
                            }
                            item1.getItems().addAll(itemToAdd.getItems());
                        }
                    }
                    if (item1.getItems().isEmpty())
                        throw new IllegalArgumentException("Folder " + folder.getName() + " nie zawiera plików w odpowiednim formacie." +
                                "Uruchom program ponownie, podając ścieżkę do folderu zawierającego poprawne pliki."
                                + "\n");
                    return item1;
                }
            }
            throw new IllegalArgumentException("Podano niepoprawną ścieżkę do folderu. Uruchom program ponownie, " +
                    "podając ścieżkę do folderu zawierającego poprawne pliki." + "\n");
        }
        ListOfItems item1 = new ListOfItems();
        item1.setItems(new LinkedList<>());
        return item1;
    }

    public void printAndSplit(Scanner reader, Map<String, Items> redundantItems, ListOfItems items){

        System.out.println("Aby wyświetlić listę dostępnych komend, wpisz: help. Aby zakończyć, wpisz: quit" + "\n");
        String start = reader.nextLine();

        while (!start.equals("quit")){
            start = reader.nextLine();
            switch (start) {
                case "rubrum":
                    System.out.println("Podaj sygnatury orzeczeń oddzielone przecinkiem i spacją");
                    String[] a = reader.nextLine().split(", ");
                    System.out.println(new SingleActions(redundantItems).rubrumResult(a));
                    break;
                case "content":
                    System.out.println("Podaj sygnaturę orzeczenia");
                    start = reader.nextLine();
                    System.out.println(new SingleActions(redundantItems).printTextContent(start));
                    break;
                case "judge":
                    System.out.println("Podaj imię i nazwisko sędziego");
                    start = reader.nextLine();
                    System.out.println(new StatisticalDistributions(items).numberOfOrdersPerJudge(start));
                    break;
                case "judges":
                    System.out.println(new Top10Statistics(items).tenJudges());
                    break;
                case "months":
                    System.out.println(new StatisticalDistributions(items).sentencesPerMonth());
                    break;
                case "courts":
                    System.out.println(new StatisticalDistributions(items).sentencesPerCourtType());
                    break;
                case "regulations":
                    System.out.println(new Top10Statistics(items).tenReferencedRegulations());
                    break;
                case "jury":
                    System.out.println(new StatisticalDistributions(items).judgesPerOrder());
                    break;
                case "help":
                    System.out.println(new SingleActions(redundantItems).info());
                    break;
                case "quit":
                    return;
                default:
                    System.out.println("Podaj odpowiednią komendę lub wpisz: help" + "\n");
                    break;
            }
        }
    }

    public Map<String,Items>  prepareRedundantItems (ListOfItems items){

        Map<String,Items> redundantItems = new HashMap<>();

        List<Items> redundantItemsList = new ArrayList<>(items.getItems()); //an array of items containing redundant items
                                                                //to make every 'CourtCases' a key in a HashMap

        for (Items item: items.getItems()){

            if (item.getCourtCases().size() > 1) {

                for (int i = 1; i<item.getCourtCases().size(); i++){

                    Items newItem = new Items();
                    newItem.setCourtCases(item.getCourtCases().subList(i, item.getCourtCases().size()));
                    //newItem will have less CourtCases than the original item, but we won't use their amount
                    newItem.setCourtType(item.getCourtType());
                    newItem.setJudges(item.getJudges());
                    newItem.setJudgmentDate(item.getJudgmentDate());
                    newItem.setReferencedRegulations(item.getReferencedRegulations());
                    newItem.setTextContent(item.getTextContent());
                    redundantItemsList.add(newItem);
                }
            }
        }
        for (Items item: redundantItemsList) redundantItems.put(item.getId(),item);
        return redundantItems;
    }
}
