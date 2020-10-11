package tomcat.learn.part1;

import java.io.*;

public class Response {
    private static final int BUFFER_SIZE = 1024;
    private OutputStream outputStream;
    private Request request;

    private String success="HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=utf-8";

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws Exception {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fileInputStream = null;
        File file = new File(HttpServer.WEB_ROOT, request.getUri());
        if(file==null){
            System.out.println("请求地址无效");
           throw new Exception();
        }
        try {
            if (file.exists()) {
                outputStream.write(success.getBytes());
                fileInputStream = new FileInputStream(file);
                int ch = fileInputStream.read(bytes, 0, BUFFER_SIZE);
                while (ch != -1) {
                    outputStream.write(bytes, 0, ch);
                    ch = fileInputStream.read(bytes, 0, BUFFER_SIZE);
                }
            } else {
                String erromessage="erro";
                outputStream.write(erromessage.getBytes());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


