import com.sun.net.ssl.internal.ssl.Provider;
//Import libraries for the server to generate sockets
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.security.Security;

public class server {
    public static void main(String[] args){
        Security.addProvider(new Provider());
        //Issues occurred when locating a path. Put the keystore and the truststore files into main Java directory
        System.setProperty("javax.net.ssl.keyStore", "../glassfish-5.0.1/glassfish5/glassfish/domains/domain1/config/keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "hello12"); //Keystore password
        //The statement below is not necessary but just describes what is happening with the actual encryption
        System.setProperty("javax.net.debug", "all");

        try{
            //Following lines of code create a SSL server socket allowing to configure the ports
            SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
            SSLServerSocket sslServerSocket = (SSLServerSocket)sslServerSocketfactory.createServerSocket(443);
            SSLSocket s1 = (SSLSocket)sslServerSocket.accept();

            //Incoming and out going data stream declarations
            DataInputStream data_in = new DataInputStream(s1.getInputStream());
            DataOutputStream data_out = new DataOutputStream(s1.getOutputStream());

            BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));

            //Declaring incoming and outgoing messages
            String message_in = "", message_out = "";

            while(!message_in.equals("/1/1/")){ //Close if that is typed
                message_in = data_in.readUTF();
                System.out.println("Client: " + message_in);
                message_out = buffRead.readLine();
                data_out.writeUTF(message_out);
                data_out.flush();
            }
            s1.close();
        }catch(Exception e){
        }
    }
}
