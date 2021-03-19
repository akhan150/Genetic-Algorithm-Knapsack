//Adil Khan
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GeneticKnapsack {

    private LinkedHashMap<Item, Integer> hm = null;

    public static void main(String[] args) {
        //Algorithm on file 1 with capacity 9 and number of iterations 10
        GeneticAlgoOnInput(1, 9, 10);
        //Algorithm on file 1 with capacity 9 and number of iterations 10
        GeneticAlgoOnInput(2, 11, 10);
        //Algorithm on file 1 with capacity 9 and number of iterations 10
        GeneticAlgoOnInput(3, 13, 10);

    }

    public static void GeneticAlgoOnInput(int file_nubmer, int capacity, int iterations) {
        Data d = new Data();
        LinkedHashMap<Integer, Item> data = d.GetInputValue(3);
        Genetic g = new Genetic(data, capacity);
        g.generate_population();
        ArrayList<ArrayList<Item>> combs = new ArrayList<ArrayList<Item>>();
        for (int i = 0; i < iterations; i++) {
            System.out.println("=====================  Iteration = " + i + "=======================");
            g.fitness();
            g.selection();
            g.crossover();
            combs.add(g.mutation());
        }

        int big_index = 0;
        int max = 0;
        boolean flag = false;
        for (int i = 0; i < combs.size(); i++) {
            int sum = Item.CalculateValueSum(combs.get(i));
            if (sum <= capacity && sum > max) {
                max = sum;
                flag = true;
                big_index = i;
            }
        }
        if (flag) {
            System.out.println(combs.get(big_index) + " with sum of " + Item.CalculateValueSum(combs.get(big_index)));
            d.writeOutput(file_nubmer, iterations, combs.get(big_index), Item.CalculateValueSum(combs.get(big_index)), capacity);
        } else {
            System.out.println("No");
        }
    }

}
