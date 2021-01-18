//Lydia Yang & Michael Nguyen

import java.util.Scanner;

public class SoundOfMusic
{
    public static char[] NOTE_LETTERS = {'C', 'D', 'E', 'F', 'G', 'A', 'B'};
    public static int[] NOTE_NUMBERS = {60, 62, 64, 65, 67, 69, 71};
    
    public static void main(String[] args) throws Exception 
    {
        MusicBox box = new MusicBox();
        Scanner scan = new Scanner(System.in);
        String userInput;
        int menuChoice = -1;


        while (menuChoice !=3)
        {
            printMenu();
        
            System.out.println("Please enter a selection:");
            menuChoice = scan.nextInt();
            scan.nextLine();

              
            //Validates the user's input and determines if it fits the criteria
            while(menuChoice <= 0 || menuChoice > 3)
            {
                System.out.println("Please enter a selection:");
                menuChoice = scan.nextInt();
            }

             //When user enters "1" as the selection to play a note
             if (menuChoice == 1)
             {
                System.out.println("Please enter a note:");
                userInput = scan.nextLine();
            
                //If user enters an invalid note, prints this.
                if (noteToInt(userInput) == -1)
                {
                    System.out.println("I don't know that note.");
                }

                //If user enters a valid note, then the note will be played on the speaker.
                else
                {
                    box.playNote(noteToInt(userInput), 500);
                }
            }

            //When user enters a "2" as the selection to place a scale
            else if (menuChoice == 2)
            {
                System.out.println("Please enter a scale:");
                userInput = scan.nextLine();
                playScale(box, userInput);
            }

              
            //program ends
            else if (menuChoice == 3)
            {
                System.exit(0);
            }  
    
        }
        
        box.close(); //MusicBox class closes.
    }
    
    //Determines the value of the user input's note.
    public static int noteToInt(String note)
    {   
        int noteTotal = 0;
        char noteChar;
        boolean bool = false;

        //This counts how many ^'s there are in "note" and then multiply by 12.
        noteTotal += (note.lastIndexOf('^') + 1) * 12;

        //Gets the substring of the note letter and suffix (basically removes ^)
        note = note.substring(note.lastIndexOf('^') + 1, note.length());

        //add by 1 if there's a #
        if (note.contains("#"))
        {
            noteTotal++;
        }

        //subtract by 1 if there's a b
        else if (note.contains("b"))
        {
            noteTotal--;
        }

        //return -1 if the suffix is invalid.
        else if (note.length() == 2)
        {
            return -1;
        }

        //turns the data type into char.
        noteChar = note.charAt(0);

        //checks to see if note is in the array
        for (int i = 0; i < NOTE_LETTERS.length; i++)
        {
            if (noteChar == NOTE_LETTERS[i])
            {
                noteTotal += NOTE_NUMBERS[i];
                bool = true;
            }
        }

        //if note is not in the array, return -1.
        if(bool == false) 
        {
            return -1;
        }
            
        return noteTotal;
    }

    //prints out the main menu
    public static void printMenu()
    {
        System.out.println("Main menu:");
        System.out.println("1. Play note");
        System.out.println("2. Play scale");
        System.out.println("3. Quit");
    }
    
    //plays the note
    public static void playNote(MusicBox box, String note)
    {
        int intNote = noteToInt(note); 
        box.playNote(intNote, 500);  
    }
    
    //plays the scale.
    public static void playScale(MusicBox box, String scaleName)
    {        
        String suffix;
        int note;

        note = noteToInt(scaleName);

        //slices the string so that the note and the suffix would be separated.
        suffix = scaleName.substring(scaleName.lastIndexOf(" ") + 1, scaleName.length()).toLowerCase();
    
       //plays major scale
        if (suffix.equals("major"))
        {
            box.playNote(note , 500);
            box.playNote(note + 2, 500);
            box.playNote(note + 4, 500);
            box.playNote(note + 5, 500);
            box.playNote(note + 7, 500);
            box.playNote(note + 9, 500);
            box.playNote(note + 11, 500);
            box.playNote(note + 12, 500);
        }

        //plays minor scale
        else if (suffix.equals("minor"))
        {
            box.playNote(note , 500);
            box.playNote(note + 2, 500);
            box.playNote(note + 3, 500);
            box.playNote(note + 5, 500);
            box.playNote(note + 7, 500);
            box.playNote(note + 8, 500);
            box.playNote(note + 10, 500);
            box.playNote(note + 12, 500);
        }
    } 
}