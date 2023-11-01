package common.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation for ILogWriter that writes log to a file.
 * @author vishnurajendran
 */
public class FileLogWriter implements ILogWriter{

   private String d_filePath;
   private String dirPath = "Logs";

    /**
     * Constructor for FileLogWriter,
     * creates a file handle to write log messages to.
     */
    public FileLogWriter(){
        SimpleDateFormat l_formatter = new SimpleDateFormat("YYYY-MM-dd-HH-mm-ss");
        d_filePath = dirPath + "/Log_" + l_formatter.format(new Date())+".log";
    }
    @Override
    public void onLogEntered(LogData p_data) {
        try {
            appendToFile(p_data.getMessage());
        }
        catch (IOException ex){
            System.out.println("Error printing to file");
        }
    }

    /**
     * Creates or appends to file.
     * @param msg message to write.
     * @throws IOException when an exception occurs during write.
     */
    private void appendToFile(String msg) throws IOException{
        File l_dir = new File(dirPath);
        if(!l_dir.exists())
            l_dir.mkdir();

        File l_file = new File(d_filePath);
        FileWriter l_fileWriter = new FileWriter(l_file, true);
        l_fileWriter.write(msg+"\n");
        l_fileWriter.close();
    }
}
