package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Server {
    private List<ClientHandler> clients;

    private AuthService authService;

    private int PORT = 8189;
    ServerSocket server = null;
    Socket socket = null;
    private ClientHandler clientHandler;

    public Server() {
        clients = new Vector<>();
        authService = new SimpleAuthService();

        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");

                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void broadcastMsg(ClientHandler sender, String msg) {
        String message = String.format("%s : %s", sender.getNickname(), msg);
        for (ClientHandler c : clients) {
            c.sendMsg(message);
        }
    }

    public void privatMsg(ClientHandler sender, String recelver, String msg) {
        String message = String.format("[%s]  privat [%s]: %s", sender.getNickname(), recelver, msg);
        for (ClientHandler c : clients) {
            if (c.getNickname().equals(recelver)) {
                c.sendMsg(message);
                if (!c.equals(sender)) {

                }
                return;
            }
            sender.sendMsg("Пользователь " + recelver + "не найден");
        }

        public void subscribe (ClientHandler clientHandler){
            clients.add(clientHandler);
        }

        public void unsubscribe (ClientHandler clientHandler){

            clients.remove(clientHandler);
        }

    }
}
