
	import java.net.*;
	import java.io.*;
	import java.util.Random;
	import java.lang.Math;
	public class Server {
		private static String username;
		
		public static void main(String[] arr)    {
			String response;
			int port = 4567;
			long input; 
			SRP6Server srp = new SRP6Server(11, 3, 3 );
			try {
				int _input;
				ServerSocket ss = new ServerSocket(port); 
				System.out.println("Waiting for a client...");
				Socket socket = ss.accept(); 
				System.out.println("Client is registering...");
				System.out.println();
				InputStream sin = socket.getInputStream();
				OutputStream sout = socket.getOutputStream();
				DataInputStream in = new DataInputStream(sin);
				DataOutputStream out = new DataOutputStream(sout);

				
				username = in.readUTF();
				srp.set_username(username);
				System.out.println("Username is : " + username);
				input = in.readInt();
				srp.set_salt((int)input);
				input = in.readInt();
				srp.set_v((int)input);
				if(srp.get_v()==0) {
					System.out.println("Wrong v was recieved!");
					out.writeUTF("Something went wrong");
					out.flush();
					return;
				}
				out.writeUTF("Fine");
				System.out.println(">>Client has been successfully registered");
				System.out.println(">>Client is authorizing");
				out.writeUTF("Вы были успешно зарегистрированы!");
				username = in.readUTF();
				input = in.readInt();
				srp.set_A((int)input);
				if(srp.get_A() == 0) {
					System.out.println("Wrong A was recieved!");
					out.writeUTF("Something went wrong");
					out.flush();
					return;
				}
				out.writeUTF("Fine");
				srp.calc_B();
				out.writeInt(srp.get_B());
				response = in.readUTF();
				if(response.equals("Something went wrong"))return;
				srp.calc_U();
				if(srp.get_U()==0) {
					System.out.println("Something went wrong");
					return;
		        }
				srp.calc_Key();
				_input = in.readInt();
				srp.calc_M();
				if((int)_input != srp.get_M())return;
				srp.calc_R();
				out.writeInt(srp.get_R());
				response = in.readUTF();
				if(!response.equals("Keys are equal")) {
					System.out.println("Connection reset");
					return;
				}
				System.out.println(">>Client has been successfully authorized");
				out.flush();
				
			}
			catch(Exception x) { x.printStackTrace(); }
		}
	   
		 
	}

