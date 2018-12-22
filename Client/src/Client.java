import java.net.*;
import java.util.Random;
import java.io.*;

public class Client {
	
	public static String username;
	
    public static void main(String[] arr) {
        int serverPort = 4567; 
        String password;
        int input;
        String address = "127.0.0.1", response;  
        SRP6Client srp= new SRP6Client(11, 3, 3 );                              

        try {
            InetAddress ipAddress = InetAddress.getByName(address); 
            Socket socket = new Socket(ipAddress, serverPort); 
            
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Введите логин: ");
            username = keyboard.readLine();
            srp.set_username(username);
            System.out.println();
            System.out.print("Введите пароль: ");
            password = keyboard.readLine();
            srp.set_password(Integer.parseInt(password));
            srp.clientReg();
            out.writeUTF(username); // отсылаем  серверу логин, соль и v.
            out.writeInt(srp.get_salt()); 
            out.writeInt(srp.get_v()); 
            response = in.readUTF();
            if (response.equals("Something went wrong"))return;
            srp.clientAuth();
            response = in.readUTF();
            out.writeUTF(username); // отсылаем  серверу логин и A.
            out.writeInt(srp.get_A());
            response = in.readUTF();
            if (response.equals("Something went wrong"))return;
            input = in.readInt();
            srp.set_B(input);
            if(srp.get_B() == 0) {
				System.out.println("Wrong B was recieved!");
				out.writeUTF("Something went wrong");
				out.flush();
				return;
			}
            out.writeUTF("Fine");
            srp.calc_U();
            if(srp.get_U()==0) {
            	System.out.println("Something went wrong");
            	return;
            }
            srp.calc_Key();
            srp.calc_M();
            out.writeInt(srp.get_M());
            input = in.readInt();
            srp.calc_R();
            if(srp.get_R()!=input) {
            	out.writeUTF("Keys aren't equal");
            	System.out.println("Авторизация не успешна");
            	return;
            }
            out.writeUTF("Keys are equal");
            System.out.println("Вы успешно авторизовались!");
            out.flush();
                
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
    
    
	   
}