//By Noah Ransom
//reads from a file and works with other classes


class A10NoahRansom
{
	public static void main(String[] args)]
	{
		String fileName = "books.txt";
		Book[] books = new Book[50];

		//read data from the file and put it into the array

		Scanner reader  = new Scanner(new FileReader(fileName)); //reads from file

		String line;			//holds line currently being parsed
		for(int i = 0; i < 50; i++)
		{
			//My assumption is that you meant 50 books, rather than 50 lines
			try
			{
				line = (reader.hasNextLine()) ? reader.nextLine() : "&&EOF&&";
				if(line.equals("&&EOF&&"))
				{
					//stop trying to read the file. it's empty
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
				}
				else
				{
					//must be tradebook
					Tradebook tmp = new Tradebook();
					tmp.setTitle(reader.nextLine());
					tmp.setAuthor(reader.nextLine());
					tmp.setIsbn(reader.nextLine());
					tmp.setPrice(Double.parseDouble(reader.nextLine()));
					tmp.setMajor(reader.nextLine());
				}
			} catch(IOException e)
			{
				System.err.println("Error reading from file");
				break;
			}
		}
		reader.close();

		Scanner input = new Scanner(System.in);		//reads user input from the command line

		System.out.print("Enter your major: ");
		String userMajor = input.nextLine();
		String course;			//temporarily holds courses as they are read
		String[] userCourses = new String[5];
		int numCourses = 0;			//tracks how many courses entered so far
		while(numBooks < 5)
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
		for(int i = 0; i < 50; i++)
		{
			
		}

	}
}
