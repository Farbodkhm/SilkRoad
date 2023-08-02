package Server;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter output= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Connected");
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                System.out.println("Enter message: ");
                String text = scanner.nextLine();
                output.write(text);
                output.newLine();
                output.flush();
                String textBack = input.readLine();
                System.out.println(textBack);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
