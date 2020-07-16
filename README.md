There are two files (Client.java and Server.java) which will run on two seperate systems. 

First of all, run the Server.java file on the command prompt in one system, which represents the Server side code.
Type the follwing on the command prompt:
javac Server.java
java Server

Get the IP address for server by running the following lines in the Java code-
InetAddress ip = InetAddress.getLocalHost();
System.out.println(ip);

The value which will be printed should be written in Client.java file. 
Socket socket=new Socket("--here--",5000);

Place a text file named 'in.txt' in the same directory as the Client.java file as it is the input file to be read. 
Next, run the Client.java file on the second system. (The Server should already be running in the first system)
javac Client.java
java Client

The outputs for both sides will be displayed. (The file along with it's hash)

NOTE: The code also creates a received.txt file in the same directory. It is essentially same as the input file in.txt, but just for verification purposes that there is no change in the file while moving it across the network we output the same. 

