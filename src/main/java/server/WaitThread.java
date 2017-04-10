package server;

import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;

public class WaitThread implements Runnable {

    public WaitThread() {
    }

    @Override
    public void run() {
        try {
            waitForConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Waiting for connection from devices
     */
    private void waitForConnection() throws IOException {
        //Create a UUID for SPP
        UUID uuid = new UUID("1101", true);
        //Create the service url
        String connectionString = "btspp://localhost:" + uuid + ";name=Sample SPP Server";
        //open server url
        StreamConnectionNotifier streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);
        //Wait for client connection
        System.out.println("\nServer Started. Waiting for clients to connect...");
        StreamConnection connection = streamConnNotifier.acceptAndOpen();

        RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
        System.out.println("Remote device address: " + dev.getBluetoothAddress());
        System.out.println("Remote device name: " + dev.getFriendlyName(true));
        
        Thread processThread = new Thread(new ProcessConnectionThread(connection));
        processThread.start();
    }
}