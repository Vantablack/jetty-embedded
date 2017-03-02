import com.yaowei.servlet.IndexServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Guide: http://nikgrozev.com/2014/10/16/rest-with-embedded-jetty-and-jersey-in-a-single-jar-step-by-step/
 *
 * @author Chong Yao Wei
 */
public class Main {
    public static void main(String[] args) throws Exception {

        // Create a basic jetty server object that will listen on port 8080.
        // Note that if you set this to port 0 then a randomly available port
        // will be assigned that you can either look in the logs for the port,
        // or programmatically obtain it for use in test cases.
        Server jettyServer = new Server(8080);

        // A ServletContextHandler is a specialization of ContextHandler with support for standard sessions and Servlets.
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");

        jettyServer.setHandler(context);

        // Passing in the class for the Servlet allows jetty to instantiate an
        // instance of that Servlet and mount it on a given context path.

        // IMPORTANT:
        // This is a raw Servlet, not a Servlet that has been configured
        // through a web.xml @WebServlet annotation, or anything similar.
        context.addServlet(IndexServlet.class, "/*");

        ResourceConfig config = new ResourceConfig().packages("com.yaowei.rest");

        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(config));
        jerseyServlet.setInitOrder(0);
        context.addServlet(jerseyServlet, "/api/*");

        // Tells the Jersey Servlet which REST service/class to load.
        // https://jersey.java.net/documentation/latest/appendix-properties.html
        // jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "com.yaowei.rest");

        try {
            jettyServer.start();
            // The use of server.join() the will make the current thread join and
            // wait until the server is done executing.
            // See
            // http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
