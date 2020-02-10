package com.devonfw.example.blobapp.binary.service.impl.rest;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devonfw.example.blobapp.binary.logic.api.Binarymanagement;
import com.devonfw.example.blobapp.binary.logic.api.to.BinaryObjectEto;
import com.devonfw.example.blobapp.binary.service.api.rest.BinaryRestService;

/**
 * The service implementation for REST calls in order to execute the logic of component {@link Binarymanagement}.
 */
@Named
public class BinaryRestServiceImpl implements BinaryRestService {

  @Inject
  private Binarymanagement binarymanagement;

  @Override
  public BinaryObjectEto getBinaryObject(long id) {

    try {
      return this.binarymanagement.findBinaryObject(id);
    } catch (EmptyResultDataAccessException e) {
      throw new NotFoundException();
    }
  }

  @Override
  public void deleteBinaryObject(long id) {

    this.binarymanagement.deleteBinaryObject(id);
  }

  // tag::getBinaryObjectMultipart[]
  @Override
  public MultipartBody getBinaryObjectMultipart(long id) throws SQLException {

    BinaryObjectEto binaryObject = this.binarymanagement.findBinaryObject(id);
    Blob binaryObjectBlob = this.binarymanagement.getBinaryObjectBlob(id);
    List<Attachment> atts = Arrays.asList(new Attachment("root", MediaType.APPLICATION_JSON, binaryObject),
        new Attachment("image", MediaType.APPLICATION_OCTET_STREAM, binaryObjectBlob.getBinaryStream()));
    return new MultipartBody(atts, true);
  }
  // end::getBinaryObjectMultipart[]

  @Override
  public BinaryObjectEto saveBinaryObject(BinaryObjectEto binaryObjectEto, InputStream data) {

    return this.binarymanagement.saveBinaryObject(binaryObjectEto, data);

  }

}