import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainApp
{
    public static void main(String[] args)
    {
        serializableVersion();
    }

    public static void serializableVersion()
    {
        try(ServerSocket serverSocket = new ServerSocket(8085))
        {
            System.out.println("Server listening");
            try(Socket socket = serverSocket.accept();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream()))
            {
                TempFileMessage tmp = (TempFileMessage) in.readObject();
                try(OutputStream out = new BufferedOutputStream(new FileOutputStream("server/" + tmp.getFileName())))
                {
                    byte[] bytes = tmp.getBytes();
                    for (byte bt : bytes)
                    {
                        out.write((char) bt);
                    }
                }
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}