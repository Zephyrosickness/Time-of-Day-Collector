import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class TimeComponent{
    int hour;
    int minute;
    String full; //this is just the full "HH:MM" string
    final private String REGEX_PATTERN = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]?$"; //the regex pattern to make sure that full matches | this could be a local var i guess but thuis makes the code more readable
    final public ArrayList<String> timeArrayList = new ArrayList<>();

    //formats given string and adds correct attributes
    public TimeComponent(int hour, int minute, String full){if(parameterChecksum(hour,minute,full)){updateAttributes(hour,minute,full);}}

    protected void addToPanel(JPanel panel) throws IOException {
        if(this.objectValidityChecksum()&&!timeArrayList.contains(full)&&!full.equals("0:0")){ //any invalid values will default to 0:0 so this is another bandaid fix to the issue of users entering invalid values
            JTextField addedTime = new JTextField(full);
            addedTime.setEditable(false);
            panel.add(addedTime);
            System.out.println("im being called! addtopanel!");
            timeArrayList.add(full);
        }
    }

    //u will never guess what this does... it updates the attributes
    protected void updateAttributes(int hour, int minute, String full){
        if(parameterChecksum(hour, minute, full)){
            try{
                //if the full value is given: runs this method | if not given, it will use hour/minute values
                if(full.matches(REGEX_PATTERN)) {
                    this.hour = Integer.parseInt(full.substring(0, 1)); //sets hour var to the first 2 nums in fullstring: this can be done because the validity check has already been passed
                    this.minute = Integer.parseInt(full.substring(3, 4));
                    this.full = full;
                }else{
                    this.hour = hour;
                    this.minute = minute;
                    this.full = hour + ":" + minute;
                }
            }catch(Exception e){throw new RuntimeException(e);}
        }
    }

    //only checks parameters (ie, the component is not yet initalized | only really used in the updateattributes method
    private boolean parameterChecksum(int hour, int minute, String full){
        final boolean hrChk = hour>=0&&hour<=23; //hour is bigger than 0 and smaller than 24
        final boolean minChk = minute>=0&&minute<60; //min is bigger than 0 and smaller than 60
        boolean fullChk = true; //this is set to true on init and then changed to an actual check if the full var isnt null because sometimes u wanna pass full as null if u only have valid hour/min vars
        if(full.matches(REGEX_PATTERN)){
            //checks if the full string matches the format of HH:MM and if the hour inside the fullString is 0-23 and if the min is 0-59
            final int fullHr = Integer.parseInt(full.substring(0,1));
            final int fullMin = Integer.parseInt(full.substring(3,4));
            final boolean fullHrChk = fullHr>=0&&fullHr<=23;
            final boolean fullMinChk = fullMin>=0&&fullMin<=23;
            fullChk = fullHrChk&&fullMinChk;
        }

        final boolean complete = hrChk&&minChk&&fullChk; //all 3 checks must return true

        //prints bigass error code if anything is fucked up
        if(!complete) {System.out.println("!![!ERROR!]!!\ndude ur time component is fuucked up.\n[FULL]: "+full+" | [FULL CHECKSUM]: "+fullChk+"HOUR: "+hour+" | [MINUTE]: "+minute+"\n[HR CHECKSUM]: "+hrChk+" | [MIN CHECKSUM]: "+minChk);}

        return complete;
    }

    //checks the actual attributes of the object you're passing
    private boolean objectValidityChecksum(){
        boolean hrChk = hour>=0&&hour<=23; //hour is bigger than 0 and smaller than 24
        boolean minChk = minute>=0&&minute<60; //min is bigger than 0 and smaller than 60
        boolean fullChk = full.matches(REGEX_PATTERN); //this doesnt have to be as complicated as the other checksum because its already known that all 3 values are going to be valid
        boolean complete = hrChk&&minChk&&fullChk; //all 3 checks must return true

        //prints bigass error code if anything is fucked up
        if(!complete) {System.out.println("!![!ERROR!]!!\ndude ur time component is fuucked up.\n[FULL]: "+full+" | [FULL CHECKSUM]: "+fullChk+"HOUR: "+hour+" | [MINUTE]: "+minute+"\n[HR CHECKSUM]: "+hrChk+" | [MIN CHECKSUM]: "+minChk);}

        return complete;
    }
}
