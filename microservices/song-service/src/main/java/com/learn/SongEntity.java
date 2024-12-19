package com.learn;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "songs")
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String artist;
    private String album;
    private String genre;
    private String releaseYear;
    @Column(unique = true)
    private Long resourceId;
    private String duration;

    public SongEntity() {

    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getArtist() {

        return artist;
    }

    public void setArtist(String artist) {

        this.artist = artist;
    }

    public String getAlbum() {

        return album;
    }

    public void setAlbum(String album) {

        this.album = album;
    }

    public String getGenre() {

        return genre;
    }

    public void setGenre(String genre) {

        this.genre = genre;
    }

    public String getReleaseYear() {

        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {

        this.releaseYear = releaseYear;
    }

    public Long getResourceId() {

        return resourceId;
    }

    public void setResourceId(Long resourceId) {

        this.resourceId = resourceId;
    }

    public String getDuration() {

        return duration;
    }

    public void setDuration(String duration) {

        this.duration = duration;
    }

    @Override
    public String toString() {

        return "SongEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseYear=" + releaseYear +
                ", resourceId=" + resourceId +
                ", duration='" + duration + '\'' +
                '}';
    }
}
