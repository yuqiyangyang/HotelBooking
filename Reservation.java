

public class Reservation{//create class Reservation
  private String name;//customer name
  private Room roomReserved;
  
  //takes as input a Room and the name under which the reservation is made.
  public Reservation(Room roomReserved, String customerName){   
    this.roomReserved = roomReserved;//initialize attributes
    this.name = customerName;   
  }
  
  public String getName(){//return name under reservation made
    return this.name;
  }
  
  public Room getRoom(){//return room which has been reserved
    return this.roomReserved;
  }
  
  public String toString(){//method to text
   return this.name + "\t" + this.roomReserved.getType();
  }//create a string method to test.
                        
}

    