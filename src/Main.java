import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends JFrame {

    //enum holds all constant values. this is better than just making a bunch of static final variables
    public enum INT_CONSTANTS{
        WINDOW_SIZE(500), //size of the window
        BOUNDS(30); //how many pixels ui elements should be offset by (so they dont hug the edges of the window and look weird)
        public final int value;

        //returns the value of the enum when called
        INT_CONSTANTS(int value){this.value = value;}
    }

    //initalizes all ui elements and generally does initial setup
    public static void main(String[] args) throws IOException {
        final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm"); //truncates all values that isnt hours and minutes
        TimeComponent currentTime = new TimeComponent(0,0,(timeFormat.format(LocalTime.now())));  //the current time WOAAAHH :EXPLODING HEAD EMOTE:
        final int WINDOW_SIZE = INT_CONSTANTS.WINDOW_SIZE.value;
        final int BOUNDS = INT_CONSTANTS.BOUNDS.value;
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); //this will schedule methods to run repeatedly on a delay like the one that updates the current date/time every second

        //changes theme to old people windows
        try{
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch (Exception e) {System.out.println("error with look and feel!\n------DETAILS------\n"+e.getMessage());}

        //the JFrame is the window itself. nothing is added to it except for the panel
        JFrame frame = new JFrame("Time of Day Collector");
        frame.setSize(WINDOW_SIZE,WINDOW_SIZE); //placeholder sizes
        frame.setResizable(false);
        frame.setVisible(true);

        //the panel is an "invisible box" that elements are applied onto. this panel is for everything that isnt in the scrollable collection panel
        //currently this panel's size is the entire window. it's supposed to be applied UNDER the scroll panel, but if the scroll panel has any errors, make this width windowsize/2
        JPanel panel = new JPanel();
        panel.setBounds(0,0,WINDOW_SIZE, WINDOW_SIZE);
        panel.setLayout(null); //this is set to null so you can directly specify the x/y values of elements instead of using a manager
        frame.add(panel);

        //this is the panel that collected times are added to: it uses a txt file to read and save times
        JPanel collectedTimesPanel = new JPanel();
        collectedTimesPanel.setLayout(new BoxLayout(collectedTimesPanel, BoxLayout.Y_AXIS)); //allows times to be added as individual boxes

        //this displays all the collected times in a scrollable format
        JScrollPane collectedTimesList = new JScrollPane(collectedTimesPanel);
        collectedTimesList.setBounds(WINDOW_SIZE/2, BOUNDS, (WINDOW_SIZE/2)-BOUNDS, WINDOW_SIZE-(BOUNDS*3)); //bounds has to be multiplied by 3 because the y value pos is already offset by the bounds, so the size has to be even smaller to compensate
        panel.add(collectedTimesList);

        //this is just for visuals
        JLabel collectedTimesTitle = new JLabel("Collected times");
        collectedTimesTitle.setBounds((int) ((collectedTimesList.getWidth())+BOUNDS*3.5), -BOUNDS/3, WINDOW_SIZE/5, WINDOW_SIZE/10);
        panel.add(collectedTimesTitle);

        //displays current time (update this every second maybe? every 30 seconds if there's performance issues)
        JLabel currentTimeDisplay = new JLabel("Current Time: "+currentTime);
        currentTimeDisplay.setBounds(collectedTimesTitle.getWidth(), -BOUNDS/3, WINDOW_SIZE/2, WINDOW_SIZE/10);
        panel.add(currentTimeDisplay);

        //this is the button u click to actually collect shit
        JButton timeCollectorButton = new JButton("Collect!");
        timeCollectorButton.setBounds(collectedTimesTitle.getWidth(), WINDOW_SIZE/3, WINDOW_SIZE/5, WINDOW_SIZE/10);
        panel.add(timeCollectorButton);

        //runs when the time collection button is clicked
        timeCollectorButton.addActionListener(e -> {
            try{currentTime.addToPanel(collectedTimesPanel);
                System.out.println(currentTime);
            }catch (IOException ex) {throw new RuntimeException(ex);}
            panel.repaint();
            panel.revalidate();
        });

        //method that gets called every second makes the current time update
        Runnable refreshCurrentDate = ()->{
            currentTime.updateAttributes(0,0, timeFormat.format(LocalTime.now())); //gets the current time using the format specified above
            currentTimeDisplay.setText("Current Time: "+currentTime.full);
        };

        scheduler.scheduleAtFixedRate(refreshCurrentDate, 0, 1, TimeUnit.SECONDS); //schedules the current time to refresh every second

        new DataReadingInterface(collectedTimesPanel);
        panel.repaint();
        panel.revalidate();
    }
}