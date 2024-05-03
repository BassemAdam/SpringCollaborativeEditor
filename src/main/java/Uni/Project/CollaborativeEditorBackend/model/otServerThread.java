package Uni.Project.CollaborativeEditorBackend.model;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//todo: THIS IS A WORK IN PROGRESS
@Setter
@Getter
public class otServerThread extends Thread {
    private final Socket socket;
    private final int tid;
    private final BufferedReader in;
    private final PrintWriter out;
    private boolean fail;

    public otServerThread(Socket socket, int tid) {
        this.socket = socket;
        this.tid = tid;
        this.fail = false;
        System.out.println("Thread#" + tid + " socket port:" + socket.getPort() + " socket address:" + socket.getInetAddress() + "addr2:" + socket.getLocalAddress());

        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Thread #" + tid +" failed to create reader");
            throw new RuntimeException(e);
        }
        // auto flush is true to flush after write
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Thread #" + tid +" failed to create writer");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        String socketInput;
        try {
            while (true) {
                // todo: READ SOCKET FOR NEW DATA
                socketInput = readFromSocket();
                if(fail)
                {
                    System.out.println("Thread #" + tid + " socket fail in read");
                    break;
                }
                if (socketInput != null) {
                    // for now just print whatever
                    System.out.println("Thread #" + tid + " sent: " + socketInput);
                    writeToSocket("Thread #" + tid + " sent: " + socketInput);
                    if(fail)
                    {
                        System.out.println("Thread #" + tid + " socket fail in write");
                        break;
                    }
                }
                // todo: WRITE TO SOCKET IF NEEDED

                // todo: APPLY OT AND WRITE CHANGES TO FILE
            }
        } catch (Exception e) {
            System.out.println("Thread #" + tid +" failed");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // todo: check if synchronized is really needed
    private synchronized void writeToSocket(String input) throws IOException {
        if (socket != null && !socket.isClosed() && socket.isConnected()) {
            out.println(input);
        } else {
            fail = true;
        }
    }

    private synchronized String readFromSocket() throws IOException {
        if (socket != null && !socket.isClosed() && socket.isConnected()) {
            return in.readLine();
        } else {
            fail = true;
            return "Thread Read FAILED FAILED";
        }
    }


    // todo: Decide if this is to be used or the other method
    /*// todo: check if synchronized is really needed
    private synchronized void writeToSocket(String input) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(input.getBytes());
    }

    // todo: check if synchronized is really needed
    private synchronized String readFromSocket() throws IOException {
        // Get the input stream of the socket
        InputStream inputStream = socket.getInputStream();
        // Read the response from the server
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        return new String(buffer, 0, bytesRead);
    }*/

}
