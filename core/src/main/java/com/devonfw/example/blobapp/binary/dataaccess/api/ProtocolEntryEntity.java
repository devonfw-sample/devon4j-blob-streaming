package com.devonfw.example.blobapp.binary.dataaccess.api;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.devonfw.example.blobapp.binary.common.api.ProtocolEntry;
import com.devonfw.example.blobapp.general.dataaccess.api.ApplicationPersistenceEntity;

/**
 * {@link ApplicationPersistenceEntity Entity} for {@link ProtocolEntry}. Contains the actual {@link Blob}.
 */
@Entity
@Table(name = "ProtocolEntry")
public class ProtocolEntryEntity extends ApplicationPersistenceEntity implements ProtocolEntry {

  private static final long serialVersionUID = 1L;

  private Date timestamp;

  private String text;

  /**
   * @return timestamp
   */
  @Override
  public Date getTimestamp() {

    return this.timestamp;
  }

  /**
   * @param timestamp new value of {@link #gettimestamp}.
   */
  @Override
  public void setTimestamp(Date timestamp) {

    this.timestamp = timestamp;
  }

  /**
   * @return text
   */
  @Override
  public String getText() {

    return this.text;
  }

  /**
   * @param text new value of {@link #gettext}.
   */
  @Override
  public void setText(String text) {

    this.text = text;
  }

}
