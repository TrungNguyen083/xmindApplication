package org.example;

public class Main {
    public static void main(String[] args) {
        CentralTopic centralTopic = new CentralTopic("Central Topic", ContainValue.centralTopicHeight, ContainValue.centralTopicWidth, ContainValue.centralTopicFontSize, new Point(0,0));
        initDefault(centralTopic);
        testMainTopicNodeHasManyChildren(centralTopic.getListTopic().get(0));
        testSubNodeHasManyChildren(centralTopic.getListTopic().get(0).getListTopic().get(0));
        testCentralTopicHasManyFloatChildren(centralTopic);
        testFloatTopicHasManyChildren(centralTopic.getListFloatTopic().get(0));
        testSubFloatTopicHasManyChildren(centralTopic.getListFloatTopic().get(0).getListTopic().get(0));
        //testTopicHasRelationship(centralTopic.getListTopic().get(0),centralTopic.getListTopic().get(1));
        //centralTopic.getListTopic().get(0).getListRelationshipTopic().get(0).getInfo();
//        centralTopic.getListTopic().get(0).getInfo();
//        centralTopic.getListTopic().get(0).getListTopic().get(0).getInfo();
//        centralTopic.getListTopic().get(0).getListTopic().get(0).getListTopic().get(0).getInfo();
//        centralTopic.getListFloatTopic().get(0).getInfo();
//        centralTopic.getListFloatTopic().get(0).getListFloatTopic().get(0).getInfo();
//        centralTopic.getListFloatTopic().get(0).getListFloatTopic().get(0).getListFloatTopic().get(0).getInfo();

    }

    public static void initDefault(CentralTopic centralTopic)
    {
        Topic mainTopic1 = new Topic("Main Topic 1", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize, new Point(35, 50));
        Topic mainTopic2 = new Topic("Main Topic 2", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize, new Point(35, 50));
        Topic mainTopic3 = new Topic("Main Topic 3", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize, new Point(35, 50));
        Topic mainTopic4 = new Topic("Main Topic 4", ContainValue.mainTopicHeight, ContainValue.mainTopicWidth, ContainValue.mainTopicFontSize, new Point(35, 50));
        centralTopic.getListTopic().add(mainTopic1);
        centralTopic.getListTopic().add(mainTopic2);
        centralTopic.getListTopic().add(mainTopic3);
        centralTopic.getListTopic().add(mainTopic4);
    }

    public static void testMainTopicNodeHasManyChildren(Topic parentNode)
    {
        Topic subNode1 = new Topic("Sub Topic 1", ContainValue.subTopicHeight, ContainValue.subTopicWidth, ContainValue.subTopicFontSize, new Point(35, 50));
        Topic subNode2 = new Topic("Sub Topic 2", ContainValue.subTopicHeight, ContainValue.subTopicWidth, ContainValue.subTopicFontSize, new Point(35, 50));
        Topic subNode3 = new Topic("Sub Topic 3", ContainValue.subTopicHeight, ContainValue.subTopicWidth, ContainValue.subTopicFontSize, new Point(35, 50));
        parentNode.addChild(subNode1);
        parentNode.addChild(subNode2);
        parentNode.addChild(subNode3);
    }

    public static void testSubNodeHasManyChildren(Topic parentNode)
    {
        Topic subNode1 = new Topic("Sub Topic 1 of " + parentNode.getTitle(), ContainValue.subTopicHeight, ContainValue.subTopicWidth, ContainValue.subTopicFontSize, new Point(35, 50));
        Topic subNode2 = new Topic("Sub Topic 2 of " + parentNode.getTitle(), ContainValue.subTopicHeight, ContainValue.subTopicWidth, ContainValue.subTopicFontSize, new Point(35, 50));
        Topic subNode3 = new Topic("Sub Topic 3 of " + parentNode.getTitle(), ContainValue.subTopicHeight, ContainValue.subTopicWidth, ContainValue.subTopicFontSize, new Point(35, 50));
        parentNode.addChild(subNode1);
        parentNode.addChild(subNode2);
        parentNode.addChild(subNode3);
    }

    public static void testCentralTopicHasManyFloatChildren(CentralTopic centralTopic)
    {
        Topic floatTopic1 = new Topic("Floating Topic 1", ContainValue.floatingTopicHeight, ContainValue.floatingTopicWidth, ContainValue.floatingTopicFontSize, new Point(35, 50));
        Topic floatTopic2 = new Topic("Floating Topic 2", ContainValue.floatingTopicHeight, ContainValue.floatingTopicWidth, ContainValue.floatingTopicFontSize, new Point(35, 50));
        Topic floatTopic3 = new Topic("Floating Topic 3", ContainValue.floatingTopicHeight, ContainValue.floatingTopicWidth, ContainValue.floatingTopicFontSize, new Point(35, 50));
        centralTopic.addFloatChild(floatTopic1);
        centralTopic.addFloatChild(floatTopic2);
        centralTopic.addFloatChild(floatTopic3);
    }

    public static void testFloatTopicHasManyChildren(Topic parentFloatTopic)
    {
        Topic subFloatTopic1 = new Topic("Sub Float Topic 1 of " + parentFloatTopic.getTitle(), ContainValue.subTopicHeight, ContainValue.subTopicWidth, ContainValue.subTopicFontSize, new Point(35, 50));
        Topic subFloatTopic2 = new Topic("Sub Float Topic 2 of " + parentFloatTopic.getTitle(), ContainValue.subTopicHeight, ContainValue.subTopicWidth, ContainValue.subTopicFontSize, new Point(35, 50));
        Topic subFloatTopic3 = new Topic("Sub Float Topic 3 of " + parentFloatTopic.getTitle(), ContainValue.subTopicHeight, ContainValue.subTopicWidth, ContainValue.subTopicFontSize, new Point(35, 50));
        parentFloatTopic.addChild(subFloatTopic1);
        parentFloatTopic.addChild(subFloatTopic2);
        parentFloatTopic.addChild(subFloatTopic3);
    }

    public static void testSubFloatTopicHasManyChildren(Topic parentFloatTopic)
    {
        Topic subFloatTopic1 = new Topic("Sub Float Topic 1 of " + parentFloatTopic.getTitle(), ContainValue.subTopicHeight, ContainValue.subTopicWidth, ContainValue.subTopicFontSize, new Point(35, 50));
        Topic subFloatTopic2 = new Topic("Sub Float Topic 2 of " + parentFloatTopic.getTitle(), ContainValue.subTopicHeight, ContainValue.subTopicWidth, ContainValue.subTopicFontSize, new Point(35, 50));
        Topic subFloatTopic3 = new Topic("Sub Float Topic 3 of " + parentFloatTopic.getTitle(), ContainValue.subTopicHeight, ContainValue.subTopicWidth, ContainValue.subTopicFontSize, new Point(35, 50));
        parentFloatTopic.addChild(subFloatTopic1);
        parentFloatTopic.addChild(subFloatTopic2);
        parentFloatTopic.addChild(subFloatTopic3);
    }

    //public static void testTopicHasRelationship(Topic topicFrom, Topic topicTo)
    {
        //topicFrom.addRelationship(topicTo);
    }
}