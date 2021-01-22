package server;

import java.io.IOException;

/**
 * @author mango
 * @date 2021/1/22 20:07
 * @description:
 */
public interface Servlet {

    void init();

    void destory();

    void service(Request request, Response response) throws IOException;
}
