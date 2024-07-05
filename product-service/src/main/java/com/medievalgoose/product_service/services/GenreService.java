package com.medievalgoose.product_service.services;

import com.medievalgoose.product_service.models.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenreService {
    List<Genre> getAllGenres();
    Genre createGenre(Genre genre);
}
