//By Noah Ransom
import java.util.*;
import java.text.NumberFormat;

class A9NoahRansom
{
	public static boolean multOfFour(int num)
	{
		//POST: return true iff num is a non-zero multiple of 4
		return(num % 4 == 0 && num != 0);
	}

	public static void main(String[] args)
	{
		final double MATERIAL_COST = 0.0023;		//Packaging material cost in dollars per in^3
		final double WRAP_COST = 0.0012;			//Plastic wrap cost in dollars per in^2

		//gather user input
		Scanner scan = new Scanner(System.in);		//gets input
		//l, w, h all multiples of 4
		int length = 0;
		int width = 0;
		int height = 0;
		while(true)
		{
			//when all the numbers are valid, we'll just use the break command
			System.out.print("Enter length, width, height of ship box: ");
			length = scan.nextInt();
			width = scan.nextInt();
			height = scan.nextInt();
			System.out.println();
			if(multOfFour(length) && multOfFour(width) && multOfFour(height))
			{
				break;
			}

		}

		double boxCost = 0.0;					//cost of box in dollars
		while(boxCost <= 0)
		{
			System.out.print("Enter box cost: ");
			boxCost = scan.nextDouble();
			System.out.println();
		}

		//the snow globe cannot be larger than the smallest dimension of the shipping box, or else it will not fit
		//according to the assignment, a 4 inch cube fits into a 4 16 by 12 by 4 inch box. This really doesn't make sense, because it means that the globe box and the shipping box have the exact same dimensions. I'm assuming that this was intentional, so I have programmed it this way, even though it doesn't really make sense.
		int smallestDimension = Math.min(Math.min(length, width), Math.min(width, height));
		int globeSize = smallestDimension + 1;		//globe size in inches. set to 1 more than largest possible value to make the loop condition work
		while(globesize > smallestDimension || !multOfFour(globeSize))
		{
			System.out.print("Enter dimension of snow globe: ");
			globeSize = scan.nextInt();
			System.out.println();
		}

		//now we have all of the data
		int toShip = -1;				//number of boxes to ship
		Box globeBox = new Box(globeSize);										//snowglobe box object
		Box shipBox = new Box(length, width, height);							//shipping box object

		NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);				//formats currency based US dollar

		while(toShip != 0)
		{
			//keep processing requests until the user quits
			do
			{
				System.out.print("Enter a number of snow globes to ship (0 to quit): ");
				toShip = scan.nextInt();
			} while (toShip < 0);
			if(toShip == 0)
			{
				break;
			}
			//Not everything below is using methods from the box class, but at a certain point, it's bad practice to include highly specific methods in a class
			//For this reason, I do printing, formatting, and the addition for the total cost outside of the box class
			String globeTitle = "Number of snow globes: " + Integer.toString(toShip);
			//23
			String lineSeperator = "-----------------------";
			System.out.print(globeTitle + "\n" + lineSeperator);
			//match number of dashes to length of line above
			for(int i = lineSeperator.length(); i < globeTitle.length(); i++)
			{
				System.out.print("-");
			}

			//Print number of boxes needed
			double matCost = shipBox.totalPackagingCost(toShip, globeBox, MATERIAL_COST);				//total packing material cost in dollars
			double plasticCost = shipbox.totalWrapCost(toship, globeBox, WRAP_COST);					//total cost of plastic wrap
			double totBoxCost = boxCost * shipBox.boxesNeeded(toShip, globeBox);						//total cost of the boxes

			System.out.println("\nNumber of shipping boxes needed: " + shipBox.boxesNeeded(toShip, globeBox));
			System.out.println("Cost of box: " + currentcy.format(boxCost));
			System.out.println("Cost of packing material: " + currency.format(matCost));
			System.out.println("Cost of plastic wrap: " + currency.format(plasticCost));
			System.out.println("Total cost: " + currency.format(matCost + plasticCost + totBoxCost));
			System.out.println();



			//reset toship to -1 before restarting the loop to make the input processing work
			toShip = -1;

		}
		scan.close();
	}
}
