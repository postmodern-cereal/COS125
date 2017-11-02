//By Noah Ransom
//Creates a tree farm from user input and simulates the result of a fire in the farm

import java.util.*;



class A8NoahRansom
{

	public static void showForest(char[][] forest)
	{
		//POST: print the forest
		for(int i = 0; i < forest.length; i++)
		{
			for(char c: forest[i])
			{
				System.out.print(c + " ");
			}
			System.out.println();
		}
	}

	public static int fillForest(char[][] forest, double density)
	{
		//POST: randomly populte the array with trees and return the number of trees
		Random rand = new Random();							//seed the random number generator
		int numTrees = 0;									//total number of trees
		int densityCutoff = (int)Math.floor(density*100); 	//rounded density as percentage rather than decimal
		for(int i = 0; i < forest.length; i++)
		{
			for(int j = 0; j < forest[i].length; j++)
			{
				int num = rand.nextInt(100);				//number between 0 and 99 inclusive
															//we're basically rolling 100 sided die, and if the roll is below the density value, we make a tree there
				if(num < densityCutoff)
				{
					forest[i][j] = 'T';
					numTrees++;
				}
				else
				{
					forest[i][j] = '.';
				}
			}
		}
		//now return the number of trees created
		return numTrees;
	}

	public static int burn(char[][] forest)
	{
		//POST: burn the forest and return how many trees were burned
		int treesBurned = 0;											//number of trees burned
		for(int i = 0; i < forest.length; i++)
		{
			if(forest[0][i] == 'T')
			{
				forest[0][i] = 'B';
				treesBurned++;
			}
		}
		for(int i = 0; i < forest.length - 1; i++)			//don't go all the way to the end because there are no trees to burn below the final row
		{
			for(int j = 0; j < forest[i].length; j++)
			{
				if(forest[i][j] == 'B')
				{
					if(forest[i + 1][j] == 'T')
					{
						forest[i + 1][j] = 'B';
						treesBurned++;
					}
					if(j < forest.length - 1 && forest[i + 1][j + 1] == 'T')					//avoids out of bounds error
					{
						forest[i + 1][j + 1] = 'B';
						treesBurned++;
					}
					if(j > 0 && forest[i + 1][j - 1] == 'T')
					{
						forest[i + 1][j - 1] = 'B';
						treesBurned++;
					}
				}

			}
		}
		return treesBurned;
	}

	public static boolean burnedThrough(char[][] forest)
	{
		//POST: return true iff the fire burned all the way through the forest
		for(char c: forest[forest.length - 1])
		{
			if(c == 'B')
			{
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		int width;
		double density;
		while(true)
		{
			System.out.print("Enter a number of trees: ");
			width = scan.nextInt();
			if(width >= 10 && width <= 30)
			{
				break;
			}
		}
		while(true)
		{
			System.out.print("Enter density: ");
			density = scan.nextDouble();
			if(density >= .2 && density <= .8)
			{
				break;
			}
		}
		scan.close();

		char[][] forest = new char[width][width];				//holds the characters for the forest
		int numTrees = fillForest(forest, density);				//fill the forest and get the number of trees
		System.out.println("The original forest:");
		double percentPop = (double)numTrees/(Math.pow(width, 2));	//calculate percent population
		showForest(forest);
		System.out.println("Percent populated: " + percentPop + "\n");


		int numBurned = burn(forest);							//simulate the fire and store how many were burned
		System.out.println("The final forest:");
		showForest(forest);
		//My assumption is that this should be a percentage of the total number of trees, rather than the total number of spaces
		double percentBurn = (double)numBurned/numTrees;

		System.out.println("Percent burned: " + percentBurn);
		if(burnedThrough(forest))
		{
			System.out.println("Fire burned through.\n");
		}
		else
		{
			System.out.println("Fire burned out.\n");
		}


	}
}
