package com.demetrio.base64.base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public final class Base64 
{
	public static String base64encode(RandomAccessFile input) throws IOException
	{
		int n=8, ch = input.read();
		StringBuilder result = new StringBuilder("");
		
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
	
	public static ByteArrayOutputStream base64decode(RandomAccessFile input) throws IOException
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
			throw new Base64DecodeException("Stringa non è multipla di 4 o ha troppi padding");
		
		return buf;
	}
	
	public static String base64encode(String str) throws UnsupportedEncodingException
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
	
	public static String base64decode(String str) throws UnsupportedEncodingException
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
			throw new Base64DecodeException("Stringa non è multipla di 4 o ha troppi padding");
		
		return buf.toString("UTF-8");
	}
	
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
	
	private static final char tab[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			  'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
			  '0','1','2','3','4','5','6','7','8','9','+','/'};
}
