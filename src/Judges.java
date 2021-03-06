import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(value = {"function"})

public class Judges {

    private String name;
    private List<SpecialRoles> specialRoles;
    private int numberOfOrders = 0;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpecialRoles> getSpecialRoles() {
        return specialRoles;
    }

    public void setSpecialRoles(List<SpecialRoles> specialRoles) {
        this.specialRoles = specialRoles;
    }

    public Integer getNumberOfOrders() {
        return this.numberOfOrders;
    }

    public void setNumberOfOrders(ListOfItems items) {

        int result = 0;

        for (Items item : items.getItems()) {

            if (item.getJudges() != null) {

                for (Judges judge : item.getJudges()) {

                    if (judge.getName().equals(name)) {
                        result++;
                        break;
                    }
                }
            }
        }
        numberOfOrders = result;
    }
}
