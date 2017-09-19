package utility;

public class URLTransformUtil {

	public int shortenURL(String url){
		int id = 0;
		
		for(int i = 0; i < url.length();i++){
			
			if('a' <= url.charAt(i) && url.charAt(i) <= 'z')
				id = id * 62 + url.charAt(i) - 'a';

			if('A' <= url.charAt(i) && url.charAt(i) <= 'Z')
				id = id * 62 + url.charAt(i) - 'A' + 26;		
			
			if('0' <= url.charAt(i) && url.charAt(i) <= '9')
					id = id * 62 + url.charAt(i) - '0' + 52;
		}
		return id;
	}
	
}
