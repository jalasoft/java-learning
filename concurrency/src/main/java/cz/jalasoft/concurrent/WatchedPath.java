package cz.jalasoft.concurrent;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jan Lastovicka
 * @since 2019-02-28
 */
public final class WatchedPath {

    public enum Status {
        NOT_EXISTS, MODIFIED, READ
    }

    private final Path path;
    private final List<Status> statuses;

    WatchedPath(Path path) {
        this.path = path;
        this.statuses = new ArrayList<>();
    }

    boolean addNewStatus(Status status) {
        if (statuses.isEmpty()) {
            statuses.add(status);
            return true;
        }

        Status lastStatus = statuses.get(statuses.size()-1);

        if (lastStatus == status) {
            return false;
        }

        statuses.add(status);
        return true;
    }
}
