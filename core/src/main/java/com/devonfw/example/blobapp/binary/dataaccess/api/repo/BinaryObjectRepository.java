package com.devonfw.example.blobapp.binary.dataaccess.api.repo;

import com.devonfw.example.blobapp.binary.dataaccess.api.BinaryObjectEntity;
import com.devonfw.example.blobapp.binary.dataaccess.impl.CustomBinaryObjectRepository;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;

/**
 * {@link DefaultRepository} for {@link BinaryObjectEntity}.
 */
public interface BinaryObjectRepository extends DefaultRepository<BinaryObjectEntity>, CustomBinaryObjectRepository {

}
