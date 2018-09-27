/**
 * CloudStorageList is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform).
 * List the files in a Cloud Storage bucket in a Google Cloud Project.
 * The application uses Application Default Credentials through a JSON service account key for authenticating.
 * The credentials are taken from GOOGLE_APPLICATION_CREDENTIALS environment variable.
 * You must provide 1 parameter:
 * BUCKET_NAME = Name of the bucket
 */

package example;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Blob;

public class CloudStorageList {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar cloudstoragelist.jar <BUCKET_NAME>");
            System.exit(1);
        }

        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // Get a bucket name
        String bucketName = args[0];

        System.out.printf("Bucket name: %s\n", bucketName);

        Bucket bucket = storage.get(bucketName);

        if (bucket != null) {    // Exists the bucket
            System.out.println("Listing objects ...");
            // List a bucket's blobs
            for (Blob blob : bucket.list().iterateAll()) {
                System.out.printf(" - %s\n", blob.getName());
                System.out.println("   " + blob);
            }
            System.out.println("Listed");
        } else {    // Not exists the bucket
            System.out.println("Error: Bucket NOT exists!!");
        }
    }
}
