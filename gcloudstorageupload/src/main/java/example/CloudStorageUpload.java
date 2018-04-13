/**
 * CloudStorageUpload is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform)
 * Upload a local file to a Google Storage bucket for a Google Cloud Project.
 * The application uses Application Default Credentials through a JSON service account key for authenticating.
 * The credentials are taken from GOOGLE_APPLICATION_CREDENTIALS environment variable.
 * You must use 1 parameter:
 * BUCKET_NAME     = Bucket name
 * OBJECT_NAME     = Object file name in the bucket
 * LOCAL_FILE_NAME = Local file name
 */

package example;

import static java.nio.charset.StandardCharsets.UTF_8;

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
        String uploadFileName; // Upload local file name

        if (args.length < 3) {
            System.out.println("Not enough parameters. Proper Usage is: java -jar cloudtorageupload.jar <BUCKET_NAME> <OBJECT_NAME> <LOCAL_FILE_NAME>");
            System.exit(1);
        }

        bucketName = args[0];
        blobName = args[1];
        uploadFileName = args[2];

        System.out.println("Bucket:     " + bucketName);
        System.out.println("Object/Key: " + blobName);
        System.out.println("Local file: " + uploadFileName);

        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // Upload a blob to the bucket
        BlobId blobId = BlobId.of(bucketName, blobName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();

        if (Files.size(Paths.get(uploadFileName)) > 1_000_000) {
            // When content is not available or large (1MB or more) it is recommended
            // to write it in chunks via the blob's channel writer.
            try (WriteChannel writer = storage.writer(blobInfo)) {
                byte[] buffer = new byte[1024];
                try (InputStream input = Files.newInputStream(Paths.get(uploadFileName))) {
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
            byte[] bytes = Files.readAllBytes(Paths.get(uploadFileName));
            // Create the blob in one request
            Blob blob = storage.create(blobInfo, bytes);
        }

        System.out.println("Uploaded");
    }
}
