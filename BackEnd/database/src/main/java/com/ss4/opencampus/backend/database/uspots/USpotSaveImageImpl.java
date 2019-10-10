package com.ss4.opencampus.backend.database.uspots;

public class USpotSaveImageImpl implements USpotRepositorySaveImage
{
  // Do these towards the end. Most likely going to have errors :).
  // Probably need Mockitio tests or something??
  // Do not know what to return yet. Could be simple as boolean letting us know of success
  public void saveImage()
  {
    // TODO: Method to save an image from the Client to a distinct path on the server

    /*
    Probably going to get a Byte Array from Client that I will have to save as a file(?) in a specific
    location. Will then update the USpot with the directory to that image.
     */
  }

  /*
  Will also probably need a method that when given an exact path, grabs the file (picture) and converts it to
  byte array and sends the array back to the Client.
   */
}
