package org.example;

import org.junit.jupiter.api.Test;

import java.text.Format;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @Test
    public void testInitDefault() {
        CentralTopic centralTopic = new CentralTopic("Central Topic");
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
    public void testOderTopic() {
        Result result = getResult();

        assertEquals(0, result.centralTopic.getListTopic().indexOf(result.mainTopic1));
        assertEquals(2, result.centralTopic.getListTopic().indexOf(result.mainTopic3));

        //Swap position between mainTopic1 and mainTopic3:
        result.centralTopic.oderTopic(result.mainTopic1, result.mainTopic3);

        assertEquals(2, result.centralTopic.getListTopic().indexOf(result.mainTopic1));
        assertEquals(0, result.centralTopic.getListTopic().indexOf(result.mainTopic3));
    }

    @Test
    public void testMoveTopicToTopic() {
        Result result = getResult();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic12 = new Topic("Sub Topic 2 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        result.mainTopic1.addChild(subTopic11);
        result.mainTopic1.addChild(subTopic12);
        result.mainTopic2.addChild(subTopic21);
        result.mainTopic3.addChild(subTopic31);

        assertEquals(4, result.centralTopic.getListTopic().size());
        assertEquals(2, result.mainTopic1.getListTopic().size());

        //I want to move subTopic 2 of mainTopic 1 become a topic with same level with mainTopic
        result.mainTopic1.moveTopicToTopic(subTopic12, result.centralTopic);

        assertEquals(5, result.centralTopic.getListTopic().size());
        assertEquals(1, result.mainTopic1.getListTopic().size());
    }

    @Test
    public void testMoveTopicToFloatingTopic() {
        Result result = getResult();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic12 = new Topic("Sub Topic 2 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        result.mainTopic1.addChild(subTopic11);
        result.mainTopic1.addChild(subTopic12);
        result.mainTopic2.addChild(subTopic21);
        result.mainTopic3.addChild(subTopic31);

        assertEquals(0, result.centralTopic.getListFloatTopic().size());
        assertEquals(1, result.mainTopic2.getListTopic().size());

        //I want to move subTopic 1 of mainTopic 2 to FloatingTopic
        result.mainTopic2.moveTopicToFloatingTopic(subTopic21, result.centralTopic);

        assertEquals(1, result.centralTopic.getListFloatTopic().size());
        assertEquals(0, result.mainTopic2.getListTopic().size());
    }

    private static Result getResult() {
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
        Result result = getResult();
        Topic floatingTopic1 = new Topic("Floating Topic 1");
        Topic floatingTopic2 = new Topic("Floating Topic 2");
        Topic floatingTopic3 = new Topic("Floating Topic 3");

        result.centralTopic.addFloatChild(floatingTopic1, floatingTopic2, floatingTopic3);

        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic12 = new Topic("Sub Topic 2 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        result.mainTopic1.addChild(subTopic11);
        result.mainTopic1.addChild(subTopic12);
        result.mainTopic2.addChild(subTopic21);
        result.mainTopic3.addChild(subTopic31);

        assertEquals(3, result.centralTopic.getListFloatTopic().size());
        assertEquals(0, subTopic11.getListTopic().size());

        //I want to move floatingTopic2 into subTopic 1 of MainTopic 1
        result.centralTopic.moveFloatingTopicToTopic(floatingTopic2, subTopic11);

        assertEquals(2, result.centralTopic.getListFloatTopic().size());
        assertEquals(1, subTopic11.getListTopic().size());
    }

    @Test
    public void testTopicHasManyRelationship() {
        Result result = getResult();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        result.mainTopic1.addChild(subTopic11, subTopic21, subTopic31);

        assertEquals(0, result.centralTopic.getListRelationship().size());

        result.centralTopic.addRelationship(subTopic11.getUuid(), result.mainTopic2.getUuid());
        result.centralTopic.addRelationship(subTopic11.getUuid(), result.mainTopic3.getUuid());
        result.centralTopic.addRelationship(subTopic11.getUuid(), subTopic21.getUuid());
        result.centralTopic.addRelationship(subTopic11.getUuid(), subTopic31.getUuid());

        assertEquals(4, result.centralTopic.getListRelationship().size());
    }

    @Test
    public void testDeleteTopic() {
        Result result = getResult();

        var subTopic1 = new Topic("Sub Topic 1 of Main Topic 1");
        var subTopic2 = new Topic("Sub Topic 2 of Main Topic 1");

        result.mainTopic1.addChild(subTopic1, subTopic2);

        assertEquals(4, result.centralTopic.getListTopic().size());

        //I want to delete mainTopic1
        result.centralTopic.deleteChild(result.mainTopic1);

        assertEquals(3, result.centralTopic.getListTopic().size());
    }

    @Test
    public void testDeleteListSelectTopic() {
        Result result = getResult();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic12 = new Topic("Sub Topic 2 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        result.mainTopic1.addChild(subTopic11);
        result.mainTopic1.addChild(subTopic12);
        result.mainTopic2.addChild(subTopic21);
        result.mainTopic3.addChild(subTopic31);

        assertEquals(4, result.centralTopic.getListTopic().size());
        assertEquals(2, result.mainTopic1.getListTopic().size());

        result.centralTopic.deleteListSelectTopic(result.mainTopic2, result.mainTopic3, subTopic11);

        assertEquals(2, result.centralTopic.getListTopic().size());
        assertEquals(1, result.mainTopic1.getListTopic().size());
    }

    @Test
    public void testMoveHeadRelationship() {
        Result result = getResult();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        result.mainTopic1.addChild(subTopic11, subTopic21, subTopic31);

        result.centralTopic.addRelationship(subTopic11.getUuid(), result.mainTopic2.getUuid());
        result.centralTopic.addRelationship(subTopic11.getUuid(), result.mainTopic3.getUuid());

        assertEquals(result.mainTopic2.getUuid(), result.centralTopic.getListRelationshipOfTopic(subTopic11).get(0).getEnd2ID());

        //I want to change head of subTopic11 relationship from mainTopic2 to mainTopic4
        result.centralTopic.moveHeadTopicRelationship(result.centralTopic.getListRelationshipOfTopic(subTopic11).get(0), result.mainTopic4);

        assertEquals(2, result.centralTopic.getListRelationshipOfTopic(subTopic11).size());
        assertEquals(result.mainTopic4.getUuid(), result.centralTopic.getListRelationshipOfTopic(subTopic11).get(0).getEnd2ID());
    }

    @Test public void testMoveTailRelationship()
    {
        Result result = getResult();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        result.mainTopic1.addChild(subTopic11, subTopic21, subTopic31);

        result.centralTopic.addRelationship(subTopic11.getUuid(), result.mainTopic2.getUuid());
        result.centralTopic.addRelationship(subTopic11.getUuid(), result.mainTopic3.getUuid());

        assertEquals(result.mainTopic2.getUuid(), result.centralTopic.getListRelationshipOfTopic(subTopic11).get(0).getEnd2ID());
        assertEquals(2, result.centralTopic.getListRelationshipOfTopic(subTopic11).size());

        //I want to change tail of relationship(subTopic11,mainTopic2) to subTopic21 relationship(subTopic21,mainTopic2)
        result.centralTopic.moveTailTopicRelationship(result.centralTopic.getListRelationshipOfTopic(subTopic11).get(0), subTopic21);

        assertEquals(1, result.centralTopic.getListRelationshipOfTopic(subTopic11).size());
        assertEquals(subTopic21.getUuid(), result.centralTopic.getListRelationshipOfTopic(subTopic21).get(0).getEnd1ID());
    }

    @Test
    public void deleteRelationship()
    {
        Result result = getResult();
        Topic subTopic11 = new Topic("Sub Topic 1 of Main Topic 1");
        Topic subTopic21 = new Topic("Sub Topic 1 of Main Topic 2");
        Topic subTopic31 = new Topic("Sub Topic 1 of Main Topic 3");

        result.mainTopic1.addChild(subTopic11, subTopic21, subTopic31);

        result.centralTopic.addRelationship(subTopic11.getUuid(), result.mainTopic2.getUuid());
        result.centralTopic.addRelationship(subTopic11.getUuid(), result.mainTopic3.getUuid());

        assertEquals(2, result.centralTopic.getListRelationshipOfTopic(subTopic11).size());

        //Delete a relationship
        result.centralTopic.removeRelationship(result.centralTopic.getListRelationshipOfTopic(subTopic11).get(0));

        assertEquals(1, result.centralTopic.getListRelationshipOfTopic(subTopic11).size());
    }
}