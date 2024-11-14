import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataReadingInterface {
    final String dataPath = "./TIMEDATA.txt";
    //called once, inits everything
    public DataReadingInterface(JPanel panel) throws IOException{
        final File DATA_FILE = new File(dataPath); //path to the txt with all the shit that matters in it
        DATA_FILE.createNewFile(); //dude this shit is so dumb idc if its ignored idk how else to make new files in java
        final Scanner scan = new Scanner(DATA_FILE);
        readFile(scan, panel);
    }

    //called when a new component is added, writes to the txt (DOESNT ADD TO THE PANEL OR ANY OTHER LOGIC, JUST THE TXT)
    public DataReadingInterface(TimeComponent time) throws IOException {writeFile(time.full);}

    private void readFile(Scanner scan, JPanel panel) throws IOException {
        //reads from the entire file and adds all time vals
        while(scan.hasNextLine()){
            String tempFullString = scan.nextLine();
            TimeComponent tempComponent = new TimeComponent(0,0,tempFullString);
            tempComponent.addToPanel(panel);
        }
    }

    private void writeFile(String full) throws IOException {
        FileWriter myWriter = new FileWriter(dataPath, true); //the true is for whether or not it appends to the end of the file instead of overwriting everything wiht the written text
        myWriter.write("\n"+full);
        myWriter.close();
    }
}
