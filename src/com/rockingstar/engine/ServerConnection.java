package com.rockingstar.engine;

import com.rockingstar.engine.command.server.ResponseHandler;
import com.rockingstar.engine.io.models.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Bert de Boer on 3/27/2018.
 */
public class ServerConnection extends Thread {

    private static ServerConnection uniqueInstance;

    private Socket _socket;
    private ResponseHandler _handler;

    private ServerConnection() {
        try {
            //_socket = new Socket("145.33.225.170", 7789);
            //_socket = new Socket("127.0.0.1", 7789);
            _socket = new Socket("77.162.40.81", 7789);

            Util.displayStatus("Established server connection");
            _handler = new ResponseHandler();
        }
        catch(IOException e) {
            System.out.printf("Could not connect to server: %s\n", e.toString());
            Util.exit("Establishing server connection...");
        }
    }

    private boolean connected(){
        return !_socket.isClosed();
    }

    public void send(String command) {
        try {
            PrintWriter output = new PrintWriter(_socket.getOutputStream(), true);
            output.println(command);
            Util.displayStatus("Client Command: " + command);
            Thread.sleep(100);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void receive() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
        String response;
        while (connected()) {
            try {
                response = input.readLine();
            } catch (SocketException e){
                return;
            }
            if (response != null) {
                Util.displayStatus("Server Response: " + response);
                _handler.handle(response);
            }
        }
    }

    @Override
    public void run() {
        try {
            this.receive();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            _socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            Util.displayStatus("Disconnecting from server");
        }
    }

    public static ServerConnection getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new ServerConnection();
        }

        return uniqueInstance;
    }

    public String getResponse() {
        return _handler.getMessage();
    }

    public boolean isValidCommand() {
        return _handler.isValidCommand();
    }
}
