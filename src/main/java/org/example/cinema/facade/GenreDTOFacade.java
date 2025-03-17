package org.example.cinema.facade;

import org.example.cinema.entity.Genre;
import org.example.cinema.dto.GenreDTO;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

public class GenreDTOFacade {
    @Autowired
    private GenreService genreService;

    public GenreDTO get(Long id){
        return genreToGenreDTO(genreService.get(id));
    }

    public List<GenreDTO> getAll(){
        return genreService.getAll().stream().map(this::genreToGenreDTO).collect(Collectors.toList());
    }

    public Long save(GenreDTO genre){
        return genreService.save(genreDTOToGenre(genre));
    }

    public Genre genreDTOToGenre(GenreDTO genreDTO) {
        return Genre.builder()
                .id(genreDTO.getId())
                .name(genreDTO.getName())
                .build();
    }

    public GenreDTO genreToGenreDTO(Genre genre) {
        return GenreDTO.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}

