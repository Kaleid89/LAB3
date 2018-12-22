import java.math.BigInteger;
import java.util.Random;

class SRP6Server extends SRP6{

	
	private static int A;
	private static int B;
	private static int U;
	private static int salt;
	private static int v;
	private static int b;
	private static int Key;
	private static int M;
	private static int R;
	private static String username;
	
	SRP6Server(int _N, int _g, int _k){
		N = _N;
		g = _g;
		k = _k;
	}
	
	public void calc_B() {
		Random rnd = new Random();
		b = rnd.nextInt(10);
		B =(int) (k*v + (Math.pow(g, b) % N)) % N;
	}
	
	public void calc_U() {
    	U = (int) calcHash(A,B);
	}

	public void calc_Key() {
		int S;
		BigInteger _S,_A,_v,_N;
		_A = BigInteger.valueOf(A);
		_v = BigInteger.valueOf(v);
		_N = BigInteger.valueOf(N);
		_S =   ((_A.multiply(_v.pow(U))).pow(b)).mod(_N);
		S = Integer.parseInt(_S.toString());
		Key = calcHash(S);
	}
	
	public void calc_M() {
		M = calcHash(calcHash(N)^calcHash(g),calcHash(username),salt,A,B,Key);
	}
	
	public void calc_R() {
		R  = calcHash(A,M,Key);
	}
		
	
	
	//setters
	public void set_salt(int _salt) {
		salt = _salt;
	}
	
	public void set_v(int _v) {
		v = _v;
	}
	
	public void set_A(int _A) {
		A = _A;
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