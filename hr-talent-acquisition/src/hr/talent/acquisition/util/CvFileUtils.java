package hr.talent.acquisition.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.primefaces.model.file.UploadedFile;

/**
 * Utility class for handling uploaded CV files.
 */
public final class CvFileUtils {

    private static final String MD_EXTENSION = ".md";

    private CvFileUtils() {
        // Utility class
    }

    /**
     * Process uploaded CV file and extract content as string.
     * 
     * @param uploadedFile PrimeFaces UploadedFile object
     * @return String content of the file
     */
    public static String processUploadedCv(UploadedFile uploadedFile) {
        if (uploadedFile == null || uploadedFile.getFileName() == null) {
            return "";
        }

        String fileName = uploadedFile.getFileName().toLowerCase();
        String fileExtension = detectFileExtension(fileName);

        try {
            if (MD_EXTENSION.equals(fileExtension)) {
                return handleMdFile(uploadedFile);
            }
            return "";
        } catch (Exception e) {
            System.err.println("Error processing uploaded CV: " + e.getMessage());
            return "";
        }
    }

    /**
     * Detects file extension from filename.
     */
    public static String detectFileExtension(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(lastDotIndex).toLowerCase();
    }

    /**
     * Handles Markdown (.md) files by reading content as UTF-8 text.
     */
    public static String handleMdFile(UploadedFile uploadedFile) throws IOException {
        if (uploadedFile == null) {
            return "";
        }

        try (InputStream inputStream = uploadedFile.getInputStream()) {
            byte[] bytes = inputStream.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }
}
