public enum SpecialRoles{

    PRESIDING_JUDGE, // przewodniczacy składu sędziowskiego
    REPORTING_JUDGE, // sędzia sprawozdawca
    REASONS_FOR_JUDGMENT_AUTHOR; // autor uzasadnienia

    public String toString()
    {
        switch(this){
            case PRESIDING_JUDGE:
                return "- Przewodnicząca/y składu sędziowskiego";
            case REPORTING_JUDGE:
                return "- Sędzia sprawozdawca";
            case REASONS_FOR_JUDGMENT_AUTHOR:
                return "- Autor/ka uzasadnienia";
        }
        return null;
    }
}
