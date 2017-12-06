
class Textbook extends Book
{
	private String course;		//course number

	public Textbook()
	{
		super();
		this.course = new String();
	}

	public TextBook(String title, String isbn, String author, double price, String course)
	{
		//POST: initialize object with all fields filled
		super(title, isbn, author, price);
		this.course = course;
	}

	public String getCourse()
	{
		//POST: return the course number
		return course;
	}

	public void setCourse(String s)
	{
		//POST: set course number to s
		this.course = s;
	}

	public double retailPrice()
	{
		//POST: return the wholesale price plus 10%
		return (this.getPrice() + this.getPrice() * 0.10);

	}
}
