import java.io.*;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
public class Client
{
    public static void main(String[] args) throws Exception
    {
        int bit=0;
        //Establish connection with server
        // We need to put the server's IP ADDRESS HERE TO RUN THE CODE
        Socket socket=new Socket("192.168.29.44",5000);
        System.out.println("Connected");

        //read file and send it to server
        System.out.println("Current bit value: "+bit);
        System.out.println();
        FileInputStream fileInputStream=new FileInputStream("in.txt");
        byte[] bytes=new byte[(int) fileInputStream.getChannel().size()];
        fileInputStream.read(bytes,0,bytes.length); //read file from memory
        DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(bytes.length+""); //send file size to server
        OutputStream outputStream=socket.getOutputStream();
        outputStream.write(bytes,0,bytes.length); //send file to server
        dataOutputStream.writeUTF(0+"");
        System.out.println("File sent to server");
        bit=1-bit;
        System.out.println("Current bit value: "+bit);
        System.out.println();

        //read file and hash code from server
        InputStream inputStream=socket.getInputStream();
        File file=new File("received.txt");
        FileOutputStream fileOutputStream=new FileOutputStream(file);
        inputStream.read(bytes,0,bytes.length); //read file from server
        fileOutputStream.write(bytes,0,bytes.length); //write in a new file
        DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
        String line=dataInputStream.readUTF(); //read hash code from server
        bit=Integer.parseInt(dataInputStream.readUTF());
        System.out.println("File received from server");
        System.out.println("Hash code received from server");
        System.out.println("Bit received from server: "+bit);
        System.out.println(line);

        inputStream.close();
        outputStream.close();
        dataInputStream.close();
        dataOutputStream.close();
        fileInputStream.close();
        fileOutputStream.close();
        socket.close();
        System.out.println("Disconnected");
    }
}
