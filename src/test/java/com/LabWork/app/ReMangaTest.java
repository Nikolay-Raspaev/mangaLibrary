package com.LabWork.app;

import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.model.Default.Manga;
import com.LabWork.app.MangaStore.model.Default.Reader;
import com.LabWork.app.MangaStore.service.CreatorService;
import com.LabWork.app.MangaStore.service.MangaService;
import com.LabWork.app.MangaStore.service.ReaderService;
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
    void testReaderRemove2() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first_C", "1");
        Manga m1 = mangaService.addManga(c1.getId(), 0, "Vagabond");
        Manga m2 = mangaService.addManga(c1.getId(), 10, "Berserk");
        Reader r1 = readerService.addReader("first_R", "1");

        readerService.addManga(m1.getId(), r1.getId());
        readerService.addManga(m2.getId(), r1.getId());

        Reader r11 = readerService.findReader(r1.getId());
        readerService.deleteReader(r11.getId());

        log.info(readerService.findAllReaders().toString());
        mangaService.deleteAllMangas();
        readerService.deleteAllReaders();
        creatorService.deleteAllCreators();
    }

    @Test
    void testAddToMangaReader2() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first_C", "1");
        Manga m1 = mangaService.addManga(c1.getId(), 0, "vagabond");
        Reader r1 = readerService.addReader("first_R", "1");
        Reader r2 = readerService.addReader("2", "2");
        Reader r3 = readerService.addReader("3", "3");

        readerService.addManga(m1.getId(), r1.getId());
        readerService.addManga(m1.getId(), r2.getId());

        log.info(r1.getMangas().toString());

        Reader r4 = readerService.findReader(r1.getId());
        log.info(r4.getMangas().toString());
        Assertions.assertEquals(2, mangaService.getReader(m1.getId()).size());
        Assertions.assertEquals(1, mangaService.findAllMangas().size());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }
    @Test
    void testCreatorAddAndFind() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first", "1");
        Creator c2 = creatorService.addCreator("second", "2");
        Assertions.assertEquals(c1.getCreatorName(), creatorService.findCreator(c1.getId()).getCreatorName());
        Assertions.assertEquals(c1, creatorService.findCreator(c1.getId()));
        Assertions.assertEquals(2, creatorService.findAllCreators().size());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

    @Test
    void test() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first", "1");
        Manga m1 = mangaService.addManga(c1.getId(), 0, "Vagabond");
        Manga m2 = mangaService.addManga(c1.getId(), 10, "Berserk");
        Manga m3 = mangaService.addManga(c1.getId(), 0, "Manga_3");
        Creator c2 = creatorService.findCreator(c1.getId());
        Assertions.assertEquals(3, c2.getMangas().size());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

/*    @Test
    void testCreatorAddManga() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangs();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first", "1");
        Manga m1 = mangaService.addManga(c1, 0, "Vagabond");
        Manga m2 = mangaService.addManga(c1, 10, "Berserk");
        c1 = creatorService.findCreator(c1.getId());
        creatorService.addManga(c1.getId(), m1);
        creatorService.addManga(c1.getId(), m2);
        log.info(c1.getMangas().toString());
        Assertions.assertEquals(2, c1.getMangas().size());
        log.info(m1.getCreator().toString());
        log.info(c1.toString());
        Assertions.assertEquals(c1.getCreatorName(), m1.getCreator().getCreatorName());
    }*/

/*    //бесполезня штука
    @Test
    void testCreatorDeleteManga() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangs();
        creatorService.deleteAllCreators();

        Creator c1 = creatorService.addCreator("first", "1");
        Creator c2 = creatorService.addCreator("second", "2");

        Manga m1 = mangaService.addManga(c1, 0, "Vagabond");
        Manga m2 = mangaService.addManga(c1, 10, "Berserk");
        Manga m3 = mangaService.addManga(c2, 0, "Manga_3");

        creatorService.addManga(c1.getId(), m1);
        creatorService.addManga(c1.getId(), m2);

        creatorService.deleteManga(c1.getId(), m2);

        c1 = creatorService.findCreator(c1.getId());

        log.info(c1.getMangas().toString());
        log.info(mangaService.findAllMangas().toString());

        Assertions.assertEquals(1, c1.getMangas().size());

    }*/
/*тестстим работоспособность гита*/
    @Test
    void testCreatorUpdated() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first", "1");
        Creator c2 = creatorService.updateCreator(c1.getId(),"second", "1");
        Creator c3 = creatorService.findCreator(c1.getId());
        log.info(c3.toString());
        Assertions.assertNotEquals(c1.getCreatorName(), c2.getCreatorName());
        Assertions.assertEquals(c1.getHashedPassword(), c2.getHashedPassword());
        Assertions.assertEquals(c3.getCreatorName(), c2.getCreatorName());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

    @Test
    void testCreatorDelete() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first", "1");
        Creator c2 = creatorService.addCreator("second", "2");
        creatorService.deleteCreator(c2.getId());
        log.info(creatorService.findAllCreators().toString());
        Assertions.assertEquals(1, creatorService.findAllCreators().size());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

    @Test
    void testCreatorMangaDelete() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first", "1");
        Manga m1 = mangaService.addManga(c1.getId(), 0, "Vagabond");
        Manga m2 = mangaService.addManga(c1.getId(), 10, "Berserk");
        creatorService.deleteCreator(c1.getId());
        log.info(mangaService.findAllMangas().toString());
        Assertions.assertEquals(0, mangaService.findAllMangas().size());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

    @Test
    void testCreatorDeleteAll() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first", "1");
        Creator c2 = creatorService.addCreator("second", "2");
        creatorService.deleteAllCreators();
        Assertions.assertEquals(0, creatorService.findAllCreators().size());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

    @Test
    void testCreatorDeleteMangaReader() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first", "1");
        Manga m1 = mangaService.addManga(c1.getId(), 0, "Vagabond");
        Manga m2 = mangaService.addManga(c1.getId(), 10, "Berserk");
        Reader r1 = readerService.addReader("1", "1");
        readerService.addManga(m1.getId(), r1.getId());
        readerService.addManga(m2.getId(), r1.getId());
        creatorService.deleteCreator(c1.getId());
        log.info(mangaService.findAllMangas().toString());
        Assertions.assertEquals(0, mangaService.findAllMangas().size());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

    @Test
    void testMangaAddAndFind() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first", "1");
        Manga m1 = mangaService.addManga(c1.getId(), 0, "Vagabond");
        Manga m2 = mangaService.addManga(c1.getId(), 10, "Berserk");
        c1 = creatorService.findCreator(c1.getId());
        m1 = mangaService.findManga(m1.getId());
        log.info(c1.getMangas().toString());
        Assertions.assertEquals(2, c1.getMangas().size());
        Assertions.assertEquals(c1.getCreatorName(), m1.getCreator().getCreatorName());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

    @Test
    void testMangaUpdated() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first", "1");
        Manga m1 = mangaService.addManga(c1.getId(), 0, "Vagabond");
        Manga m2 = mangaService.updateManga(m1.getId(), 10);
        m2 = mangaService.findManga(m2.getId());
        c1 = creatorService.findCreator(c1.getId());
        log.info(m2.toString());
        Assertions.assertEquals(m1.getMangaName(), m2.getMangaName());
        Assertions.assertNotEquals(m1.getChapterCount(), m2.getChapterCount());
        Assertions.assertEquals(c1.getMangas().get(0).getChapterCount(), m2.getChapterCount());
        Assertions.assertEquals(10, m2.getChapterCount());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

    @Test
    void testMangaDelete() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first", "1");
        Manga m1 = mangaService.addManga(c1.getId(), 0, "Vagabond");
        Manga m2 = mangaService.addManga(c1.getId(), 10, "Berserk");
        Reader r1 = readerService.addReader("reader1", "password1");

        readerService.addManga(m1.getId(), r1.getId());
        readerService.addManga(m2.getId(), r1.getId());
        mangaService.deleteManga(m1.getId());
        r1 = readerService.findReader(r1.getId());

        log.info(mangaService.findAllMangas().toString());
        log.info(readerService.findAllReaders().toString());

        Assertions.assertEquals(1, mangaService.findAllMangas().size());
        Assertions.assertEquals(1, r1.getMangas().size());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

    @Test
    void testMangaDeleteAll() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first", "1");
        Manga m1 = mangaService.addManga(c1.getId(), 0, "Vagabond");
        Manga m2 = mangaService.addManga(c1.getId(), 10, "Berserk");
        mangaService.deleteAllMangas();
        Assertions.assertEquals(0, mangaService.findAllMangas().size());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

    @Test
    void testAddToMangaReader() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first_C", "1");
        Manga m1 = mangaService.addManga(c1.getId(), 0, "vagabond");
        Manga m2 = mangaService.addManga(c1.getId(), 10, "Berserk");
        Reader r1 = readerService.addReader("first_R", "1");

        readerService.addManga(m1.getId(), r1.getId());
        readerService.addManga(m2.getId(), r1.getId());

        log.info(r1.getMangas().toString());

        Reader r2 = readerService.findReader(r1.getId());
        Manga m3 = mangaService.findManga(m1.getId());
        log.info(r2.getMangas().toString());
        Assertions.assertEquals(2, r2.getMangas().size());
        //Assertions.assertEquals(1, m3.getReaders().size());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

    @Test
    void testReaderAddAndFind() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Reader r1 = readerService.addReader("first_R", "1");
        Reader r2 = readerService.addReader("second_R", "2");

        r1 = readerService.findReader(r1.getId());
        r2 = readerService.findReader(r2.getId());
        Assertions.assertEquals(2, readerService.findAllReaders().size());

        Reader r3 = readerService.findReader(r2.getId());
        Assertions.assertEquals(r2.getReaderName(), r3.getReaderName());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

    @Test
    void testRemoveToMangaReader() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first_C", "1");
        Manga m1 = mangaService.addManga(c1.getId(), 0, "Vagabond");
        Manga m2 = mangaService.addManga(c1.getId(), 10, "Berserk");
        Reader r1 = readerService.addReader("first_R", "1");

        readerService.addManga(m1.getId(), r1.getId());
        readerService.addManga(m2.getId(), r1.getId());

        Reader r11 = readerService.findReader(r1.getId());
        readerService.removeManga(m1.getId(), r11.getId());
        Reader r12 = readerService.findReader(r11.getId());

        Manga m11 = mangaService.findManga(m1.getId());
        Assertions.assertEquals(1, r12.getMangas().size());
        log.info(mangaService.findAllMangas().toString());
        log.info(r12.getMangas().toString());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }

    @Test
    void testReaderRemove() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first_C", "1");
        Manga m1 = mangaService.addManga(c1.getId(), 0, "Vagabond");
        Manga m2 = mangaService.addManga(c1.getId(), 10, "Berserk");
        Reader r1 = readerService.addReader("first_R", "1");
        Reader r2 = readerService.addReader("2_R", "2");

        readerService.addManga(m1.getId(), r1.getId());
        readerService.addManga(m2.getId(), r1.getId());

        Reader r11 = readerService.findReader(r1.getId());
        readerService.deleteReader(r11.getId());

        Manga m11 = mangaService.findManga(m1.getId());
        log.info(readerService.findAllReaders().toString());
        log.info(mangaService.getReader(m11.getId()).toString());
        Assertions.assertEquals(0, mangaService.getReader(m11.getId()).size());
        mangaService.deleteAllMangas();
        readerService.deleteAllReaders();
        creatorService.deleteAllCreators();
    }

    @Test
    void testReaderUpdate() {
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
        Creator c1 = creatorService.addCreator("first_C", "1");
        Manga m1 = mangaService.addManga(c1.getId(), 0, "Vagabond");
        Manga m2 = mangaService.addManga(c1.getId(), 10, "Berserk");
        Reader r1 = readerService.addReader("first_R", "1");

        readerService.addManga(m1.getId(), r1.getId());
        readerService.addManga(m2.getId(), r1.getId());

        Reader r11 = readerService.updateReader(r1.getId(), "reader", "password");
        r11 = readerService.findReader(r11.getId());
        m1 = mangaService.findManga(m1.getId());
        log.info(r1.getReaderName());
        log.info(r11.getReaderName());
        Assertions.assertNotEquals(r11.getReaderName(), r1.getReaderName());
        //Assertions.assertEquals(r11.getReaderName(), m1.getReaders().get(0).getReaderName());
        readerService.deleteAllReaders();
        mangaService.deleteAllMangas();
        creatorService.deleteAllCreators();
    }
}
