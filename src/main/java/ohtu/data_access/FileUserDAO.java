package ohtu.data_access;

import ohtu.domain.User;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arto on 9.4.2016.
 */

public class FileUserDAO implements UserDao {
    private ArrayList<User> users;
    private String filename;


    public FileUserDAO(String filename){
        this.filename = filename;
        this.users = new ArrayList<User>();

        readUsersFromFile();
    }
    private void createFile(){
        try{
            File f = new File(this.filename);
            f.createNewFile();
        }catch (IOException io){
            System.out.println("IoException");
        }


    }
    private void readUsersFromFile(){
        try {
            FileInputStream fileStream = new FileInputStream(this.filename);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            this.users =  (ArrayList<User>) objectStream.readObject();
            objectStream.close();
            fileStream.close();
        } catch (IOException io){
            createFile();
        } catch (ClassNotFoundException c){
            System.out.println("Class not found");
        }
    }
    private void writeUsersToFile(){
        try {
            FileOutputStream fileStream = new FileOutputStream(this.filename);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

            objectStream.writeObject(this.users);


            objectStream.close();
            fileStream.close();
        } catch(FileNotFoundException file) {
            System.out.printf("File not found");
        }catch(IOException io) {
            System.out.println("IO Error");
        }

    }
    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void add(User user) {
        this.users.add(user);
        this.writeUsersToFile();
    }
}
