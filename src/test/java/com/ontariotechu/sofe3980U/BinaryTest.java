package com.ontariotechu.sofe3980U;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for Binary class.
 */
public class BinaryTest 
{
    /**
     * Test The constructor with a valid binary vallue
     */
    @Test
    public void normalConstructor()
    {
		Binary binary=new Binary("1001001");
        assertTrue( binary.getValue().equals("1001001"));
    }
    /**
     * Test The constructor with an invalid binary value of out-of-range digits
     */
    @Test
    public void constructorWithInvalidDigits()
    {
		Binary binary=new Binary("1001001211");
        assertTrue( binary.getValue().equals("0"));
    }
    /**
     * Test The constructor with an invalid binary value of alphabetic characters
     */
    @Test
    public void constructorWithInvalidChars()
    {
		Binary binary=new Binary("1001001A");
        assertTrue( binary.getValue().equals("0"));
    }
    /**
     * Test The constructor with an invalid binary value that has a sign
     */
    @Test
    public void constructorWithNegativeSign()
    {
		Binary binary=new Binary("-1001001");
        assertTrue( binary.getValue().equals("0"));
    }
    /**
     * Test The constructor with a zero tailing valid binary value
     */
    @Test
    public void constructorWithZeroTailing()
    {
		Binary binary=new Binary("00001001");
        assertTrue( binary.getValue().equals("1001"));
    }
    /**
     * Test The constructor with an empty string
     */
    @Test
    public void constructorEmptyString()
    {
		Binary binary=new Binary("");
        assertTrue( binary.getValue().equals("0"));
    }
	/**
     * Test The add functions with two binary numbers of the same length
     */
    @Test
    public void add()
    {
		Binary binary1=new Binary("1000");
		Binary binary2=new Binary("1111");
		Binary binary3=Binary.add(binary1,binary2);
        assertTrue( binary3.getValue().equals("10111"));
    }
	/**
     * Test The add functions with two binary numbers, the length of the first argument is less than the second
     */
    @Test
    public void add2()
    {
		Binary binary1=new Binary("1010");
		Binary binary2=new Binary("11");
		Binary binary3=Binary.add(binary1,binary2);
        assertTrue( binary3.getValue().equals("1101"));
    }
	/**
     * Test The add functions with two binary numbers, the length of the first argument is greater than the second
     */
    @Test
    public void add3()
    {
		Binary binary1=new Binary("11");
		Binary binary2=new Binary("1010");
		Binary binary3=Binary.add(binary1,binary2);
        assertTrue( binary3.getValue().equals("1101"));
    }
	/**
     * Test The add functions with a binary numbers with zero
     */
    @Test
    public void add4()
    {
		Binary binary1=new Binary("0");
		Binary binary2=new Binary("1010");
		Binary binary3=Binary.add(binary1,binary2);
        assertTrue( binary3.getValue().equals("1010"));
    }
	/**
     * Test The add functions with two zeros
     */
    @Test
    public void add5()
    {
		Binary binary1=new Binary("0");
		Binary binary2=new Binary("0");
		Binary binary3=Binary.add(binary1,binary2);
        assertTrue( binary3.getValue().equals("0"));
    }
	/**
     * Test the or function with two binary numbers of the same length
     */
    @Test
    public void or()
    {
		Binary binary1=new Binary("1000");
		Binary binary2=new Binary("1111");
		Binary binary3=Binary.or(binary1,binary2);
        assertTrue( binary3.getValue().equals("1111"));
    }
	/**
     * Test the or function when the first operand is longer than the second
     */
    @Test
    public void or2()
    {
		Binary binary1=new Binary("1010");
		Binary binary2=new Binary("11");
		Binary binary3=Binary.or(binary1,binary2);
        assertTrue( binary3.getValue().equals("1011"));
    }
	/**
     * Test the or function when the first operand is shorter than the second
     */
    @Test
    public void or3()
    {
		Binary binary1=new Binary("100");
		Binary binary2=new Binary("100011");
		Binary binary3=Binary.or(binary1,binary2);
        assertTrue( binary3.getValue().equals("100111"));
    }
	/**
     * Test the or function with a zero operand, which should return the other operand
     */
    @Test
    public void or4()
    {
		Binary binary1=new Binary("0");
		Binary binary2=new Binary("1010");
		Binary binary3=Binary.or(binary1,binary2);
        assertTrue( binary3.getValue().equals("1010"));
    }
	/**
     * Test the or function with two zeros
     */
    @Test
    public void or5()
    {
		Binary binary1=new Binary("0");
		Binary binary2=new Binary("0");
		Binary binary3=Binary.or(binary1,binary2);
        assertTrue( binary3.getValue().equals("0"));
    }
	/**
     * Test the and function with two binary numbers of the same length
     */
    @Test
    public void and()
    {
		Binary binary1=new Binary("1100");
		Binary binary2=new Binary("1010");
		Binary binary3=Binary.and(binary1,binary2);
        assertTrue( binary3.getValue().equals("1000"));
    }
	/**
     * Test the and function when the operands have different lengths, the leading digits are masked out
     */
    @Test
    public void and2()
    {
		Binary binary1=new Binary("1010");
		Binary binary2=new Binary("11");
		Binary binary3=Binary.and(binary1,binary2);
        assertTrue( binary3.getValue().equals("10"));
    }
	/**
     * Test the and function when the result has leading zeros that must be removed
     */
    @Test
    public void and3()
    {
		Binary binary1=new Binary("1111");
		Binary binary2=new Binary("11");
		Binary binary3=Binary.and(binary1,binary2);
        assertTrue( binary3.getValue().equals("11"));
    }
	/**
     * Test the and function with two operands that have no common one digit
     */
    @Test
    public void and4()
    {
		Binary binary1=new Binary("1000");
		Binary binary2=new Binary("111");
		Binary binary3=Binary.and(binary1,binary2);
        assertTrue( binary3.getValue().equals("0"));
    }
	/**
     * Test the and function with a zero operand, which should return zero
     */
    @Test
    public void and5()
    {
		Binary binary1=new Binary("1111");
		Binary binary2=new Binary("0");
		Binary binary3=Binary.and(binary1,binary2);
        assertTrue( binary3.getValue().equals("0"));
    }
	/**
     * Test the multiply function with two binary numbers of the same length
     */
    @Test
    public void multiply()
    {
		Binary binary1=new Binary("1010");	// 10
		Binary binary2=new Binary("1010");	// 10
		Binary binary3=Binary.multiply(binary1,binary2);
        assertTrue( binary3.getValue().equals("1100100"));	// 100
    }
	/**
     * Test the multiply function when the first factor is longer than the second
     */
    @Test
    public void multiply2()
    {
		Binary binary1=new Binary("1010");	// 10
		Binary binary2=new Binary("11");	// 3
		Binary binary3=Binary.multiply(binary1,binary2);
        assertTrue( binary3.getValue().equals("11110"));	// 30
    }
	/**
     * Test the multiply function when the first factor is shorter than the second
     */
    @Test
    public void multiply3()
    {
		Binary binary1=new Binary("111");	// 7
		Binary binary2=new Binary("1001");	// 9
		Binary binary3=Binary.multiply(binary1,binary2);
        assertTrue( binary3.getValue().equals("111111"));	// 63
    }
	/**
     * Test the multiply function with a factor of one, which should return the other factor
     */
    @Test
    public void multiply4()
    {
		Binary binary1=new Binary("1");
		Binary binary2=new Binary("1101");
		Binary binary3=Binary.multiply(binary1,binary2);
        assertTrue( binary3.getValue().equals("1101"));
    }
	/**
     * Test the multiply function with a zero factor, which should return zero
     */
    @Test
    public void multiply5()
    {
		Binary binary1=new Binary("0");
		Binary binary2=new Binary("1101");
		Binary binary3=Binary.multiply(binary1,binary2);
        assertTrue( binary3.getValue().equals("0"));
    }
	/**
     * Test the multiply function with a power of two factor, which shifts the other factor
     */
    @Test
    public void multiply6()
    {
		Binary binary1=new Binary("1011");	// 11
		Binary binary2=new Binary("100");	// 4
		Binary binary3=Binary.multiply(binary1,binary2);
        assertTrue( binary3.getValue().equals("101100"));	// 44
    }
}
