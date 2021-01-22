package server;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

/**
 * @author mango
 * @date 2021/1/21 21:14
 * @description: Minicat启动入口
 */
public class Bootstrap {

    /**
     * server.xml里面的端口号
     */
    private int port = 8080;

    private Map<String, HttpServlet> servletMap = new HashMap<>();

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Minicat启动需要初始化的操作
     */
    public void start() throws IOException {
        //解析配置文件, web.xml
        loadServlet();

        //线程池
        int corePoolSize = 10;
        int maxMumPollSize = 50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize, maxMumPollSize, keepAliveTime, unit, workQueue, threadFactory, rejectedExecutionHandler);

        //完成1.0版本 浏览器请求http://localhost:8080， 返回一个固定的字符串 this Minicat it's mango
        ServerSocket socket = new ServerSocket(port);
        System.out.println("---------------------------->   Minicat start on port:" + port);
        //        while (true) {
        //            Socket accept = socket.accept();
        //            String str = "this Minicat it's mango";
        //            String response = HttpProtocolUtil.getHttpHeader(str.getBytes().length) + str;
        //            accept.getOutputStream().write(response.getBytes());
        //            accept.close();
        //        }

        //完成Minicat2.0 封装Request与Response对象，返回静态资源文件
        //        while (true) {
        //            Socket accept = socket.accept();
        //            InputStream inputStream = accept.getInputStream();
        //            Request request = new Request(inputStream);
        //            Response response = new Response(accept.getOutputStream());
        //            response.outputHtml(request.getUrl());
        //            accept.close();
        //        }

        // 请求动态资源 Servlet
        //        while (true) {
        //            Socket accept = socket.accept();
        //            InputStream inputStream = accept.getInputStream();
        //            Request request = new Request(inputStream);
        //            Response response = new Response(accept.getOutputStream());
        //
        //            HttpServlet httpServlet = servletMap.get(request.getUrl());
        //            if(httpServlet != null){
        //                //动态资源
        //                httpServlet.service(request, response);
        //            } else {
        //                //静态资源
        //                response.outputHtml(request.getUrl());
        //            }
        //            accept.close();
        //        }

        // 多线程
        //        while (true) {
        //            Socket accept = socket.accept();
        //            RequestProcessor processor = new RequestProcessor(accept, servletMap);
        //            processor.start();
        //        }

        // 多线程 线程池
        while (true) {
            Socket accept = socket.accept();
            RequestProcessor processor = new RequestProcessor(accept, servletMap);
            threadPoolExecutor.execute(processor);
        }
    }

    /**
     * 初始化servlet  加载web.xml
     */
    private void loadServlet() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(inputStream);
            Element rootElement = document.getRootElement();
            List<Element> list = rootElement.selectNodes("//servlet");
            for (Element element : list) {
                //找到servletName
                Element servletNameElement = (Element) element.selectSingleNode("servlet-name");
                Element servletClassElement = (Element) element.selectSingleNode("servlet-class");
                String servletName = servletNameElement.getStringValue();
                String servletClass = servletClassElement.getStringValue();

                //根据servletName找到url-pattern
                Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name = '" + servletName + "']");
                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();

                //放入servlet map
                servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
            }
        } catch (DocumentException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
