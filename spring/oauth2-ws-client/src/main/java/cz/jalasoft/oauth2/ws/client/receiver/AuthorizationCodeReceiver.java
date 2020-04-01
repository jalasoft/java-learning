package cz.jalasoft.oauth2.ws.client.receiver;

import cz.jalasoft.oauth2.ws.client.OAuth2AuthenticationException;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;

/**
 * @author lastovicka
 */
public final class AuthorizationCodeReceiver {

    static final String HANDLER_CONTEXT_PARAM = "hadler";
    private static final String SERVLET_NAME = "AUTH_SERVLET";

    private CompletableFuture<AuthenticationCode> future;

    private Tomcat tomcat;

    public Tomcat start() {
        tomcat = new Tomcat();
        tomcat.setPort(9090);
        Context context = tomcat.addContext("/", new File(".").getAbsolutePath());
        tomcat.addServlet(context, SERVLET_NAME, AuthorizationCodeServlet.class.getName());
        context.addServletMapping("/login/oauth2/code/erste", SERVLET_NAME);

        BiConsumer<HttpServletRequest, HttpServletResponse> reqHandler = this::handler;
        context.getServletContext().setAttribute(HANDLER_CONTEXT_PARAM, reqHandler);

        return tomcat;
    }

    private void handler(HttpServletRequest req, HttpServletResponse resp) {

            String authCode = req.getParameter("code");
            String state = req.getParameter("state");

            AuthenticationCode result = new AuthenticationCode(authCode, state);

            future.complete(result);
            future = null;
    }

    public Future<AuthenticationCode> awaitAuthenticationAsync() {
        if (future != null) {
            throw new IllegalStateException("Another future awaiting.");
        }

        try {
            tomcat.start();
        } catch (LifecycleException exc) {
            throw new RuntimeException(exc);
        }

        future = new CompletableFuture<>();
        return future;
    }

    public AuthenticationCode awaitAuthentication() throws OAuth2AuthenticationException {
        try {
            Future<AuthenticationCode> future = awaitAuthenticationAsync();
            return future.get();
        } catch (InterruptedException exc) {
            throw new RuntimeException(exc);
        } catch (ExecutionException exc) {
            Throwable t = exc.getCause();

            if (t instanceof Exception) {
                throw new OAuth2AuthenticationException((Exception) t);
            }
            throw new Error(t);
        }
    }

    public void stop() {
        try {
            tomcat.stop();
        } catch (LifecycleException exc) {
            throw new RuntimeException(exc);
        }
    }
}
