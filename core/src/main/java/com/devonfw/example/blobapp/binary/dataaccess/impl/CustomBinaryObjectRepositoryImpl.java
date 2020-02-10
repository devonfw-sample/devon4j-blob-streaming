package com.devonfw.example.blobapp.binary.dataaccess.impl;

import java.io.InputStream;
import java.sql.Blob;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.LobHelper;
import org.hibernate.internal.SessionImpl;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import com.devonfw.example.blobapp.binary.dataaccess.api.BinaryObjectEntity;

/**
 * Custom Spring Data repository to handle saving of blobs as stream.
 *
 */
// tag::class[]
public class CustomBinaryObjectRepositoryImpl implements CustomBinaryObjectRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public BinaryObjectEntity save(BinaryObjectEntity binaryObjectEntity, InputStream data) {

    LobHelper lh = this.entityManager.unwrap(SessionImpl.class).getLobHelper();
    Blob blob = lh.createBlob(data, binaryObjectEntity.getSize());
    binaryObjectEntity.setData(blob);
    JpaEntityInformation entityInformation = JpaEntityInformationSupport.getEntityInformation(BinaryObjectEntity.class,
        this.entityManager);
    if (entityInformation.isNew(binaryObjectEntity)) {
      this.entityManager.persist(binaryObjectEntity);
      return binaryObjectEntity;
    } else {
      return this.entityManager.merge(binaryObjectEntity);
    }

  }

}
// end::class[]