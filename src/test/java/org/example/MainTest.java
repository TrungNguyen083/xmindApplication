package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @Test
    public void testInitDefault() {
        CentralTopic centralTopic = new CentralTopic("Central Topic");
        Point topic1Point = new Point();
        topic1Point.calculatePoint();
        Topic topic1 = new Topic("Main Topic 1");
        Topic topic2 = new Topic("Main Topic 2");
        Topic topic3 = new Topic("Main Topic 3");
        Topic topic4 = new Topic("Main Topic 4");

        centralTopic.addChild(topic1, topic2, topic3, topic4);

        assertEquals(4, centralTopic.getListTopic().size());
    }

    @Test
    public void testTopicHasManyChildren() {
        Topic mainTopic = new Topic("Main Topic");
        Topic subTopic1 = new Topic("Sub Topic 1");
        Topic subTopic2 = new Topic("Sub Topic 2");

        mainTopic.addChild(subTopic1, subTopic2);

        assertEquals(2, mainTopic.getListTopic().size());
    }

    @Test
    public void testSubTopicHasManyChildren() {
        Topic mainTopic = new Topic("Main Topic");
        Topic subTopic1 = new Topic("Sub Topic 1");

        mainTopic.addChild(subTopic1);

        Topic subTopic11 = new Topic("Sub Topic 1 of SubTopic 1");
        Topic subTopic12 = new Topic("Sub Topic 2 of SubTopic 1");

        subTopic1.addChild(subTopic11, subTopic12);

        assertEquals(2, subTopic1.getListTopic().size());
    }

    @Test
    public void testCentralTopicHasManyFloatingChildren() {
        CentralTopic centralTopic = new CentralTopic("Central Topic");
        Topic floatingTopic1 = new Topic("Floating Topic 1");
        Topic floatingTopic2 = new Topic("Floating Topic 2");
        Topic floatingTopic3 = new Topic("Floating Topic 3");

        centralTopic.addFloatChild(floatingTopic1, floatingTopic2, floatingTopic3);

        assertEquals(3, centralTopic.getListFloatTopic().size());
    }

    @Test
    public void testFloatingTopicHasManyChildren() {
        CentralTopic centralTopic = new CentralTopic("Central Topic");
        Topic floatingTopic1 = new Topic("Floating Topic 1");

        centralTopic.addFloatChild(floatingTopic1);

        Topic subTopic1 = new Topic("Sub Topic 1 of Floating Topic 1");
        Topic subTopic2 = new Topic("Sub Topic 1 of Floating Topic 2");
        Topic subTopic3 = new Topic("Sub Topic 1 of Floating Topic 3");

        floatingTopic1.addChild(subTopic1, subTopic2, subTopic3);

        assertEquals(3, floatingTopic1.getListTopic().size());
    }


    @Test
    public void testReOderTopic() {
        Result init = initData();

        assertEquals(0, init.centralTopic.getListTopic().indexOf(init.mainTopic1));
        assertEquals(2, init.centralTopic.getListTopic().indexOf(init.mainTopic3));

        //Swap position between mainTopic1 and mainTopic3:
        init.centralTopic.oderTopic(init.mainTopic1, init.mainTopic3);

        assertEquals(2, init.centralTopic.getListTopic().indexOf(init.mainTopic1));
        assertEquals(0, init.centralTopic.getListTopic().indexOf(init.mainTopic3));
    }

    @Test
    public void testMoveTopicToTopic() {
        Result init = initData();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic12 = new Topic("Sub Topic 2 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        init.mainTopic1.addChild(subTopic11);
        init.mainTopic1.addChild(subTopic12);
        init.mainTopic2.addChild(subTopic21);
        init.mainTopic3.addChild(subTopic31);

        assertEquals(4, init.centralTopic.getListTopic().size());
        assertEquals(2, init.mainTopic1.getListTopic().size());

        //I want to move subTopic 2 of mainTopic 1 become a topic with same level with mainTopic
        init.mainTopic1.moveTopicToTopic(subTopic12, init.centralTopic);

        assertEquals(5, init.centralTopic.getListTopic().size());
        assertEquals(1, init.mainTopic1.getListTopic().size());
    }

    @Test
    public void testMoveTopicsToTopic() {
        Result init = initData();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic12 = new Topic("Sub Topic 2 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        init.mainTopic1.addChild(subTopic11);
        init.mainTopic1.addChild(subTopic12);
        init.mainTopic2.addChild(subTopic21);
        init.mainTopic3.addChild(subTopic31);

        assertEquals(4, init.centralTopic.getListTopic().size());
        assertEquals(1, init.mainTopic3.getListTopic().size());

        //I want to move mainTopic2, mainTopic4 to have same level with subTopic31
        init.centralTopic.moveSelectTopicsToTopic(init.mainTopic3, init.mainTopic2, init.mainTopic4);

        assertEquals(2, init.centralTopic.getListTopic().size());
        assertEquals(3, init.mainTopic3.getListTopic().size());
    }

    @Test
    public void testMoveTopicToFloatingTopic() {
        Result init = initData();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic12 = new Topic("Sub Topic 2 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        init.mainTopic1.addChild(subTopic11);
        init.mainTopic1.addChild(subTopic12);
        init.mainTopic2.addChild(subTopic21);
        init.mainTopic3.addChild(subTopic31);

        assertEquals(0, init.centralTopic.getListFloatTopic().size());
        assertEquals(1, init.mainTopic2.getListTopic().size());

        //I want to move subTopic 1 of mainTopic 2 to FloatingTopic
        init.mainTopic2.moveTopicToFloatingTopic(subTopic21, init.centralTopic);

        assertEquals(1, init.centralTopic.getListFloatTopic().size());
        assertEquals(0, init.mainTopic2.getListTopic().size());
    }

    @Test
    public void testMoveTopicsToFloatingTopic() {
        Result init = initData();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic12 = new Topic("Sub Topic 2 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        init.mainTopic1.addChild(subTopic11);
        init.mainTopic1.addChild(subTopic12);
        init.mainTopic2.addChild(subTopic21);
        init.mainTopic3.addChild(subTopic31);

        assertEquals(0, init.centralTopic.getListFloatTopic().size());
        assertEquals(4, init.centralTopic.getListTopic().size());
        assertEquals(2, init.mainTopic1.getListTopic().size());

        //I want to move mainTopic3 and subTopic12 to FloatingTopic
        init.centralTopic.moveTopicsToFloatingTopic(init.mainTopic3, subTopic12);

        assertEquals(2, init.centralTopic.getListFloatTopic().size());
        assertEquals(3, init.centralTopic.getListTopic().size());
        assertEquals(1, init.mainTopic1.getListTopic().size());
    }

    private static Result initData() {
        CentralTopic centralTopic = new CentralTopic("Central Topic");
        Topic mainTopic1 = new Topic("Main Topic 1");
        Topic mainTopic2 = new Topic("Main Topic 2");
        Topic mainTopic3 = new Topic("Main Topic 3");
        Topic mainTopic4 = new Topic("Main Topic 4");

        centralTopic.addChild(mainTopic1, mainTopic2, mainTopic3, mainTopic4);

        Result result = new Result(centralTopic, mainTopic1, mainTopic2, mainTopic3, mainTopic4);
        return result;
    }

    private record Result(CentralTopic centralTopic, Topic mainTopic1, Topic mainTopic2, Topic mainTopic3,
                          Topic mainTopic4) {
    }

    @Test
    public void testMoveFloatingTopicToTopic() {
        Result init = initData();
        Topic floatingTopic1 = new Topic("Floating Topic 1");
        Topic floatingTopic2 = new Topic("Floating Topic 2");
        Topic floatingTopic3 = new Topic("Floating Topic 3");

        init.centralTopic.addFloatChild(floatingTopic1, floatingTopic2, floatingTopic3);

        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic12 = new Topic("Sub Topic 2 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        init.mainTopic1.addChild(subTopic11);
        init.mainTopic1.addChild(subTopic12);
        init.mainTopic2.addChild(subTopic21);
        init.mainTopic3.addChild(subTopic31);

        assertEquals(3, init.centralTopic.getListFloatTopic().size());
        assertEquals(0, subTopic11.getListTopic().size());

        //I want to move floatingTopic2 into subTopic 1 of MainTopic 1
        init.centralTopic.moveFloatingTopicToTopic(floatingTopic2, subTopic11);

        assertEquals(2, init.centralTopic.getListFloatTopic().size());
        assertEquals(1, subTopic11.getListTopic().size());
    }

    @Test
    public void testMoveFloatTopicsAndTopicsToTopic() {
        Result init = initData();
        Topic floatingTopic1 = new Topic("Floating Topic 1");
        Topic floatingTopic2 = new Topic("Floating Topic 2");
        Topic floatingTopic3 = new Topic("Floating Topic 3");

        init.centralTopic.addFloatChild(floatingTopic1, floatingTopic2, floatingTopic3);

        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic12 = new Topic("Sub Topic 2 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        init.mainTopic1.addChild(subTopic11);
        init.mainTopic1.addChild(subTopic12);
        init.mainTopic2.addChild(subTopic21);
        init.mainTopic3.addChild(subTopic31);

        assertEquals(2, init.mainTopic1.getListTopic().size());
        assertEquals(3, init.centralTopic.getListFloatTopic().size());
        assertEquals(4, init.centralTopic.getListTopic().size());

        //I want to move floatingTopic1, floatingTopic2, mainTopic3 into mainTopic1
        init.centralTopic.moveSelectTopicsToTopic(init.mainTopic1, floatingTopic1, floatingTopic2, init.mainTopic3);

        assertEquals(5, init.mainTopic1.getListTopic().size());
        assertEquals(1, init.centralTopic.getListFloatTopic().size());
        assertEquals(3, init.centralTopic.getListTopic().size());
    }

    @Test
    public void testDeleteTopic() {
        Result init = initData();

        var subTopic1 = new Topic("Sub Topic 1 of Main Topic 1");
        var subTopic2 = new Topic("Sub Topic 2 of Main Topic 1");

        init.mainTopic1.addChild(subTopic1, subTopic2);

        assertEquals(4, init.centralTopic.getListTopic().size());

        //I want to delete mainTopic1
        init.centralTopic.deleteChild(init.mainTopic1);

        assertEquals(3, init.centralTopic.getListTopic().size());
    }

    @Test
    public void testDeleteListSelectTopic() {
        Result init = initData();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic12 = new Topic("Sub Topic 2 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        init.mainTopic1.addChild(subTopic11);
        init.mainTopic1.addChild(subTopic12);
        init.mainTopic2.addChild(subTopic21);
        init.mainTopic3.addChild(subTopic31);

        assertEquals(4, init.centralTopic.getListTopic().size());
        assertEquals(2, init.mainTopic1.getListTopic().size());

        init.centralTopic.deleteListSelectTopic(init.mainTopic2, init.mainTopic3, subTopic11);

        assertEquals(2, init.centralTopic.getListTopic().size());
        assertEquals(1, init.mainTopic1.getListTopic().size());
    }

    @Test
    public void testTopicHasManyRelationship() {
        Result init = initData();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        init.mainTopic1.addChild(subTopic11, subTopic21, subTopic31);

        assertEquals(0, init.centralTopic.getListRelationship().size());

        init.centralTopic.addRelationship(subTopic11.getID(), init.mainTopic2.getID());
        init.centralTopic.addRelationship(subTopic11.getID(), init.mainTopic3.getID());
        init.centralTopic.addRelationship(subTopic11.getID(), subTopic21.getID());
        init.centralTopic.addRelationship(subTopic11.getID(), subTopic31.getID());

        assertEquals(4, init.centralTopic.getListRelationship().size());
    }

    @Test
    public void testAddRelationshipWithoutEnd2() {
        Result init = initData();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        init.mainTopic1.addChild(subTopic11, subTopic21, subTopic31);

        assertEquals(0, init.centralTopic.getListFloatTopic().size());
        assertEquals(0, init.centralTopic.getListRelationship().size());

        //I want to add a new relationship form subTopic21 to space point. And then application will create new floating point
        init.centralTopic.addRelationship(subTopic21.getID());

        assertEquals(1, init.centralTopic.getListFloatTopic().size());
        assertEquals(1, init.centralTopic.getListRelationship().size());
    }

    @Test
    public void testAddRelationshipWithoutAnyEnd() {
        Result init = initData();

        assertEquals(0, init.centralTopic.getListFloatTopic().size());
        assertEquals(0, init.centralTopic.getListRelationship().size());

        //I want to add a new relationship form subTopic21 to space point. And then application will create new floating point
        init.centralTopic.addRelationship();

        assertEquals(2, init.centralTopic.getListFloatTopic().size());
        assertEquals(1, init.centralTopic.getListRelationship().size());
    }

    @Test
    public void testMoveHeadRelationship() {
        Result init = initData();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        init.mainTopic1.addChild(subTopic11, subTopic21, subTopic31);

        init.centralTopic.addRelationship(subTopic11.getID(), init.mainTopic2.getID());
        init.centralTopic.addRelationship(subTopic11.getID(), init.mainTopic3.getID());

        assertEquals(init.mainTopic2.getID(), init.centralTopic.getRelationshipsOfTopic(subTopic11).get(0).getEnd2ID());

        //I want to change head of subTopic11 relationship from mainTopic2 to mainTopic4
        init.centralTopic.moveHeadTopicRelationship(init.centralTopic.getRelationshipsOfTopic(subTopic11).get(0), init.mainTopic4);

        assertEquals(2, init.centralTopic.getRelationshipsOfTopic(subTopic11).size());
        assertEquals(init.mainTopic4.getID(), init.centralTopic.getRelationshipsOfTopic(subTopic11).get(0).getEnd2ID());
    }

    @Test
    public void testMoveTailRelationship() {
        Result init = initData();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        init.mainTopic1.addChild(subTopic11, subTopic21, subTopic31);

        init.centralTopic.addRelationship(subTopic11.getID(), init.mainTopic2.getID());
        init.centralTopic.addRelationship(subTopic11.getID(), init.mainTopic3.getID());

        assertEquals(init.mainTopic2.getID(), init.centralTopic.getRelationshipsOfTopic(subTopic11).get(0).getEnd2ID());
        assertEquals(2, init.centralTopic.getRelationshipsOfTopic(subTopic11).size());

        //I want to change tail of relationship(subTopic11,mainTopic2) to subTopic21 relationship(subTopic21,mainTopic2)
        init.centralTopic.moveTailTopicRelationship(init.centralTopic.getRelationshipsOfTopic(subTopic11).get(0), subTopic21);

        assertEquals(1, init.centralTopic.getRelationshipsOfTopic(subTopic11).size());
        assertEquals(subTopic21.getID(), init.centralTopic.getRelationshipsOfTopic(subTopic21).get(0).getEnd1ID());
    }

    @Test
    public void deleteRelationship() {
        Result init = initData();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        init.mainTopic1.addChild(subTopic11, subTopic21, subTopic31);

        init.centralTopic.addRelationship(subTopic11.getID(), init.mainTopic2.getID());
        init.centralTopic.addRelationship(subTopic11.getID(), init.mainTopic3.getID());

        assertEquals(2, init.centralTopic.getRelationshipsOfTopic(subTopic11).size());

        //Delete a relationship
        init.centralTopic.removeRelationship(init.centralTopic.getRelationshipsOfTopic(subTopic11).get(0));

        assertEquals(1, init.centralTopic.getRelationshipsOfTopic(subTopic11).size());
    }

    @Test
    public void testParentLineHeight() {
        CentralTopic centralTopic = new CentralTopic("Central Topic", ContainValue.centralTopicHeight, ContainValue.centralTopicWidth, ContainValue.centralTopicFontSize, new Point(0, 0));
        Topic mainTopic1 = new Topic("Main Topic 1", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);
        Topic mainTopic2 = new Topic("Main Topic 2", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);
        Topic mainTopic3 = new Topic("Main Topic 3", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);
        Topic mainTopic4 = new Topic("Main Topic 4", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);

        centralTopic.addChild(mainTopic1, mainTopic2, mainTopic3, mainTopic4);

        //Test central line height

        assertEquals(300, centralTopic.getLineHeight());
    }

    @Test
    public void testAssignTopic() {
        CentralTopic centralTopic = new CentralTopic("Central Topic", ContainValue.centralTopicHeight, ContainValue.centralTopicWidth, ContainValue.centralTopicFontSize, new Point(0, 0));
        Topic mainTopic1 = new Topic("Main Topic 1", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);
        Topic mainTopic2 = new Topic("Main Topic 2", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);
        Topic mainTopic3 = new Topic("Main Topic 3", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);
        Topic mainTopic4 = new Topic("Main Topic 4", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);
        Topic mainTopic5 = new Topic("Main Topic 5", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);
        Topic mainTopic6 = new Topic("Main Topic 6", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);
        Topic mainTopic7 = new Topic("Main Topic 7", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);

        centralTopic.addChild(mainTopic1, mainTopic2, mainTopic3, mainTopic4, mainTopic5, mainTopic6, mainTopic7);

        List<Topic> rightTopics = new ArrayList<>();
        List<Topic> leftTopics = new ArrayList<>();

        centralTopic.assignTopic(rightTopics, leftTopics);

        assertEquals(3, rightTopics.size());
        assertEquals(4, leftTopics.size());
    }

    @Test
    public void testSetPosition() {
        CentralTopic centralTopic = new CentralTopic("Central Topic", ContainValue.centralTopicHeight, ContainValue.centralTopicWidth, ContainValue.centralTopicFontSize, new Point(0, 0));
        Topic mainTopic1 = new Topic("Main Topic 1", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);
        Topic mainTopic2 = new Topic("Main Topic 2", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);
        Topic mainTopic3 = new Topic("Main Topic 3", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);
        Topic mainTopic4 = new Topic("Main Topic 4", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize);

        centralTopic.addChild(mainTopic1, mainTopic2, mainTopic3, mainTopic4);

        List<Topic> rightTopics = new ArrayList<>();
        List<Topic> leftTopics = new ArrayList<>();

        centralTopic.assignTopicPosition(rightTopics, leftTopics);

        for (var item : centralTopic.getListTopic()) {
            System.out.println(item.getTitle() + " Point(" + item.getPoint().getX() + ";" + item.getPoint().getY() + ")");
        }

//        assertEquals(350, mainTopic1.getPoint().getX());
//        assertEquals(90, mainTopic1.getPoint().getY());
//        assertEquals(350, mainTopic2.getPoint().getX());
//        assertEquals(-90, mainTopic2.getPoint().getY());
//        assertEquals(-300, mainTopic3.getPoint().getX());
//        assertEquals(90, mainTopic3.getPoint().getY());
//        assertEquals(-300, mainTopic4.getPoint().getX());
//        assertEquals(-90, mainTopic4.getPoint().getY());

    }
}