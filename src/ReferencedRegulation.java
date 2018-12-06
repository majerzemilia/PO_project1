import java.util.List;

public class ReferencedRegulation {
    private String journalTitle;
    private int journalNo;
    private int journalYear;
    private int journalEntry;
    private String text;
    private int numberOfOrders;


    public String getJournalTitle() {
        return journalTitle;
    }

    public void setJournalTitle(String journalTitle) {
        this.journalTitle = journalTitle;
    }

    public int getJournalNo() {
        return journalNo;
    }

    public void setJournalNo(int journalNo) {
        this.journalNo = journalNo;
    }

    public int getJournalYear() {
        return journalYear;
    }

    public void setJournalYear(int journalYear) {
        this.journalYear = journalYear;
    }

    public int getJournalEntry() {
        return journalEntry;
    }

    public void setJournalEntry(int journalEntry) {
        this.journalEntry = journalEntry;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getNumberOfOrders(){
        return this.numberOfOrders;
    }

    public void setNumberOfOrders(ListOfItems items){

        int result = 0;

        String id = String.valueOf(journalNo) + "." + String.valueOf(journalYear) + "." +
                String.valueOf(journalEntry);

        for(int i=0; i<items.getItems().size(); i++) {
            for (int j=0; j<items.getItems().get(i).getReferencedRegulations().size(); j++) {

                ReferencedRegulation regulation = items.getItems().get(i).getReferencedRegulations().get(j);

                String comparingId = String.valueOf(regulation.journalNo) + "." +
                        String.valueOf(regulation.journalYear) + "." + String.valueOf(regulation.journalEntry);

                if (comparingId.equals(id)){
                    result++;
                    break;
                }
            }
        }
        numberOfOrders = result;
    }
}
