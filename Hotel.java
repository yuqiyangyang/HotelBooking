//name: Yuqi Yang
//student# 260365378

import java.util.NoSuchElementException;
import java.util.Arrays;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.*;

public class Hotel{
  private String name;//name of hotel
  private Room[] rooms;
  private Reservation[] reservations;
  
//constructor that takes as input the name of the hotel, and an array of rooms.
  public Hotel(String hotelName, Room[] rooms){
    this.name = hotelName;
    this.rooms = new Room[rooms.length];
    for(int i=0; i< rooms.length; i++){//copy room reference from input array to hotel array
      this.rooms[i] = rooms[i];
    }
  }
  
  //takes a Reservation as input and adds it to the array of Reservation of the hotel.
  private void addReservation(Reservation r){//reservation include roomReserved and cutomerName
    Reservation [] addReservation;
    if (this.reservations == null){//since this.reservation is not initiated then it default null, imp to check null
      addReservation = new Reservation[1];
    }else{  
      addReservation = new Reservation[this.reservations.length+1];//create new R array with new size
      for(int i=0; i< addReservation.length-1; i++){
        addReservation[i]= this.reservations[i];
      }
    }
    addReservation[addReservation.length-1] = r;//assignm input r to new array
    this.reservations = addReservation;//update this.reservations by pointing to addR
  }
  //Note that the array of Reservations of the hotel should never be null elements.
  //The number of elements of such array matches exactly the number of Rooms reserved in the hotel.  

  private void removeReservation(String customerName, String type){
    
    int indexRemoveRoom = -1;//create a variable to find the index of the room need to be removed
    for(int i=0; i< this.reservations.length; i++){//check the customer with room type in array of reservation
      //find the reserved room which should be removed
      if(this.reservations[i].getName().equals(customerName) && this.reservations[i].getRoom().getType().equals(type)){
        this.reservations[i].getRoom().changeAvailability();//modifiy availability of the room 
        indexRemoveRoom = i;
        break;//stop once find the first one to remove
      }
    }
    if (indexRemoveRoom == -1){
      throw new NoSuchElementException();
    }
    //now need to update the array of reservation after removal of room and by creating new array with right length
    Reservation[] updateReservations = new Reservation[this.reservations.length-1];
    for(int j=0; j < updateReservations.length; j++){
      if(j < indexRemoveRoom){//take the same element in original reservations except the indexRemoveRoom
        updateReservations[j] = this.reservations[j];
      }else{
        updateReservations[j] = this.reservations[j+1];
      }
    }
    this.reservations = updateReservations;//update the reservation array
  }
   
  //input name and type of room to be reserved 
  public int createReservation(String customerName, String type){
    //FindAvailable method returns the first available type of room in Hotel room array
    Room roomAvailable = Room.findAvailableRoom(this.rooms, type);//call fAR which returns a room
    if (roomAvailable == null){//null means room is not available or not such type
      return -1;
    } else {//if find an avaialble room create a reservation with that roomtype and input customer name 
      Reservation newReservation = new Reservation(roomAvailable, customerName); 
      addReservation(newReservation);//call add method to add reservation and update the length of reservation
      roomAvailable.changeAvailability();//change the availablity of that room to not available
    } 
    return this.reservations.length; 
  }
  
  public void cancelReservation(String customerName, String type){
    
    try{//use catch/try block in case customerName is wrong or type is wrong
      removeReservation(customerName, type);
      System.out.println("Your reservation for a " + type + " room has been successfully cancelled!");  
    }
    catch(NoSuchElementException e){
      System.out.println("Sorry, there is no reservation for a "+ type +" room under the name of " + customerName );
    }
  }
  
  public void printInvoice(String customerName){
    double amountOwe = 0;
    //use for loop to find go through the reservations array and find all the reservations made under this customer 
    for(int i=0; i < this.reservations.length; i++){ 
      if (this.reservations[i].getName().equals(customerName)){
        amountOwe = amountOwe + this.reservations[i].getRoom().getPrice();//call getPrice method
      }
    }
    if (amountOwe == 0){
      System.out.println("No reservations have been made at this name.");
    }
    System.out.println( customerName +"'s total booking price is: $" + amountOwe);
  }                        
  
  //create a private method to return the num of avaialbe room under given type
  private int numAvailableRooms(String type){
    int numAvailableRooms = 0;
    for(int i=0; i< this.rooms.length; i++){//use Hotel attribute room[] to find different type of rooms n availability
      if(rooms[i].getAvailability() && rooms[i].getType().equals(type))//getAvailabiity should be "isAvaiable"
        numAvailableRooms++;
    } 
    return numAvailableRooms;
  }
  
  public String toString() {
    String hotelName= this.name;
    String numDouble = "double " + numAvailableRooms("double");//call numAvailableRooms methods
    String numQueen = "queen " + numAvailableRooms("queen");
    String numKing = "king " + numAvailableRooms("king");
    return "Here is the hotel info: \nHotel name: " + hotelName + "\nAvailable rooms:  "+  numDouble + ", " + numQueen + ", " + numKing;
  }
  
  public void loadReservations(String fileName) throws FileNotFoundException, IOException{//throw error prevent exception
   
    FileReader file = new FileReader(fileName);
    BufferedReader reader = new BufferedReader(file);
    String currentLine = reader.readLine();//read first line of the file
    while(currentLine != null){//make sure the file is not null
      String[] toSplit = currentLine.split("\t");//seperate the file by two elements whenever a "\t"
      createReservation(toSplit[0], toSplit[1]);//call createReservation method add/update reservation array 
      currentLine =  reader.readLine();//keep reading next lines, its like count++;
    }
    reader.close();
    file.close();  
  }
  
  public void saveReservations(String fileName) throws IOException{
   
    FileWriter file = new FileWriter(fileName);
    BufferedWriter writer = new BufferedWriter(file);
    //method goes throw array of reservation
    for(int i=0; i < this.reservations.length; i++){//go through the list of array& convert the message to string
      String message = this.reservations[i].getName() + "\t" + this.reservations[i].getRoom().getType()+ "\n";  
      writer.write(message);
      //System.out.println(message);
    }
    writer.close();
    file.close();
  }
  
}


