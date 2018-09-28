/**
 * CloudStorageCopy is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform).
 * Copy an object from a Cloud Storage bucket to another Cloud Storage bucket in a Google Cloud Project.
 * The application uses Application Default Credentials through a JSON service account key for authenticating.
 * The credentials are taken from GOOGLE_APPLICATION_CREDENTIALS environment variable.
 * You must use 3 parameters:
 * SOURCE_BUCKET      = Source bucket name
 * SOURCE_OBJECT      = Source object name
 * DESTINATION_BUCKET = Destination bucket name
 */

package example;

import java.io.IOException;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.CopyWriter;

public class CloudStorageCopy {

    public static void main(String[] args) throws IOException {
        String sourceBucketName;       // Source bucket name
        String sourceBlobName;         // Source blob name
        String destinationBucketName;  // Destination bucket name
        String destinationBlobName;    // Destination blob name

        if (args.length < 3) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar gcloudstoragecopy.jar <SOURCE_BUCKET> <SOURCE_OBJECT> <DESTINATION_BUCKET>");
            System.exit(1);
        }

        sourceBucketName = args[0];
        sourceBlobName = args[1];
        destinationBucketName = args[2];
        destinationBlobName = sourceBlobName;

        System.out.println("From - bucket: " + sourceBucketName);
        System.out.println("From - object: " + sourceBlobName);
        System.out.println("To   - bucket: " + destinationBucketName);
        System.out.println("To   - object: " + destinationBlobName);

        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // Copy a blob from a bucket to another bucket
        System.out.println("Copying object ...");
        Blob sourceBlob = storage.get(sourceBucketName, sourceBlobName);

        if (sourceBlob != null) {    // Exists source bucket/object
            Bucket destinationBucket = storage.get(destinationBucketName);
            if (destinationBucket != null) {   // Exists destination bucket
                CopyWriter copyWriter = sourceBlob.copyTo(BlobId.of(destinationBucketName, destinationBlobName));
                Blob copiedBlob = copyWriter.getResult();
                if (copiedBlob != null) {
                    System.out.println("Copied");
                } else {
                    System.out.println("Error: Object does NOT copied.");
                }
            } else {
                System.out.println("Error: Destination Bucket does NOT exist.");
            }
        } else {
            System.out.println("Error: Source Bucket/Object does NOT exist.");
        }
    }
}
