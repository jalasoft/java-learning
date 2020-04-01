package cz.jalasoft.oauth2.ws.client.receiver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.BiConsumer;

/**
 * @author lastovicka
 */
public class AuthorizationCodeServlet extends HttpServlet {

    private BiConsumer<HttpServletRequest, HttpServletResponse> consumer;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        consumer = (BiConsumer<HttpServletRequest, HttpServletResponse>) context.getAttribute(AuthorizationCodeReceiver.HANDLER_CONTEXT_PARAM);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        consumer.accept(req, resp);
    }
}
