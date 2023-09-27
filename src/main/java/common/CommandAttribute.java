package common;

import java.util.ArrayList;

/**
 * this class holds attributes for a command.
 * @author vishnurajendran
 */

public class CommandAttribute {
    private String d_option;
    private ArrayList<String> d_arguments;

    public CommandAttribute(){
        this("", new ArrayList<>());
    }

    public CommandAttribute(String p_option, ArrayList<String> p_arguments){
        d_arguments = p_arguments;
        d_option = p_option;
    }

    public String getOption() {
        return d_option;
    }

    public ArrayList<String> getArguments() {
        return d_arguments;
    }
}
