package pk;

public class FirstProgram {

	public static void maleTreppe(int hoehe, int stufentiefe){
		for(int i=0;i<hoehe;i++){
			for (int j=1;j<=stufentiefe*hoehe-i*stufentiefe;j++){
				System.out.print(' ');
			}
			for(int k=0;k<stufentiefe*(i+1);k++){
			System.out.print('*');
			}

			System.out.println();
		}
	}
	
	public static void main(String[]args){
		maleTreppe(8,3);
	}
}

//Aufgabe 9: Edit!
