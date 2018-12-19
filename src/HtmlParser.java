import com.google.common.io.Files;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class HtmlParser{

    public ListOfItems prepareListOfHtmlItems (Scanner reader) throws IllegalArgumentException{

        System.out.println("Podaj ścieżkę do folderu zawierającego pliki w formacie HTML. Jeśli nie chcesz wczytywać" +
                " plików w formacie HTML, wpisz: next");
        String path = reader.next();

        if (!path.equals("next")) {
            File folder = new File(path);
            if (folder.isDirectory()) {
                File[] listOfFiles = folder.listFiles();
                if (listOfFiles != null) {
                    ListOfItems item1 = new ListOfItems();
                    item1.setItems(new LinkedList<>());
                    for (File file : listOfFiles) {
                        if (file.isFile() && Files.getFileExtension(file.getName()).equals("html")) {
                            Items itemToAdd;

                            try {
                                Document doc = Jsoup.parse(file, "UTF-8");
                                itemToAdd = extractedItems(doc);

                            } catch (IOException e) {
                                throw new IllegalArgumentException(file.getName() + " zawiera niepoprawne dane. " +
                                        "Uruchom program ponownie, podając ścieżkę do folderu zawierającego poprawne pliki."
                                        + "\n");
                            }
                            item1.getItems().add(itemToAdd);
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

    private Items extractedItems (Document doc){

        Items result = new Items();

        Date judgmentDate = null;
        CourtType courtType = null;
        List <Judges> judges = new LinkedList<>();
        List<ReferencedRegulation> referencedRegulation = new LinkedList<>();

        extractCourtCase(doc, result);
        extractTextContent(doc, result);

        Element table = doc.select("table").get(3);
        Elements rows = table.select("tr");

        for (int i = 0; i < rows.size(); i++){

            Element row = rows.get(i);
            Elements cols = row.select("td");

            if(cols.get(0).text().contains("Data orzeczenia")) extractJudgmentDate(cols, judgmentDate, result);
            else if(cols.get(0).text().contains("Sąd")) extractCourtType(cols, courtType, result);
            else if(cols.get(0).text().contains("Sędziowie")) extractJudges(cols, judges, result);
            else if(cols.get(0).text().contains("Powołane"))
                extractReferencedRegulations(cols, referencedRegulation, result);

        }
        return result;
    }

    private void extractCourtCase(Document doc, Items result){

        String caseNumberToBe = doc.title(); //courtcases are the keys in a hashmap, so the title will make a courtcase
        if(caseNumberToBe.contains(" - ")) {
            String[] tokens = caseNumberToBe.split(" - ");
            List<CourtCases> cases = new LinkedList<>();
            CourtCases case1 = new CourtCases();
            case1.setCaseNumber(tokens[0]);
            cases.add(case1);
            result.setCourtCases(cases);
        }
        else throw new IllegalArgumentException("String " + caseNumberToBe + " does not contain - ");
    }

    private void extractTextContent(Document doc, Items result){

        String textContent = doc.select("td.info-list-label-uzasadnienie").text();
        int cut = textContent.indexOf("Uzasadnienie");
        if(cut == -1) textContent = "Brak dostępnego uzasadnienia";
        else textContent = textContent.substring(cut);
        result.setTextContent(textContent);
    }


    private void extractJudgmentDate(Elements cols, Date judgmentDate, Items result){

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(cols.size() == 5) {
                judgmentDate = format.parse(cols.get(3).text());
                result.setJudgmentDate(judgmentDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void extractCourtType(Elements cols, CourtType courtType, Items result){

        if (cols.size() == 3){
            if (cols.get(2).text().contains("Wojewódzki"))
                courtType = CourtType.Wojewódzki_Sąd_Administracyjny;
            else courtType = CourtType.Naczelny_Sąd_Administracyjny;
            result.setCourtType(courtType);
        }
    }

    private void extractJudges(Elements cols, List<Judges> judges, Items result){

        if(cols.size() == 3){
            String text = cols.get(2).toString().substring(29, cols.get(2).toString().lastIndexOf("<")-1);
                             // to skip <td class="info-list-value"> and </td>

            String[] tabOfJudges = text.split("<br>");

            for (String s: tabOfJudges) {
                String[] judgeAndRole = s.split(" /");
                Judges judge = new Judges();
                List<SpecialRoles> roles = new LinkedList<>();
                judge.setSpecialRoles(roles);
                judge.setName(judgeAndRole[0]);
                if (judgeAndRole.length == 2) {
                    if (judgeAndRole[1].contains("przewodniczący")) roles.add(SpecialRoles.PRESIDING_JUDGE);
                    if (judgeAndRole[1].contains("sprawozdawca"))
                        roles.add(SpecialRoles.REPORTING_JUDGE);
                }
                judges.add(judge);

            }
            result.setJudges(judges);
        }
    }

    private void extractReferencedRegulations(Elements cols, List<ReferencedRegulation> referencedRegulation, Items result){

        if(cols.size() == 3){
            String text = cols.get(2).toString().substring(29); // to skip <td class="info-list-value">;
            String[] reg = text.split("<span class=\"nakt\">");

            for(int j=1; j<reg.length; j++){
                if(reg[j].startsWith(" ")) reg[j] = reg[j].substring(1);
                int cut = reg[j].indexOf("<");
                reg[j] = reg[j].substring(0, cut);
                if(reg[j].contains("- tekst") || reg[j].contains("- tj"))
                    reg[j] = reg[j].substring(0, reg[j].lastIndexOf("-"));
                if(reg[j].endsWith(" ")) reg[j] = reg[j].substring(0, reg[j].length()-1);
                if(reg[j].endsWith(".")) reg[j] = reg[j].substring(0, reg[j].length()-1);
                ReferencedRegulation refreg = new ReferencedRegulation();
                refreg.setJournalTitle(reg[j]);
                referencedRegulation.add(refreg);
            }
            result.setReferencedRegulations(referencedRegulation);

        }

    }

}
