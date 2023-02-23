package org.example;

import org.example.Person;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        Address address = new Address("123 Main St", "Anytown", "Zagreb", 12345);
        Person person = new Person("Alaa Essam", 30, address);

        try {
            FileOutputStream fileOut = new FileOutputStream("person.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(person);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in person.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

        try {
            FileInputStream fileIn = new FileInputStream("person.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            person = (Person) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Deserialized data:");
            System.out.println("Name: " + person.getName());
            System.out.println("Age: " + person.getAge());
            System.out.println("Address: " + person.getAddress().getStreet() + ", " +
                    person.getAddress().getCity() + ", " + person.getAddress().getState() + " " +
                    person.getAddress().getZipCode());
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Person class not found");
            c.printStackTrace();
            return;
        }
    }

}