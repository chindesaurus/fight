/**
 * Fight.java
 *
 * Compilation:  javac Fight.java
 * Execution:    java Fight
 * Dependencies: AudioFile.java Stopwatch.java
 *
 * Reads out combinations for my shadowboxing workout.
 *
 * @author chindesaurus
 * @version 1.00
 */

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Fight {

    // the length of rounds in seconds (120 for amateur, 180 for pro)
    private final static int ROUND_LEN = 180;

    // the number of punching combinations to choose from
    private final static int COMBOS = 18;

    // the time in between rounds in seconds
    private final static int REST = 60;

    // for generating pseudorandom numbers
    private static Random generator = new Random();


    /**
     * This class is non-instantiable.
     */
    private Fight() { }


    /**
     * Randomly chooses the number of a punching combo.
     *
     * @return an integer in [1, COMBOS]
     */
    private static int getCombo() {
        return generator.nextInt(COMBOS) + 1;
    }


    /**
     * The main method.
     *
     * @param argv the command-line arguments
     */
    public static void main(String[] argv) {
       
        int rounds;
        Scanner console = new Scanner(System.in);

        do {
            System.out.print("How many rounds will you be doing today? ");
        } while (!console.hasNextInt());
            
        rounds = Math.abs(console.nextInt());
        System.out.println("Very good, sir.");

        
        // go through rounds 
        for (int i = 0; i < rounds; i++) {

            System.out.println("Round " + (i + 1) + " - Begin!");

            // start the clock
            Stopwatch clock = new Stopwatch();
            while (clock.elapsedTime() < ROUND_LEN) {
                String file = "./waves/" + getCombo() + ".wav";
                AudioFile combo = new AudioFile(file);
                combo.playBlocking();
            }
            
            if (i == rounds - 1) {
                break;
            }
            System.out.println("Dingding!!! Take " + REST + " seconds of rest.");

            // rest for REST seconds between rounds
            try {
                TimeUnit.SECONDS.sleep(REST);
            } catch (InterruptedException e) {
            // handle exception
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Good workout!");
    }
}
