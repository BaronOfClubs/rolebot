package net.baronofclubs.Rolebot.Utility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceManager {

    public static void save(Serializable data, String filename) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)));
            outputStream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object load(String filename) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(filename)));
            return inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

}
