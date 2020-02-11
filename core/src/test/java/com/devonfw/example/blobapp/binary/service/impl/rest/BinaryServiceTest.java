package com.devonfw.example.blobapp.binary.service.impl.rest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.jaxrs.provider.MultipartProvider;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import com.devonfw.example.blobapp.binary.logic.api.to.BinaryObjectEto;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * @author simon
 *
 */
public class BinaryServiceTest {
  private final static String BASE_URL = "http://localhost:8081/services/rest/binary/v1/";

  private final static String BINARY_OBJ_ID = "10";

  private final static List<Object> PROVIDERS = Arrays
      .asList(new Object[] { new JacksonJaxbJsonProvider(), new MultipartProvider() });

  public WebClient getClient() {

    return WebClient.create(BASE_URL, PROVIDERS);
  }

  @Test
  public void testGetBinaryObjectWithoutBlob() {

    BinaryObjectEto eto = getClient().type(MediaType.APPLICATION_JSON).path("binaryobject/" + BINARY_OBJ_ID)
        .get(BinaryObjectEto.class);
    assertEquals(BINARY_OBJ_ID, "" + eto.getId());
  }

  @Test
  public void testGetBinaryObject() throws IOException {

    MultipartBody result = getClient().path("binaryobject/multipart/" + BINARY_OBJ_ID).get(MultipartBody.class);
    BinaryObjectEto eto = result.getAttachment("root").getObject(BinaryObjectEto.class);
    assertEquals(BINARY_OBJ_ID, "" + eto.getId());

    byte[] dataArr = IOUtils.readBytesFromStream(result.getAttachment("image").getDataHandler().getInputStream());
    assertEquals(eto.getSize(), dataArr.length);
  }

  @Test
  public void testSaveBinaryObject() throws IOException, URISyntaxException {

    Path imagePath = Paths.get(this.getClass().getResource("/devon.png").toURI());
    BinaryObjectEto eto = createBinaryObjetEto("image/png", Files.size(imagePath));

    List<Attachment> attachments = createAttachments(imagePath, eto);

    BinaryObjectEto resultEto = getClient().type("multipart/mixed").accept("application/json").path("binaryobject/")
        .post(attachments, BinaryObjectEto.class);
    assertTrue(resultEto.getId() > 0);
    assertEquals(resultEto.getSize(), Files.size(imagePath));
  }

  /**
   * @param imagePath
   * @return
   * @throws IOException
   */
  private BinaryObjectEto createBinaryObjetEto(String mimeType, long size) throws IOException {

    BinaryObjectEto eto = new BinaryObjectEto();
    eto.setMimeType("image/tiff");
    eto.setSize(size);
    return eto;
  }

  /**
   * @param imagePath
   * @param eto
   * @return
   * @throws IOException
   */
  private List<Attachment> createAttachments(Path imagePath, BinaryObjectEto eto) throws IOException {

    List<Attachment> attachments = new LinkedList<>();
    attachments.add(new Attachment("root", MediaType.APPLICATION_JSON, eto));
    attachments.add(new Attachment("image", MediaType.APPLICATION_OCTET_STREAM,
        Files.newInputStream(imagePath, StandardOpenOption.READ)));
    return attachments;
  }

  @Test
  public void testSaveUploadDeleteBinaryObject() throws IOException, URISyntaxException {

    // Step 1: Upload image from file
    Path imagePath = Paths.get(this.getClass().getResource("/devon.png").toURI());
    BinaryObjectEto myEto = createBinaryObjetEto("image/png", Files.size(imagePath));

    List<Attachment> attachments = createAttachments(imagePath, myEto);

    BinaryObjectEto resultEto = getClient().type("multipart/mixed").accept("application/json").path("binaryobject/")
        .post(attachments, BinaryObjectEto.class);
    assertTrue(resultEto.getId() > 0);

    // Step 2: Download uploaded image via id
    MultipartBody result2 = getClient().path("binaryobject/multipart/" + resultEto.getId()).get(MultipartBody.class);
    BinaryObjectEto resultEto2 = result2.getAttachment("root").getObject(BinaryObjectEto.class);
    assertTrue(resultEto2.getId() > 0);

    byte[] resultBytes2 = IOUtils.readBytesFromStream(result2.getAttachment("image").getDataHandler().getInputStream());
    assertEquals(resultEto2.getSize(), resultBytes2.length);
    assertEquals(resultEto2.getSize(), resultEto.getSize());
    assertArrayEquals(resultBytes2, Files.readAllBytes(imagePath));

    // Step 3: Deleted uploaded image via id
    Response response3 = getClient().type("application/json").accept("application/json")
        .path("binaryobject/" + resultEto.getId()).delete();
    assertEquals(Status.NO_CONTENT, response3.getStatusInfo().toEnum());
    Response responseGet3 = getClient().type(MediaType.APPLICATION_JSON).path("binaryobject/" + resultEto.getId())
        .get();
    // Should be Status.NOT_FOUND, see https://github.com/devonfw/devon4j/pull/207
    // assertEquals(Status.NOT_FOUND, responseGet3.getStatusInfo().toEnum());
    assertEquals(Status.INTERNAL_SERVER_ERROR, responseGet3.getStatusInfo().toEnum());

  }

}
