package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author mango
 * @date 2021/1/22 17:40
 * @description: 响应信息
 */
public class Response {

    private OutputStream outputStream;

    public Response() {

    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void output(String content) throws IOException {
        this.outputStream.write(content.getBytes());
    }

    /**
     * @param path url 根据url来获取到静态资源的绝对路径，读取该静态资源文件，输出
     */
    public void outputHtml(String path) throws IOException {

        String resourcePath = StaticResourceUtil.getAbsolutePath(path);
        File file = new File(resourcePath);
        if (file.exists() && file.isFile()) {
            StaticResourceUtil.outputStaticResource(new FileInputStream(file), outputStream);
        } else {
            output(HttpProtocolUtil.getHttpHeader());
        }
    }
}
