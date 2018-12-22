import java.math.BigInteger;
import java.util.Random;

public class  SRP6Client extends SRP6{
	private static int x;
	private static int v;
	private static int A;
	private static int a;
	private static int B;
	private static int U;
	private static int salt;
	private static long password; 
	private static int Key;
	private static int M;
	private static int R;
	private static String username;
	
	SRP6Client (int _N, int _g, int _k){
		N = _N;
		g = _g;
		k = _k;
	}
	
	public void clientReg() {
		calc_salt();
		calc_x();
		calc_v();
	}
	
	public void clientAuth() {
		calc_A();
	}
	

	private void calc_v() {
		v = (int)(Math.pow(g, x )% N);
	}

	private void calc_x() {
		x = calcHash(salt,password);
	}
	
	private void calc_A() {
		Random rnd = new Random();
		a = rnd.nextInt(10);
		A = (int)(Math.pow(g, a) % N);
	}
	
	public void calc_U() {
    	U = calcHash(A,B);
	}
	
	public void calc_Key() {
		int S;
		BigInteger _S,_B,_k,_g,_N;
		_B = BigInteger.valueOf(B);
		_k = BigInteger.valueOf(k);
		_g = BigInteger.valueOf(g);
		_N = BigInteger.valueOf(N);
		_S = (_B.subtract((_k.multiply(_g.pow(x)))).pow(a+U*x)).mod(_N);
		S = Integer.parseInt(_S.toString());
		Key = calcHash(S);
	}
	
	public void calc_M() {
		M = calcHash(calcHash(N)^calcHash(g),calcHash(username),salt,A,B,Key);
	}
	
	public void calc_R() {
		R  = calcHash(A,M,Key);
	}
	
	
	private void calc_salt() {
		salt = 0;
		int length = 7;
		Random rnd = new Random();
		salt+= (rnd.nextInt(9) + 1) * Math.pow(10, length) ;
		length--;
		while(length!=0) {
			salt+= rnd.nextInt(10) * Math.pow(10, length) ;
			length--;
		}
	}
	

	
		//setters	
		public void set_A(int _A) {
			A = _A;
		}
		
		public void set_B(int _B) {
			B = _B;
		}
		
		public void set_password(int _password) {
			password = _password;
		}
		
		public void set_username(String _username) {
			username = _username;
		}
		
		//getters
		public int get_salt() {
			return salt;
		}
		
		public int get_v() {
			return v;
		}
		
		public int get_A() {
			return A;
		}
		
		public int get_B() {
			return B;
		}
		
		public int get_U() {
			return U;
		}
		
		public int get_Key() {
			return Key;
		}
		
		public int get_M() {
			return M;
		}
		
		public int get_R() {
			return R;
		}
		
}
