package server;

import java.io.IOException;

/**
 * @author mango
 * @date 2021/1/22 20:11
 * @description:
 */
public class MangoServlet extends HttpServlet {

    @Override
    public void doGet(Request request, Response response) {
        String content = "<h1>mango servlet get</h1>";
        try {
            response.output(HttpProtocolUtil.getHttpHeader(content.getBytes().length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        String content = "<h1>mango servlet post</h1>";
        try {
            response.output(HttpProtocolUtil.getHttpHeader(content.getBytes().length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void service(Request request, Response response) throws IOException {
        super.service(request, response);
    }

    @Override
    public void init() {

    }

    @Override
    public void destory() {

    }
}
