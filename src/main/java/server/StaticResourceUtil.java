package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author mango
 * @date 2021/1/22 19:45
 * @description:
 */
public class StaticResourceUtil {

    /**
     * 获取静态资源文件的绝对路径
     *
     * @param path
     * @return
     */
    public static String getAbsolutePath(String path) {
        String absolutePath = StaticResourceUtil.class.getResource("/").getPath();
        return absolutePath.replaceAll("\\\\", "/") + path;
    }

    /**
     * 读取静态资源文件输入流， 根据输出流输出
     *
     * @param inputStream
     * @param outputStream
     */
    public static void outputStaticResource(InputStream inputStream, OutputStream outputStream) throws IOException {
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        int resourceSize = count;
        outputStream.write(HttpProtocolUtil.getHttpHeader(resourceSize).getBytes());

        long written = 0;
        int byteSize = 1024;
        byte[] bytes = new byte[byteSize];
        while (written < resourceSize) {
            if (written + byteSize > resourceSize) {
                byteSize = (int) (resourceSize - written);
                bytes = new byte[byteSize];
            }
            inputStream.read(bytes);
            outputStream.write(bytes);

            outputStream.flush();
            written += byteSize;
        }
    }
}
