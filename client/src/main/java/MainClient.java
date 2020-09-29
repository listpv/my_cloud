import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Paths;

public class MainClient
{
    public static void main(String[] args)
    {
        serializibleVersion();
    }

    public static void serializibleVersion()
    {
        try(Socket socket = new Socket("localhost", 8085))
        {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            TempFileMessage tmp = new TempFileMessage(Paths.get("client/example.txt"));
            out.writeObject(tmp);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void binaryVersion()
    {
        try(Socket socket = new Socket("localhost", 8085))
        {
            TempFileMessage tmp = new TempFileMessage(Paths.get("client/example.txt"));
            int temp = tmp.getFileName().length();
            byte[] bts = new byte[5 + temp + (int) tmp.getSize()];
            bts[0] = 15;
            for(int i = 4; i >= 1; i--)
            {
                bts[4 - i] = (byte)(temp >> i*4 );
            }
            char[] chars = tmp.getFileName().toCharArray();
            for(int i = 5; i < 5 + temp; i++)
            {
                bts[i] = (byte) chars[i - 5];
            }
//            for(int i = 5; i < 5 + tmp.getFileName().length(); i++)
//            {
//                System.out.print((char) bts[i]);
//            }
//            System.out.println();
            System.arraycopy(tmp.getBytes(),0, bts, 5 + tmp.getFileName().length(), (int) tmp.getSize());
//            for (int i = 5 + tmp.getFileName().length(); i < 5 + tmp.getFileName().length() + tmp.getSize(); i++)
//            {
//                System.out.print((char) bts[i]);
//            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

//    CloudPackage cp = new CloudPackage("Hello server");
//            out.writeObject(cp);
