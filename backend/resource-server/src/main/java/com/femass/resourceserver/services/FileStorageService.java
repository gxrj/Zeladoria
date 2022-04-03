package com.femass.resourceserver.services;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter

public class FileStorageService {

    private Path basePath;

    private final Logger LOG = LoggerFactory.getLogger( FileStorageService.class );

    public FileStorageService( String path ) {
        this.basePath = Paths.get( path )
                            .toAbsolutePath().normalize();

        createDir( this.basePath.toString() );
    }

    public String store( MultipartFile file, String userId, String objectId ) throws  RuntimeException {

        var filePath= StringUtils.cleanPath( userId+"/"+objectId+"/"+file.getOriginalFilename() );
        createDir( userId+"/"+objectId+"/" );

        try{
            if( file.isEmpty() )
                throw new RuntimeException( "Failed to store empty file: " + filePath );

            Files.write( basePath.resolve( filePath ), file.getBytes() );
        }
        catch ( IOException ex ) {

            LOG.error( "Failed to store file at: {} \n {}",
                       basePath.resolve( filePath ), ex.getMessage() );

            throw new RuntimeException(
                    "Failed to store file at: "+ basePath.resolve( filePath ) );
        }
        return filePath;
    }

    public Resource loadAsResource( String filename ) throws FileNotFoundException {
        try {
            var filePath = basePath.resolve( filename ).normalize();
            var resource = new UrlResource( filePath.toUri() );

            if( resource.exists() ) return resource;
            else
                throw new FileNotFoundException( "File not found: " + filename );
        }
        catch( MalformedURLException ex ) {
            throw new FileNotFoundException( "File not found: " + filename + "\n" + ex.getMessage() );
        }
    }

    public List<Resource> loadAllFilesFromDirectory( String directoryAbsPath ) {

        try{
            Path filePath = basePath.resolve( directoryAbsPath ).normalize();
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream( filePath );

            var resources = new ArrayList<Resource>();
            directoryStream.forEach( element -> {
                try {
                    var attr = Files.readAttributes(
                                   element, BasicFileAttributes.class);

                    if ( attr.isRegularFile() )
                        resources.add( loadAsResource( element.toAbsolutePath().toString() ) );
                    else
                        LOG.warn( "The resource is not a file: {}", element.toAbsolutePath() );
                }
                catch ( IOException ex ) {
                    LOG.error( "Error at creation of DirectoryStream: {}", ex.getMessage() );
                } } );

            return resources;
        }
        catch ( InvalidPathException | IOException ex ) {
            LOG.error( "Error: {}", ex.getMessage() );
            return null;
        }
    }

    public boolean delete( String fileOrDirPath ) throws IOException {
        return FileSystemUtils.deleteRecursively( basePath.resolve( fileOrDirPath ) );
    }

    private void createDir( String dirPath ) {

        try{
            Files.createDirectories( basePath.resolve( dirPath ) );
        }
        catch ( FileAlreadyExistsException ex ) {
            LOG.warn( "Directory {} already exists!", dirPath );
        }
        catch ( IOException ex ) {
            LOG.error( "Could not initialize the directory: {}\nCaused by: {}", dirPath, ex.getMessage() );
        }
    }
}
