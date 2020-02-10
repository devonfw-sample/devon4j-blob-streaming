package com.devonfw.example.blobapp.binary.dataaccess.api;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.devonfw.example.blobapp.binary.common.api.BinaryObject;
import com.devonfw.example.blobapp.general.dataaccess.api.ApplicationPersistenceEntity;

import javax.persistence.Column;

/**
 * {@link ApplicationPersistenceEntity Entity} for {@link BinaryObject}. Contains the actual {@link Blob}.
 */
@Entity
@Table(name = "BinaryObject")
public class BinaryObjectEntity extends ApplicationPersistenceEntity implements BinaryObject {

  private static final long serialVersionUID = 1L;

  private Blob data;

  private String mimeType;

  private long size;

  /**
   * The constructor.
   */
  public BinaryObjectEntity() {

    super();
  }

  @Override
  public void setMimeType(String mimeType) {

    this.mimeType = mimeType;

  }

  @Override
  public String getMimeType() {

    return this.mimeType;
  }

  /**
   * @return the {@link Blob} data.
   */
  @Lob
  @Column(name = "content")
  public Blob getData() {

    return this.data;
  }

  /**
   * @param data the data to set
   */
  public void setData(Blob data) {

    this.data = data;
  }

  @Column(name = "filesize")
  @Override
  public long getSize() {

    return this.size;
  }

  @Override
  public void setSize(long size) {

    this.size = size;
  }
}
