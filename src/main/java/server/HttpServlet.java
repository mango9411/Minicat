package server;

import java.io.IOException;

/**
 * @author mango
 * @date 2021/1/22 20:08
 * @description:
 */
public abstract class HttpServlet implements Servlet {

    public abstract void doGet(Request request, Response response) throws IOException;

    public abstract void doPost(Request request, Response response);

    @Override
    public void service(Request request, Response response) throws IOException {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            doGet(request, response);
        } else if (request.getMethod().equalsIgnoreCase("POST")) {
            doPost(request, response);
        }
    }
}
