package ch.heigvd.server;

import java.rmi.server.UID;

/**
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
