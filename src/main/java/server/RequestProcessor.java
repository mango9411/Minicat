package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @author mango
 * @date 2021/1/22 20:50
 * @description:
 */
public class RequestProcessor extends Thread {

    private Socket accept;

    private Map<String, HttpServlet> servletMap;

    public RequestProcessor(Socket accept, Map<String, HttpServlet> servletMap) {
        this.accept = accept;
        this.servletMap = servletMap;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = accept.getInputStream();
            Request request = new Request(inputStream);
            Response response = new Response(accept.getOutputStream());

            HttpServlet httpServlet = servletMap.get(request.getUrl());
            if(httpServlet != null){
                //动态资源
                httpServlet.service(request, response);
            } else {
                //静态资源
                response.outputHtml(request.getUrl());
            }
            accept.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
