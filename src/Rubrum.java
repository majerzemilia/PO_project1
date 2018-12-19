import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Rubrum{

    private String signature;
    private Date date;
    private CourtType courtType;
    private List<Judges> judges;

    Rubrum(String key, Map<String,Items> items){

        this.signature = key;
        this.date = items.get(key).getJudgmentDate();
        this.courtType = items.get(key).getCourtType();
        this.judges = items.get(key).getJudges();
    }

    private String squad(List<Judges> judges){

        String result = "";
        for (Judges judge: judges){
            result += judge.getName() + " ";
            for (SpecialRoles role: judge.getSpecialRoles()){
                result += role.toString() + " ";
            }
            result += '\n';
        }
        return result;
    }

    public String toString(){
        return "Sygnatura orzeczenia: " + this.signature +'\n' +
                "Data wydania orzeczenia: " + getDate(this.date) + '\n' +
                "Rodzaj sądu: " + this.courtType.toString() + '\n'+
                "Skład: " + squad(this.judges);
    }

    private String getDate(Date date){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH)+ "-" + cal.get(Calendar.MONTH)+1 + "-" + cal.get(Calendar.YEAR);
    }
}
