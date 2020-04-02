package cz.jalasoft.concurrent;

import java.nio.file.Path;

/**
 * @author Jan Lastovicka
 * @since 2019-02-28
 */
public class WatchEvent {

    private final Path path;
    private final WatchedPath.Status status;

    public WatchEvent(Path path, WatchedPath.Status status) {
        this.path = path;
        this.status = status;
    }
}
