import com.sun.net.ssl.internal.ssl.Provider;
import javax.net.ssl.SSLSocket; //These do not utilize the server feature, as it is only for the server.
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.security.Security;



public class client {
    public static void main(String[] args){

        Security.addProvider(new Provider());
        //Issues occurred when locating a path. Put the keystore and the truststore files into main Java directory
        System.setProperty("javax.net.ssl.trustStore", "../glassfish-5.0.1/glassfish5/glassfish/domains/domain1/config/trustStore.jts");
        System.setProperty("javax.net.ssl.trustStorePassword", "hello12");
        //The statement below is not necessary but just describes what is happening with the actual encryption
        System.setProperty("javax.net.debug", "all");

        try{
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
            //Create a new socket identifying the server and the port that needs to be connected to
            SSLSocket sSocket = (SSLSocket)sslsocketfactory.createSocket("localhost", 443);

            //Used to get user data that comes in or if data is sent out
            DataInputStream data_in = new DataInputStream(sSocket.getInputStream());
            DataOutputStream data_out = new DataOutputStream(sSocket.getOutputStream());

            //Reads if something is sent from server
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));
            //Declare variables for messages incoming and outgoing
            String message_in = "", message_out = "";

            while(!message_in.equals("/1/1/")){ //If the client types this, it ends the communication with server
                //Following lines are just basic read in and out to output.
                message_out = buffRead.readLine();
                data_out.writeUTF(message_out);
                message_in = data_in.readUTF();
                System.out.println("Server: " + message_in);
            }
        }catch(Exception e){
        }
    }
}