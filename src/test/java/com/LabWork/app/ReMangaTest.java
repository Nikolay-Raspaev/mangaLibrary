package com.LabWork.app;

import com.LabWork.app.student.model.Creator;
import com.LabWork.app.student.model.Manga;
import com.LabWork.app.student.model.Reader;
import com.LabWork.app.student.service.CreatorService;
import com.LabWork.app.student.service.MangaService;
import com.LabWork.app.student.service.ReaderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
public class ReMangaTest {
    @Autowired
    CreatorService creatorService;

    @Autowired
    MangaService mangaService;

    @Autowired
    ReaderService readerService;

    private static final Logger log = LoggerFactory.getLogger(ReMangaTest.class);

    @Test
    void testCreator() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangs();
        creatorService.deleteAllCreators();

        Creator c1 = creatorService.addCreator("first", "1");
        Creator c2 = creatorService.addCreator("second", "2");
        Creator c3 = creatorService.addCreator("third", "3");

        Assertions.assertEquals("first", c1.getCreatorName());
        Assertions.assertEquals("second", c2.getCreatorName());
        Assertions.assertEquals("third", c3.getCreatorName());

        Assertions.assertEquals(c1, creatorService.findCreator(c1.getId()));

        creatorService.deleteCreator(c2.getId());

        Assertions.assertEquals(2, creatorService.findAllCreators().size());

        Creator c4 = creatorService.updateCreator(c3.getId(), "fourth", "4");

        Assertions.assertNotEquals(c3.getCreatorName(), c4.getCreatorName());
        Assertions.assertNotEquals(c3.getHashedPassword(), c4.getHashedPassword());

        readerService.deleteAllReaders();
        mangaService.deleteAllMangs();
        creatorService.deleteAllCreators();
    }

    @Test
    void testManga() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangs();
        creatorService.deleteAllCreators();

        Creator c1 = creatorService.addCreator("first", "1");
        Creator c2 = creatorService.addCreator("second", "2");

        Manga m1 = mangaService.addManga(c1, 0, "vagabond");
        Manga m2 = mangaService.addManga(c2, 10, "Berserk");

        Assertions.assertEquals(2, mangaService.findAllMangas().size());

        Assertions.assertEquals(m1.getCreator(), c1);
        Assertions.assertEquals(m2.getCreator(), c2);

        Assertions.assertEquals(c1.getMangas().get(0), m1);
        Assertions.assertEquals(c2.getMangas().get(0), m2);

        Assertions.assertEquals(m1, mangaService.findManga(m1.getId()));
        Assertions.assertEquals(m2, mangaService.findManga(m2.getId()));

        Manga p3 = mangaService.addManga(c1, 10, "Solo Leveling");
        Manga test_manga = mangaService.deleteManga(m1.getId());
        log.info(test_manga.toString());
        log.info(test_manga.getMangaName());
        log.info(creatorService.findCreator(c1.getId()).getMangas().toString());
        Assertions.assertEquals(1, creatorService.findCreator(c1.getId()).getMangas().size());

        Manga p4 = mangaService.updateManga(m2.getId(), 100);

        Assertions.assertNotEquals(m2.getChapterCount(), p4.getChapterCount());

        readerService.deleteAllReaders();
        mangaService.deleteAllMangs();
        creatorService.deleteAllCreators();

    }

    @Test
    void testAddToMangaReader() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangs();
        creatorService.deleteAllCreators();

        Creator c1 = creatorService.addCreator("first_C", "1");

        Manga m1 = mangaService.addManga(c1, 0, "vagabond");
        Manga m2 = mangaService.addManga(c1, 10, "Berserk");

        Reader r1 = readerService.addReader("first_R", "1");
        log.info(r1.getMangas().size() + "");
        readerService.addManga(m1, r1.getId());
        log.info(r1.getMangas().size() + "");
        readerService.addManga(m2, r1.getId());
        log.info(r1.getMangas().toString());
        Reader r2 = readerService.findReader(r1.getId());
        log.info(r2.getMangas().toString());
        Assertions.assertEquals(2, r2.getMangas().size());
        Assertions.assertEquals(1, m1.getReaders().size());
    }

    @Test
    void testRemoveToMangaReader() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangs();
        creatorService.deleteAllCreators();

        Creator c1 = creatorService.addCreator("first_C", "1");

        Manga m1 = mangaService.addManga(c1, 0, "Vagabond");
        Manga m2 = mangaService.addManga(c1, 10, "Berserk");

        Reader r1 = readerService.addReader("first_R", "1");

        log.info(r1.getMangas().size() + "");
        readerService.addManga(m1, r1.getId());
        log.info(r1.getMangas().size() + "");
        readerService.addManga(m2, r1.getId());
        log.info(r1.getMangas().toString());
        Reader r11 = readerService.findReader(r1.getId());
        log.info(r11.getMangas().toString());
        Assertions.assertEquals(2, r11.getMangas().size());
        Assertions.assertEquals(1, m1.getReaders().size());

        Reader r2 = readerService.addReader("second_R", "2");

        readerService.addManga(m1, r2.getId());
        readerService.addManga(m2, r2.getId());
        Reader r22 = readerService.findReader(r2.getId());
        Assertions.assertEquals(2, r22.getMangas().size());
        Assertions.assertEquals(2, m1.getReaders().size());

        log.info(r22.getMangas().toString());

        readerService.removeManga(m1, r22.getId());

        log.info(r22.getMangas().toString());
        Reader r23 = readerService.findReader(r22.getId());
        log.info(r23.getMangas().toString());
        Manga m11 = mangaService.findManga(m1.getId());
        log.info(m11.getReaders().toString());
        log.info(m2.getReaders().toString());

        Assertions.assertEquals(1, r23.getMangas().size());

        log.info(mangaService.findAllMangas().toString());

        readerService.deleteAllReaders();
        mangaService.deleteAllMangs();
        creatorService.deleteAllCreators();

    }

    @Test
    void testReader() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangs();
        creatorService.deleteAllCreators();


        Creator c1 = creatorService.addCreator("first_C", "1");
        Creator c2 = creatorService.addCreator("second_C", "2");

        Manga m1 = mangaService.addManga(c1, 0, "vagabond");
        Manga m2 = mangaService.addManga(c2, 10, "Berserk");

        Assertions.assertEquals(2, mangaService.findAllMangas().size());

        Reader r1 = readerService.addReader("first_R", "1");
        Reader r2 = readerService.addReader("second_R", "2");
        readerService.addManga(m1, r1.getId());
        readerService.addManga(m2, r2.getId());
        r1 = readerService.findReader(r1.getId());
        log.info(r1.getMangas().get(0).getCreator().toString());
        log.info(c1.toString());
        r2 = readerService.findReader(r2.getId());
        Assertions.assertEquals(c1, r1.getMangas().get(0).getCreator());
        Assertions.assertEquals(c2, r2.getMangas().get(0).getCreator());


        Reader r3 = readerService.addReader("third_R", "3");

        log.info(m1.getReaders().toString());
        log.info(c1.getId().toString());
        Assertions.assertEquals(r1, m1.getReaders().get(0));

        Reader r4 = readerService.updateReader(r3.getId(), "fourth_R", "3");

        Assertions.assertNotEquals(r3.getReaderName(), r4.getReaderName());
        Assertions.assertEquals(r3.getHashedPassword(), r4.getHashedPassword());


        readerService.deleteAllReaders();
        mangaService.deleteAllMangs();
        creatorService.deleteAllCreators();
    }
}
