package ch.heigvd.frogger;

import ch.heigvd.frogger.Constants;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clock pour le mouvement des obstacles
 *
 * @author Maxime Guillod
 */
public class ItemClock extends Observable implements Runnable {

    private static ItemClock instance = null;
    private Thread thread;
    private boolean running = false;

    private ItemClock() {
        thread = new Thread(this);
        /*
        Lancement de la clock dès le premier appelle à getInstance
         */
        launch();
    }

    public static ItemClock getInstance() {
        if (instance == null) {
            instance = new ItemClock();
        }
        return instance;
    }

    @Override
    public void run() {
        while (running) {
            try {
                setChanged();
                notifyObservers();
                Thread.sleep(Constants.ITEM_CLOCK_DELAY);
            } catch (InterruptedException e) {
                Logger.getLogger(ItemClock.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     * Start the thread and the timer
     */
    public void launch() {
        running = true;
        thread.start();
    }
    
    /**
     * To pause the timer
     */
    public void pause() {
        running = false;
    }
    
    /**
     * To restart the timer
     */
    public void restart() {
        running = true;
    }
    
    /**
     * Check whether the clock is running
     * @return If the clock is running
     */
    public boolean isRunning() {
        return running;
    }
}
