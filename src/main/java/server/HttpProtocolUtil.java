package server;

/**
 * @author mango
 * @date 2021/1/21 21:31
 * @description: http协议工具类  提供响应头信息 200&404
 */
public class HttpProtocolUtil {

    public static String getHttpHeader(long contentLength) {

        return "HTTP/1.1 200 OK \n" +
                "Content-type: text/html \n" +
                "Content-length: " + contentLength + "\n" +
                "\r\n";
    }

    public static String getHttpHeader() {
        String str404 = "<h1>404 not found</h1>";
        return "HTTP/1.1 404 NOT FOUND \n" +
                "Content-type: text/html \n" +
                "Content-length: " + str404.length() + "\n" +
                "\r\n" + str404;
    }
}
