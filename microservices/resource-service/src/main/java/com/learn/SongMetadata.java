package com.learn;

public class SongMetadata {

    private Long id;
    private String name;
    private String artist;
    private String album;
    private String genre;
    private String year;
    private Long resourceId;
    private String duration;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
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

    public String getYear() {

        return year;
    }

    public void setYear(String year) {

        this.year = year;
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
}
