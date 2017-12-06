
class Tradebook extends Book
{
	private String major;			//associated major

	public Tradebook()
	{
		//default contstructor
		super();
		this.major = new String();
	}

	public Tradebook(String title, String isbn, String author, double price, String major)
	{
		//parameterized constructor
		super(title, isbn, author, price);
		this.major = major;
	}

	public String getMajor()
	{
		//POST: return the major
		return major;
	}

	public void setMajor(String s)
	{
		//POST: set major to contents of s
		this.major = s;
	}

	public double retailPrice()
	{
		//POST: return wholesale price plus 15% markup
		return (this.getPrice() + this.getPrice() * 0.15);
	}
}
