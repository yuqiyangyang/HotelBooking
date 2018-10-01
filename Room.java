
import java.util.Arrays;

public class Room{//class Room has 3 attributes
  private String type;
  private double price;
  private boolean availability;
  
  public Room(String type){//build a constructor
    
    if(!type.equals("double")&&!type.equals("queen")&&!type.equals("king"))
      throw new IllegalArgumentException("Sorry this type of room is not available");
    
    this.type = type;//type of room and use type to initialize other attributes
    
    if(this.type.equals("double")){//intialize price by room type
      this.price = 90;
    }else if(this.type.equals("queen")){
      this.price = 110;
    }else{
      this.price = 150;
    }
    this.availability = true;//set default room availability as true
  }
  
  public String getType(){//return type of the room
    return this.type;
  }
  
  public double getPrice(){//return price of the room
    return this.price;
  }
    
  public boolean getAvailability(){//change state of availability
    return this.availability;
  }
  
  public void changeAvailability(){//set the status of room availability to opposite 
    this.availability = !this.availability;
    
  }
  //input an array of type Room and type 
  public static Room findAvailableRoom (Room[] rooms, String typeRoom){//method should be static
    //use for loop to search for the room match the input room type
    for(int i=0; i < rooms.length; i++){
      if((rooms[i].type).equals(typeRoom) && (rooms[i].availability)){
        return rooms[i];
      }//should return the available room in the array of the indicated type
    }
    return null; //either room is not available or no such type exist
  }
}
    