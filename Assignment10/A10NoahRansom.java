//By Noah Ransom
//reads from a file and works with other classes
import java.util.*;
import java.io.*;
import java.text.NumberFormat;

class A10NoahRansom
{
	public static void printBook(Tradebook book, double tot)
	{
		//POST: print the book in a nicely formatted way
		//If tot is 0 or greater, then this book is the last of its type to print, so we have to print the summary line
		NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
		final int LINEWIDTH = 80;
		int buffer = 4;						//num spaces between columns

		//number of characters allotted to course, author, and price
		int courseWidth = 6 + buffer;
		int authorWidth = 15 + buffer;
		int priceWidth = 6;
		int titleWidth = LINEWIDTH - (courseWidth + authorWidth	+ priceWidth);
		String fmtSum = "%-" + (courseWidth + authorWidth + titleWidth) + "s";

		titleWidth = courseWidth + titleWidth;
		String fmtAuthor = "%-" + authorWidth + "s";
		String fmtPrice = "%-" + priceWidth + "s";
		String fmtTitle = "%-" + titleWidth + "s";

		System.out.print(String.format(fmtTitle, book.getTitle()));
		System.out.print(String.format(fmtAuthor, book.getAuthor()));
		System.out.println(String.format(fmtPrice, currency.format(book.retailPrice())));

		if(tot > 0)
		{
			System.out.print(String.format(fmtSum, "Sum of retail book prices:"));
			System.out.println(String.format(fmtPrice, currency.format(tot)));
		}

	}

	public static void printBook(Textbook book, double tot)
	{
		//POST: print the book in a nicely formatted way
		//If tot is 0 or greater, then this book is the last of its type to print, so we have to print the summary line
		NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
		final int LINEWIDTH = 80;
		int buffer = 4;						//num spaces between columns

		//number of characters allotted to course, author, and price
		int courseWidth = 6 + buffer;
		int authorWidth = 15 + buffer;
		int priceWidth = 6;
		int titleWidth = LINEWIDTH - (courseWidth + authorWidth	+ priceWidth);
		String fmtSum = "%-" + (courseWidth + authorWidth + titleWidth) + "s";

		String fmtCourse = "%-" + courseWidth + "s";
		String fmtAuthor = "%-" + authorWidth + "s";
		String fmtPrice = "%-" + priceWidth + "s";
		String fmtTitle = "%-" + titleWidth + "s";

		System.out.print(String.format(fmtCourse, book.getCourse()));
		System.out.print(String.format(fmtTitle, book.getTitle()));
		System.out.print(String.format(fmtAuthor, book.getAuthor()));
		System.out.print(String.format(fmtPrice, currency.format(book.retailPrice())));
		System.out.println();


		if(tot > 0)
		{
			System.out.print(String.format(fmtSum, "Sum of retail book prices:"));
			System.out.println(String.format(fmtPrice, currency.format(tot)));
		}
	}

	public static void main(String[] args)
	{
		NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
		String fmtSum = "%-" + 74 + "s";
		String fmtPrice = "%-" + 6 + "s";
		int capacity = 0;														//how many books are stored
		String fileName = "books.txt";
		Book[] books = new Book[50];

		//read data from the file and put it into the array
		try
		{
			Scanner reader  = new Scanner(new FileReader(fileName)); //reads from file
			String line;			//holds line currently being parsed
			for(int i = 0; i < 50; i++)
			{
				//My assumption is that you meant 50 books, rather than 50 lines
				line = (reader.hasNextLine()) ? reader.nextLine() : "&&EOF&&";
				if(line.equals("&&EOF&&"))
				{
					//stop trying to read the file. it's empty
					break;
				}
				else if(!reader.hasNextLine())
				{
					break;
				}
				if(line.equals("Textbook"))
				{
					String title = reader.nextLine();
					String author = reader.nextLine();
					String isbn = reader.nextLine();
					double price = Double.parseDouble(reader.nextLine());
					String course = reader.nextLine();
					books[i] = new Textbook(title, isbn, author, price, course);
					capacity++;
				}
				else if(line.equals("Tradebook"))
				{
					//must be tradebook
					String title = reader.nextLine();
					String author = reader.nextLine();
					String isbn = reader.nextLine();
					double price = Double.parseDouble(reader.nextLine());
					String major = reader.nextLine();
					books[i] = new Tradebook(title, isbn, author, price, major);
					capacity++;
				}

			}
			reader.close();
		} catch(IOException e)
		{
			System.err.println("Error reading from file. Terminating.");
			return;
		}


		Scanner input = new Scanner(System.in);		//reads user input from the command line

		System.out.print("Enter your major: ");
		String userMajor = input.nextLine();
		String course;			//temporarily holds courses as they are read
		String[] userCourses = new String[5];
		int numCourses = 0;			//tracks how many courses entered so far
		while(numCourses < 5)
		{
			System.out.print("Enter course name (xxx to quit): ");
			course = input.nextLine();
			if(course.equals("xxx"))
			{
				break;
			}
			else
			{
				userCourses[numCourses] = course;
				numCourses++;

			}
		}
		System.out.println(userCourses[0]);
		input.close();
		System.out.println("capacity is " + capacity);
		//now we find all of the relevant textbooks
		System.out.println("\nList of textbooks:");
		double totCost = 0.0;												//total cost of all textbooks
		for(int i = 0; i < 50; i++)
		{
			//find textbooks with matching course names
			if(!(books[i] instanceof Textbook))
			{
				if(i == capacity)
				{
					//line is 80 chars long, 6 chars of which is price

					System.out.print(String.format(fmtSum, "Sum of retail book prices:"));
					System.out.println(String.format(fmtPrice, currency.format(totCost)));

				}
				continue;
			}
			Textbook tmp = (Textbook)books[i];//declare tmp to be a textbook variable and deal with abstract class weirdness
			for(int j = 0; j < numCourses; j++)
			{
				if(userCourses[j].equals((tmp.getCourse())))
				{
					if(i < capacity)
					{
						//we don't want to print the summary line
						totCost += tmp.retailPrice();
						printBook(tmp, -1);
					}
					else
					{
						totCost += tmp.retailPrice();
						printBook(tmp, totCost);
					}
				}
			}
		}

		System.out.println("\nList of tradebooks:");
		totCost = 0.0;
		for(int i = 0; i < 50; i++)
		{
			if(books[i] instanceof Tradebook)
			{
				//find tradebooks with matching majors
				Tradebook tmp = (Tradebook)books[i];
				if(userMajor.equals(tmp.getMajor()))
				{
					if(i < capacity)
					{
						//we don't want to print the summary line
						totCost += tmp.retailPrice();
						printBook(tmp, -1);
					}
					else
					{
						totCost += tmp.retailPrice();
						printBook(tmp, totCost);
					}
				}
			}
			else
			{
				//we don't care right now. we just want to print textbooks
				continue;
			}
		}
	}
}
