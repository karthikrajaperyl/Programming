package program;
import java.util.*;
import java.io.*;
public class RepeatingSubstring {
	static final int total=26;
	/*static List<Trie> arrayList=new ArrayList<>();
	static Trie root;
	static {
		root=new Trie();
	}
	static class Trie
	{
		Trie[] character=new Trie[total];
		boolean isEnd;
		Trie()
		{
			isEnd=false;
			for(int i=0;i<total;i++)
			{
				character[i]=null;
			}
		}
	}
	public void insertElement(String str)
	{
		int n=str.length();
		int a;
		for(int i=0;i<n;i++)
		{
			a=str.charAt(i)-'A';
			if()
		}
	}*/
	public void split(String s,List<String> ansList)
	{
		String temp="";
		int n=s.length(),i;
		List<String> list=new ArrayList<>();
		for(i=0;i<n;i++){
			if(Character.compare(s.charAt(i),'$')==0&&temp!="")
			{
				ansList.add(temp);
				temp="";
			}
			else if(Character.compare(s.charAt(i),'$')!=0)
			{
				temp+=s.charAt(i);
			}
		}
		if(temp!=null)
		ansList.add(temp);
	}
	public void print(String s)
	{
		int len=s.length(),i,count,j,k;
		for(i=0;i<=len/2;i++)
		{
			count=i+2;
			if(len%count==0)
			{
				/*int iteration=len/i;
				StringBuilder sb=new StringBuilder();
				String subStr=s.substring(0, i);
				for(int j=0;j<iteration;j++) {
					sb.append(subStr);
					System.out.println("i-"+i+"\nvalue-"+sb.toString());
				}
				if(sb.toString().equals(s))
					System.out.println(sb.toString());*/
				if(s.substring(0,len-i).equals(s.substring(i)))
				{
					System.out.println(s.substring(0,len-i));
				}
			boolean same=true;
				for(j=count;j<len&&same;j+=count)
				{
					for(k=0;k<=i&&same;k++)
					{
						if(s.charAt(k)!=s.charAt(k+j))
						{
							same=false;
						}
					}
				}
				if(same==true)
				System.out.println(s.substring(0,i));
			}
		}
	}
	public String split(String s)
	{
		String temp="";
		int n=s.length(),i;
		//List<String> list=new ArrayList<>();
		for(i=0;i<n;i++){
			/*if(Character.compare(s.charAt(i),'$')==0&&temp!="")
			{
				ansList+=temp;
				temp="";
			}
			else */if(Character.compare(s.charAt(i),'$')!=0)
			{
				temp+=s.charAt(i);
			}
		}
		//if(temp!=null)
		//ansList+=temp;
		return temp;
	}
	public static void main(String[] args) throws IOException{
		RepeatingSubstring tempObj=new RepeatingSubstring();
		BufferedReader readerObj=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the word-");
		String str=readerObj.readLine().toUpperCase();
		//System.out.println(str);
		StringBuilder s=new StringBuilder(str);
		int[] freq=new int[total];
		for(char a:s.toString().toCharArray())
			if(Character.compare(a,' ')!=0)
			freq[a-'A']++;
		for(int i=0;i<s.length();i++)
		{
			if(Character.compare(s.charAt(i),' ')==0)
			{
				s.replace(i,i+1, "$");
				continue;
			}
			if(freq[s.charAt(i)-'A']<2)
			{
				s.replace(i,i+1, "$");
			}
		}
		System.out.println(s.toString());
		String newstr=tempObj.split(s.toString());
		
		System.out.println("In main-"+newstr);
		tempObj.print(newstr);
		/*int listSize=ansList.size();
		Collections.sort(ansList);
		for(int i=0;i<listSize;i++)
		{
			String temp=ansList.get(i);
			//if(str.indexOf(temp)!=0)
			//	System.out.println(temp);
		}*/

		
	}

}
