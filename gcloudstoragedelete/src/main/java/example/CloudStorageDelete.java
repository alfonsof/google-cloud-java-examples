/**
 * CloudStorageDelete is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform)
 * Delete a Google Storage bucket in a Google Cloud Project.
 * The application uses Application Default Credentials through a JSON service account key for authenticating.
 * The credentials are taken from GOOGLE_APPLICATION_CREDENTIALS environment variable.
 * You must provide 1 parameter:
 * BUCKET_NAME = Name of the bucket
 */

package example;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class CloudStorageDelete {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar cloudstoragedelete.jar <BUCKET_NAME>");
            System.exit(1);
        }

        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // The name for the new bucket
        String bucketName = args[0];

        System.out.printf("Bucket name: %s\n", bucketName);

        Bucket bucket = storage.get(bucketName);

        if (bucket != null) {   // Exists the bucket
            System.out.println("Deleting bucket ...");
            // Delete the bucket
            boolean deleted = bucket.delete(Bucket.BucketSourceOption.metagenerationMatch());
            if (deleted) {  // the bucket was deleted
                System.out.printf("Bucket \"%s\" Deleted\n", bucket.getName());
            } else {    // the bucket was not found
                System.out.printf("Error: Bucket \"%s\" NOT Deleted.\n", bucket.getName());
            }
        } else {   // Not exists the bucket
            System.out.println("Error: Bucket does not exist!!");
        }
    }
}
