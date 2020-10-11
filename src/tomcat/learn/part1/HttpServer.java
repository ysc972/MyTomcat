package tomcat.learn.part1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {


    public static final String WEB_ROOT=System.getProperty("user.dir")+ File.separator+"webroot";

    private static final String SHUTUDOWN_COMMAND="/SHUTDOWN";

    private boolean shutdown =false;

    public static void main(String[] args) {
        HttpServer httpServer=new HttpServer();
        httpServer.await();
    }

    public void await(){
        ServerSocket serverSocket=null;
        int port=8081;
        try {
            serverSocket=new ServerSocket(port,1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!shutdown){
            InputStream inputStream=null;
            OutputStream outputStream=null;
            try {
                Socket socket = serverSocket.accept();
                inputStream=socket.getInputStream();
                outputStream=socket.getOutputStream();
                //根据inputstream获取request对象
                Request request=new Request(inputStream);
                request.parse();
                //根据output对象获取到response对象,并将request对象传入
                Response response=new Response(outputStream);
                response.setRequest(request);
                response.sendStaticResource();

                //关闭socket
                socket.close();

                //判断是否shutdown
                shutdown=SHUTUDOWN_COMMAND.equals(request.getUri());
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

}
