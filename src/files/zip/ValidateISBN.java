
public class ValidateISBN {
	private String ISBN;
	
	public ValidateISBN(String ISBN) {
		this.ISBN = ISBN;
	}
	
	public ValidateISBN() {
		ISBN = "-1";
	}

	public boolean checkISBN(String ISBN) {
		
		if(ISBN.length() == 13) {
			int total = 0;
			
			for(int i=0; i< 13; i++) {
				if(i % 2 == 0) {
					total += Character.getNumericValue(ISBN.charAt(i));
				}
				else {
					total += Character.getNumericValue(ISBN.charAt(i)) * 3;
				}
			}
			if(total % 10  == 0) {
				return true;
			}
			else {
				return false;
			}
			
		}
		else {
			
			if(ISBN.length() != 10 ) {
				throw new NumberFormatException("ISBN must be 10 or 13 digits long");
			}
			
			int total = 0;
			
			for (int i = 0; i < 10; i++)
			{
				if(!Character.isDigit(ISBN.charAt(i))) {
					if(i==9 && ISBN.charAt(i) == 'X') {
						//Valid
						total += 10;
					}
					else {
						throw new NumberFormatException("ISBN must contain only numbers");
					}
				}
				else {
					
					total += Character.getNumericValue(ISBN.charAt(i)) * (10 -i);
				}
			}
			
			if (total % 11 == 0) {
				return true;
			}
			else {
				return false;
			}
			
		}
	}
	
	
}
