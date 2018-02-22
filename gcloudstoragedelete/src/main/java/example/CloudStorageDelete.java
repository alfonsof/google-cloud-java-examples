/**
 * CloudStorageDelete is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform)
 * Delete a Google Storage bucket for a Google Cloud Project.
 * The application uses Application Default Credentials through a JSON service account key for authenticating.
 * The credentials are taken from GOOGLE_APPLICATION_CREDENTIALS environment variable.
 * You must use 1 parameter:
 * BUCKET = Name of bucket
 */

package example;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class CloudStorageDelete {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Not enough parameters. Proper Usage is: java -jar cloudstoragecreate.jar <BUCKET_NAME>");
            System.exit(1);
        }

        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // The name for the new bucket
        String bucketName = args[0];

        Bucket bucket = storage.get(bucketName);

        if (bucket == null) {
            // Not exists the bucket
            System.out.println("No such bucket");
            System.exit(1);
        }

        // Delete the bucket
        boolean deleted = bucket.delete(Bucket.BucketSourceOption.metagenerationMatch());
        if (deleted) {  // the bucket was deleted
            System.out.printf("Bucket %s Deleted.\n", bucket.getName());    
        } else {    // the bucket was not found
            System.out.printf("Bucket %s NOT Deleted.\n", bucket.getName());    
        }
    }
}
