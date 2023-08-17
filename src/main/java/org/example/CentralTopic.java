package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class CentralTopic extends Topic {

    private List<Topic> _listFloatTopic;
    private List<Relationship> _listRelationshipTopic;

    public CentralTopic(String _title, float _height, float _width, int _fontSize, Point _point) {
        super(_title, _height, _width, _fontSize, _point);
        _listFloatTopic = new ArrayList<Topic>();
        _listRelationshipTopic = new ArrayList<Relationship>();
    }

    public CentralTopic(String _title) {
        super(_title);
        _listFloatTopic = new ArrayList<Topic>();
        _listRelationshipTopic = new ArrayList<Relationship>();
    }

    public void moveHeadTopicRelationship(Relationship relationshipToMove, Topic toTopic) {
        relationshipToMove.setEnd2ID(toTopic.getID());
    }

    public void moveTailTopicRelationship(Relationship relationshipToMove, Topic toTopic) {
        relationshipToMove.setEnd1ID(toTopic.getID());
    }

    public void removeRelationship(Relationship... relationshipsToMove) {
        for (var item : relationshipsToMove) {
            this._listRelationshipTopic = removeRelationshipItem(item, this._listRelationshipTopic);
        }
    }

    public static List<Relationship> removeRelationshipItem(Relationship element, List<Relationship> list) {
        return list.stream()
                .filter(item -> item != element)
                .collect(Collectors.toList());
    }

    public List<Relationship> getRelationshipsOfTopic(Topic topic) {
        List<Relationship> listRelationshipOfTopic = new ArrayList<>();
        for (var item : this._listRelationshipTopic) {
            if (item.getEnd1ID() == topic.getID()) {
                listRelationshipOfTopic.add(item);
            }
        }
        return listRelationshipOfTopic;
    }


    public List<Relationship> getListRelationship() {
        return _listRelationshipTopic;
    }

    public Relationship getListRelationshipByID(Relationship relationship) {
        for (var item : _listRelationshipTopic) {
            if (item.getID() == relationship.getID()) {
                return item;
            }
        }
        return null;
    }

    public List<Topic> getListFloatTopic() {
        return _listFloatTopic;
    }

    public Topic getFloatingTopicByID(String topicID){
        for (var item : this._listFloatTopic) {
            if (item.getID() == topicID) {
                return item;
            }
            item.getTopicByID(topicID);
        }
        return null;
    }

    public void addFloatChild(Topic... subTopics) {
        for (var item : subTopics) {
            _listFloatTopic.add(item);
            this.set_oder(getListTopic().size());
        }
    }



    public void moveFloatingTopicToTopic(String floatingTopicIDMove, Topic newParentTopic) {
        Topic selectTopic = getFloatingTopicByID(floatingTopicIDMove);
        this.deleteFloatChildByID(floatingTopicIDMove);
        newParentTopic.addChild(selectTopic);
    }

    public void deleteFloatChildByID(String... floatTopicsID) {
        for (var element : floatTopicsID) {
            List<Topic> filteredTopics = _listFloatTopic.stream()
                    .filter(item -> item.getID() != element)
                    .collect(Collectors.toList());
            this._listFloatTopic = filteredTopics;
        }
    }

    public void deleteListSelectTopic(String ... selectTopicIDs) { //ID
        //Delete Children
        removeTopics(selectTopicIDs);
        //Delete Floating
        removeFloatingTopics(selectTopicIDs);
    }

    void removeFloatingTopics(String... topicsID) {
        List<String> topicsNeedToRemove = new ArrayList<>();
        for (var topicID : topicsID) {
            topicsNeedToRemove.add(topicID);
        }
        this.traversalFloating(topicsNeedToRemove);
    }

    void traversalFloating(List<String> topicsIDNeedToRemove) {
        for (var item : this.getListFloatTopic()) {
            if (topicsIDNeedToRemove.contains(item.getID())) {
                this.deleteFloatChildByID(item.getID());
                topicsIDNeedToRemove.remove(item.getID());
            }
            item.traversal(topicsIDNeedToRemove);
        }
    }

    public void addRelationship(String end1ID, String end2ID) {
        this._listRelationshipTopic.add(new Relationship(end1ID, end2ID));
    }

    public void addRelationship(String end1ID) {
        Topic newFloatingTopic = new Topic("Floating Topic");
        this.addFloatChild(newFloatingTopic);
        addRelationship(end1ID, newFloatingTopic.getID());
    }

    public void addRelationship() {
        Topic newFloatingTopic1 = new Topic("Floating Topic 1");
        Topic newFloatingTopic2 = new Topic("Floating Topic 2");
        this.addFloatChild(newFloatingTopic1, newFloatingTopic2);
        addRelationship(newFloatingTopic1.getID(), newFloatingTopic2.getID());
    }


    public void deleteRelationship(Topic topicRelationship) {
        this._listRelationshipTopic.remove(topicRelationship);
    }


    public void moveSelectTopicsToTopic(Topic newParentTopic, String ... topicsIDToMove) {
        for (var itemID : topicsIDToMove) {
            Topic selectTopic = getTopicByID(itemID);
            deleteChildByID(itemID);
            if(selectTopic == null)
            {
                selectTopic = getFloatingTopicByID(itemID);
                deleteFloatChildByID(itemID);
            }
            newParentTopic.addChild(selectTopic);
        }
    }

    public void moveTopicsToFloatingTopic(String ... topicsIDToMove) {
        deleteListSelectTopic(topicsIDToMove);
        for (var itemID : topicsIDToMove) {
            Topic selectTopic = getTopicByID(itemID);
            this.addFloatChild(selectTopic);
        }
    }

    public Map<String,List<Topic>> assignTopic() {
        List<Topic> rightTopics = new ArrayList<>();
        List<Topic> leftTopics = new ArrayList<>();
        Map<String,List<Topic>> map =new HashMap();
        float sumLineHeight = 0;
        int sideLineHeight = Math.round((this.getLineHeight() - this.getHeight()) / 2);
        for (var item : getListTopic()) {
            sumLineHeight += item.getLineHeight();
            if (sumLineHeight <= sideLineHeight) {
                rightTopics.add(item);
            } else {
                leftTopics.add(item);
            }
        }
        map.put("rightTopics",rightTopics);
        map.put("leftTopics",leftTopics);
        return map;
    }

    public void assignTopicPosition() {
        Map<String,List<Topic>> map = assignTopic();
        float sum1 = 0;
        float sum2 = 0;
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        float x, y;
        int sideLineHeight = Math.round((this.getLineHeight() - this.getHeight()) / 4);
        //Split elements around central topic to 4 parts
        for (int i = 0; i < (map.get("rightTopics")).size(); i++) {
            x = 0;
            y = 0;
            sum1 = sum1 + (map.get("rightTopics")).get(0).getLineHeight();

            // Right side of central topic
            if (sum1 <= sideLineHeight) {
                // First element of top right of central topic
                if (count1 == 0) {
                    x = (this.getPoint().getX()) + this.getWidth() + ContainValue.spaceSizeHorizontal;
                    y = (this.getPoint().getY()) + (map.get("rightTopics")).get(i).getHeight() + ContainValue.spaceSizeVertical;
                    count1 = 1;
                } else {
                    x = (map.get("rightTopics")).get(i - 1).getPoint().getX();
                    y = ((map.get("rightTopics")).get(i - 1).getPoint().getY()) + ContainValue.spaceSizeVertical + (map.get("rightTopics")).get(i).getHeight();
                }
                (map.get("rightTopics")).get(i).setPoint(new Point(x, y));

            } else {
                // First element of bottom right of central topic
                if (count2 == 0) {
                    x = (this.getPoint().getX()) + this.getWidth() + ContainValue.spaceSizeHorizontal;
                    y = (this.getPoint().getY()) - (map.get("rightTopics")).get(i).getHeight() - ContainValue.spaceSizeVertical;
                    count2 = 1;
                } else {
                    x = (map.get("rightTopics")).get(i - 1).getPoint().getX();
                    y = ((map.get("rightTopics")).get(i - 1).getPoint().getY()) - ((map.get("rightTopics")).get(i - 1).getHeight()) - ContainValue.spaceSizeVertical;
                }
                (map.get("rightTopics")).get(i).setPoint(new Point(x, y));
            }
        }

        //Left side of central topic
        for (int i = 0; i < (map.get("leftTopics")).size(); i++) {
            x = 0;
            y = 0;
            sum2 = sum2 + (map.get("leftTopics")).get(i).getLineHeight();
            if (sum2 <= sideLineHeight) {
                //First element of top right of central topic
                if (count3 == 0) {
                    x = (this.getPoint().getX()) - (map.get("leftTopics")).get(i).getWidth() - ContainValue.spaceSizeHorizontal;
                    y = (this.getPoint().getY()) + (map.get("leftTopics")).get(i).getHeight() + ContainValue.spaceSizeVertical;
                    count3 = 1;
                } else {
                    x = (map.get("leftTopics")).get(i - 1).getPoint().getX();
                    y = ((map.get("leftTopics")).get(i - 1).getPoint().getY()) + ContainValue.spaceSizeVertical + (map.get("leftTopics")).get(i).getHeight();
                }
                (map.get("leftTopics")).get(i).setPoint(new Point(x, y));
            } else {
                //First element of bottom right of central topic
                if (count4 == 0) {
                    x = (this.getPoint().getX()) - (map.get("leftTopics")).get(i).getWidth() - ContainValue.spaceSizeHorizontal;
                    y = (this.getPoint().getY()) - (map.get("leftTopics")).get(i).getHeight() - ContainValue.spaceSizeVertical;
                    count4 = 1;
                } else {
                    x = (map.get("leftTopics")).get(i - 1).getPoint().getX();
                    y = ((map.get("leftTopics")).get(i - 1).getPoint().getY()) - (map.get("leftTopics")).get(i - 1).getHeight() - ContainValue.spaceSizeVertical;
                }
                (map.get("leftTopics")).get(i).setPoint(new Point(x, y));
            }
        }
    }
}
