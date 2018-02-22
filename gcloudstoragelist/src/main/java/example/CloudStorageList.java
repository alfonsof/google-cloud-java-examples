/**
 * CloudStorageList is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform)
 * List the files in a Cloud Storage bucket for a Google Cloud Project.
 * The application uses Application Default Credentials through a JSON service account key for authenticating.
 * The credentials are taken from GOOGLE_APPLICATION_CREDENTIALS environment variable.
 * You must use 1 parameter:
 * BUCKET = Name of bucket
 */

package example;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Blob;

public class CloudStorageList {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Not enough parameters. Proper Usage is: java -jar cloudstoragelistall.jar <BUCKET_NAME>");
            System.exit(1);
        }

        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // Get a bucket name
        String bucketName = args[0];

        Bucket bucket = storage.get(bucketName);

        if (bucket == null) {
            // Not exists the bucket
            System.out.println("No such bucket");
            System.exit(1);
        }

        // list a bucket's blobs
        System.out.printf("Bucket %s - file list:\n", bucketName);
        for (Blob blob : bucket.list().iterateAll()) {
            System.out.printf("File %s:\n", blob.getName());
            System.out.println(blob);
        }
    }
}
