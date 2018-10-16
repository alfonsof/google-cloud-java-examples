/**
 * CloudStorageDeleteObject is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform)
 * Delete an object in a Google Storage bucket in a Google Cloud Project.
 * The application uses Application Default Credentials through a JSON service account key for authenticating.
 * The credentials are taken from GOOGLE_APPLICATION_CREDENTIALS environment variable.
 * You must provide 1 parameter:
 * BUCKET_NAME = Name of the bucket
 * OBJECT_NAME = Name of the object in the bucket
 */

package example;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class CloudStorageDeleteObject {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar cloudstoragedeleteobject.jar <BUCKET_NAME> <OBJECT_NAME>");
            System.exit(1);
        }

        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // The name for the bucket
        String bucketName = args[0];

        // The name for the object
        String blobName = args[1];

        System.out.printf("Bucket name: %s\n", bucketName);
        System.out.printf("Object name: %s\n", blobName);

        Blob blob = storage.get(bucketName, blobName);

        if (blob != null) {   // Exists bucket/object
            System.out.println("Deleting object ...");
            // Delete the bucket
            boolean deleted = blob.delete(Blob.BlobSourceOption.generationMatch());
            if (deleted) {  // the bucket was deleted
                System.out.printf("Object \"%s\" Deleted\n", blobName);
            } else {    // the bucket was not found
                System.out.printf("Error: Object \"%s\" NOT Deleted.\n", blobName);
            }
        } else {   // Not exists the bucket
            System.out.println("Error: Bucket/Object does not exist!!");
        }
    }
}
