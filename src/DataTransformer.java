import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;	
/**
 * @author Vali Shaik- B00835822
 *
 */

//DataTransformer class contains all operations carried on the matrix
public class DataTransformer {

	//constant value use to print top five rows in the matrix
	public int TOP_FIVE_ROWS=5;

	//ArrayList data structure used to create a table of matrix
	ArrayList<ArrayList<String>>table;

	//constructor to invoke matrix on DataTransformer object creation
	public DataTransformer()
	{
		this.table=new ArrayList<ArrayList<String>>();
	}

	/*operation used to clear all the data in the matrix*/
	public boolean clear()
	{

		boolean flag;
		try {
			flag = false;
			//clear all rows and columns in the table
			table.clear();
			//checking if table is empty
			if(table.isEmpty())
			{
				table=new ArrayList<ArrayList<String>>();
				flag=true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			flag=false;
		}
		return flag;
	}//clear

	/*read function is used to read the data from the file and store in an datastructure of matrix form
	 * 
	 * input: file name as String
	 * Output: No of rows read and stored in an object
	 * */
	public int read(String fileName)
	{
		int flag;
		try {
			if(fileName==null)
			{
				return 0;
			}
			flag = 0;
			try{
				BufferedReader buf = new BufferedReader(new FileReader(fileName.trim()));
				ArrayList<String> words = null;
				String lineJustFetched = null;
				String[] wordsArray;
				while(true)
				{
					//reading each line in a file
					lineJustFetched = buf.readLine();
					if(lineJustFetched == null)
					{  
						break; 
					}
					else
					{
						words = new ArrayList<>();
						//splitting words from a file based on tab space
						wordsArray = lineJustFetched.split("\t");
						for(String each : wordsArray)
						{
							if(!"".equals(each))
							{
								words.add(each);

							}//if
						}//for
						//Adding all the rows to the table
						table.add(words);
					}//else
				}//while

				//file is closed
				buf.close();

				flag=table.size();

				if(table.size()>0 && table.get(0).size()<=10)
				{
					for(int i=0;i<table.get(0).size();i++)
					{
						//changing words to small case for future duplicate identification while adding new column
						table.get(0).set(i, table.get(0).get(i).toLowerCase());
					}

					flag=table.size();
				}
				else
				{
					flag=0;
					table=new ArrayList<ArrayList<String>>();
				}
			}
			catch(FileNotFoundException e){
				flag=0;
			} catch (IOException e) {
				flag=0;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			flag=0;
		}
		return flag;
	}//read

	/*Calculate function is used to calculate( String equation ) and apply the given equation to the data in the object and return the number of rows that were calculated for the equation.  
	 *  If a calculated value will have decimal places then round the value to the nearest integer (a value of .5 rounds up). 
	 *
	 *
	 *Input: String equation
	 *Output: Integer
	 */
	public int calculate(String equation)
	{
		try {
			try
			{
				//Splitting the eqaution into two token as left par and right part based on equal operator
				StringTokenizer st=new StringTokenizer(equation, "=");//y=a+bcd
				if(st.hasMoreTokens())
				{
					String columnName=st.nextToken();//y
					String rightSideEquation=st.nextToken();//a+bcd
					String operator = null;
					//splitting the right part of equation by checking the operators
					Matcher m=Pattern.compile("[\\-\\+\\*\\/]\\s").matcher(rightSideEquation);
					if(m.find())
					{
						//checking for an operator in the right part of eqaution
						operator=m.group();
					}

					String [] splitstring=rightSideEquation.split("[\\-\\+\\*\\/]\\s");
					String value1=null;
					String value2=null;
					if(splitstring.length==2)
					{
						value1=splitstring[0];
						value2=splitstring[1];
						Matcher mat1=Pattern.compile("[a-zA-Z]\\s[\\+\\-\\*\\/]").matcher(value1.trim());
						Matcher mat2=Pattern.compile("[a-zA-Z]\\s[\\+\\-\\*\\/]").matcher(value2.trim());
						if(mat1.find())
						{
							return 0;
						}
						if(mat2.find())
						{
							return 0;
						}
					}
					else if(splitstring.length==1)
					{
						value1=splitstring[0];
						Matcher mat=Pattern.compile("[a-zA-Z]\\s[\\+\\-\\*\\/]").matcher(value1);
						if(mat.find())
						{
							return 0;
						}
					}
					//Initializing indexes of all the columns in an equation
					int columnNumber = 0,value1Index = 0,value2Index=0;
					int flag=0;
					for(int i=0,j=0;j<table.get(i).size();j++)
					{
						if(table.get(i).get(j).trim().equalsIgnoreCase(columnName.trim()))
						{
							columnNumber=j;
							flag++;
						}//if
						if(value1!=null&&table.get(i).get(j).trim().equalsIgnoreCase(value1.toString().trim()))
						{
							value1Index=j;
							flag++;
						}//if
						if(value2!=null&&table.get(i).get(j).trim().equalsIgnoreCase(value2.toString().trim()))
						{
							value2Index=j;
							flag++;
						}//if

					}//for
					if(flag==0)
					{
						return 0;
					}//if

					//If there is no operator in the equation,the check for the single value and assingning to the rows
					if(operator==null)
					{
						Double value1ColumnValue = null;
						String value1ColumnValuString=null;
						if(value1!=null&&isString(value1.trim()))
						{
							if(columnNumber==0)
							{
								value1ColumnValuString=value1.trim();
								Matcher match=Pattern.compile("[\\+\\-\\/\\*]").matcher(value1ColumnValuString);
								if(!match.find())
								{
									for(int i=1;i<table.size();i++)
									{
										table.get(i).set(columnNumber,value1ColumnValuString.trim());
									}//for
									return table.size()-1;
								}
								else
								{
									return 0;
								}
							}//if
							else
							{
								// value1ColumnValuString=table.get(value1Index);
								for(int i=1;i<table.size();i++)
								{
									// value1ColumnValuString=table.get(value1Index);
									table.get(i).set(columnNumber,table.get(i).get(value1Index));
								}//for
								return table.size()-1;
							}

						}//if
						else if(value1!=null&&isNumeric(value1.toString()))
						{
							if(columnNumber!=0)
							{
								value1ColumnValue=Double.parseDouble(value1.toString());

								for(int i=1;i<table.size();i++)
								{
									table.get(i).set(columnNumber,Integer.toString((int) Math.round(value1ColumnValue)));
								}//for
								return table.size()-1;
							}
						}//if
						else
						{
							for(int i=1;i<table.size();i++)
							{
								Double seconColumnValue=Double.valueOf(table.get(i).get(value1Index).trim());

								table.get(i).set(columnNumber,Integer.toString((int) Math.round(seconColumnValue)));
							}//for
							return table.size()-1;
						}//else
					}
					for(int i=1;i<table.size();i++)
					{
						Double value1ColumnValue=0.0;
						Double value2ColumnValue=0.0;
						if(isNumeric(value1.toString().trim()))
						{
							value1ColumnValue=Double.parseDouble(value1.toString().trim());
						}
						else
						{
							value1ColumnValue=Double.valueOf(table.get(i).get(value1Index).trim());
						}
						if(isNumeric(value2.toString()))
						{
							value2ColumnValue=Double.parseDouble(value2.toString());
						}
						else
						{
							value2ColumnValue=Double.valueOf(table.get(i).get(value2Index).trim());
						}
						switch (operator.trim()) {
						case "+":
							table.get(i).set(columnNumber,Integer.toString((int) Math.round(value1ColumnValue+value2ColumnValue)));
							break;
						case "*":
							table.get(i).set(columnNumber,Integer.toString((int) Math.round(value1ColumnValue*value2ColumnValue)));
							break;
						case "-":
							table.get(i).set(columnNumber,Integer.toString((int) Math.round(value1ColumnValue-value2ColumnValue)));
							break;
						case "/":
							table.get(i).set(columnNumber,Integer.toString((int) Math.round(value1ColumnValue/value2ColumnValue)));
							break;
						default:
							System.out.println("Invalid Operator");
							break;
						}//switch

					}//for	5
					return table.size()-1;

				}//if
				return 0;
			}
			catch(NumberFormatException e)
			{
				//e.printStackTrace();
				return 0;
			}
			catch(Exception e)
			{
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}//calculate



	/*Function to check if the value in the String input is of type Numeric
	 * 
	 * Input: String
	 * Output: Boolean
	 * */
	public static boolean isNumeric(String strNum) {
		try {
			double f = Double.parseDouble(strNum);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
		return true;
	}

	/*Function to check if the value in the String input contains only alphabets
	 * 
	 * Input: String
	 * Output: Boolean
	 * */
	public static boolean isString(String strNum) {
		boolean flag=false;
		try {
			Matcher m=Pattern.compile("[a-zA-Z]").matcher(strNum);
			if(m.find())
			{
				flag=true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag=false;
		}
		return flag;
	}//isString


	/*Top function is used to print only the top five rows in the matrix*/
	public void top()
	{
		try {
			int topFiveRowSize=0;
			//print top 5 rows in the table	
			if(TOP_FIVE_ROWS<table.size())
			{
				topFiveRowSize=TOP_FIVE_ROWS;
			}
			else
			{
				topFiveRowSize=table.size();
			}

			for(int i=0;i<topFiveRowSize;i++)
			{
				for(int j=0;j<table.get(i).size();j++)
				{

					System.out.print(table.get(i).get(j)+"\t");
				}//for

				System.out.println();
			}//for
		}
		catch(Exception e)
		{

		}

	}//top



	/*Print function to display all the data in the object*/
	public void print()
	{
		try {
			//print complete data in the table
			for(int i=0;i<table.size();i++)
			{
				for(int j=0;j<table.get(i).size();j++)
				{
					System.out.print(table.get(i).get(j)+"\t");
				}//for

				System.out.println();
			}//for
		} catch (Exception e) {
		}
	}//print


	/*Write function writes all the data in the object to the given file
	 * 
	 * 
	 * Input: String file name
	 * Output: Integer no of rows written onto the file
	 * */
	public int write(String fileName)
	{
		try {
			if(fileName==null)
			{
				return 0;
			}
			FileWriter writer = null;
			try {
				//Opening the file connection for the given file using FileWriter
				writer = new FileWriter(fileName.trim());
				for(int i=0;i<table.size();i++)
				{
					for(int j=0;j<table.get(i).size();j++) 
					{
						//Writing data to the file
						writer.write(table.get(i).get(j) +"\t");
					}//for
					writer.write(System.lineSeparator());
				}//for
				//File is closed after use
				writer.close();
			}//try 
			catch(FileNotFoundException e)
			{
				return 0;
			}
			catch (IOException e) {
				return 0;
			}//catch
			return table.size();
		} catch (Exception e) {
			return 0;
		}
	}//write


	/*New Column operation is used to add the new column to the matrix and add if there is no column with same name
	 * 
	 * Input: String
	 * Ouput: boolean
	 * */
	public boolean newColumn(String columnName)
	{
		boolean flag=false;
		try
		{
			if(columnName==null)
			{
				flag=false;		

			}

			if(table.isEmpty())
			{
				return false;
			}
			//Checking whether column names is of only alphabets
			boolean match=Pattern.matches("[a-zA-Z]+", columnName.trim());
			if(match)
			{
				if(columnName!=null&&columnName.trim().length()>0)
				{
					if(!table.get(0).contains(columnName.toLowerCase()))
					{
						table.get(0).add(columnName.toLowerCase());
						for(int i=1;i<table.size();i++)
						{
							table.get(i).add("0");
						}//for
						flag=true;	
					}//if
				}//if

			}//if
		}//try
		catch(NullPointerException e)
		{
			flag=false;
		}//catch
		catch(Exception e)
		{
			flag=false;
		}
		return flag;	
	}//newColumn
}//class
