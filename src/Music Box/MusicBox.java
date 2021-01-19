import java.util.Arrays;
import javax.sound.midi.*;
public class MusicBox implements AutoCloseable {
   private Synthesizer synthesizer;
   private MidiChannel[] channels;
   private boolean noSound = false; // change this to "true" and you won't hear any sound.

   // You can run this file to test the sound synthesizer on your computer.
   public static void main(String[] args) {
      MusicBox box = new MusicBox(0); // try different numbers here, up to 127.
      box.playNote(60, 600);
      box.playNote(64, 600);
      box.playNote(67, 600);
      box.playChord(new int[] {60, 64, 67, 72}, 3000);
      box.close();
   }

   /**
    * Creates a MusicBox using the default piano instrument.
    */
   public MusicBox() {
      initialize(0);
   }

   
   /**
    * Creates a MusicBox using the given instrument number.
    */
   public MusicBox(int instrument) {
      initialize(instrument);
   }

   private void initialize(int instrument) {
      try {
         if (!noSound) {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();

            channels = synthesizer.getChannels();

            Instrument[] instr = synthesizer.getDefaultSoundbank()
             .getInstruments();
            synthesizer.loadInstrument(instr[instrument]);
            channels[0].programChange(instrument);
         }

      }
      catch (Exception ex) {
         System.out.println("Could not load the MIDI synthesizer.");
      }
   }

   /**
    * Closes the MIDI synthesizer resource.
    */
   public void close() {
      if (synthesizer != null)
         synthesizer.close();
   }

   /**
    * Changes the synthesizer instrument.
    * @param instrument a number from 0 to 127.
    */
   public void setInstrument(int instrument) {
      if (!noSound) {
         Instrument[] instr = synthesizer.getDefaultSoundbank()
             .getInstruments();
         synthesizer.loadInstrument(instr[instrument]);
         for (MidiChannel c : synthesizer.getChannels()) {
            c.programChange(instrument);
         }
      }
   }

   /**
    * Plays the given note integer for the given number of milliseconds.
    */
   public void playNote(int note, int milliseconds) {
      System.out.println("<MusicBox: playNote(" + note + ", " + milliseconds +
       ")>");
      if (!noSound && channels != null && channels.length > 0) {
         channels[0].noteOn(note, 120);
         sleep(milliseconds);
         channels[0].noteOff(note);
      }
   }

   public void playChord(int[] notes, int milliseconds) {
      System.out.println("<MusicBox: playChord(" + Arrays.toString(notes) +
       ", " + milliseconds + ")>");

      if (!noSound && channels != null && channels.length > 0) {
         int channel = 0;
         for (int n : notes) {
            channels[channel++].noteOn(n, 120);
         }
         sleep(milliseconds);
         for (channel = 0; channel < notes.length; channel++) {
            channels[channel].noteOff(notes[channel]);
         }
      }
   }

   private static void sleep(int length) {
      try {
         Thread.sleep(length);
      }
      catch (Exception ex) {}
   }
}
