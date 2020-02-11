package com.devonfw.example.blobapp.binary.dataaccess.impl;

import java.io.InputStream;

import com.devonfw.example.blobapp.binary.dataaccess.api.BinaryObjectEntity;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;

/**
 * {@link DefaultRepository} for {@link BinaryObjectEntity}.
 */
public interface CustomBinaryObjectRepository {

  /**
   * @param binaryObjectEntity
   * @param data
   * @return
   */
  public BinaryObjectEntity save(BinaryObjectEntity binaryObjectEntity, InputStream data);

}
