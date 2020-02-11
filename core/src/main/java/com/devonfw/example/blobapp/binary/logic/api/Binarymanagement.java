package com.devonfw.example.blobapp.binary.logic.api;

import java.io.InputStream;
import java.sql.Blob;

import com.devonfw.example.blobapp.binary.logic.api.to.BinaryObjectEto;

/**
 * Use case for managing BinaryObject.
 *
 */
public interface Binarymanagement {

  /**
   *
   * @param binaryObjectEto the BinaryObjectEto to save
   * @param data the InputStream providing the data for new the binary object
   * @return an ETO for the newly created persistent entity
   */
  BinaryObjectEto saveBinaryObject(BinaryObjectEto binaryObjectEto, InputStream data);

  /**
   * @param binaryObjectId the ID of the {@link BinaryObjectEto} that should be deleted
   */
  void deleteBinaryObject(Long binaryObjectId);

  /**
   * @param binaryObjectId the ID of the {@link BinaryObjectEto} to find
   * @return {@link BinaryObjectEto}
   */
  BinaryObjectEto findBinaryObject(Long binaryObjectId);

  /**
   * @param binaryObjectId the ID of the {@link BinaryObjectEto} the blob corresponds to
   * @return {@link Blob}
   */
  Blob getBinaryObjectBlob(Long binaryObjectId);

}
