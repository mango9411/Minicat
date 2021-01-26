package server;

import java.io.IOException;

/**
 * @author mango
 * @date 2021/1/26 22:34
 * @description:
 */
public class MangoServlet2 extends HttpServlet{

    @Override
    public void doGet(Request request, Response response) throws IOException {
        String content = "<h1>mango servlet2 get</h1>";
        try {
            response.output(HttpProtocolUtil.getHttpHeader(content.getBytes().length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        String content = "<h1>mango servlet2 post</h1>";
        try {
            response.output(HttpProtocolUtil.getHttpHeader(content.getBytes().length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void destory() {

    }
}
