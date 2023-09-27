package common;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is purely meant to be a data class. a flat string is used
 * to fill this class. the components of a command are split and provided
 * @author vishnurajendran
 */
public class Command {
    private static final String OPTION_PREFIX = "-";


    private String d_cmdName;
    private ArrayList<CommandAttribute> d_attributes;

    /**
     * default constructor,
     * Initialises an empty object
     */
    public Command(){
        this("",new ArrayList<>());
    }

    /**
     * Parameterised constructor for initialising this class with required data.
     * @param p_cmdName the name for this command.
     * @param p_attributes attributes of the command. (can be empty)
     */
    public Command(String p_cmdName, ArrayList<CommandAttribute> p_attributes){
        d_cmdName = p_cmdName;
        d_attributes =p_attributes;
    }

    /**
     * @return name of the command
     */
    public String getCmdName() {
        return d_cmdName;
    }


    /**
     * @return attributes for this command. (can be empty)
     */
    public ArrayList<CommandAttribute> getCmdAttributes() {return d_attributes;}

    /**
     * this method converts a flat string to and Command instance.
     * @param p_inputString flat string for command processing.
     */
    public static Command parseString(String p_inputString){
        if(p_inputString == null || p_inputString.isBlank())
            return null;
        //remove any trailing or leading spaces.
        p_inputString = p_inputString.trim();
        //initialise the vars we need, for processing
        String[] l_initialSplit = p_inputString.split(" ", 2);

        //if we only have just the command name, we return directly from here.
        if(l_initialSplit.length == 1){
            return new Command(l_initialSplit[0], new ArrayList<>());
        }

        // first in the list is the cmd name
        String l_cmdName = l_initialSplit[0];

        if(!l_initialSplit[1].startsWith("-")){
            String[] l_args = l_initialSplit[1].split("\\s+");
            CommandAttribute attribute = new CommandAttribute("",new ArrayList<>(Arrays.asList(l_args)));
            return new Command(l_initialSplit[0], new ArrayList<>(Arrays.asList(attribute)));
        }

        // we split the remaining by `-` to seperate each option + argument string.
        String[] l_attributes = l_initialSplit[1].split("-+");

        ArrayList<CommandAttribute> l_cmdAttributes = new ArrayList<>();

        //we iterate through all the attributes and convert them to CommandAttribute
        for(String l_attribString : l_attributes){

            //ignore empty lines.
            if(l_attribString.isBlank())
                continue;

            l_attribString = l_attribString.trim();
            String[] l_attribComponents = l_attribString.split("\\s+");

            //if we see only one component in this string, we consider this as
            //a flag only attribute
            if(l_attribComponents.length == 1){
                l_cmdAttributes.add(new CommandAttribute(l_attribComponents[0], new ArrayList<>()));
                continue;
            }

            ArrayList<String> l_arguments = new ArrayList<>(Arrays.asList(l_attribComponents).subList(1, l_attribComponents.length));
            l_cmdAttributes.add(new CommandAttribute(l_attribComponents[0], l_arguments));
        }
        return new Command(l_cmdName, l_cmdAttributes);
    }
}
