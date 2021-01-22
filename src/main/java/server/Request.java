package server;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mango
 * @date 2021/1/22 17:40
 * @description: 请求信息(根据获取到的inputStream封装)
 */
public class Request {

    /**
     * 请求方式
     */
    private String method;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 输入流  其他属性从输入流解析出来
     */
    private InputStream inputStream;

    public Request() {

    }

    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        //从输入流中获取请求信息
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        byte[] bytes = new byte[count];
        inputStream.read(bytes);
        String str = new String(bytes);
        //获取第一行  请求头信息  协议、url、请求方式
        String firstLine = str.split("\\n")[0];
        //按空格截取
        String[] strings = firstLine.split(" ");
        this.method = strings[0];
        this.url = strings[1];
        System.out.println(this.method);
        System.out.println(this.url);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", inputStream=" + inputStream +
                '}';
    }
}
