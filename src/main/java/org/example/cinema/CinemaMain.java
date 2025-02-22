package org.example.cinema;

import org.example.cinema.infrasturcture.core.impl.ApplicationContext;

import java.util.HashMap;
import java.util.Random;

public class CinemaMain {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext("org.example.cinema", new HashMap<>());
        FilmsCollection collection = context.getObject(FilmsCollection.class);
        collection.display();
    }

    static class Helper {
        public static void printFilms(Film[] films) {
            for (Film film: films) {
                System.out.println(film);
            }
        }

        private static Film[] sortFilms(Film[] films) {
            return recursiveQuickSort(films, 0, films.length);
        }

        private static Film[] recursiveQuickSort(Film[] films, int left, int right) {
            if (right <= left) {
                return films;
            }
            int a = new Random().nextInt(right - left) + left;
            swap(films, left, a);

            int i = left + 1;
            Film pivot = films[left];
            for (int j = i; j < right; j++) {
                if (pivot.compareTo(films[j]) > 0) {
                    swap(films, i, j);
                    i++;
                }
            }
            swap(films, left, i - 1);

            int middleSectionEnd = i;
            for (int j = i; j < right; j++) {
                if (pivot.compareTo(films[j]) == 0) {
                    swap(films, middleSectionEnd, j);
                    middleSectionEnd++;
                }
            }

            recursiveQuickSort(films, left, i - 1);
            recursiveQuickSort(films, middleSectionEnd, right);

            return films;
        }

        private static void swap(Film[] films, int first, int second) {
            Film tmp = films[first];
            films[first] = films[second];
            films[second] = tmp;
        }
    }
}
