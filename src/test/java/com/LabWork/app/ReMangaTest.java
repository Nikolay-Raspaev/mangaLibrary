package com.LabWork.app;

import com.LabWork.app.student.model.Creator;
import com.LabWork.app.student.model.Manga;
import com.LabWork.app.student.service.CreatorService;
import com.LabWork.app.student.service.MangaService;
import com.LabWork.app.student.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReMangaTest {
    @Autowired
    CreatorService creatorService;

    @Autowired
    MangaService mangaService;

    @Autowired
    UserService userService;

    @Test
    void testCreator() {
        creatorService.deleteAllCreators();
        mangaService.deleteAllMangs();
        userService.deleteAllUsers();

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

        creatorService.deleteAllCreators();
        mangaService.deleteAllMangs();
        userService.deleteAllUsers();
    }

    @Test
    void testManga() {
        creatorService.deleteAllCreators();
        mangaService.deleteAllMangs();
        userService.deleteAllUsers();

        Creator c1 = creatorService.addCreator("first", "1");
        Creator c2 = creatorService.addCreator("second", "2");

        Manga p1 = mangaService.addManga(c1, 0, "Vagrant");
        Manga p2 = mangaService.addManga(c2, 10, "Berserk");

        Assertions.assertEquals(2, mangaService.findAllMangs().size());

        Assertions.assertEquals(p1.getCreator(), c1);
        Assertions.assertEquals(p2.getCreator(), c2);

        Assertions.assertEquals(c1.getMangs().get(0), p1);
        Assertions.assertEquals(c2.getMangs().get(0), p2);

        Assertions.assertEquals(p1, mangaService.findManga(p1.getId()));
        Assertions.assertEquals(p2, mangaService.findManga(p2.getId()));

        Manga p3 = mangaService.addManga(c1, 10, "Solo Leveling");
        mangaService.deleteManga(p1.getId());
        Assertions.assertEquals(1, creatorService.findCreator(c1.getId()).getMangs().size());

        Manga p4 = mangaService.updateManga(p2.getId(), 100);

        Assertions.assertNotEquals(p2.getChapterCount(), p4.getChapterCount());

        creatorService.deleteAllCreators();
        mangaService.deleteAllMangs();
        userService.deleteAllUsers();
    }

  /*  @Test
    void testUser() {
        creatorService.deleteAllCreators();
        mangaService.deleteAllMangs();
        userService.deleteAllUsers();

        Creator c1 = creatorService.addCreator("first", "1");
        Creator c2 = creatorService.addCreator("second", "2");

        Post p1 = postService.addPost(c1, "first title", "nonsense");
        Post p2 = postService.addPost(c2, "second title", "ordinal");

        Assertions.assertEquals(2, postService.findAllPosts().size());

        User com1 = commentService.addComment(c1, p2, "What");
        User com2 = commentService.addComment(c2, p1, "How");

        Assertions.assertEquals(c1, p2.getComments().get(0).getCustomer());
        Assertions.assertEquals(c2, p1.getComments().get(0).getCustomer());

        Comment com3 = commentService.addComment(c1, p1, "Really");

        Assertions.assertEquals(com2, commentService.findComment(p1.getComments().get(0).getId()));

        Comment com4 = commentService.updateComment(com3.getId(), "Not really");

        Assertions.assertNotEquals(com3.getContent(), com4.getContent());
        Assertions.assertEquals(com3.getCustomer().getId(), com4.getCustomer().getId());

        creatorService.deleteAllCreators();
        mangaService.deleteAllMangs();
        userService.deleteAllUsers();
    }*/
}
