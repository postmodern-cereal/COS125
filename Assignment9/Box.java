//By Noah Ransom
//Holds all of the box stuff

class Box
{
	private int length;
	private int width;
	private int height;

	public Box()
	{
		//default constructor
		//POST: initialize all values to 1
		length = 1;
		width = 1;
		height = 1;
	}

	public Box(int l, int w, int h)
	{
		//POST: initialize with length = l, width = w, height = h
		length = l;
		width = w;
		height = h;
	}

	public int getLength()
	{
		//POST: returns the value of the length attribute
		return this.length;
	}

	public int getWidth()
	{
		//POST: returns the value of the width attribute
		return this.width;
	}

	public int getHeight()
	{
		//POST: returns the value of the height attribute
		return this.height;
	}

	public void setLength(int len)
	{
		//POST: sets the value of the length attribute to len
		this.length = len;
	}

	public void setWidth(int wid)
	{
		//POST: sets the value of the width attribute to wid
		this.width = wid;
	}

	public void setHeight(int high)
	{
		//POST: sets the value of the height attribute to high
		this.height = high;
	}

	public int area()
	{
		//POST: return the total surface area of the box in in^2
		return (2*(this.width * this.height) + 2*(this.length*this.width) + 2*(this.length * this.height));
	}

	public int volume()
	{
		//POST: return volume in in^3
		return this.width * this.length * this.height;
	}

	public int numFitInside(Box inner)
	{
		//POST: return the number of instances of Box other that will fit in this box object
		//Dividing the volumes of the two objects only shows how many times the smaller volume will fit in the larger,
		//regardelss of whether that smaller volume would need to be broken into several pieces


		//As an example, consider a globe size of 8 inches, and a shipping box that is 8*12*16 inches
		//If you simply divide the volume of the shipping box by the volume of the globe box, you get 3
		//but the problem with that is that the third box does not fit inside the box unless it is split into two
		//4*4*8 boxes, which obviously is not allowed

		return this.getLength()/inner.getLength() * this.getWidth()/inner.getWidth() * this.getHeight()/other.getHeight();
	}

	public int extraSpacePerBox(Box inner)
	{
		//PRE: inner's dimensions exist such that it fits inside this
		//POST: find how many globes fit in the box, and then find out how much empty space is in ever "full" box
		int exSpace = 0;				//remaining volume in in^3
		int numFit = this.numFitInside(inner);			//how many inner boxes fit in outer box
		int filledVolume = numFit*inner.volume();		//how much volume is filled
		return this.volume() - filledVolume;
	}

	public int boxesNeeded(int numUnits, Box inner)
	{
		//POST: return the number of boxes needed to ship numUnits snowglobes
		//First, we find how many full boxes we will need by following these steps:
		/*
		*	1. Find how many inner boxes fit inside this one
		*	2. Divide numUnits by the above result, casting to a double
		*	3. Round the answer up using Math.ceil, because we can only have whole numbers of boxes
		*	4. Return that result
		*/
		int numFit = this.numFitInside(inner);			//how many boxes fit
		double toReturn = (double)numUnits/numFit;
		toReturn = Math.ceil(toReturn);
		return (int)toReturn;
	}

	private int minBoxesNeeded(int numUnits, Box inner)
	{
		//POST: return number of boxes needed to ship numUnits inner boxes, rouned down
		//Having a rounded down version is useful for calculating packaging costs, because it lets us determine if the
		//final box is full or only partially full

		int numFit = this.numFitInside(inner);			//how many boxes fit
		int toReturn = (double)numUnits/numFit;
		return toReturn;
	}

	private double wrapCost(double cost)
	{
		//POST: return the cost in dollars to wrap this box in plastic
		return this.area()*cost;
	}

	private double packagingCost(int volume, double cost)
	{
		//PRE: volume is the volume to be filled, cost is the cost of the packaging material
		//POST: return the cost in dollars to fill volume with packaging material
		return volume * cost;
	}

	public double totalWrapCost(int numUnits, Box inner, double cost)
	{
		//PRE: inner fits inside this
		//POST: return total cost of wrapping enough boxes to ship numUnits units
		int numBoxes = this.boxesNeeded(numUnits, inner);
		return numBoxes * this.wrapCost(cost);
	}

	public double totalPackagingCost(int numUnits, Box inner, double cost)
	{
		//POST: return total packaging cost in dollars
		/*
		*	1. Find how many inner boxes each box can fit
		*	2. Find cost to fill each fully packed box with packaging material
		*	3. Find cost to fill final partially full box with packaging material
		*/
		//Sometimes, each shipping box won't be completely full, even though it has as many inner boxes inside of it as possible
		//This function accounts for that
		double materialCost = 0.0;												//packaging cost in dollars
		int needed = this.minBoxesNeeded(numUnits, inner);
		int leftOver = numUnits - needed*this.numFitInside();					//number of inner boxes left over after all other boxes are totally filled
																				//for example, if you needed to ship 51 units, and each box held 5 units, then
																				//you would need 11 boxes total, one of which would have 1 unit inside.
																				//in this case, leftOver would equal 1
		if(leftOver != 0)
		{
			int emptyVolume = this.volume() - (leftOver * inner.volume());
			materialCost += emptyVolume * cost;									//add cost to fill space
		}
		//Account for any empty space in "full" boxes
		materialCost += this.extraSpacePerBox() * needed * cost;
		return materialCost;
	}
}
