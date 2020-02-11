package com.devonfw.example.blobapp.binary.common.api;

import com.devonfw.example.blobapp.general.common.api.ApplicationEntity;

/**
 * This is the interface for a {@link BinaryObject}.
 */
public interface BinaryObject extends ApplicationEntity {

  /**
   * @param mimeType is the MIME-Type of this {@link BinaryObject}
   */
  void setMimeType(String mimeType);

  /**
   * Returns MIME-Type of thie {@link BinaryObject}
   *
   * @return the MIME-Type, e.g image/jpeg
   */
  String getMimeType();

  /**
   * @return Returns the size in bytes
   */
  long getSize();

  /**
   * Sets the size of bytes
   *
   * @param size the size in bytes
   */
  void setSize(long size);

}
