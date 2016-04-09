// Object that loads and plays sound files.  After creating an object for a specific 
// file, the audio is played using one of three methods:
//   play()           - play the audio, method returns immediately
//   playBlocking()   - play the audio, method waits for audio to finish before returning
//   playLoop()       - play the audio in a loop forever
//
// Keith Vertanen

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class AudioFile
{
    private static final int SLEEP_MS = 50;

    private Clip clip;

    // Construct based on an audio filename
    public AudioFile(String filename)
    {
        try {
            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource(filename);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);

            AudioFormat format = audioIn.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            // Get a sound clip resource.
            clip = (Clip) AudioSystem.getLine(info);

            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Check to see if the current sound is playing
    public boolean isPlaying()
    {
        return clip.isRunning();
    }

    // Get the current position in the clip in seconds
    public double getCurrentPosition()
    {
        return      clip.getMicrosecondPosition() / 1e6;
    }

    // Find out how long this audio file is in seconds
    public double getLength()
    {
        return clip.getMicrosecondLength() / 1e6;
    }

    // Stop the sound 
    public void stop()
    {
        clip.stop();
    }

    // Play the sound and then wait until it finishes playing.
    // If the sound is already playing it will stop it and start over.
    public void playBlocking()
    {
        play();
        // We must sleep a bit otherwise to give the audio change to fire up
        do
        {
            //System.out.println("Waiting for clip to finish, sleep");
            try 
            {
                Thread.sleep(SLEEP_MS);
            } catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        while (clip.isRunning());
    }

    // Play the sound in the background and loop forever
    public void playLoop()
    {
        if (clip.isRunning())
        {
            clip.stop();
            try 
            {
                Thread.sleep(SLEEP_MS);
            } catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Play the sound in the background.
    // If the sound is already playing it will stop it and start over.
    public void play()
    {
        clip.stop();
        try 
        {
            Thread.sleep(SLEEP_MS);
        } catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        clip.setFramePosition(0);
        clip.start();
    }
}

