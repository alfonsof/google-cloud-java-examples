/**
 * CloudStorageDownload is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform)
 * Download an object in a Cloud Storage bucket in a Google Cloud Project to a local file.
 * The application uses Application Default Credentials through a JSON service account key for authenticating.
 * The credentials are taken from GOOGLE_APPLICATION_CREDENTIALS environment variable.
 * You must use 1 parameter:
 * BUCKET_NAME     = Bucket name
 * OBJECT_NAME     = Object name in the bucket
 * LOCAL_FILE_NAME = Local file name
 */

package example;

import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class CloudStorageDownload {
    public static void main(String[] args) throws IOException {
        String bucketName;       // Bucket name
        String blobName;         // Key name, it is the object name
        String downloadFileName; // Download local file name

        if (args.length < 3) {
            System.out.println("Not enough parameters. Proper Usage is: java -jar cloudtoragedownload.jar <BUCKET_NAME> <OBJECT_NAME> <LOCAL_FILE_NAME>");
            System.exit(1);
        }

        bucketName = args[0];
        blobName = args[1];
        downloadFileName = args[2];

        System.out.println("Bucket:     " + bucketName);
        System.out.println("Object/Key: " + blobName);
        System.out.println("Local file: " + downloadFileName);

        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // Download a blob to local file
        Blob blob = storage.get(bucketName, blobName);
        if (blob != null) {
            OutputStream outputStream = Files.newOutputStream(Paths.get(downloadFileName));
            if (blob.getSize() > 1_000_000) {
                // When Blob size is big or unknown use the blob's channel reader
                try (ReadChannel reader = blob.reader()) {
                    WritableByteChannel channel = Channels.newChannel(outputStream);
                    ByteBuffer bytes = ByteBuffer.allocate(64 * 1024);
                    while (reader.read(bytes) > 0) {
                        try {
                            bytes.flip();
                            channel.write(bytes);
                            bytes.clear();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                ReadChannel readChannel = blob.reader();
                FileOutputStream fileOuputStream = new FileOutputStream(downloadFileName);
                fileOuputStream.getChannel().transferFrom(readChannel, 0, Long.MAX_VALUE);
                fileOuputStream.close();
            }
            System.out.println("Downloaded");
        } else {
            System.out.println("Error: Bucket/Blob no such object");
        }
    }
}
