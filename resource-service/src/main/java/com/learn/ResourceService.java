package com.learn;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.learn.exceptions.InvalidCsvException;
import com.learn.exceptions.ParseResourceException;
import com.learn.exceptions.ResourceNotFoundException;
import com.learn.exceptions.WrongIdException;
import com.learn.responses.ResourceCreationResponseDTO;
import com.learn.responses.ResourceRemoveResponseDTO;

@Service
public class ResourceService {

    private static final int MAX_CSV_LENGTH = 200;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int MINIMUM_ID_VALUE = 1;
    public static final String NO_DURATION = "00:00";
    public static final String SEPARATOR = ",";
    public static final String PARSE_EXCEPTION_MESSAGE = "It was not possible to correctly parse uploaded file";

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private SongServiceClient songServiceClient;

    public ResourceCreationResponseDTO saveFile(byte[] file) {

        Metadata metadata = new Metadata();
        BodyContentHandler handler = new BodyContentHandler();
        ParseContext parseContext = new ParseContext();
        Mp3Parser mp3Parser = new Mp3Parser();

        try (var inputStream = new ByteArrayInputStream(file)) {
            mp3Parser.parse(inputStream, handler, metadata, parseContext);
        } catch (IOException | TikaException | SAXException e) {
            throw new ParseResourceException(PARSE_EXCEPTION_MESSAGE);
        }

        String title = metadata.get("dc:title");
        String artist = metadata.get("xmpDM:artist");
        String album = metadata.get("xmpDM:album");
        String genre = metadata.get("xmpDM:genre");
        String duration = convertDuration(metadata.get("xmpDM:duration"));
        String releaseYear = metadata.get("xmpDM:releaseDate");

        ResourceEntity resourceEntity = new ResourceEntity(file, metadata.get("Content-Type"));
        resourceEntity = resourceRepository.save(resourceEntity);

        Long resourceId = resourceEntity.getId();
        if (resourceId == null) {
            throw new ParseResourceException(PARSE_EXCEPTION_MESSAGE);
        }

        SongMetadata songMetadata = new SongMetadata();
        songMetadata.setId(resourceId);
        songMetadata.setName(title != null ? title : "Unknown title");
        songMetadata.setArtist(artist != null ? artist : "Unknown artist");
        songMetadata.setAlbum(album != null ? album : "Unknown album");
        songMetadata.setGenre(genre != null ? genre : "Unknown genre");
        songMetadata.setDuration(duration);
        songMetadata.setYear(releaseYear != null ? releaseYear : "Unknown year");

        songServiceClient.saveMetadata(songMetadata);

        return new ResourceCreationResponseDTO(resourceId);

    }

    private String convertDuration(String duration) {

        if (duration == null) {
            return NO_DURATION;
        }

        double durationValue = Double.parseDouble(duration);
        int durationFullSeconds = (int) durationValue;
        int minutes = durationFullSeconds / SECONDS_PER_MINUTE;
        int seconds = durationFullSeconds % SECONDS_PER_MINUTE;

        return String.format("%02d:%02d", minutes, seconds);
    }

    public byte[] getResourceById(Long id) {

        if (id < MINIMUM_ID_VALUE) {
            throw new WrongIdException("ID needs to be a positive integer.");
        }

        Optional<ResourceEntity> resourceEntity = resourceRepository.findById(id);
        return resourceEntity.map(ResourceEntity::getFileData)
                .orElseThrow(() -> new ResourceNotFoundException("Resource with id = " + id + " not found"));
    }

    public ResourceRemoveResponseDTO deleteResourcesByIds(String ids) {

        if (ids.length() > 200) {
            throw new InvalidCsvException(
                    "The provided CSV is invalid or exceeds the maximum length of " + MAX_CSV_LENGTH + " characters.");
        }

        List<Long> idsToRemove = Arrays.stream(ids.split(SEPARATOR)).map(Long::parseLong).toList();
        List<Long> existingIds = resourceRepository.findAllById(idsToRemove).stream().map(ResourceEntity::getId)
                .toList();
        resourceRepository.deleteAllById(existingIds);

        songServiceClient.removeMetadata(ids);

        return new ResourceRemoveResponseDTO(existingIds);
    }
}
