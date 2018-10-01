//name: Yuqi Yang
//student# 260365378

import java.util.NoSuchElementException;
import java.util.Arrays;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.Scanner;


public class BookingSystem{
  
  private static Room[] loadRooms(String fileName){//need to change back to private
    Room[] rooms = new Room[0];//when IOException is throw, return array with no element
    
    try {
      FileReader file = new FileReader(fileName);
      BufferedReader reader = new BufferedReader(file);
      
      int[] loadRooms = new int [3];//file with only 3 integers representing 3 type of room
      String loadRoomsStr = reader.readLine();//read file line by line
      for(int i = 0; i < 3; i++){//copy the textfile element to an integer array loadRooms
        loadRooms[i] = Integer.parseInt(loadRoomsStr);//convert int to string
        loadRoomsStr = reader.readLine();
      }
      int totalRooms = loadRooms[0] + loadRooms[1] + loadRooms[2];//calculate total rooms in hotel
      //update room[]
      rooms = new Room[totalRooms];//create array of room
      for(int i = 0; i < loadRooms[0]; i++){
        rooms[i] = new Room("double");//keep creating newRooms by using type Room
      }
      for(int j = loadRooms[0]; j < loadRooms[0]+loadRooms[1]; j++){
        rooms[j] = new Room("queen");
      }
      for(int z = loadRooms[0]+loadRooms[1]; z < totalRooms; z++){
        rooms[z] = new Room("king");
      }
      reader.close();
      file.close();  
    }
    catch(FileNotFoundException e){
      System.out.println("Sorry this file does not exists");
    } 
    catch(IOException e){//problem with buffer reader
    }
    return rooms;
  }
  
  
  public static void main(String[] args){
    
    int option = 0;
    System.out.println("Welcome to the COMP 202 booking system!\nPlease enter the name of the hotel you'd like to book");
    Scanner s = new Scanner(System.in);//read from keyboard
    Room[] rooms = loadRooms("roomInfo.txt");//load rooms info
    String hotelName = s.nextLine();//read the line
    Hotel h = new Hotel (hotelName, rooms);
    try{
      h.loadReservations("reservationsInfo.txt");//load reservation after created hotel
    }
    catch(FileNotFoundException e){
    }
    catch(IOException e){
    }
    
    do {//use do while loop to go back to main manu
      System.out.println(); 
      System.out.println("*******************************************************************************************************"); 
      System.out.println("Welcome to "+ hotelName + "! Choose one of the following options:");//???
      System.out.println("1. Make a reservation \n2. Cancel a reservation \n3. See an invoice \n4. See hotel info \n5. Exit the booking system");  
      System.out.println("*******************************************************************************************************"); 
      System.out.println(); 
      option = s.nextInt();//task customer choose
      
      if (option==1){//make reservation
        System.out.println("Please enter your name: ");
        s.skip("\n");
        String customerName = s.nextLine();
        System.out.println("What type of room would you like to reserve?");
        String typeRoom = s.nextLine();
        String typeRoomToLower = typeRoom.toLowerCase();//change it to lower case
        Room roomType = Room.findAvailableRoom (rooms, typeRoomToLower);
        if (roomType == null){
          System.out.println("Sorry " + customerName + " we have no available rooms of the desired type.");
        }else{
          h.createReservation(customerName, typeRoomToLower);
          try{
            h.saveReservations("reservationsInfo.txt");
          }
          catch(IOException e){
          }
          System.out.print("You have successfully reserved a " + typeRoom + " room under the name of "+ customerName);
          System.out.println(". We look forward having you at " + hotelName + " !");
        }
      }
      else if (option == 2){//cancel reservation
        System.out.println("Please enter the name that you used to make the reservation: ");
        s.skip("\n");
        String customerName = s.nextLine();
        System.out.println("What type of room did you reserve?");
        String typeRoom = s.next().toLowerCase();
        //Room roomType = new Room(typeRoom);//make sure the type of room is correct
        h.cancelReservation(customerName, typeRoom);
        try{
          h.saveReservations("reservationsInfo.txt");
        }
        catch(IOException e){
        }  
      }
      else if (option == 3){//see an invoice
        System.out.println("Please enter your name: ");
        s.skip("\n");
        String customerName = s.nextLine();
        h.printInvoice(customerName);
      }
      else if (option==4){//see hotel info
        System.out.println(h.toString());
      }
    }
      while(option !=5);
      System.out.println();
      System.out.println(" It was a pleasure doing business with you! Have a nice day!");
  }
}

  
