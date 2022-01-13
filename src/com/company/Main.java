package com.company;

import com.company.objects.book;
import com.company.objects.fileModifiers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    public static File bookStore = new File("Books.txt");
    public static final File logInDetails = new File("LogIn.txt");
    public static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        while(true) {
            System.out.println("Login or Register:");
            String response = input.next();
            if (response.equalsIgnoreCase("login")) {
                Login();
                break;

            } else if (response.equalsIgnoreCase("register")) {
                Register();
                Login();
                break;
            } else {
                System.out.println("Invalid response");
            }
        }
        fileModifiers.CreateFile();
        MainMenu();
    }

    private static void Login(){
        try {
            A:while(true) {
                System.out.println("What is your login:");
                String username = input.next();
                String password = input.next();
                Scanner reader = new Scanner(logInDetails);
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    if ((data.contains(username)) && (data.contains(Integer.toString(password.hashCode())))) {
                        System.out.println("Login Successful");
                        break A;
                    }
                }
                System.out.println("Email/Password not found");
            }

        }catch(Exception e){
            System.out.println(e);
        }

    }

    private static void Register(){
        String email;
        String password;
        while (true) {
            System.out.println("Input your email address:");
            email = input.next();
            if (!((email.contains("@")) && (email.contains(".com")))) {
                System.out.println("Invalid email");
            } else {
                break;
            }
        }
        //Checks password for specific format
        A : while(true) {
            System.out.println("Input your password:");
            password = input.next();
            for (char i : password.toCharArray()) {
                if (Character.isUpperCase(i)) {
                    if ((password.length() >= 8)) {
                        break A;
                    }
                    System.out.println("Invalid Password Length");
                }
            }
            System.out.println("Invalid Password, needs to contain capital letters");
        }
        int hashPW = password.hashCode();
        //Writes to file
        try {
            FileWriter myWriter = new FileWriter(logInDetails.getName(), true);
            myWriter.write(email + " " + hashPW + "\n");
            myWriter.close();

        }catch(Exception e){
            System.out.println(e);
        }
    }

    private static void MainMenu() {
        while (true) {
            System.out.println("-Add a book (1)" +"\n" + "-Read File (2)" +"\n" + "-Delete file contents (3)" + "\n" + "-Delete file (4)" + "\n" + "-Search ISBN (5)");
            try {
                int menuChoice = input.nextInt();
                if (menuChoice == 1) {
                    book temp = addBookInfo();
                    fileModifiers.WriteToFile(temp.toString());
                }
                else if (menuChoice == 2){
                    fileModifiers.ReadFile();
                }
                else if (menuChoice == 3) {
                    fileModifiers.FileEmptier();
                }
                else if (menuChoice == 4) {
                    fileModifiers.DeleteFile();
                }
                else if (menuChoice == 5){
                    System.out.println("What is the ISBN you are searching for:");
                    String answer = input.next();
                    fileModifiers.SearchFile(answer);
                }
                else {
                    System.out.println("Invalid Answer");
                }
            }catch(Exception e){
                System.out.println(e);
                input.next(); //Clears scanner of error
            }
        }
    }

    private static book addBookInfo(){
        System.out.println("Type in your Book Title, ISBN, Author, Genre and Owner");
        String title = input.next();
        int isbn = input.nextInt();
        String author = input.next();
        String genre = input.next();
        String owner = input.next();
        return new book(title,isbn,author,genre,owner);
    }

    public static void SearchLocation(String bookName) {
        try {
            Scanner myReader = new Scanner(bookStore);
            boolean check = true;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.contains(bookName)) {
                    String[] result = data.split(", ");
                    System.out.println(result[4]);
                    check = false;
                }
            }
            myReader.close();
            if (check){
                System.out.println("ISBN not found");
            }
            System.out.println(" ");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
