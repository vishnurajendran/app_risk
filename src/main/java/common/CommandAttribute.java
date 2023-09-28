package common;

import java.util.ArrayList;

/**
 * This class holds attributes for a command.
 * @author vishnurajendran
 */

public class CommandAttribute {
    private String d_option;
    private ArrayList<String> d_arguments;

    /**
     * Default constructor, initialises and empty
     * object.
     */
    public CommandAttribute() {
        this("", new ArrayList<>());
    }

    /**
     * Parameterised constructor for Command Attribute
     * @param p_option option for this attribute
     * @param p_arguments list of string as arguments for this attribute.
     */
    public CommandAttribute(String p_option, ArrayList<String> p_arguments) {
        d_arguments = p_arguments;
        d_option = p_option;
    }

    /**
     * @return option for this attribute (can be empty)
     */
    public String getOption() {
        return d_option;
    }

    /**
     * @return list of arguments for this attribute (can be empty)
     */
    public ArrayList<String> getArguments() {
        return d_arguments;
    }
}
