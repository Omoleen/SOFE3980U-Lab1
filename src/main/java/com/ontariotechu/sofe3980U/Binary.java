package com.ontariotechu.sofe3980U;

/**
 * Unsigned integer Binary variable
 *
 */
public class Binary
{
	private String number="0";  // string containing the binary value '0' or '1'
	/**
	* A constructor that generates a binary object.
	*
	* @param number a String of the binary values. It should contain only zeros or ones with any length and order. otherwise, the value of "0" will be stored.   Trailing zeros will be excluded and empty string will be considered as zero.
	*/
	public Binary(String number) {
		if (number == null || number.isEmpty()) {
			this.number = "0"; // Default to "0" for null or empty input
			return;
		}

		// Validate the binary string (only '0' or '1' allowed)
		for (int i = 0; i < number.length(); i++) {
			char ch = number.charAt(i);
			if (ch != '0' && ch != '1') {
				this.number = "0"; // Default to "0" for invalid input
				return;
			}
		}

		// Remove leading zeros
		int beg;
		for (beg = 0; beg < number.length(); beg++) {
			if (number.charAt(beg) != '0') {
				break;
			}
		}

		// If all digits are '0', ensure number is "0"
		this.number = (beg == number.length()) ? "0" : number.substring(beg);
	}
	/**
	* Return the binary value of the variable
	*
	* @return the binary value in a string format.
	*/
	public String getValue()
	{
		return this.number;
	}
	/**
	* Adding two binary variables. For more information, visit <a href="https://www.wikihow.com/Add-Binary-Numbers"> Add-Binary-Numbers </a>.
	*
	* @param num1 The first addend object
	* @param num2 The second addend object
	* @return A binary variable with a value of <i>num1+num2</i>.
	*/
	public static Binary add(Binary num1,Binary num2)
	{
		// the index of the first digit of each number
		int ind1=num1.number.length()-1;
		int ind2=num2.number.length()-1;
		//initial variable
		int carry=0;
		String num3="";  // the binary value of the sum
		while(ind1>=0 ||  ind2>=0 || carry!=0) // loop until all digits are processed
		{
			int sum=carry; // previous carry
			if(ind1>=0){ // if num1 has a digit to add
				sum += (num1.number.charAt(ind1)=='1')? 1:0; // convert the digit to int and add it to sum
				ind1--; // update ind1
			}
			if(ind2>=0){ // if num2 has a digit to add
				sum += (num2.number.charAt(ind2)=='1')? 1:0; // convert the digit to int and add it to sum
				ind2--; //update ind2
			}
			carry=sum/2; // the new carry
			sum=sum%2;  // the resultant digit
			num3 =( (sum==0)? "0":"1")+num3; //convert sum to string and append it to num3
		}
		Binary result=new Binary(num3);  // create a binary object with the calculated value.
		return result;

	}
	/**
	* Bitwise logical OR of two binary variables. The shorter operand is treated as
	* if it were padded with leading zeros, so a digit of the result is one whenever
	* at least one of the corresponding operand digits is one.
	*
	* @param num1 The first operand object
	* @param num2 The second operand object
	* @return A binary variable with a value of <i>num1|num2</i>.
	*/
	public static Binary or(Binary num1,Binary num2)
	{
		int ind1=num1.number.length()-1; // the index of the first digit of num1
		int ind2=num2.number.length()-1; // the index of the first digit of num2
		String num3="";  // the binary value of the result
		while(ind1>=0 || ind2>=0) // loop until all digits are processed
		{
			// a missing digit is a leading zero
			char digit1=(ind1>=0)? num1.number.charAt(ind1) : '0';
			char digit2=(ind2>=0)? num2.number.charAt(ind2) : '0';
			num3=((digit1=='1' || digit2=='1')? "1":"0")+num3; // one digit is enough to set the result
			ind1--;
			ind2--;
		}
		return new Binary(num3); // create a binary object with the calculated value.
	}
	/**
	* Bitwise logical AND of two binary variables. The shorter operand is treated as
	* if it were padded with leading zeros, so a digit of the result is one only when
	* both corresponding operand digits are one.
	*
	* @param num1 The first operand object
	* @param num2 The second operand object
	* @return A binary variable with a value of <i>num1&amp;num2</i>.
	*/
	public static Binary and(Binary num1,Binary num2)
	{
		int ind1=num1.number.length()-1; // the index of the first digit of num1
		int ind2=num2.number.length()-1; // the index of the first digit of num2
		String num3="";  // the binary value of the result
		while(ind1>=0 || ind2>=0) // loop until all digits are processed
		{
			// a missing digit is a leading zero
			char digit1=(ind1>=0)? num1.number.charAt(ind1) : '0';
			char digit2=(ind2>=0)? num2.number.charAt(ind2) : '0';
			num3=((digit1=='1' && digit2=='1')? "1":"0")+num3; // both digits are required
			ind1--;
			ind2--;
		}
		return new Binary(num3); // leading zeros are dropped by the constructor
	}
	/**
	* Multiplying two binary variables using the shift-and-add algorithm: for every
	* digit of the second operand that is one, the first operand shifted by the
	* position of that digit is added to the running product.
	*
	* @param num1 The first factor object
	* @param num2 The second factor object
	* @return A binary variable with a value of <i>num1*num2</i>.
	*/
	public static Binary multiply(Binary num1,Binary num2)
	{
		Binary result=new Binary("0"); // the running product
		for(int ind2=num2.number.length()-1; ind2>=0; ind2--) // process num2 from its first digit
		{
			if(num2.number.charAt(ind2)=='1') // a zero digit contributes nothing
			{
				// shift num1 left by the position of the current digit
				String shifted=num1.number;
				for(int shift=num2.number.length()-1-ind2; shift>0; shift--){
					shifted+="0";
				}
				result=add(result,new Binary(shifted)); // accumulate the partial product
			}
		}
		return result;
	}
}
