package com.devonfw.example.blobapp.binary.service.api.rest;

import java.io.InputStream;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import com.devonfw.example.blobapp.binary.logic.api.to.BinaryObjectEto;

/**
 * The service interface for REST calls in order to execute the logic of component {@link General}.
 */
@Path("/binary/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface BinaryRestService {

  /**
   * Delegates to {@link Binarymanagement#findBinaryObject}.
   *
   * @param id the ID of the {@link BinaryObjectEto}
   * @return the {@link BinaryObjectEto}
   */
  @GET
  @Path("/binaryobject/{id}/")
  public BinaryObjectEto getBinaryObject(@PathParam("id") long id);

  // tag::getBinaryObjectMultipart[]
  /**
   * Get (download) the BinaryObject and data stream as multipart.
   *
   * @param id the ID of the {@link BinaryObjectEto}
   * @return Multipart body containing the {@link BinaryObjectEto} (Part root) and the data stream (Part image)
   * @throws SQLException
   */
  @GET
  @Path("/binaryobject/multipart/{id}/")
  @Produces("multipart/mixed")
  public MultipartBody getBinaryObjectMultipart(@PathParam("id") long id) throws SQLException;
  // end::getBinaryObjectMultipart[]

  // tag::saveBinaryObject[]
  /**
   * Save (upload) a new BinaryObject.
   *
   * @param binaryObjectEto the {@link BinaryObjectEto}
   * @param data the stream for the data for the new BinaryObject.
   * @return the {@link BinaryObjectEto}
   * @throws SQLException
   */
  @Consumes("multipart/mixed")
  @POST
  @Path("/binaryobject/")
  public BinaryObjectEto saveBinaryObject(
      @Multipart(value = "root", type = MediaType.APPLICATION_JSON) BinaryObjectEto binaryObjectEto,
      @Multipart(value = "image", type = MediaType.APPLICATION_OCTET_STREAM) InputStream data);
  // end::saveBinaryObject[]

  /**
   * Delete a {@link BinaryObjectEto} with the given id.
   *
   * @param id the ID of the {@link BinaryObjectEto}
   */
  @DELETE
  @Path("/binaryobject/{id}/")
  public void deleteBinaryObject(@PathParam("id") long id);

}