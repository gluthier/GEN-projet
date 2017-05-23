package ch.heigvd.frogger;

import ch.heigvd.frogger.Constants;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clock pour le mouvement des obstacles
 *
 * @author Maxime Guillod
 * @author Gabriel Luthier
 */
public class ItemClock extends Observable implements Runnable {

    private static ItemClock instance = null;
    private Thread thread;
    private volatile boolean running = false;
    
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();

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
            synchronized (pauseLock) {
                if (!running) {
                    break;
                }
                if (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException ex) {
                        break;
                    }
                }
            }
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
     * Pause the timer
     */
    public void pause() {
        synchronized (pauseLock) {
            paused = true;
        }
    }
    
    /**
     * Resume the timer
     */
    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }
    /**
     * To pause the timer
     */
    public void stop() {
        this.deleteObservers();
        running = false;
    }
    
    /**
     * Check whether the clock is running
     * @return If the clock is running
     */
    public boolean isRunning() {
        return running;
    }
}
