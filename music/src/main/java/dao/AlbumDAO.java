package dao;

import model.Album;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Collection;

public class AlbumDAO {
    private JdbcTemplate jdbcTemplate;

    public AlbumDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }

    public Album createAlbum(Album album){
        //TODO: Implement this CRUD function
        String query = "INSERT into albums (id, title) values (?,?)";
        this.jdbcTemplate.update(query, album.getId(), album.getTitle());
        return album;
    }

    public Album getAlbum(int id){
        //TODO: Implement this CRUD function
        //Get album and set tracks using getTracksByAlbumId(id) in TracksDAO
        String query = "SELECT * FROM albums WHERE id = ?";
        Album album = this.jdbcTemplate.queryForObject(query, new Object[]{id},
                new BeanPropertyRowMapper<>(Album.class));
        TrackDAO trackDAO = new TrackDAO(jdbcTemplate);
        //Get album and set tracks using getTracksByAlbumId(id) in TracksDAO
        album.setTracks(trackDAO.getTracksByAlbumId(id));

        return album;
    }

    public Collection<Album> getAllAlbums(){
        Collection<Album> albums = new ArrayList<Album>();
        this.jdbcTemplate.query(
                "SELECT * FROM albums", new Object[] { },
                (rs, rowNum) -> new Album(rs.getInt("id"), rs.getString("title"))
        ).forEach(album -> albums.add(album));

        return albums;
    }

    public Album updateAlbum(Album album){
        //TODO: Implement this CRUD function
        String query = "UPDATE albums SET title = ? WHERE id = ?";
        this.jdbcTemplate.update(query, album.getTitle(), album.getId());
        return album;
    }

    public boolean deleteAlbum(Album album){
        boolean success = false;
        String query = "DELETE FROM albums WHERE id = ?";
        int flag = 0;
        flag = this.jdbcTemplate.update(query, album.getId());
        if (flag != 0) {
            return success=true;
        } else
            return success;
    }



}
