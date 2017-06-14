package ch.heig.bdd;

import java.rmi.server.UID;

/**
 * Interface for log into our database
 *
 * @author Maxime Guillod
 */
public interface ILog {

    /**
     * Get the unique id (UID) of the instance on one class (or one thread)
     *
     * @return
     */
    public UID getUid();
}
