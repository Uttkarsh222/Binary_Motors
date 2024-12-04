package car_rental_book_and_manage.Utility;


import car_rental_book_and_manage.Objects.Client;
import javafx.collections.ObservableList;

public class QuickSort
{
    // Function to swap elements in an ObservableList
    static <Client extends Comparable<car_rental_book_and_manage.Objects.Client>> void swap(ObservableList<car_rental_book_and_manage.Objects.Client> list, int i, int j) {
        car_rental_book_and_manage.Objects.Client temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    // Function to sort an ObservableList using the QuickSort algorithm
    public static <T extends Comparable<T>> void quickSort(ObservableList<Client> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    // Function to partition the ObservableList
    static <T extends Comparable<T>> int partition(ObservableList<Client> list, int low, int high) {
        Client pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j).getUsername().compareTo(pivot.getUsername()) < 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }
}