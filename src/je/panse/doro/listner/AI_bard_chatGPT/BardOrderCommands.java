package je.panse.doro.listner.AI_bard_chatGPT;

public class BardOrderCommands {

    private static final String BARDORDERLAB = """
        clear previous values;
        make table
        if parameter does not exist -> remove the row;
        Parameter Value Unit 
        using value format
        merge parameters like below
        do not calculate between values\n
        the row titles ;----------------------
    """;

    private static final String BARDORDERLAB1 = """
        Execute next step by step;
        clear previous input data;
        
        you are a physician special assistant for EMR interface.
        i would like to make EMR clinical laboratory result table;
        
        make table ;
        Column titles - > Parameter, Value, Unit ;
        Parameter row titles - >
    """;

    private static final String BARDORDERLIST = """
        i would like to make EMR interface for physician.
        clear previous input data;
        
        organize and make summary list using table format;
        the list will be classified
            using Mesh main heading classifications;
        and sort the list using disease base; 
        
        modify table like column titles;
        #	,   MeSH Main Heading	,    Date	,    Details
    """;

    private static final String BARDORDERPRO = "// ... (content of bardorderpro)...";

    public static String getBardOrderLab() {
        return BARDORDERLAB;
    }

    public static String getBardOrderLab1() {
        return BARDORDERLAB1;
    }

    public static String getBardOrderList() {
        return BARDORDERLIST;
    }

    public static String getBardOrderPro() {
        return BARDORDERPRO;
    }
}
