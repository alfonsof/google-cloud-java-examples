# Java examples on Google Cloud Platform (GCP)

This repo contains Java code examples on Google Cloud Platform (GCP).

These examples show how to use Java 8 and Google Cloud Client Library for Java in order to manage services on Google Cloud Platform.

Google Cloud Client Library for Java allows Java developers to write software that makes use of Google Cloud services like Compute Engine and Cloud Storage.

## Quick start

You must have a [Google Cloud Platform (GCP)](http://cloud.google.com/) account.

The code for the samples is contained in individual folders on this repository.

For instructions on running the code, please consult the README in each folder.

This is the list of examples:

**Compute - Compute Engine:**

* [gcloudcomputeengine](/gcloudcomputeengine) - Google Cloud Compute Engine VM instances: Example of how to handle Compute Engine VM instances.

**Storage - Cloud Storage:**

* [gcloudstoragecreate](/gcloudstoragecreate) - Google Cloud Storage Create: Example of how to handle Cloud Storage buckets and create a new Google Storage bucket in a Google Cloud Project.
* [gcloudstoragedelete](/gcloudstoragedelete) - Google Cloud Storage Delete: Example of how to handle Cloud Storage buckets and delete a Google Storage bucket in a Google Cloud Project.
* [gcloudstoragelist](/gcloudstoragelist) - Google Cloud Storage List: Example of how to handle Cloud Storage buckets and list information about the objects in a Cloud Storage bucket in a Google Cloud Project.
* [gcloudstoragelistall](/gcloudstoragelistall) - Google Cloud Storage List All: Example of how to handle Cloud Storage buckets and list information about all Cloud Storage buckets and the objects that they contain in a Google Cloud Project. The credentials are taken from GOOGLE_APPLICATION_CREDENTIALS environment variable.
* [gcloudstoragelistallparam](/gcloudstoragelistallparam) - Google Cloud Storage List All Parameters: Example of how to handle Cloud Storage buckets and list all Cloud Storage buckets and the files they contain in a Google Cloud Project. The user must provide the credentials using the application parameters.
* [gcloudstorageupload](/gcloudstorageupload) - Google Cloud Storage Upload: Example of how to handle Cloud Storage buckets and upload a local file to a Cloud Storage bucket in a Google Cloud Project.
* [gcloudstoragedownload](/gcloudstoragedownload) - Google Cloud Storage Download: Example of how to handle Cloud Storage buckets and download an object in a Cloud Storage bucket in a Google Cloud Project to a local file.
* [gcloudstoragedeleteobject](/gcloudstoragedeleteobject) - Google Cloud Storage Delete Object: Example of how to handle Cloud Storage buckets and delete an object in a Google Storage bucket in a Google Cloud Project.
* [gcloudstoragecopy](/gcloudstoragecopy) - Google Cloud Storage Copy: Example of how to handle Cloud Storage buckets and copy an object from a Google Storage bucket to another Google Storage bucket in a Google Cloud Project.
* [gcloudstoragemove](/gcloudstoragemove) - Google Cloud Storage Move: Example of how to handle Cloud Storage buckets and move an object from a Google Storage bucket to another Google Storage bucket in a Google Cloud Project.

## License

This code is released under the MIT License. See LICENSE file.