package com.learn;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "resources")
public class ResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] fileData;
    private String contentType;


    public ResourceEntity() {

    }

    public ResourceEntity(byte[] fileData, String contentType) {

        this.fileData = fileData;
        this.contentType = contentType;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public byte[] getFileData() {

        return fileData;
    }

    public void setFileData(byte[] fileData) {

        this.fileData = fileData;
    }

    public String getContentType() {

        return contentType;
    }

    public void setContentType(String contentType) {

        this.contentType = contentType;
    }
}
