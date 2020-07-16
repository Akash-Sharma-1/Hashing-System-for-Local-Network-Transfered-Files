import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server
{
    public static byte[] getHash(String input) throws NoSuchAlgorithmException
    {
        MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
        return messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String convertToHex(byte[] hash)
    {
        BigInteger bigInteger=new BigInteger(1,hash);
        StringBuilder hex=new StringBuilder(bigInteger.toString(16));
        while(hex.length()<32)
        {
            hex.insert(0,'0');
        }
        return hex.toString();
    }
    public static void main(String[] args) throws Exception
    {
        // TO KNOW WHAT'S THE IP ADDRESS
        InetAddress ip=InetAddress.getLocalHost();
        System.out.println("IP ADDRESS of the Server");
        System.out.println(ip);
        ServerSocket serverSocket=new ServerSocket(5000);
        System.out.println("Waiting for client...");
        Socket socket=serverSocket.accept();
        System.out.println("Client connected");
        System.out.println();

        //read file from client
        InputStream inputStream=socket.getInputStream();
        DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
        int fileSize=Integer.parseInt(dataInputStream.readUTF()); //read file size
        byte[] bytes=new byte[fileSize];
        inputStream.read(bytes,0,bytes.length); //read file
        int bit=Integer.parseInt(dataInputStream.readUTF());
        System.out.println("File received from client");
        System.out.println("Received bit from client: "+bit);
        System.out.println();

        String s="";
        for(int i=0;i<bytes.length;i++)
        {
            s+=(char)bytes[i];
        }

        //calculate hash
        String fileHash=convertToHex(getHash(s));
        System.out.println("File hashed");

        //send file and hash to client
        OutputStream outputStream=socket.getOutputStream();
        outputStream.write(bytes,0,bytes.length); //send file to client
        DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("File Hash: "+fileHash); //send hash to client
        dataOutputStream.writeUTF(bit+"");
        bit=1-bit;
        System.out.println("File sent to client");
        System.out.println("Hash code sent to client");
        System.out.println("Current bit value: "+bit);

        inputStream.close();
        dataInputStream.close();
        outputStream.close();
        dataOutputStream.close();
        System.out.println("Closing connection");
        socket.close();
        serverSocket.close();
    }
}
