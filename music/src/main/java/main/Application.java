package main;

import dao.AlbumDAO;
import dao.TrackDAO;
import model.Album;
import model.Track;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) {
        AlbumDAO albumDAO = new AlbumDAO(jdbcTemplate);
        TrackDAO trackDAO = new TrackDAO(jdbcTemplate);

        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE IF EXISTS tracks");
        jdbcTemplate.execute("CREATE TABLE tracks(" +
                "id SERIAL, title VARCHAR(255), album INT)");
        trackDAO.createTrack(new Track("Track 1", 42));
        trackDAO.createTrack(new Track("Track 2", 42));
        trackDAO.createTrack(new Track ("Track 3", 42));

        jdbcTemplate.execute("DROP TABLE IF EXISTS albums");
        jdbcTemplate.execute("CREATE TABLE albums(" +
                "id INT, title VARCHAR(255))");

        albumDAO.createAlbum(new Album (42, "Album 1"));
        albumDAO.createAlbum(new Album (45, "Album 45"));
        albumDAO.getAllAlbums().forEach(album -> log.info(album.toString()));
        log.info(albumDAO.getAlbum(42).toString());
        log.info(trackDAO.getAllTracks().toString());
        log.info(albumDAO.getAllAlbums().toString());
       System.out.println(trackDAO.deleteTrack(new Track(1)));
       System.out.println(albumDAO.deleteAlbum(new Album (45, "Album 45")));

    }
}
