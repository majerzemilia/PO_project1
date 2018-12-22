import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(value = {"id", "source", "courtReporters", "decision", "summary", "legalBases", "keywords",
        "referencedCourtCases", "receiptDate", "meansOfAppeal", "judgmentResult", "lowerCourtJudgments", "personnelType",
        "judgmentForm", "division", "chambers", "dissentingOpinions", "judgmentType"})

public class Items {

    private CourtType courtType;
    private List<CourtCases> courtCases;
    private List<Judges> judges;
    private String textContent;
    private Date judgmentDate;
    private List<ReferencedRegulation> referencedRegulations;


    public CourtType getCourtType() {
        return courtType;
    }

    public void setCourtType(CourtType courtType) {
        this.courtType = courtType;
    }

    public List<CourtCases> getCourtCases() {
        return this.courtCases;
    }

    public void setCourtCases(List<CourtCases> courtCases) {
        this.courtCases = courtCases;
    }

    public List<Judges> getJudges() {
        return this.judges;
    }

    public void setJudges(List<Judges> judges) {
        this.judges = judges;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Date getJudgmentDate() {
        return judgmentDate;
    }

    public void setJudgmentDate(Date judgmentDate) {
        this.judgmentDate = judgmentDate;
    }

    public List<ReferencedRegulation> getReferencedRegulations() {
        return referencedRegulations;
    }

    public void setReferencedRegulations(List<ReferencedRegulation> referencedRegulations) {
        this.referencedRegulations = referencedRegulations;
    }

    public String getId() {
        return this.getCourtCases().get(0).getCaseNumber();
    }

}
