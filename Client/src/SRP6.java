import java.util.Random;
import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

public class SRP6 {
	protected static int N;
	protected static int g;
	protected static int k;
	
	protected static int calcHash(int _A, int _B) {
    	int hash;
    	hash = _A + _B;
    	String tmp = Long.toString(hash);
    	hash = 0;
    	for(int i = 0;i<tmp.length();i++) {
    		hash+= tmp.charAt(i)* Math.pow(31,i); 
    	}
    	return hash%13;
 	}
	
	protected static int calcHash(long _A, long _B) {
    	long hash;
    	hash = _A + _B;
    	String tmp = Long.toString(hash);
    	hash = 0;
    	for(int i = 0;i<tmp.length();i++) {
    		hash+= tmp.charAt(i)* Math.pow(31,i); 
    	}
    	return (int) (hash%13);
 	}
	
	 protected static int calcHash(int _S) {
	    	int hash;
	    	hash = _S;
	    	String tmp = Long.toString(hash);
	    	hash = 0;
	    	for(int i = 0;i<tmp.length();i++) {
	    		hash+= tmp.charAt(i)* Math.pow(31,i); 
	    	}
	    	return hash%13;
	 	}
	 
	 protected static int calcHash(int HN_xor_Hg, int I,long s,int _A, int _B, int K) {
	    	long hash;
	    	hash = HN_xor_Hg + I + s + _A  + _B + K;
	    	String tmp = Long.toString(hash);
	    	hash = 0;
	    	for(int i = 0;i<tmp.length();i++) {
	    		hash+= tmp.charAt(i)* Math.pow(11,i); 
	    	}
	    	return (int)hash%133;
	}
	
	 protected static int calcHash(int _A, int _M,int _K) {
		 long hash;
		 hash =  _A  + _M + _K;
		 String tmp = Long.toString(hash);
		 hash = 0;
		 for(int i = 0;i<tmp.length();i++) {
			 hash+= tmp.charAt(i)* Math.pow(11,i); 
		 }
		 return (int)hash%45625;
	 }
	 
	 protected static int calcHash(String _S) {
	    	int hash;
	    	hash = 0;
	    	for(int i = 0;i<_S.length();i++) {
	    		hash+= _S.charAt(i)* Math.pow(31,i); 
	    	}
	    	return hash%13;
	 }
	
}



