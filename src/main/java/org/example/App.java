package org.example;

import org.example.model.Director;
import org.example.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Director.class).addAnnotatedClass(Movie.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            deleteMovieAtTheDirector(session);

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }


    }

    private static void showDirectorAndMovies(Session session) {
        Director director = session.get(Director.class, 4);
        System.out.println(director);
        List<Movie> movieList = director.getMovieList();
        System.out.println(movieList);
    }

    private static void showMovieAndDirector(Session session) {
        Movie movie = session.get(Movie.class, 4);
        System.out.println(movie);
        Director director = movie.getOwner();
        System.out.println(director);
    }

    private static void addFilmToDirector(Session session) {
        Director director = session.get(Director.class, 4);
        System.out.println(director);
        Movie movie = new Movie("Camera248", 2012, director);
        director.getMovieList().add(movie);
        session.save(movie);
    }

    private static void createNewDirectorAndNewMovie(Session session) {
        Director director = new Director("Stefan Jano", 23);
        Movie movie = new Movie("Twelve friends", 1998, director);

        director.setMovieList(new ArrayList<>(Collections.singletonList(movie)));

        session.save(director);
        session.save(movie);
    }

    private static void changeDirectorAtTheMovie(Session session) {
        Movie movie = session.get(Movie.class, 9);
        Director director = session.get(Director.class, 1);
        System.out.println(movie);
        System.out.println();
        System.out.println(director);
        movie.setOwner(director);
        director.getMovieList().add(movie);
        System.out.println(director);
    }

    private static void deleteMovieAtTheDirector(Session session) {
        Movie movie = session.get(Movie.class, 2);
        movie.getOwner().getMovieList().remove(movie);
        session.delete(movie);
    }

}
