package com.medievalgoose.product_service.services.implementations;

import com.medievalgoose.product_service.models.Genre;
import com.medievalgoose.product_service.repositories.GenreRepository;
import com.medievalgoose.product_service.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }
}
