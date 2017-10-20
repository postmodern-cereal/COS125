//By Noah Ransom
//Reads studen IDs and number grades from a specified file, then prints letter grades and analysis

import java.io.*;
import java.util.Scanner;
import java.text.DecimalFormat;

class A6NoahRansom
{
    public static int numRecords(String fileName) throws IOException
    {
        //POST: return how many records are in the file
        Scanner reader = new Scanner(new FileReader(fileName));
        int toReturn = reader.nextInt();
        reader.close();
        return toReturn;
    }


    public static void populate(String[] ids, int[] midGrades, int[] finGrades, String fileName) throws IOException
    {
                                                                //POST: populate the arrays with data from the file
        Scanner reader = new Scanner(new FileReader(fileName)); //Reads the file
        reader.nextLine();                                      //Skip the irrelevant first line telling us how many records there are
        String currLine;                                        //Holds current line
        Scanner parser;                                         //lets us parse the data for the current line
        for(int i = 0; i < ids.length; i ++)
        {
            currLine = reader.nextLine();
            parser = new Scanner(currLine);
            ids[i] = parser.next();
            midGrades[i] = parser.nextInt();
            finGrades[i] = parser.nextInt();
            parser.close();
        }
        reader.close();
    }

    public static double mean(int[] data)
    {
                                                //POST: return the arithmetic mean of the elements in data, or 0 if no elements exist
        double sum = 0;                         //holds the sum of the elements
        for(int i = 0; i < data.length; i++)
        {
            sum += data[i];
        }
        if(sum == 0)                            //If sum is still 0, then data must have been empty
        {
            return 0;
        }
        return sum/data.length;                 //perform the division operation and return
    }

    public static double standardDeviation(int[] data, double mean)
    {
        //POST: return the standard deviation according to the formula posted online
        double sum = 0;
        for(int i = 0; i < data.length; i++)
        {
            sum += Math.pow(((double)data[i] - mean), 2);           //add square of datapoint minus mean
        }
        return Math.sqrt(sum/(data.length - 1));                    //divide by n-1, take square root, and return the value
    }

    public static char letterGrade(int grade, double mean, double stdDev)
    {
        //POST: returns a letter grade based on the formula for grading on a curve
        //The if statements are shortened due to their structure. Instead of including both sides of the boundary,
        //I cover the non-inclusive upper end in the statement before the current one. This means that each conditional
        //only needs to check whether the grade is above the next lowest cutoff.
        if(grade >= (mean + 1.5 * stdDev))
        {
            return 'A';
        }
        else if(mean + 0.5 * stdDev <= grade)
        {
            return 'B';
        }
        else if(mean - 0.5 * stdDev <= grade)
        {
            return 'C';
        }
        else if(mean - 1.5 * stdDev <= grade)
        {
            return 'D';
        }
        else
        {
            return 'E';
        }
    }

    public static void showHistogram(int[] counts)
    {
        //POST: Prints the quantities of each letter grade as a histogram
        char letter;                //holds the current letter
        for(int i = 0; i < 5; i ++)    //even if the array has more than 5 elements, there are only 5 letter grades to give
        {
            letter = (char)(i + 65);  //turns i into a capital letter
            System.out.print(letter + "\t");    //print out the letter and a blank space
            for(int j = 0; j < counts[i]; j++)  //print as many asterisks as needed
            {
                System.out.print("*");
            }
            System.out.println();   //go to next line to do next grade
        }
    }

    public static void main(String[] args)
    {
        DecimalFormat fmt = new DecimalFormat("#.#");
        Scanner scan = new Scanner(System.in);
        String fileName = "";
        int records = -1;
        int[] midCounts = new int[5];           //holds a count of each letter grade for midterm exam
        int[] finCounts = new int[5];           //holds a count of each letter grade for final exam
        String[] ids;                           //student ids
        int[] midGrades;                        //midterm number grades
        int[] finGrades;                        //final number grades
        double meanMT = 0;                      //midterm mean
        double stdDevMT = 0;                    //midterm standard deviation
        double meanFin = 0;                     //final mean
        double stdDevFin = 0;                   //final standard deviation

        System.out.println("Grade roster by Noah Ransom\n");

        //The loop below ensures that we get a valid file name
        //if an invalid file name is entered, we start over
        while(records < 0)
        {
            System.out.print("Enter name of file: ");
            fileName = scan.next();
            try
            {
                records = numRecords(fileName);
                continue;
            }catch(IOException e)
            {
                System.err.println("File not found");
            }
        }
        scan.close();
        System.out.println();

        ids = new String[records];
        midGrades = new int[records];
        finGrades = new int[records];
        //now read the data from the file and put it into the arrays
        try
        {
            populate(ids, midGrades, finGrades, fileName);
        }catch(IOException e){}

        meanMT = mean(midGrades);
        stdDevMT = standardDeviation(midGrades, meanMT);

        meanFin = mean(finGrades);
        stdDevFin = standardDeviation(finGrades, meanFin);

        int buffer = 3;                 //buffer space of 3 characters
                                        //Formatting widths are based on length of column title
        int studWid = 7 + buffer;
        int mtWid = 7 + buffer;
        int gradeWid = 5 + buffer;      //same width for both midterm and final, because both have same title
        int finWid = 5 + buffer;
        int totWid = studWid + mtWid + finWid + gradeWid + gradeWid - buffer;    //holds total width of output. used for dashes. gradewid appears twice because there are two grade columns
        //I subtract the buffer width from the above to account for the fact that there is no buffer needed after the last column


        String fmtStud = "%-" + studWid + "s";              //The format strings are designed to print the actual data entries
        String fmtMT = "%-" + mtWid + "d";                  //I manually created the strings for the column titles because this was easier than
        String fmtGrade = "%-" + gradeWid + "c";            //making two seperate sets of format strings
        String fmtFin = "%-" + finWid + "d";


        //Print titles
        System.out.print(String.format(fmtStud, "Student"));
        System.out.print(String.format(("%-" + mtWid + "s"), "Midterm"));
        System.out.print(String.format(("%-" + gradeWid + "s"), "Grade"));
        System.out.print(String.format(("%-" + finWid + "s"), "Final"));
        System.out.print(String.format(("%-" + gradeWid + "s"), "Grade"));
        System.out.println();
        //Print dashes
        for(int i = 0; i < totWid; i++)
        {
            System.out.print("-");
        }
        System.out.println();


        //print and format the student records
        char midLetter;        //temporary variable to store letter grade for miterm
        char finLetter;          //temproary variable to store letter grade for final
        for(int i = 0; i < records; i++)
        {
            midLetter = letterGrade(midGrades[i], meanMT, stdDevMT);
            finLetter = letterGrade(finGrades[i], meanFin, stdDevFin);
            //Increment letter grade counts
            midCounts[(int)(midLetter - 65)]++;
            finCounts[(int)(finLetter - 65)]++;
            System.out.print(String.format(fmtStud, ids[i]));
            System.out.print(String.format(fmtMT, midGrades[i]));
            System.out.print(String.format(fmtGrade, midLetter));
            System.out.print(String.format(fmtFin, finGrades[i]));
            System.out.print(String.format(fmtGrade, finLetter));
            System.out.println();

        }
        System.out.println();

        //print midterm analysis
        System.out.println("Midterm Analysis");
        System.out.printf("%-10s", "Mean:");          //I'm using a field width of 10 because the std dev: line is 8 characters long plus 2 characters of buffer
        System.out.printf("%-3s\n", fmt.format(meanMT));
        System.out.printf("%-10s", "Std Dev:");
        System.out.printf("%-3s\n", fmt.format(stdDevMT));
        showHistogram(midCounts);
        System.out.println();

        //print final analysis
        System.out.println("Final Analysis");
        System.out.printf("%-10s", "Mean:");          //I'm using a field width of 10 because the std dev: line is 8 characters long plus 2 characters of buffer
        System.out.printf("%-3s\n", fmt.format(meanFin));
        System.out.printf("%-10s", "Std Dev:");
        System.out.printf("%-3s\n", fmt.format(stdDevFin));
        showHistogram(finCounts);
    }
}
