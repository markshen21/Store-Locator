import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Project2 {

    public static void main(String[] args) {
        ArrayList<Store> starbucksList = new ArrayList<>();
        ArrayList<Store> whataburgerList = new ArrayList<>();
        ArrayList<double[]> queryList = new ArrayList<>();

        readStoreData("C:\\\\Users\\\\marky\\\\Downloads\\\\Project2\\\\StarbucksData.csv", starbucksList);
        readStoreData("C:\\\\Users\\\\marky\\\\Downloads\\\\Project2\\\\WhataburgerData.csv", whataburgerList);
        readQueryData("C:\\\\Users\\\\marky\\\\Downloads\\\\Project2\\\\Queries.csv", queryList);

        // Compute and print Starbucks results
        for (double[] query : queryList) {
            computeDistances(starbucksList, query[0], query[1]);
            sortStoresByDistance(starbucksList);
            System.out.printf("The %d closest Starbucks stores to (%f, %f):%n", (int) query[2], query[0], query[1]);
            printTopNStores(starbucksList, (int) query[2]);
            System.out.println();
        }

        // Compute and print Whataburger results
        for (double[] query : queryList) {
            computeDistances(whataburgerList, query[0], query[1]);
            sortStoresByDistance(whataburgerList);
            System.out.printf("The %d closest Whataburger stores to (%f, %f):%n", (int) query[2], query[0], query[1]);
            printTopNStores(whataburgerList, (int) query[2]);
            System.out.println();
        }
    }

    public static void readStoreData(String filename, ArrayList<Store> storeList) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // Read header line and ignore it
            while ((line = br.readLine()) != null) {
                String[] storeData = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (storeData.length == 7) {
                    Store store = new Store(storeData[0], storeData[1], storeData[2], storeData[3], storeData[4],
                            Double.parseDouble(storeData[5]), Double.parseDouble(storeData[6]));
                    storeList.add(store);
                } else {
                    System.out.println("Skipping line with an unexpected number of columns: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readQueryData(String filename, ArrayList<double[]> queryList) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // Read header line and ignore it
            while ((line = br.readLine()) != null) {
                String[] queryData = line.split(",");
                double[] query = { Double.parseDouble(queryData[0]), Double.parseDouble(queryData[1]),
                        Double.parseDouble(queryData[2]) };
                queryList.add(query);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void computeDistances(ArrayList<Store> storeList, double lat, double lon) {
        for (Store store : storeList) {
            store.computeDistance(lat, lon);
        }
    }

    public static void sortStoresByDistance(ArrayList<Store> storeList) {
        Collections.sort(storeList, new Comparator<Store>() {
            @Override
            public int compare(Store s1, Store s2) {
                return Double.compare(s1.distance, s2.distance);
            }
        });
    }
    public static void printTopNStores(ArrayList<Store> storeList, int n) {
        for (int i = 0; i < n && i < storeList.size(); i++) {
            Store store = storeList.get(i);
            System.out.printf("Store #%s. %s, %s, %s, %s. - %.2f miles.%n", store.id, store.address, store.city, store.state, store.zipCode, store.distance);
        }
    }
}