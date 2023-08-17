package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Topic {
    private String ID;
    private String _title;
    private float _height;

    private float _lineHeight;
    private float _width;

    private int _fontSize;

    private int _oder;

    private Point _point;
    private List<Topic> _listTopic;


    public Topic(String _title, float _height, float _width, int _fontSize, Point _point) {
        this._title = _title;
        this._height = _height;
        this._width = _width;
        this._fontSize = _fontSize;
        this._lineHeight = _height;
        this._point = _point;
        _listTopic = new ArrayList<Topic>();
    }

    public Topic(String _title, float _height, float _width, int _fontSize) {
        this._title = _title;
        this._height = _height;
        this._width = _width;
        this._fontSize = _fontSize;
        this._lineHeight = _height;
        _listTopic = new ArrayList<Topic>();
    }

    public Topic(String _title) {
        ID = UUID.randomUUID().toString();
        this._title = _title;
        _listTopic = new ArrayList<Topic>();
        this._lineHeight = ContainValue.subTopicHeight;
    }

    public float getLineHeight() {
        return _lineHeight;
    }

    public void set_lineHeight(float _lineHeight) {
        this._lineHeight = _lineHeight;
    }

    public int get_oder() {
        return _oder;
    }

    public void set_oder(int _oder) {
        this._oder = _oder;
    }

    public String getID() {
        return ID;
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

    public Point getPoint() {
        return _point;
    }

    public void setPoint(Point point1) {
        this._point = point1;
    }

    public List<Topic> getListTopic() {
        return _listTopic;
    }


    public String getTitle() {
        return _title;
    }

    public void addChild(Topic... subNodes) {
        for (var item : subNodes) {
            _listTopic.add(item);
            this.set_lineHeight(this._lineHeight + item._lineHeight);
            item._oder = _listTopic.size();
        }
    }

    public Topic getTopicByID(String topicID)
    {
        for (var item : this.getListTopic()) {
            if (item.getID() == topicID) {
                return item;
            }
            item.getTopicByID(topicID);
        }
        return null;
    }

//    public void traversal(List<String> topicsIDNeedToRemove) {
//        for (var item : this.getListTopic()) {
//            if (topicsIDNeedToRemove.contains(item.getID())) {
//                this.deleteChildByID(item.getID());
//                topicsIDNeedToRemove.remove(item.getID());
//            }
//            item.traversal(topicsIDNeedToRemove);
//        }
//    }

//    public void deleteChild(String... subTopicsID) {
//        for (var itemID : subTopicsID) {
//            this._listTopic = removeElement(itemID, this._listTopic);
//        }
//    }
//
//    public static List<String> removeElement(Topic element, List<Topic> list) {
//        return list.stream()
//                .filter(item -> item != element)
//                .collect(Collectors.toList());
//    }

    void deleteChildByID(String... subTopicsID) {
        for (var element : subTopicsID) {
            List<Topic> filteredTopics = _listTopic.stream()
                    .filter(item -> item.getID() != element)
                    .collect(Collectors.toList());
            this._listTopic = filteredTopics;
        }
    }

    public void oderTopic(Topic topicA, Topic topicB) {
        int tempIndexA = this._listTopic.indexOf(topicA);
        int tempIndexB = this._listTopic.indexOf(topicB);
        this._listTopic.set(tempIndexA, topicB);
        this._listTopic.set(tempIndexB, topicA);
    }

    public void moveTopicToTopic(Topic topicMove, Topic newParentTopic) {
        newParentTopic.addChild(topicMove);
        this.deleteChildByID(topicMove.getID());
    }

    public void moveTopicToFloatingTopic(Topic topicMove, CentralTopic centralTopic) {
        centralTopic.addFloatChild(topicMove);
        this.deleteChildByID(topicMove.getID());
    }


    public void getInfo() {
        System.out.println("Title:" + this.getTitle() + " Height:" + this.getHeight() + " Width:" + this.getWidth() + " FontSize:" + this.getFontSize() + " X(" + this.getPoint().getX() + ";" + this.getPoint().getY() + ")");
    }

    public void updateNodeWidthByCharacter(Topic topic) {
        float width = ((topic.getTitle().length()) * 20) + 40;
        topic.setWidth(width);
    }

    public void removeTopics(String... topicsID) {
        List<String> topicsIDNeedToRemove = new ArrayList<>();
        for (var topicID : topicsID) {
            topicsIDNeedToRemove.add(topicID);
        }
        this.traversal(topicsIDNeedToRemove);
    }

    public void traversal(List<String> topicsIDNeedToRemove) {
        for (var item : this.getListTopic()) {
            if (topicsIDNeedToRemove.contains(item.getID())) {
                this.deleteChildByID(item.getID());
                topicsIDNeedToRemove.remove(item.getID());
            }
            item.traversal(topicsIDNeedToRemove);
        }
    }

}
