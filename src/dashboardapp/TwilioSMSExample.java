/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dashboardapp;


import Project.ConnectionProvider;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TwilioSMSExample {
    
    Connection con;
    ResultSet rs;
    PreparedStatement pst,pst2;
    public static final String ACCOUNT_SID = "AC1be63eec0c69ae94fdd5d2fbbaccd726";
    public static final String AUTH_TOKEN = "90efff39409e0481325f34db641b9ddf";
    
    public TwilioSMSExample()
    {
        try {
            //Database

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","Pawaskar@2023");
            System.out.println(con);
            pst = con.prepareStatement("Select * from Product");
            rs=pst.executeQuery();
        
          
            
            // Initialize Twilio client
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            // Your Twilio phone number
            String twilioPhoneNumber = "+12054330948";
            // Define an array of recipient phone numbers
            String[] recipientPhoneNumbers = {
                "+919321543686","+918369147159","+919175205766","+918369097541"// Replace with the actual recipient phone numbers
                    
                    // You can add more phone numbers to this array
            };
            
            String messageBody = "Hi";
            
              try {
                Connection connection = ConnectionProvider.getCon();
                PreparedStatement pst = connection.prepareStatement("Select p_id,Quantity,low_stock,P_name from Product");
                ResultSet rs = pst.executeQuery();
                
                while(rs.next())
                {
                    int Quantity = rs.getInt("quantity");
                    int low_stock = rs.getInt("low_stock");
                    int id = rs.getInt("p_id");
                    String name = rs.getString("P_name");
                    if(Quantity<=low_stock)
                    {
                        messageBody = "Hello Admin!\n Your Store has run out of stock for an item:" + " "+name+" " + "with Product Id:"+id + "\nPlease contact the seller and get it restocked.\nRegards Apna Kirana";
                        System.out.println(messageBody);
                        for (String recipientPhoneNumber : recipientPhoneNumbers) {
                Message message = Message.creator(
                        new PhoneNumber(recipientPhoneNumber),
                        new PhoneNumber(twilioPhoneNumber),
                        messageBody)
                        .create();
                
                System.out.println("Message SID for " + recipientPhoneNumber + ": " + message.getSid());
            }
                    }
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Message content
            //String messageBody = """
                                        // Hello Admin!
                                        // Your store has run out of stock of an item Condoms with ProductId:34 please contact the seller and get it restocked.
                                        //                                                     Regards,\u00a0
                                       //  APNA\u00a0KIRANA""";
            // Sending an SMS to multiple recipients
            
           
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(TwilioSMSExample.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
            Logger.getLogger(TwilioSMSExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 
    

    public static void main(String[] args) {
     
   TwilioSMSExample t = new  TwilioSMSExample();
}

}
        