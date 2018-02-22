/**
 * CloudStorageCreate is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform)
 * Create a new Google Storage bucket for a Google Cloud Project.
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

public class CloudStorageCreate {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Not enough parameters. Proper Usage is: java -jar cloudstoragecreate.jar <BUCKET_NAME>");
            System.exit(1);
        }

        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // The name for the new bucket
        String bucketName = args[0];

        // Creates the new bucket
        Bucket bucket = storage.create(BucketInfo.of(bucketName));

        System.out.printf("Bucket %s created.\n", bucket.getName());
    }
}
