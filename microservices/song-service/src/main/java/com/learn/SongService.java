package com.learn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.exceptions.InvalidCsvException;
import com.learn.exceptions.SongAlreadyExistException;
import com.learn.exceptions.SongNotFoundException;
import com.learn.responses.SongCreationResponseDTO;
import com.learn.responses.SongDTO;
import com.learn.responses.SongRemoveResponseDTO;

@Service
public class SongService {

    public static final String SEPARATOR = ",";
    private static final int MAX_CSV_LENGTH = 200;

    @Autowired
    private SongRepository songRepository;

    public SongCreationResponseDTO saveMetadata(SongMetadata metadata) {

        Long songId = metadata.getId();
        if (songId != null && songRepository.existsById(songId)) {
            throw new SongAlreadyExistException("Song with id = " + songId + " already exists.");
        }

        SongEntity song = new SongEntity();
        song.setId(songId);
        song.setTitle(metadata.getName());
        song.setArtist(metadata.getArtist());
        song.setAlbum(metadata.getAlbum());
        song.setGenre(metadata.getGenre());
        song.setReleaseYear(metadata.getYear());
        song.setDuration(metadata.getDuration());
        song.setResourceId(metadata.getResourceId());
        SongEntity songEntity = songRepository.save(song);
        return new SongCreationResponseDTO(songEntity.getId());
    }

    public Optional<SongDTO> getSongById(Long id) {

        Optional<SongEntity> optionalSongEntity = songRepository.findById(id);

        if (optionalSongEntity.isPresent()) {
            SongEntity songEntity = optionalSongEntity.get();
            return Optional.of(new SongDTO(songEntity.getId(), songEntity.getTitle(), songEntity.getArtist(),
                    songEntity.getAlbum(), songEntity.getDuration(), String.valueOf(songEntity.getReleaseYear())));
        }

        throw new SongNotFoundException("Resource with id = " + id + " not found");
    }

    public SongRemoveResponseDTO deleteSongsByIds(String ids) {

        if (ids.length() > 200) {
            throw new InvalidCsvException(
                    "The provided CSV is invalid or exceeds the maximum length of " + MAX_CSV_LENGTH + " characters.");
        }

        List<Long> idsToRemove = Arrays.stream(ids.split(SEPARATOR)).map(Long::parseLong).toList();
        List<Long> existingIds = songRepository.findAllById(idsToRemove).stream().map(SongEntity::getId).toList();
        songRepository.deleteAllById(existingIds);

        return new SongRemoveResponseDTO(existingIds);
    }
}
