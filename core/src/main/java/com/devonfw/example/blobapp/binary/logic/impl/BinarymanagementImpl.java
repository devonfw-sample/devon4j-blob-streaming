package com.devonfw.example.blobapp.binary.logic.impl;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.devonfw.example.blobapp.binary.dataaccess.api.BinaryObjectEntity;
import com.devonfw.example.blobapp.binary.dataaccess.api.ProtocolEntryEntity;
import com.devonfw.example.blobapp.binary.dataaccess.api.repo.BinaryObjectRepository;
import com.devonfw.example.blobapp.binary.dataaccess.api.repo.ProtocolRepository;
import com.devonfw.example.blobapp.binary.logic.api.to.BinaryObjectEto;
import com.devonfw.example.blobapp.general.logic.base.AbstractComponentFacade;

/**
 * @author simon
 *
 */
@Named
@Transactional
public class BinarymanagementImpl extends AbstractComponentFacade
    implements com.devonfw.example.blobapp.binary.logic.api.Binarymanagement {

  @Inject
  private BinaryObjectRepository binaryObjectRepository;

  @Inject
  private ProtocolRepository protocolRepository;

  @Override
  public void deleteBinaryObject(Long binaryObjectId) {

    this.binaryObjectRepository.deleteById(binaryObjectId);
  }

  @Override
  public BinaryObjectEto findBinaryObject(Long binaryObjectId) {

    createProtocolEntity("Read binary object " + binaryObjectId);
    return getBeanMapper().map(this.binaryObjectRepository.find(binaryObjectId), BinaryObjectEto.class);
  }

  @Override
  public Blob getBinaryObjectBlob(Long binaryObjectId) {

    return this.binaryObjectRepository.find(binaryObjectId).getData();
  }

  private ProtocolEntryEntity createProtocolEntity(String text) {

    ProtocolEntryEntity protocolEty = new ProtocolEntryEntity();
    protocolEty.setTimestamp(Calendar.getInstance().getTime());
    protocolEty.setText(text);
    return this.protocolRepository.save(protocolEty);
  }

  @Override
  public BinaryObjectEto saveBinaryObject(BinaryObjectEto binaryObjectEto, InputStream data) {

    BinaryObjectEntity binaryObjectEntity = getBeanMapper().map(binaryObjectEto, BinaryObjectEntity.class);
    this.binaryObjectRepository.save(binaryObjectEntity, data);
    return getBeanMapper().map(binaryObjectEntity, BinaryObjectEto.class);
  }

}
