package com.recipes.service;

import com.recipes.model.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class FileStorageService
{
    private final Logger log = Logger.getLogger(FileStorageService.class.getName());

    private final String uploadsDir;

    public FileStorageService(@Value("${uploads-path}") String uploadsDir) {
        Path uploadsPath = Paths.get(uploadsDir);
        if (Files.notExists(uploadsPath)) {
            try {
                Files.createDirectories(uploadsPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.uploadsDir = uploadsDir;
    }

    public String saveMainPhoto(Recipe recipe, MultipartFile file) throws IOException {
        if (recipe.getMainPhotoName() != null)
            removeMainPhoto(recipe);

        Path fileDir = resolveRecipeFullPath(recipe.getId());
        Files.createDirectories(fileDir);

        String ofn = file.getOriginalFilename();
        String fileName = "mph-"+System.currentTimeMillis()+"."+ofn.substring(ofn.lastIndexOf('.')+1);

        Path filePath = Paths.get(fileDir.toString(), fileName);
        file.transferTo(filePath);

        return fileName;
    }

    public Optional<Path> getMainPhotoRelativePath(Recipe recipe) {
        Path mainPhotoFullPath = Path.of(resolveRecipeFullPath(recipe.getId()).toString(), recipe.getMainPhotoName());
        if (Files.exists(mainPhotoFullPath)) {
            Path mainPhotoRelativePath = Path.of(resolveRecipeRelativePath(recipe.getId()).toString(), recipe.getMainPhotoName());
            return Optional.of(mainPhotoRelativePath);
        }
        return Optional.empty();
    }

    public void removeMainPhoto(Recipe recipe) {
        Path mainPhotoPath = Path.of(resolveRecipeFullPath(recipe.getId()).toString(), recipe.getMainPhotoName());
        try {
            Files.delete(mainPhotoPath);
        } catch (IOException e) {
            log.warning("Unable to delete file: " + mainPhotoPath);
        }
    }

    private Path resolveRecipeFullPath(long recipeId) {
        return Path.of(uploadsDir, resolveRecipeRelativePath(recipeId).toString());
    }

    private Path resolveRecipeRelativePath(long recipeId) {
        return Path.of(String.valueOf(recipeId));
    }
}
