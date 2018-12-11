import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(value = { "text"})

public class ReferencedRegulation {
    private String journalTitle;
    private int journalNo;
    private int journalYear;
    private int journalEntry;
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


    public Integer getNumberOfOrders(){
        return this.numberOfOrders;
    }

    public void setNumberOfOrders(ListOfItems items){

        int result = 0;

        String id = this.getId();

        for(Items item: items.getItems()) {

            for(ReferencedRegulation regulation: item.getReferencedRegulations()) {

                String comparingId = regulation.getId();

                if (comparingId.equals(id)){
                    result++;
                    break;
                }
            }
        }
        numberOfOrders = result;
    }

    public String getId(){

        return this.journalNo + "." + this.journalYear + "." + this.journalEntry;
    }
}
