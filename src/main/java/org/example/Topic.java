package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Topic {
    private UUID uuid;
    private String _title;
    private float _height;
    private float _width;

    private int _fontSize;

    private Position _point;
    private List<Topic> _listTopic;


    public Topic(String _title, float _height, float _width, int _fontSize, Position _point) {
        this._title = _title;
        this._height = _height;
        this._width = _width;
        this._fontSize = _fontSize;
        this._point = _point;
        _listTopic = new ArrayList<Topic>();
    }

    public Topic(String _title)
    {
        uuid = UUID.randomUUID();
        this._title = _title;
        _listTopic = new ArrayList<Topic>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getFontSize() {
        return _fontSize;
    }

    public float getHeight() {
        return _height;
    }

    public void setHeight(float height) {
        this._height = height;
    }

    public float getWidth() {
        return _width;
    }

    public void setWidth(float width) {
        this._width = width;
    }

    public Position getPoint() {
        return _point;
    }

    public void setPoint(Position point1) {
        this._point = point1;
    }

    public List<Topic> getListTopic() {
        return _listTopic;
    }


    public String getTitle() {
        return _title;
    }

    public void addChild(Topic ... subNodes)
    {
        for (var item: subNodes) {
            _listTopic.add(item);
        }
    }

    public void deleteChild(Topic ... subTopics) {
        for (var item: subTopics) {
            this._listTopic = removeElement(item, this._listTopic);
        }
    }

    public static List<Topic> removeElement(Topic element, List<Topic> list) {
        return list.stream()
                .filter(item -> item != element)
                .collect(Collectors.toList());
    }

    public void oderTopic(Topic topicA, Topic topicB)
    {
        int tempIndexA = this._listTopic.indexOf(topicA);
        int tempIndexB = this._listTopic.indexOf(topicB);
        this._listTopic.set(tempIndexA, topicB);
        this._listTopic.set(tempIndexB, topicA);
    }

    public void moveTopicToTopic(Topic topicMove, Topic newParentTopic)
    {
        newParentTopic.addChild(topicMove);
        this.deleteChild(topicMove);
    }

    public void moveTopicToFloatingTopic(Topic topicMove, CentralTopic centralTopic)
    {
        centralTopic.addFloatChild(topicMove);
        this.deleteChild(topicMove);
    }



    public void getInfo()
    {
        System.out.println("Title:" + this.getTitle() + " Height:" + this.getHeight() + " Width:" + this.getWidth() + " FontSize:" + this.getFontSize() + " X(" + this.getPoint().getX() + ";" + this.getPoint().getY() + ")");
    }

    public void updateNodeWidthByCharacter(Topic topic)
    {
        float width = ((topic.getTitle().length()) * 20) + 40;
        topic.setWidth(width);
    }




}
