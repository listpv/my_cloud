import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class NioChatClientMain implements Runnable {
    private SocketChannel socketChannel;
    private Selector selector;
    private ByteBuffer buf = ByteBuffer.allocate(256);

    public NioChatClientMain() throws IOException {
        this.socketChannel = SocketChannel.open();
        this.socketChannel.connect(new InetSocketAddress("localhost", 8189));
        this.socketChannel.configureBlocking(false);
        this.selector = Selector.open();
    }

    @Override
    public void run() {

        try
        {
            System.out.println("client on");
            while (this.socketChannel.isOpen())
            {
                Scanner sc = new Scanner(System.in);
                String str = sc.nextLine();
                handleWrite(str);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }
        public void handleWrite(String str) throws IOException
    {
        ByteBuffer msgBuf = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(msgBuf);
    }

    public static void main(String[] args) throws IOException {
        new Thread(new NioChatClientMain()).start();
    }
}