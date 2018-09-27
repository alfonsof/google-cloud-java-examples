/**
 * CloudStorageCreate is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform).
 * Create a new Google Storage bucket in a Google Cloud Project.
 * The application uses Application Default Credentials through a JSON service account key for authenticating.
 * The credentials are taken from GOOGLE_APPLICATION_CREDENTIALS environment variable.
 * You must provide 1 parameter:
 * BUCKET_NAME = Name of the bucket
 */

package example;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class CloudStorageCreate {

    private static final String STORAGE_CLASS  = "regional";      // Cloud Storage class
    // See here for storage class possible values: http://g.co/cloud/storage/docs/storage-classes
    private static final String STORAGE_LOCATION  = "us-east1";   // Cloud Storage location
    // See here for storage location possible values: http://g.co/cloud/storage/docs/bucket-locations#location-mr

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar cloudstoragecreate.jar <BUCKET_NAME>");
            System.exit(1);
        }

        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // The name for the new bucket
        String bucketName = args[0];

        System.out.printf("Bucket name: %s\n", bucketName);

        Bucket bucketNow = storage.get(bucketName);

        if (bucketNow == null) {  // Not exists the bucket
            System.out.println("Creating bucket ...");
            // Creates the new bucket
            Bucket bucket = storage.create(BucketInfo.newBuilder(bucketName)
                    .setStorageClass(STORAGE_CLASS)
                    .setLocation(STORAGE_LOCATION)
                    .build());
            System.out.println("Created");
            System.out.printf("Bucket: %s\n", bucket.getName());
        } else {
            System.out.println("Error: Bucket already exists!!");
        }
    }
}
