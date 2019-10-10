package com.ss4.opencampus.backend.database.uspots;

/**
 * Strict naming convention!! Had to have this be called the EXACT same as my custom interface except I had
 * to replace the word "Custom" with "Impl"
 *
 * This would not pass the maven tests if named differently.
 */
public class USpotRepositoryImpl implements USpotRepositoryCustom
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
