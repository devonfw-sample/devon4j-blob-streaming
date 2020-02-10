package com.devonfw.example.blobapp.binary.dataaccess.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.LobHelper;
import org.hibernate.internal.SessionImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;

import com.devonfw.example.blobapp.SpringBootApp;
import com.devonfw.example.blobapp.binary.dataaccess.api.BinaryObjectEntity;
import com.devonfw.example.blobapp.binary.dataaccess.api.repo.BinaryObjectRepository;
import com.devonfw.module.test.common.base.SubsystemDbTest;

/**
 * This class contains test for the persistence layer.
 *
 */
@SpringBootTest(classes = { SpringBootApp.class }, webEnvironment = WebEnvironment.NONE)
public class DbTest extends SubsystemDbTest {

  @PersistenceContext
  private EntityManager entityManager;

  @Inject
  private BinaryObjectRepository repo;

  /**
   * This tests load a existing binary file into the database. You may also modify this test for loading different
   * binary files into your database for further manual testing. To make this work this test method is annotated
   * as @Rollback(false) to assure that loaded test data is committed to the database.
   *
   * @throws Exception if an error occurs
   */
  @Test
  @Transactional
  @Rollback(false)
  public void testInsertImage() throws Exception {

    Path inputPath = Paths.get(DbTest.class.getResource("/devon.png").toURI());
    BinaryObjectEntity obj = new BinaryObjectEntity();
    LobHelper lh = this.entityManager.unwrap(SessionImpl.class).getLobHelper();
    Blob blob = lh.createBlob(Files.newInputStream(inputPath), Files.size(inputPath));
    obj.setData(blob);
    obj.setSize(Files.size(inputPath));
    obj.setMimeType("image/png");
    this.repo.save(obj);
    System.out.println("Saved entity with ID " + obj.getId());
  }

}
