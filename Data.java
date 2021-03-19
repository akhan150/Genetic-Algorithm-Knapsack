//Adil Khan
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Data {

    /*
    Sets the File Name
     */
    private File file1 = new File("InputFiles\\1.txt"); //Input File 1
    private File file2 = new File("InputFiles\\2.txt"); //Input File 2
    private File file3 = new File("InputFiles\\3.txt"); //Input File 3
    private File file4 = new File("OutputFiles\\1.txt"); //Output File 1
    private File file5 = new File("OutputFiles\\2.txt"); //Output File 2
    private File file6 = new File("OutputFiles\\3.txt"); //Output File 3

    /*
    Gets the Input Files Information and utilizes it
     */
    public LinkedHashMap<Integer, Item> GetInputValue(int filenumber) {
        LinkedHashMap<Integer, Item> hm = new LinkedHashMap<Integer, Item>();
        ArrayList<Item> weights_and_values = new ArrayList<>();
        File file = new File("");
        //Sets the input file to its respected designated slot, input file 1 = file1, input file 2 = file2, input file 3 = file3
        if (filenumber == 1) {
            file = file1;
        } else if (filenumber == 2) {
            file = file2;
        } else if (filenumber == 3) {
            file = file3;
        }
        Scanner reader = null;
        try {
            reader = new Scanner(file);
            int j = 0;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String refined = line.substring(1, line.length() - 1);
                String[] pair = refined.split(",");
                Item i = new Item(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
                weights_and_values.add(i);
                hm.put(j, i);
                j++;
            }
        } catch (Exception e) {
            System.out.println("Exception " + e.toString() + " Occured");
        }
        return hm;
    }

    /*
    Output File Structure
    */
    public void writeOutput(int filenumber, int iterations, ArrayList<Item> comb, int sum, int c) {
        File file = new File("");
        //Sets the output file to its respected designated slot, output file 1 = file4, output file 2 = file5, output file 3 = file6
        if (filenumber == 1) {
            file = file4;
        } else if (filenumber == 2) {
            file = file5;
        } else if (filenumber == 3) {
            file = file6;
        }
        PrintWriter pw = null;
        //Prints the following lines to each of the respected output files
        try { 
            pw = new PrintWriter(new FileWriter(file));
            pw.println("Ouput for File Input " + filenumber); 
            pw.println("Used Genetic Algorithm to Solve the Knapsack");
            pw.println("Capcity = " + c);
            pw.println("Number of Iterations = " + iterations);
            pw.println("Best Combination From Genetic Algo is " + comb);
            pw.println("Total Value " + sum);
            pw.close();
        } catch (IOException e) {
        }
    }

}
