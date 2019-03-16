package com.demetrio.base64.base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/** Class that contains static methods for decoding and encoding in base64.
 * 	Even if there is already {@link java.util.Base64} that does the same thing
 * 	(even better cause it is written by a great programmer), I wanted to implement
 * 	my own like a sort of exercise.
 * 	@author Alessandro Chiariello (Demetrio)
 * 	@version 1.0
 * 	@see java.util.Base64 */
public final class Base64 
{
	/** It encodes the input file (given by a {@link java.io.RandomAccessFile RandomAccessFile}) in
	 * 	base64 text.
	 * 	@param input - the input file
	 * 	@return a string that represents the encoded base64 text
	 * 	@throws IOException in case of I/O error with the input file
	 * 	@author Alessandro Chiariello (Demetrio)
	 * 	@version 1.0
	 * 	@see java.io.RandomAccessFile RandomAccessFile */
	public static String encode(RandomAccessFile input) throws IOException
	{
		int n=8, ch = input.read();
		StringBuilder result = new StringBuilder();
		
		while (ch!=-1)
		{
			int resto=0, num;
			for(n=2;n<=6 && ch!=-1;ch=input.read(),n+=2)
			{
				int pow = (int) Math.pow(2,n);
				num = ch / pow + resto;
				resto = (ch % pow) << (6-n);
				result.append(tab[num]);
			}
			result.append(tab[resto]);
		}
		
		while (n<8)
		{
			result.append('=');
			n+=2;
		}
		
		return result.toString();
	}
	
	/** It decodes the base64 input file (given by a {@link java.io.RandomAccessFile RandomAccessFile}) in a
	 * 	{@link java.io.ByteArrayOutputStream ByteArrayOutputStream} that can be written to a file.
	 * 	@param input - the base64 input file
	 * 	@return a {@link java.io.ByteArrayOutputStream ByteArrayOutputStream} that represents the decoded file
	 * 	@throws IOException in case of I/O error with the input file
	 * 	@throws Base64DecodeException when the input file isn't coded in base64 (or doesn't respect some rule)
	 * 	@author Alessandro Chiariello (Demetrio)
	 * 	@version 1.0
	 * 	@see java.io.RandomAccessFile RandomAccessFile
	 * 	@see java.io.ByteArrayOutputStream ByteArrayOutputStream*/
	public static ByteArrayOutputStream decode(RandomAccessFile input) throws IOException
	{
		int n=-2, count=0;
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int ch = input.read();
		
		while (ch!=-1)
		{
			int prev = indexOf((char) ch) << 2, now;
			if (prev<0)
				throw new Base64DecodeException();
			
			ch = input.read();
			for(n=4;n>=0 && ch!=-1;n-=2,ch=input.read())
			{
				int temp = (int) Math.pow(2,n);
				int index = indexOf((char) ch);
				if (index<0)
				{
					if (ch=='=' && (input.getFilePointer()==input.length()))
					{
						count++;
						continue;
					}
					else
					if (ch=='=' && (ch=input.read())=='=')
					{
						count++;
						input.seek(input.getFilePointer()-1);
						continue;
					}
					else
						throw new Base64DecodeException();
				}
				now = (index / temp + prev);
				prev = (index % temp) << (8-n);
				buf.write(now);
			}
		}
		
		if (n!=-2 || count>=3)
			throw new Base64DecodeException("String isn't multiple of 4 or has too much paddings");
		
		return buf;
	}
	
	/** It encodes the input string in base64 text.
	 * 	@param str - the input string to be encoded
	 * 	@return a string that represents the encoded base64 text
	 * 	@throws UnsupportedEncodingException if utf-8 charset is not supported
	 * 	@author Alessandro Chiariello (Demetrio)
	 * 	@version 1.0  */
	public static String encode(String str) throws UnsupportedEncodingException
	{
		int n=8, i=0;
		String result = "";
		byte buf[] = str.getBytes("UTF-8");
		
		while (i<buf.length)
		{
			int resto = 0, num;
			try
			{
				for(n=2;n<=6;n+=2,i++)
				{
					int pow = (int) Math.pow(2,n);
					num = ((int) buf[i] & 0xff) / pow + resto;
					resto = (((int) buf[i] & 0xff) % pow) << (6-n);
					result+=tab[num];
				}
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				//exit
			}
			finally
			{
				result+=tab[resto];
			}
		}
		
		while (n<8)
		{
			result+='=';
			n+=2;
		}
		
		return result;
	}
	
	/** It decodes the base64 input string in plain text.
	 * 	@param str - the input base64 string to be decoded
	 * 	@return a string that represents the decoded input string
	 * 	@throws UnsupportedEncodingException if utf-8 charset is not supported
	 * 	@throws Base64DecodeException if the input string isn't coded in base64 (or doesn't respect some rule)
	 * 	@author Alessandro Chiariello (Demetrio)
	 * 	@version 1.0*/
	public static String decode(String str) throws UnsupportedEncodingException
	{
		int n=-2, count=0, i=0;
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		
		while (i<str.length())
		{
			int prev = indexOf(str.charAt(i++)) << 2;
			if (prev<0)
				throw new Base64DecodeException();
			int now;
			try
			{
				for(n=4;n>=0;n-=2,i++)
				{
					int temp = (int) Math.pow(2,n);
					int index = indexOf(str.charAt(i));
					if (index<0)
					{
						if (str.charAt(i)=='=' && (i+1==str.length() || str.charAt(i+1)=='='))
						{
							count++;
							continue;
						}
						else
							throw new Base64DecodeException();
					}
					now = (index / temp + prev);
					prev = (index % temp) << (8-n);
					buf.write(now);
				}
			}
			catch(IndexOutOfBoundsException e)
			{
				//exit
			}
		}
		
		if (n!=-2 || count>=3)
			throw new Base64DecodeException("String isn't multiple of 4 or has too much paddings");
		
		return buf.toString("UTF-8");
	}
	
	/* Private method for getting the tab index of a base64 character */
	private static int indexOf(char ch)
	{
		int i;
		for(i=0;i<tab.length;i++)
		{
			if (ch==tab[i])
				return i;
		}
		return -1;
	}
	
	/* The base64 tab */
	private static final char tab[] = {'A','B','C','D','E','F','G','H',
									   'I','J','K','L','M','N','O','P',
									   'Q','R','S','T','U','V','W','X',
									   'Y','Z','a','b','c','d','e','f',
									   'g','h','i','j','k','l','m','n',
									   'o','p','q','r','s','t','u','v',
									   'w','x','y','z','0','1','2','3',
									   '4','5','6','7','8','9','+','/'};
}
