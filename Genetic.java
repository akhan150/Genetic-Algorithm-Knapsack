//Adil Khan
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Genetic {

    private LinkedHashMap<Integer, Item> hm;
    private LinkedHashMap<Integer, Integer> fitness;

    private int size;
    private int capacity;

    private ArrayList<int[]> population;
    private ArrayList<int[]> selected;
    private ArrayList<int[]> crossed;
    private ArrayList<int[]> mutated;

    public Genetic(LinkedHashMap<Integer, Item> hm, int capacity) {
        this.hm = hm;
        System.out.println("Items with IDs");
        for (Map.Entry<Integer, Item> entry : hm.entrySet()) {
            int id = entry.getKey();
            Item item = entry.getValue();
            System.out.println(id + " = " + item);
        }
        this.size = hm.size();
        this.capacity = capacity;
    }

    //Generates the Random population of 4 chromosomes
    public ArrayList<int[]> generate_population() {
        ArrayList<int[]> chromosomes = new ArrayList<int[]>();
        for (int i = 0; i < 4; i++) {
            int[] chromosome = new int[size];
            for (int j = 0; j < chromosome.length; j++) {
                chromosome[j] = GenerateRandomBetween(0, 1);
            }
            chromosomes.add(chromosome);
        }
        this.population = chromosomes;
        return population;
    }

    //Calculates the fitness value for each chromosimes in the population
    public void fitness() {
        LinkedHashMap<Integer, Integer> fitness = new LinkedHashMap<Integer, Integer>();
        for (int i = 0; i < 4; i++) {
            int[] chromosome = population.get(i);
            int sum = 0;
            for (int j = 0; j < chromosome.length; j++) {
                if (chromosome[j] != 0) {
                    sum = sum + hm.get(j).getValue();
                }
            }
            if (sum <= capacity) {
                fitness.put(i, sum);
            } else {
                fitness.put(i, 0);
            }
        }
        System.out.println("Population");
        prettierDisplay(this.population);
        System.out.println("Fitness Values");
        for (Map.Entry<Integer, Integer> entry : fitness.entrySet()) {
            int id = entry.getKey();
            int fits = entry.getValue();
            System.out.println(id + " = " + fits);
        }
        this.fitness = fitness;
    }

    //Selectes the two highest fitness chromosomes
    public ArrayList<int[]> selection() {
        int[] two_high_fitnes = max2Index();
        System.out.println("Two High Index Values");
        System.out.println(Arrays.toString(two_high_fitnes));
        ArrayList<int[]> select = new ArrayList<int[]>();
        select.add(population.get(two_high_fitnes[0]));
        select.add(population.get(two_high_fitnes[1]));
        this.selected = select;
        System.out.println("Selected High Index Chromosome");
        prettierDisplay(select);
        return this.selected;
    }

    //Crossover the selected chromosomes
    public void crossover() {
        ArrayList<int[]> crossed = new ArrayList<int[]>();
        copyArrayList(this.selected, crossed);
        int size = crossed.get(0).length;
        int no_of_bits_crossed = size / 2;
        System.out.println("No of Bits to be Changes = " + no_of_bits_crossed);
        int random_postion = GenerateRandomBetween(0, size - no_of_bits_crossed);
        System.out.println("Starting Point Random Sequence to be Changed = " + random_postion);

        int j = 0;
        for (int i = 0; i < crossed.get(0).length; i++) {
            if (i >= random_postion) {
                int temp = crossed.get(0)[i];
                int temp2 = crossed.get(1)[i];
                crossed.get(1)[i] = temp;
                crossed.get(0)[i] = temp2;
                j++;
                if (j == no_of_bits_crossed) {
                    break;
                }
            }
        }
        System.out.println("After Croossing");
        this.crossed = crossed;
        prettierDisplay(this.crossed);

        this.crossed = crossed;
    }

    //Mutation of the crossed chromosomes
    public ArrayList<Item> mutation() {
        ArrayList<int[]> mutated = new ArrayList<int[]>();
        copyArrayList(this.crossed, mutated);
        int size = mutated.get(0).length;
        int no_of_mutation = size / 3;
        System.out.println("Number of Mutation = " + no_of_mutation);

        ArrayList<Integer> indexs1 = generateIndexs(size, no_of_mutation);
        ArrayList<Integer> indexs2 = generateIndexs(size, no_of_mutation);

        System.out.println("Indexes for change");
        System.out.println("First Mutation Indexes" + indexs1);
        System.out.println("Second Mutation Indexes" + indexs2);

        for (int i = 0; i < size; i++) {
            if (indexs1.contains(i)) {
                if (mutated.get(0)[i] == 0) {
                    mutated.get(0)[i] = 1;
                } else {
                    mutated.get(0)[i] = 0;
                }
            }
            if (indexs2.contains(i)) {
                if (mutated.get(1)[i] == 0) {
                    mutated.get(1)[i] = 1;
                } else {
                    mutated.get(1)[i] = 0;
                }
            }
        }
        this.mutated = mutated;

        System.out.println("Mutated One");
        prettierDisplay(this.mutated);

        updatePopulation();
        System.out.println("New Selected");
        prettierDisplay(this.population);

        return maxCombination(this.population);
    }

    //Generates the random number between the range
    public static int GenerateRandomBetween(int min, int max) {
        int random = (int) (Math.random() * ((max - min) + 1)) + min;
        return random;
    }

    //Gets the 2 high fitness indexs from the populations
    public int[] max2Index() {
        int[] maxs = new int[2];
        LinkedHashMap<Integer, Integer> new_fit = (LinkedHashMap<Integer, Integer>) this.fitness.clone();
        for (int i = 0; i < 2; i++) {
            Map.Entry<Integer, Integer> maxEntry = null;
            for (Map.Entry<Integer, Integer> entry : new_fit.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            maxs[i] = maxEntry.getKey();
            new_fit.remove(maxEntry.getKey());
        }
        int temp = maxs[0];
        maxs[0] = maxs[1];
        maxs[1] = temp;
        return maxs;
    }

    //Generates the random indexs for mutation
    public ArrayList<Integer> generateIndexs(int size, int no_of_mutation_number) {
        ArrayList<Integer> indexs = new ArrayList<Integer>();
        int i = 0;
        while (true) {
            int random_number = GenerateRandomBetween(0, size - 1);
            if (i == 0) {
                indexs.add(random_number);
                i++;
            } else {
                if (indexs.contains(random_number)) {
                    continue;
                } else {
                    indexs.add(random_number);
                    i++;
                }
            }
            if (i == no_of_mutation_number) {
                break;
            }
        }
        return indexs;
    }

    //Update the population by adding two selected and two mutated chromosmes in the population after clearing the population 
    public void updatePopulation() {
        ArrayList<int[]> population = new ArrayList<int[]>();
        population.add(this.selected.get(0));
        population.add(this.selected.get(1));
        population.add(this.mutated.get(0));
        population.add(this.mutated.get(1));
        this.population = population;
    }

    //Display function for chromosomes
    public void prettierDisplay(ArrayList<int[]> l) {
        for (int i = 0; i < l.size(); i++) {
            System.out.println(Arrays.toString(l.get(i)));
        }
    }

    //Copy Arraylist
    public static void copyArrayList(ArrayList<int[]> a, ArrayList<int[]> b) {
        for (int i = 0; i < a.size(); i++) {
            b.add(a.get(i).clone());
        }
    }

    //Max Fitness Combination
    public ArrayList<Item> maxCombination(ArrayList<int[]> population) {
        LinkedHashMap<Integer, Integer> fitness = new LinkedHashMap<Integer, Integer>();
        for (int i = 0; i < 4; i++) {
            int[] chromosome = population.get(i);
            int sum = 0;
            for (int j = 0; j < chromosome.length; j++) {
                if (chromosome[j] != 0) {
                    sum = sum + hm.get(j).getValue();
                }
            }
            if (sum <= capacity) {
                fitness.put(i, sum);
            } else {
                fitness.put(i, 0);
            }
        }
        Map.Entry<Integer, Integer> maxEntry = null;
        for (Map.Entry<Integer, Integer> entry : fitness.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        ArrayList<Item> comb = new ArrayList<Item>();
        for (int i = 0; i < population.size(); i++) {
            if (i == maxEntry.getKey()) {
                int[] list = population.get(i);
                for (int j = 0; j < list.length; j++) {
                    if (list[j] == 0) {
                        continue;
                    } else {
                        comb.add(hm.get(j));
                    }
                }
            }
        }
        return comb;
    }

}
