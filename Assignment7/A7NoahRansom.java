//By Noah Ransom
//Manages inventory by reading from a file and taking user input

import java.io.*;
import java.util.*;

class A7NoahRansom
{
	public static int[] resize(int[] array, int newSize)
	{
		//POST: returns a new array of size newSize containing all the elements in array and in the same order
		int[] tmp = new int[newSize];												//array of desired size
		for(int i = 0; i < (newSize < array.length ? newSize : array.length); i++)	//go up to the end of the input array or until the desired value, whichever is smaller
		{
			//populate the array that we will return at the end
			tmp[i] = array[i];
		}
		return tmp;
	}

	public static String[] resize(String[] array, int newSize)
	{
		//Identical to above resize method, but works on a string array
		String[] tmp = new String[newSize];
		for(int i = 0; i < (newSize < array.length ? newSize : array.length); i++)
		{
			tmp[i] = array[i];
		}
		return tmp;
	}

	public static double[] resize(double[] array, int newSize)
	{
		//Identical to above resize method, but works on an array of doubles
		double[] tmp = new double[newSize];
		for(int i = 0; i < (newSize < array.length ? newSize : array.length); i++)
		{
			tmp[i] = array[i];
		}
		return tmp;
	}

	public static int sequentialSearch(String[] array, int size, String item)
	{
		//POST: return index of first occurrence of item or -1 if not found
		for(int i = 0; i < size; i++)
		{
			if(array[i].equals(item))
			{
				return i;
			}
		}
		//if we manage to exit the for loop, then we did not find what we were looking for
		return -1;
	}

	public static void insert(String[] names, String name, int[] stocks, int stock, double[] prices, double price, int size)
	{
		//POST: insert items int arrays
		//Cannot resize in this method because java is pass by value and the arrays will not stay resized when the function returns
		names[size] = name;
		stocks[size] = stock;
		prices[size] = price;

	}

	public static void remove(String[] names, int[] stocks, double[] prices, String toRemove, int size)
	{
		//POST: remove toRemove, if it exists, and remove corresponding records from the other arrays, then put the item at the end of the array in its place
		int idx = sequentialSearch(names, size, toRemove);
		if(idx < 0)
		{
			//the desired item is not in the array
			printLine("Item " + toRemove + " does not exist.\n");
		}
		else
		{
			//the item will be at the same index in each array, so remove it from all 3 at once
			size--;
			names[idx] = names[size];
			stocks[idx] = stocks[size];
			prices[idx] = prices[size];
			printLine("Item " + toRemove + " is removed.\n");
		}
	}

	public static int readFile(String fileName, String[] names, int[] stocks, double[] prices, int size) throws IOException
	{
		//POST: read all data from file fileName and populate the arrays, return new size
		//This method does not work because java does pass by value, but it's a good function, so I left it in, but did not use it


		Scanner reader = new Scanner(new FileReader(fileName)); //reads from the file
		Scanner lineReader;										//parses the current line
		String entry;											//holds current line
		String name;											//holds name of current product
		int stock;												//stores number of current product in stock
		double price;											//stores price of current object
		while(reader.hasNextLine())
		{
			entry = reader.nextLine();
			//System.out.println(entry);
			lineReader = new Scanner(entry);
			name = lineReader.next();
			stock = lineReader.nextInt();
			price = lineReader.nextDouble();
			lineReader.close();
			//resize the arrays if necessary
			if(names.length == size)
			{

				names = resize(names, names.length * 2);
				//System.out.println("Length of names: " + names.length);
				stocks = resize(stocks, stocks.length*2);
				prices = resize(prices, prices.length*2);
			}
			insert(names, name, stocks, stock, prices, price, size);
			size++;
		}
		reader.close();
		return size;
	}

	public static void printLine(String toPrint)
	{
		//POST: writes toPrint to console and file
		try
		{
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File("inventory_out.txt"), true));
			writer.printf(toPrint);
			writer.close();
		} catch (IOException e)
		{
			System.err.println("Unable to write to file");
		}
		System.out.printf(toPrint);
	}

	public static void printLineFile(String toPrint)
	{
		//POST: writes toPrint to file only - lets you see what the user inputs by looking at the output file alone
		try
		{
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File("inventory_out.txt"), true));
			writer.printf(toPrint);
			writer.close();
		} catch (IOException e)
		{
			System.err.println("Unable to write to file");
		}
	}

	public static void displayTable(String[] names, int[] stocks, double[] prices, int size)
	{
		//POST: print out all of the data in a nicely formatted way
		double total = 0.0;														//stores total inventory value
		int buffer = 3;															//extra space between columns
		int idLength = 20 + buffer;												//names probably won't be more than 20 characters long
		int stocklength = 5 + buffer;
		int unitPriceLength = 9 + buffer;

		//format strings to make the table alighn nicely
		String fmtID = "%-" + idLength + "s";
		String fmtStock = "%-" + stocklength + "s";
		String fmtUnitPrice = "%-" + unitPriceLength + "s";

		printLine(String.format(fmtID, "ID"));
		printLine(String.format(fmtStock, "Stock"));
		printLine(String.format(fmtUnitPrice, "Unit Price"));
		printLine("\n");

		for(int i = 0; i < size; i++)
		{
			printLine(String.format(fmtID, names[i]));
			printLine(String.format(fmtStock, Integer.toString(stocks[i])));
			printLine(String.format(fmtUnitPrice, Double.toString(prices[i])));
			printLine("\n");
			total += (stocks[i] * prices[i]);
		}
		printLine("Total Inventory: " + Double.toString(total) + "\n");
	}

	public static void main(String[] args)
	{
		boolean done = false;
		String[] names = new String[5];			//names of each item
		int[] stocks = new int[5];				//how many of an item is in stock
		double[] prices = new double[5];		//price of each thing
		int size = 0;							//how many items are in each array
												//read everything from the inventory file

		try
		{
			//size = readFile("inventory.txt", names, stocks, prices, size);
			Scanner reader = new Scanner(new FileReader("inventory.txt")); //reads from the file
			Scanner lineReader;										//parses the current line
			String entry;											//holds current line
			String name;											//holds name of current product
			int stock;												//stores number of current product in stock
			double price;											//stores price of current object
			while(reader.hasNextLine())
			{
				entry = reader.nextLine();
				//System.out.println(entry);
				lineReader = new Scanner(entry);
				name = lineReader.next();
				stock = lineReader.nextInt();
				price = lineReader.nextDouble();
				lineReader.close();
				//resize the arrays if necessary
				if(names.length == size)
				{

					names = resize(names, names.length * 2);
					//System.out.println("Length of names: " + names.length);
					stocks = resize(stocks, stocks.length*2);
					prices = resize(prices, prices.length*2);
				}
				insert(names, name, stocks, stock, prices, price, size);
				size++;
			}
			reader.close();
		}
		catch(IOException e)
		{
			System.err.println("Error reading from file");
		}
		//make input loop
		printLine("Inventory Manager by Noah Ransom\n");
		Scanner scan = new Scanner(System.in);
		String choice;															//stores the user's option choice
		String input;															//used to store arguments given by the user
		printLine("Select from menu:\n");
		printLine("E - Edit\nI - Insert\nR - Remove\nD - Display Table\nQ - Quit\n");
		while(!done)
		{
			printLine("\nEnter letter of choice: ");
			choice = scan.nextLine();
			printLineFile(choice + "\n");
			choice = choice.toUpperCase();
			switch(choice.charAt(0))
			{
				case 'E':	printLine("Enter product: ");
							input = scan.nextLine();
							printLineFile(input + "\n");
							input = input.trim();
							if(sequentialSearch(names, size, input) >= 0)
							{
								int itemIndex = sequentialSearch(names, size, input);
								printLine("Enter amount to add (+) or subtract (-): ");
								int change = Integer.parseInt(scan.nextLine());
								printLineFile(Integer.toString(change) + "\n");
								stocks[itemIndex] += change;
								printLine("Item " + input + " now has amount in stock " + Integer.toString(stocks[itemIndex]) + "\n");
							}
							else
							{
								printLine("Item " + input + " is not found.\n");
							}
							break;

				case 'I':	printLine("Enter new product ID, stock, and unit price: ");
				 			input = scan.nextLine();
							printLineFile(input + "\n");
							//the split method breaks the input into an array of strings delimited by spaces.
							String name = input.split(" ")[0];
							int stock = Integer.parseInt(input.split(" ")[1]);
							double price = Double.parseDouble(input.split(" ")[2]);
							if(sequentialSearch(names, size, name) >= 0)
							{
								printLine("Item " + name + " already exists.\n");
							}
							else
							{
								if(names.length == size)
								{

									names = resize(names, names.length * 2);
									//System.out.println("Length of names: " + names.length);
									stocks = resize(stocks, stocks.length*2);
									prices = resize(prices, prices.length*2);
								}
								insert(names, name, stocks, stock, prices, price, size);
								size++;
								printLine("Item " + name + " is inserted.\n");
							}
							break;

				case 'R':	printLine("Enter ID of product to remove: ");
							input = scan.nextLine();
							printLineFile(input + "\n");
							remove(names, stocks, prices, input, size);
							size--;
							break;

				case 'D':	displayTable(names, stocks, prices, size);
							break;

				case 'Q':	done = true;
							break;

				default:	printLine("Choice not recognized.\n");
							break;
			}
		}
		scan.close();

	}
}
