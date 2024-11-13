import javax.swing.*;

public class Main extends JFrame {

    //enum holds all constant values. this is better than just making a bunch of static final variables
    public enum INT_CONSTANTS{
        WINDOW_SIZE(500), //size of the window
        BOUNDS(30); //how many pixels ui elements should be offset by (so they dont hug the edges of the window and look weird)
        public final int value;

        //returns the value of the enum when called
        INT_CONSTANTS(int value){this.value = value;}
    }
    static int currentTime = 0;
    //initalizes all ui elements
    public static void main(String[] args) {
        final int WINDOW_SIZE = INT_CONSTANTS.WINDOW_SIZE.value;
        final int BOUNDS = INT_CONSTANTS.BOUNDS.value;

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

        //this displays all the collected times. it uses a txt file to read and save times.
        JScrollPane collectedTimesList = new JScrollPane();
        collectedTimesList.setBounds(WINDOW_SIZE/2, BOUNDS, (WINDOW_SIZE/2)-BOUNDS, WINDOW_SIZE-(BOUNDS*3)); //bounds has to be multiplied by 3 because the y value pos is already offset by the bounds, so the size has to be even smaller to compensate
        panel.add(collectedTimesList);

        //this is just for visuals
        JLabel collectedTimesTitle = new JLabel("Collected times");
        collectedTimesTitle.setBounds((int) ((collectedTimesList.getWidth())+BOUNDS*3.5), 0, WINDOW_SIZE/5, WINDOW_SIZE/10);
        panel.add(collectedTimesTitle);

        //displays current time (update this every second maybe? every 30 seconds if there's performance issues)
        JLabel currentTimeDisplay = new JLabel("Current Time: "+currentTime);
        currentTimeDisplay.setBounds(collectedTimesTitle.getWidth(), 0, WINDOW_SIZE/5, WINDOW_SIZE/10);
        panel.add(currentTimeDisplay);

        //this is the button u click to actually collect shit
        JButton timeCollectorButton = new JButton("Collect!");
        timeCollectorButton.setBounds(collectedTimesTitle.getWidth(), WINDOW_SIZE/3, WINDOW_SIZE/5, WINDOW_SIZE/10);
        panel.add(timeCollectorButton);

        //runs when the time collection button is clicked
        timeCollectorButton.addActionListener(e ->{

        });

        /*so what im cooking up is that u click a button to collect every time of day like pokemon cards like u gotta get 5:00 and 5:01 and 5:02 yeah yeah yeah
        so the main menu displays the time and the times youve currently collected by reading off a txt file
        and when that's done u can also make a counter of all the ones u have vs how many you need and a list of the ones you dont have
         */
        componentInit(panel);
    }

    //initializes all elements in the panel
    private static void componentInit(JPanel panel){
        final int WINDOW_SIZE = INT_CONSTANTS.WINDOW_SIZE.value;


    }
}