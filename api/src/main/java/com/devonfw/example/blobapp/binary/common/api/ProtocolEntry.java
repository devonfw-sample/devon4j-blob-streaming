package com.devonfw.example.blobapp.binary.common.api;

import java.util.Date;

import com.devonfw.example.blobapp.general.common.api.ApplicationEntity;

/**
 * This entity is used for protocol entries, which create rudimentary a business log. It's main purpose is to
 * demonstrate that writing to database and streaming of blobs in the same transaction is working correctly.
 *
 */
public interface ProtocolEntry extends ApplicationEntity {

  /**
   * @return timestampId
   */

  public Date getTimestamp();

  /**
   * @param timestamp setter for timestamp attribute
   */

  public void setTimestamp(Date timestamp);

  /**
   * @return textId
   */

  public String getText();

  /**
   * @param text setter for text attribute
   */

  public void setText(String text);

}
