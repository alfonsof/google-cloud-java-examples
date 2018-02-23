# Google Cloud Storage List All Parameters Java example

This folder contains a Java application example that handles Cloud Storage buckets on GCP (Google Cloud Platform).

List all Cloud Storage buckets and the files they contain for a Google Cloud Project.

The application uses Application Default Credentials through a JSON service account key for authenticating.

The user must provide the credentials using the application parameters.




## Requirements

* You must have a [Google Cloud Platform (GCP) account](http://cloud.google.com/).

* You can install and use the Google Cloud SDK.

  The Google Cloud SDK is a set of tools for Google Cloud Platform.
  It contains gcloud, gsutil, and bq, which you can use to access Google Compute Engine, Google Cloud Storage, Google BigQuery,
  and other products and services from the command line. You can run these tools interactively or in your automated scripts.

* This example uses the Google Cloud Client Library for Java.

  The Google Cloud Client Library for Java is the idiomatic way for Java developers to integrate with Google Cloud Platform services,
  like Cloud Datastore and Cloud Storage. You can install the package for an individual API using Maven, Gradle, SBT, etc.

This code was written for Java 1.8 and Google Cloud Client Library for Java.




## Using the code

* Configure your Google Cloud access keys.

  The Google Cloud client library for Java allows you to use several authentication schemes.

  The application uses Application Default Credentials through a JSON service account key for authenticating.

  The user must provide the credentials using the application parameters.

  For example:
  
  ```
  GOOGLE_APPLICATION_CREDENTIALS = /path/to/my/key.json
  ```

  Use the [Google Cloud Platform console](http://cloud.google.com/):

  * Go to the Google Cloud Project. 

  * Prepare the credentials:
    * Create a Service account.
    
      For example:
      ```
      Name: gcloud-java-examples
      Role: Owner
      Service account ID: gcloud-java-examples@gcloud-java-examples.iam.gserviceaccount.com)
      ```

    * Create a key as a JSON file and download it.

    * Add the Service accounts id (Ex.: gcloud-java-examples@gcloud-java-examples.iam.gserviceaccount.com) as a member of the project in the IAM.
    
* Create a Google Cloud Storage bucket.

* Copy some files to the Google Cloud Storage bucket.

* Run the code:

  You must provide 2 parameters:

  <CREDENTIALS_FILE_NAME> = Path and name of the JSON credential file

  <PROJECT_ID> = Name of the Google Cloud Project

  ```
  java -jar out/artifacts/gcloudstoragelistallparam_jar/gcloudstoragelistallparam.jar ~/.gcloud/gcloud-java-examples-45b588704dbf.json gcloud-java-examples
  ```

* Test the application:

  You should see the list of all the files in every Cloud Storage bucket in a Google Cloud Project.