package server;

import javax.microedition.io.StreamConnection;
import java.io.InputStream;


public class ProcessConnectionThread implements Runnable {

    private StreamConnection connection;

    // Constant that indicate command from devices
    private static final int EXIT_CMD = -1;
    private static final int KEY_RIGHT = 1;
    private static final int KEY_LEFT = 2;

    public ProcessConnectionThread(StreamConnection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            // prepare to receive data
            InputStream inputStream = connection.openInputStream();
            System.out.println("waiting for input");

            while (true) {
                int command = inputStream.read();

                if (command == EXIT_CMD) {
                    System.out.println("finish process");
                    break;
                }
                processCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Process the command from client
     *
     * @param command the command code
     */
    private void processCommand(int command) {
        try {
            switch (command) {
                case KEY_RIGHT:
                    System.out.println("Right");
                    break;
                case KEY_LEFT:
                    System.out.println("Left");
                    break;
                case EXIT_CMD:
                    System.out.println("Exit");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}