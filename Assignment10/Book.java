public abstract class Book
{
	private String title;
	private String isbn;
	private String author;
	private double price;		//wholesale price

	public Book()
	{
		title = new String();
		isbn = new String();
		author = new String();
		price = new double();
	}

	public Book(String title, String isbn, String author, double price)
	{
		//Parameterized constructor
		this.title = title;
		this.isbn = isbn;
		this.author = author;
		this.price = price;
	}

	public String getTitle()
	{
		//POST: return the title
		return title;
	}

	public String getIsbn()
	{
		//POST: return the ISBN
		return isbn;
	}

	public String getAuthor()
	{
		//POST: return the author
		return author;
	}

	public double getPrice()
	{
		//POST: return the price
		return price;
	}

	public void setTitle(String t)
	{
		//POST: set title field to t
		this.title = t;
	}

	public void setIsbn(String t)
	{
		//POST: set isbn field to t
		this.isbn = t;
	}

	public void setAuthor(String t)
	{
		//POST: set author field to t
		this.author = t;
	}

	public void setPrice(double d)
	{
		//POST: set price to d
		this.price = d;
	}

	public abstract double retailPrice();
}
