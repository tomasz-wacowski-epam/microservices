package com.learn;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SongMetadata {

    private Long id;
    @NotBlank(message = "Title field cannot be null")
    @Size(min = 1, max = 100, message = "Title field needs to have from 1 to 100 characters")
    private String name;
    @NotBlank(message = "Artist field cannot be null")
    @Size(min = 1, max = 100, message = "Artist field needs to have from 1 to 100 characters")
    private String artist;
    @NotBlank(message = "Album field cannot be null")
    @Size(min = 1, max = 100, message = "Album field needs to have from 1 to 100 characters")
    private String album;
    private String genre;
    @Pattern(regexp = "^(19|20)\\d{2}$", message = "Year must be in a YYYY format")
    private String year;
    private Long resourceId;
    @NotNull(message = "Duration field cannot be null")
    @Pattern(regexp = "^([0-5]?[0-9]):([0-5][0-9])$", message = "Duration must be in format MM:SS")
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
