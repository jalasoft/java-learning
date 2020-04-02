package cz.jalasoft.concurrent.reactive;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.concurrent.Flow;

/**
 * @author Jan Lastovicka
 * @since 2019-03-04
 */
public final class WatchEventSubscriber implements Flow.Subscriber<WatchEvent<?>> {

    private Flow.Subscription subscription;
    private final String name;


    public WatchEventSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(WatchEvent<?> item) {
        Path path = (Path) item.context();
        String name = item.kind().name();

        System.out.println(this.name + ": udalost - " + name + " na " + path);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println(name + ": konec!!!!");
    }
}
