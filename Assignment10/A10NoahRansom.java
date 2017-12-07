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
		final int LINEWIDTH = 50;
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
		final int LINEWIDTH = 50;
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
					Textbook tmp = new Textbook();
					tmp.setTitle(reader.nextLine());
					tmp.setAuthor(reader.nextLine());
					tmp.setIsbn(reader.nextLine());
					tmp.setPrice(Double.parseDouble(reader.nextLine()));
					tmp.setCourse(reader.nextLine());
					books[i] = tmp;
					capacity++;
				}
				else if(line.equals("Tradebook"))
				{
					//must be tradebook
					Tradebook tmp = new Tradebook();
					tmp.setTitle(reader.nextLine());
					tmp.setAuthor(reader.nextLine());
					tmp.setIsbn(reader.nextLine());
					tmp.setPrice(Double.parseDouble(reader.nextLine()));
					tmp.setMajor(reader.nextLine());
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
				numCourses++;
				userCourses[numCourses] = course;

			}
		}
		input.close();

		//now we find all of the relevant textbooks
		System.out.println("\nList of textbooks:");
		double totCost = 0.0;												//total cost of all textbooks
		for(int i = 0; i < 50; i++)
		{
			//find textbooks with matching course names
			for(String c: userCourses)
			{
				Textbook tmp = (Textbook)books[i];								//declare tmp to be a textbook variable and deal with abstract class weirdness
				System.out.println(i);
				System.out.println(tmp.getTitle());
				if(c.equals((tmp.getCourse())))
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
