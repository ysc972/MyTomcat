package tomcat.learn.part1;

import java.io.IOException;
import java.io.InputStream;

public class Request {

    private InputStream inputStream;

    private String uri;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    public void parse(){
        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer=new byte[2048];

        try {
            i=inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i=-1;
        }
        for(int j=0;j<i;j++){
            request.append((char)buffer[j]);
        }
        System.out.println(request.toString());
        uri=parseuri(request.toString());

    }
    private String  parseuri(String request){
        int index1,index2;
        index1=request.indexOf(' ');
        if(index1!=-1){
            index2=request.indexOf(' ',index1+1);
            if(index2>index1){
                return request.substring(index1+1,index2);
            }
        }
        return null;
    }
    public String getUri(){
        return this.uri;
    }
}
