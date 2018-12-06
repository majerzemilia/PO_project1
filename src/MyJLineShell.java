import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import jline.TerminalFactory;
import jline.console.ConsoleReader;
import jline.console.completer.FileNameCompleter;

public class MyJLineShell {
    public static void main(String[] args) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            ListOfItems item1 = mapper.readValue(new File("C:\\Users\\majer\\Downloads\\judgments-1117.json"),
                    ListOfItems.class);
            Map<String,Items> items = new HashMap<>();
            for (Items item: item1.getItems()) items.put(item.getCourtCases().get(0).getCaseNumber(),item);
            ConsoleReader console = new ConsoleReader();
            console.addCompleter(new FileNameCompleter());
            console.setPrompt("prompt> ");
            String line = null;
            if(console.readLine().equals("4")){
                console.println(item1.getItems().get(8).getCourtCases().get(0).getCaseNumber());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                TerminalFactory.get().restore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
