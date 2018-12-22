import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import jline.console.ConsoleReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class Preparer extends AbstractPreparer {

    public ListOfItems prepareListOfJsonItems(ConsoleReader reader) throws IllegalArgumentException, IOException {

        System.out.println("Podaj ścieżkę do folderu zawierającego pliki w formacie JSON. Jeśli nie chcesz wczytywać" +
                " plików w formacie JSON, wpisz: next");

        String path1 = reader.readLine();

        ListOfItems item1 = new ListOfItems();
        item1.setItems(new LinkedList<>());

        if (!path1.equals("next")) {

            File[] listOfFiles = prepareListOfFiles(path1);

            System.out.println("Trwa ładowanie plików JSON" + "\n");

            ObjectMapper mapper = new ObjectMapper();

            for (File file : listOfFiles) {

                if (file.isFile() && Files.getFileExtension(file.getName()).equals("json")) {
                    ListOfItems itemToAdd;
                    try {
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
                throw new IllegalArgumentException("Podany folder nie zawiera plików w odpowiednim formacie. " +
                        "Uruchom program ponownie, podając ścieżkę do folderu zawierającego poprawne pliki."
                        + "\n");
        }
        return item1;
    }


    public ListOfItems prepareListOfHtmlItems(ConsoleReader reader) throws IllegalArgumentException, IOException {

        System.out.println("Podaj ścieżkę do folderu zawierającego pliki w formacie HTML. Jeśli nie chcesz wczytywać" +
                " plików w formacie HTML, wpisz: next");
        String path = reader.readLine();
        ListOfItems item1 = new ListOfItems();
        item1.setItems(new LinkedList<>());

        if (!path.equals("next")) {

            File[] listOfFiles = prepareListOfFiles(path);

            System.out.println("Trwa ładowanie plików HTML" + "\n");

            for (File file : listOfFiles) {

                if (file.isFile() && Files.getFileExtension(file.getName()).equals("html")) {
                    Items itemToAdd;

                    try {
                        Document doc = Jsoup.parse(file, "UTF-8");
                        itemToAdd = new HtmlParser(doc).getExtractedItems();

                    } catch (IOException e) {
                        throw new IllegalArgumentException(file.getName() + " zawiera niepoprawne dane. " +
                                "Uruchom program ponownie, podając ścieżkę do folderu zawierającego poprawne pliki."
                                + "\n");
                    }
                    if (itemToAdd.getCourtCases().isEmpty()) //if item doesn't have any courtcases (from which we take item's signature)
                        //it means it has wrong data inside
                        throw new IllegalArgumentException(file.getName() + " zawiera niepoprawne dane. " +
                                "Uruchom program ponownie, podając ścieżkę do folderu zawierającego poprawne pliki."
                                + "\n");
                    item1.getItems().add(itemToAdd);
                }
            }
            if (item1.getItems().isEmpty())
                throw new IllegalArgumentException("Podany folder nie zawiera plików w odpowiednim formacie. " +
                        "Uruchom program ponownie, podając ścieżkę do folderu zawierającego poprawne pliki."
                        + "\n");
        }

        return item1;
    }

    public Map<String, Items> prepareRedundantItems(ListOfItems items) {

        Map<String, Items> redundantItems = new HashMap<>();

        List<Items> redundantItemsList = new ArrayList<>(items.getItems()); //an array of items containing redundant items
        //to make every 'CourtCases' a key in a HashMap

        for (Items item : items.getItems()) {

            if (item.getCourtCases().size() > 1) {

                for (int i = 1; i < item.getCourtCases().size(); i++) {

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
        for (Items item : redundantItemsList) redundantItems.put(item.getId(), item);
        return redundantItems;
    }

    public BufferedWriter prepareBufferedReader(ConsoleReader reader) throws IOException {

        System.out.println("Podaj ścieżkę do pliku, do którego chcesz zapisać wyniki działania programu. " +
                "Jeśli chcesz opuścić ten krok, wpisz: next");

        String path = reader.readLine();

        BufferedWriter writer = null;

        if (!path.equals("next")) {
            File file = new File(path);
            if (!file.isFile())
                throw new IllegalArgumentException("Podano nieprawidłową ścieżkę do pliku");
            try {
                writer = new MyBufferedWriter().createBufferedWritter(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return writer;
    }
}
