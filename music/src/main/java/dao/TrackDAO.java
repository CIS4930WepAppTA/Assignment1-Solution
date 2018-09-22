package dao;

import model.Track;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Collection;


public class TrackDAO {
    private JdbcTemplate jdbcTemplate;

    public TrackDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }


    public Track createTrack(Track track){
        String query = "INSERT into tracks (title, album) values (?,?)";
        jdbcTemplate.update(query, track.getTitle(), track.getAlbumId());
        return track;
    }

    public Track getTrack(int id){
        String query = "SELECT * FROM tracks WHERE id = ?";
        Track track = this.jdbcTemplate.queryForObject(query, new Object[] {id},
                new BeanPropertyRowMapper<>());
        return track;
    }

    public Collection<Track> getAllTracks(){
        Collection<Track> tracks = new ArrayList<Track>();
        String query = "SELECT * FROM tracks";
        this.jdbcTemplate.query(
                query, new Object[] {},
                (rs, rowNum) -> new Track(rs.getInt("id"), rs.getString("title"), rs.getInt("album"))
        ).forEach(track -> tracks.add(track));
        return tracks;
    }




    public Collection<Track> getTracksByAlbumId(int albumId){
        Collection<Track> tracks = new ArrayList<Track>();

        this.jdbcTemplate.query(
                "SELECT id, title FROM tracks WHERE album = ?", new Object[] { albumId },
                (rs, rowNum) -> new Track(rs.getInt("id"), rs.getString("title"),albumId)
        ).forEach(track -> tracks.add(track) );

        return tracks;
    }
    public Track updateTrack(Track track){
        //Assumption: only title is getting updated
        String query = "UPDATE tracks SET title = ? WHERE id = ?";
        this.jdbcTemplate.update(query, track.getTitle(), track.getId());
        return track;
    }

    public boolean deleteTrack(Track track){
        boolean success = false;
        String query = "DELETE FROM tracks WHERE id = ?";
        int flag = 0;
        flag = this.jdbcTemplate.update(query, track.getId());
        if (flag != 0) {
            return success=true;
        } else
            return success;

    }

}
