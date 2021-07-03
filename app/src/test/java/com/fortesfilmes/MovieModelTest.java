package com.fortesfilmes;

import com.fortesfilmes.model.MovieModel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class MovieModelTest {

    private List<MovieModel> list = null;

    @Before
    public void setup() {

        MovieModel movie;
        list = new ArrayList<>();

        movie = new MovieModel();
        movie.setTitle("Iron Man");
        movie.setRating(2.1f);
        list.add(movie);

        movie = new MovieModel();
        movie.setTitle("The Incredible Hulk");
        movie.setRating(4.7f);
        list.add(movie);

        movie = new MovieModel();
        movie.setTitle("Captain America");
        movie.setRating(1.5f);
        list.add(movie);

        movie = new MovieModel();
        movie.setTitle("Guardians of the Galaxy");
        movie.setRating(5.3f);
        list.add(movie);

        movie = new MovieModel();
        movie.setTitle("Ant-Man");
        movie.setRating(3.2f);
        list.add(movie);

    }

    @Test
    public void sortAscTitle() {
        Collections.sort(list, MovieModel.titleAsc);
        assertEquals("Ant-Man", list.get(0).getTitle());
        assertEquals("Iron Man", list.get(3).getTitle());
    }

    @Test
    public void sortDescTitle() {
        Collections.sort(list, MovieModel.titleDesc);
        assertEquals("The Incredible Hulk", list.get(0).getTitle());
        assertEquals("Captain America", list.get(3).getTitle());
    }

    @Test
    public void sortAscRating() {
        Collections.sort(list, MovieModel.ratingAsc);
        assertEquals(1.5, list.get(0).getRating(), 0.1);
        assertEquals(4.7, list.get(3).getRating(), 0.1);
    }

    @Test
    public void sortDescRating() {
        Collections.sort(list, MovieModel.ratingDesc);
        assertEquals(5.3, list.get(0).getRating(), 0.1);
        assertEquals(2.1, list.get(3).getRating(), 0.1);
    }
}