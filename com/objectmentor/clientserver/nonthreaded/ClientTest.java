package com.objectmentor.clientserver.nonthreaded;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import common.MessageUtils;

public class ClientTest {
    public static void main(String[] args) {
        int serverPort = 8080; // Đổi cổng theo nhu cầu của bạn

        // Khởi tạo và chạy server
        try {
            Server server = new Server(serverPort, 10000); // Đổi timeout theo nhu cầu của bạn
            Thread serverThread = new Thread(server);
            serverThread.start();

            // Đợi cho server khởi động trước khi tiếp tục
            Thread.sleep(1000);

            // Gửi và nhận thông điệp thông qua MessageUtils
            sendAndReceiveMessage();

            // Dừng server
            server.stopProcessing();
            serverThread.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sendAndReceiveMessage() {
        try {
            // Tạo một Socket để kết nối đến server (với cổng serverPort)
            Socket socket = new Socket("localhost", 8080); // localhost và 8080 là ví dụ, thay đổi theo nhu cầu của bạn

            // Nhập thông điệp từ người dùng
            System.out.print("Enter a message to send to the server: ");
            Scanner scanner = new Scanner(System.in);
            String messageToSend = scanner.nextLine();
            scanner.close();
            // Gửi thông điệp tới server bằng MessageUtils
            MessageUtils.sendMessage(socket, messageToSend);

            // Nhận và in ra thông điệp từ server bằng MessageUtils
            String receivedMessage = MessageUtils.getMessage(socket);
            System.out.println("Received from server: " + receivedMessage);

            // Đóng kết nối socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
