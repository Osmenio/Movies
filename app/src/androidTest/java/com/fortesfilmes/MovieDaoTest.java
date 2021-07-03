package com.fortesfilmes;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.fortesfilmes.dao.MovieDao;
import com.fortesfilmes.model.MovieModel;
import com.fortesfilmes.service.RoomDB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MovieDaoTest {

    private RoomDB roomDB;
    private MovieDao movieDao;
    private MovieModel movie1;
    private MovieModel movie2;
    private MovieModel movie3;
    private MovieModel movie4;
    private MovieModel movie5;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        roomDB = Room.inMemoryDatabaseBuilder(context, RoomDB.class).build();
        movieDao = roomDB.movieDao();
    }

    @After
    public void closeDb() {
        roomDB.close();
    }

    @Test
    public void insertAndFindAllTest() {

        movie1 = new MovieModel();
        movie1.setTitle("Iron Man");
        movie1.setRating(2.1f);

        // Insert data into db
        try {
            movieDao.persist(movie1);
        } catch (Exception e) {
            assertTrue(false);
        }
        //
        List<MovieModel> list = movieDao.findAll();

        assertEquals(1, list.size());
        assertEquals("Iron Man", list.get(0).getTitle());
    }

    @Test
    public void findByTitleTest() {

        movie1 = new MovieModel();
        movie1.setTitle("The Incredible Hulk");
        movie1.setRating(4.7f);

        movie2 = new MovieModel();
        movie2.setTitle("Captain America");
        movie2.setRating(1.5f);

        movie3 = new MovieModel();
        movie3.setTitle("Guardians of the Galaxy");
        movie3.setRating(5.3f);

        // Insert data into db
        try {
            movieDao.persist(movie1);
            movieDao.persist(movie2);
            movieDao.persist(movie3);
        } catch (Exception e) {
            assertTrue(false);
        }

        //
        MovieModel result = movieDao.findByTitle(movie2.getTitle());

        assertEquals(movie2.getTitle(), result.getTitle());
    }

    @Test
    public void searchByTitleTest() {

        movie1 = new MovieModel();
        movie1.setTitle("The Incredible Hulk");
        movie1.setRating(4.7f);

        movie2 = new MovieModel();
        movie2.setTitle("Captain America");
        movie2.setRating(1.5f);

        movie3 = new MovieModel();
        movie3.setTitle("Guardians of the Galaxy");
        movie3.setRating(5.3f);

        // Insert data into db
        try {
            movieDao.persist(movie1);
            movieDao.persist(movie2);
            movieDao.persist(movie3);
        } catch (Exception e) {
            assertTrue(false);
        }

        //
        List<MovieModel> list = movieDao.searchByTitle("Galaxy");

        assertThat(list.get(0).getTitle(), containsString("Galaxy"));
    }

    @Test
    public void findAllFavoriteTest() {

        movie1 = new MovieModel();
        movie1.setTitle("The Incredible Hulk");
        movie1.setRating(4.7f);

        movie2 = new MovieModel();
        movie2.setTitle("Captain America");
        movie2.setRating(1.5f);
        movie2.setFavorite(true);

        movie3 = new MovieModel();
        movie3.setTitle("Guardians of the Galaxy");
        movie3.setRating(5.3f);

        movie4 = new MovieModel();
        movie4.setTitle("Ant-Man");
        movie4.setRating(3.2f);

        movie5 = new MovieModel();
        movie5.setTitle("Iron Man");
        movie5.setRating(2.1f);

        // Insert data into db
        try {
            movieDao.persist(movie1);
            movieDao.persist(movie2);
            movieDao.persist(movie3);
            movieDao.persist(movie4);
            movieDao.persist(movie5);
        } catch (Exception e) {
            assertTrue(false);
        }

        //
        List<MovieModel> list = movieDao.findAllFavorite();

        assertEquals(1, list.size());
        assertEquals(movie2.getTitle(), list.get(0).getTitle());
    }

    @Test
    public void updateTest() {

        movie1 = new MovieModel();
        movie1.setTitle("The Incredible Hulk");
        movie1.setRating(4.7f);

        movie2 = new MovieModel();
        movie2.setTitle("Captain America");
        movie2.setRating(1.5f);
        movie2.setFavorite(true);

        movie3 = new MovieModel();
        movie3.setTitle("Guardians of the Galaxy");
        movie3.setRating(5.3f);

        // Insert data into db
        try {
            movieDao.persist(movie1);
            movieDao.persist(movie2);
            movieDao.persist(movie3);
        } catch (Exception e) {
            assertTrue(false);
        }

        // Update
        movie2.setRating(7.3f);
        movie2.setFavorite(false);
        //
        movieDao.update(movie2);
        //
        MovieModel result = movieDao.findByTitle(movie2.getTitle());

        assertEquals(7.3, result.getRating(), 0.1);
        assertFalse(movie2.isFavorite());
    }

    @Test
    public void deleteTest() {

        movie1 = new MovieModel();
        movie1.setTitle("The Incredible Hulk");
        movie1.setRating(4.7f);

        movie2 = new MovieModel();
        movie2.setTitle("Captain America");
        movie2.setRating(1.5f);
        movie2.setFavorite(true);

        movie3 = new MovieModel();
        movie3.setTitle("Guardians of the Galaxy");
        movie3.setRating(5.3f);

        // Insert data into db
        try {
            movieDao.persist(movie1);
            movieDao.persist(movie2);
            movieDao.persist(movie3);
        } catch (Exception e) {
            assertTrue(false);
        }

        //
        movieDao.delete(movie1);
        movieDao.delete(movie2);
        movieDao.delete(movie3);
        //
        List<MovieModel> list = movieDao.findAll();

        assertEquals(0, list.size());
    }
}