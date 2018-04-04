/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author Wassim
 */
public class SMSService {
    public static final String ACCOUNT_SID = "AC193511a5bddbfa4269f13212896fa132";
    public static final String AUTH_TOKEN = "6b992db85aa56702d3ed3e8f643206ed";

    public static void send(String to, String body) {
      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

      Message message = Message.creator(new PhoneNumber("+216" + to),
          new PhoneNumber("+15034465185"), 
          body).create();

      System.out.println(message.getSid());
    }
}
