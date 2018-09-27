/**
 * CloudStorageUpload is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform).
 * Upload a local file to a Google Storage bucket in a Google Cloud Project.
 * The application uses Application Default Credentials through a JSON service account key for authenticating.
 * The credentials are taken from GOOGLE_APPLICATION_CREDENTIALS environment variable.
 * You must provide 3 parameters:
 * BUCKET_NAME     = Bucket name
 * OBJECT_NAME     = Object name in the bucket
 * LOCAL_FILE_NAME = Local file name
 */

package example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.WriteChannel;

public class CloudStorageUpload {
    public static void main(String[] args) throws IOException {
        String bucketName;     // Bucket name
        String blobName;       // Key name, it is the object name
        String localFileName;  // Local file name

        if (args.length < 3) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar cloudtorageupload.jar <BUCKET_NAME> <OBJECT_NAME> <LOCAL_FILE_NAME>");
            System.exit(1);
        }

        bucketName = args[0];
        blobName = args[1];
        localFileName = args[2];

        System.out.println("Bucket:     " + bucketName);
        System.out.println("Object:     " + blobName);
        System.out.println("Local file: " + localFileName);

        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

        System.out.println("Uploading an object to Cloud Storage bucket from a file ...");

        // Get local file
        File file = new File(localFileName);
        if (file.exists()) {
            // Upload a blob to the bucket
            BlobId blobId = BlobId.of(bucketName, blobName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();

            if (Files.size(Paths.get(localFileName)) > 1_000_000) {
                // When content size is not available or large (1MB or more) it is recommended
                // to write it in chunks via the blob's channel writer
                try (WriteChannel writer = storage.writer(blobInfo)) {
                    byte[] buffer = new byte[1024];
                    try (InputStream input = Files.newInputStream(Paths.get(localFileName))) {
                        int limit;
                        while ((limit = input.read(buffer)) >= 0) {
                            try {
                                writer.write(ByteBuffer.wrap(buffer, 0, limit));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            } else {
                byte[] bytes = Files.readAllBytes(Paths.get(localFileName));
                // Create the blob in one request
                Blob blob = storage.create(blobInfo, bytes);
            }

            System.out.println("Uploaded");
        } else {
            System.out.printf("Error: Local file \"%s\" does NOT exist.", localFileName);
        }
    }
}
