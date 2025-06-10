package adrien.faouzi.utility;

import org.primefaces.model.file.UploadedFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManaging {

    //location of folder for file.
    private static final String FOLDER_PATH_DOWNLOAD = "C:\\wamp\\www\\imageFolderLocalHost\\";
    private static final String FOLDER_PATH_APPLY = "http://localhost/imageFolderLocalHost/";
    private static final String DEFAULT_FILE = "default.png";



    //save a file (from input file).
    public static boolean saveNewFile(UploadedFile uploadingFile){
        boolean out = false;

        //instance file and path.
        File file = new File(FOLDER_PATH_DOWNLOAD+uploadingFile.getFileName());
        Path filePath = file.toPath();

        //delete previous file if existing.
        if(Files.exists(filePath)){
            file.deleteOnExit();
        }

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            outputStream.write(uploadingFile.getContent());
            out = true;
        } catch (IOException error) {
            UtilityProcessing.debug("Error from write in file (FileManaging.java)");
            UtilityProcessing.debug(error.getMessage());
        }

        return out;
    }



    //get seed of file in folder download.
    public static String getUrlForApply(String nameFile){
        return FOLDER_PATH_APPLY+nameFile;
    }
    public static String getUrlForApply(UploadedFile uploadingFile){
        return getUrlForApply(uploadingFile.getFileName());
    }
    public static String getDefaultUrlForApply(){
        return getUrlForApply(DEFAULT_FILE);
    }

}
