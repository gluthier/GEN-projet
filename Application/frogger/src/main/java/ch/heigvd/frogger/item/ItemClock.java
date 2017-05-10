package ch.heigvd.frogger.item;

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
                Thread.sleep(Constants.ITEM_CLOCK_DELAY);
                setChanged();
                notifyObservers();
            } catch (InterruptedException ex) {
                Logger.getLogger(ItemClock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * To stop the thread and the timer
     */
    public void stop() {
        running = false;
    }
    
    /**
     * Start the thread and te timer
     */
    public void launch() {
        running = true;
        thread.start();
    }

}
