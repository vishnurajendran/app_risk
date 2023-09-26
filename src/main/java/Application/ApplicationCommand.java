package Application;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is purely meant to be a data class. a flat string is used
 * to fill this class. the components of a command are split and provided
 * @author vishnurajendran
 * Dated 23-09-2023
 */
public class ApplicationCommand {

    private static final String OPTION_PREFIX = "-";

    private String d_cmdName;
    private String d_option;
    private ArrayList<String> d_arguments;

    /**
     * default constructor,
     * Initialises an empty object
     */
    public ApplicationCommand(){
        d_cmdName = "";
        d_option = "";
        d_arguments = new ArrayList<>();
    }

    /**
     * Parameterised constructor for initialising this class with required data.
     * @param p_cmdName the name for this command.
     * @param p_option the option for this command. (can be empty)
     * @param p_arguments the list of arguments provided. (can be empty)
     */
    public ApplicationCommand(String p_cmdName, String p_option, ArrayList<String> p_arguments){
        this();
        d_cmdName = p_cmdName;
        d_option = p_option;
        d_arguments = p_arguments;
    }

    /**
     * @return name of the command
     */
    public String getCmdName() {return d_cmdName;}

    /**
     * @return option provided for this command. (can be empty)
     */
    public String getCmdOption() {return d_option;}

    /**
     * @return arguments for this command. (can be empty)
     */
    public ArrayList<String> getCmdArgs() {return d_arguments;}

    /**
     * this method converts a flat string to and ApplicationCommand instance.
     * @param p_inputString flat string for command processing.
     */
    public static ApplicationCommand parseString(String p_inputString){
        if(p_inputString == null || p_inputString.isBlank())
            return null;
        //remove any trailing or leading spaces.
        p_inputString = p_inputString.trim();
        //initialise the vars we need, for processing
        String[] l_components = p_inputString.split("\\s+");
        String l_cmdName = l_components[0];

        //if we don't have more than a name, we just return from here.
        if(l_components.length == 1){
            return new ApplicationCommand(l_cmdName, "", new ArrayList<String>());
        }

        String l_cmdOption = "";
        int l_indx = 1;

        //if the words start with a '-', this is considered as an option,
        //so we set the index to skip this word, and we add this word without prefix as the option.
        if(l_components[1].startsWith(OPTION_PREFIX)){
            l_cmdOption = l_components[1].substring(1);
            l_indx = 2;
        }

        //we add all the remaining as arguments.
        ArrayList<String> l_arguments = new ArrayList<>(Arrays.asList(l_components).subList(l_indx, l_components.length));
        return new ApplicationCommand(l_cmdName, l_cmdOption, l_arguments);
    }
}
