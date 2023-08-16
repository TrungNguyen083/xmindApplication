package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    public void addFloatChild(Topic... subTopics) {
        for (var item : subTopics) {
            _listFloatTopic.add(item);
            this.set_oder(getListTopic().size());
        }
    }

    public void moveFloatingTopicToTopic(Topic floatingTopicMove, Topic newParentTopic) {
        newParentTopic.addChild(floatingTopicMove);
        this.deleteFloatChild(floatingTopicMove);
    }

    public void deleteFloatChild(Topic... subTopics) {
        for (var item : subTopics) {
            this._listFloatTopic = removeElement(item, this._listFloatTopic);
        }
    }

    public void deleteListSelectTopic(Topic... selectTopics) { //ID
        //Delete Children
        removeTopics(selectTopics);
        //Delete Floating
        removeFloatingTopics(selectTopics);
    }

    void removeFloatingTopics(Topic... topics) {
        List<Topic> topicsNeedToRemove = new ArrayList<>();
        for (var topic : topics) {
            topicsNeedToRemove.add(topic);
        }
        this.traversalFloating(topicsNeedToRemove);
    }

    void traversalFloating(List<Topic> topicsNeedToRemove) {
        for (var item : this.getListFloatTopic()) {
            if (topicsNeedToRemove.contains(item)) {
                this.deleteFloatChild(item);
                topicsNeedToRemove.remove(item);
            }
            item.traversal(topicsNeedToRemove);
        }
    }

    public void addRelationship(UUID end1ID, UUID end2ID) {
        this._listRelationshipTopic.add(new Relationship(end1ID, end2ID));
    }

    public void addRelationship(UUID end1ID) {
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


    public void moveSelectTopicsToTopic(Topic newParentTopic, Topic... topicsToMove) {
        deleteListSelectTopic(topicsToMove);
        for (var item : topicsToMove) {
            newParentTopic.addChild(item);
        }
    }

    public void moveTopicsToFloatingTopic(Topic... topicsToMove) {
        deleteListSelectTopic(topicsToMove);
        for (var item : topicsToMove) {
            this.addFloatChild(item);
        }
    }

    public void assignTopic(List<Topic> rightTopics, List<Topic> leftTopics) {
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
    }

    public void assignTopicPosition(List<Topic> rightTopics, List<Topic> leftTopics) {
        assignTopic(rightTopics, leftTopics);
        float sum1 = 0;
        float sum2 = 0;
        float x, y;
        int sideLineHeight = Math.round((this.getLineHeight() - this.getHeight()) / 4);
        for (var item : rightTopics) {
            x = 0;
            y = 0;
            sum1 = sum1 + sideLineHeight;
            if (sum1 <= sideLineHeight) {
                x = (this.getPoint().getX()) + this.getWidth() + ContainValue.spaceSizeHorizontal;
                y = (this.getPoint().getY()) + item.getHeight() + ContainValue.spaceSizeVertical;
                item.setPoint(new Point(x, y));
            } else {
                x = (this.getPoint().getX()) + this.getWidth() + ContainValue.spaceSizeHorizontal;
                y = (this.getPoint().getY()) - item.getHeight() - ContainValue.spaceSizeVertical;
                item.setPoint(new Point(x, y));
            }
        }
        for (var item : leftTopics) {
            x = 0;
            y = 0;
            sum2 = sum2 + sideLineHeight;
            if (sum2 <= sideLineHeight) {
                x = (this.getPoint().getX()) - item.getWidth() - ContainValue.spaceSizeHorizontal;
                y = (this.getPoint().getY()) + item.getHeight() + ContainValue.spaceSizeVertical;
                item.setPoint(new Point(x, y));
            } else {
                x = (this.getPoint().getX()) - item.getWidth() - ContainValue.spaceSizeHorizontal;
                y = (this.getPoint().getY()) - item.getHeight() - ContainValue.spaceSizeVertical;
                item.setPoint(new Point(x, y));
            }
        }
    }
//    public void assignTopicPosition(List<Topic> rightTopics, List<Topic> leftTopics) {
//        assignTopic(rightTopics, leftTopics);
//        float sum1 = 0;
//        float sum2 = 0;
//        int count = 0;
//        float x, y;
//        int sideLineHeight = Math.round((this.get_lineHeight() - this.getHeight()) / 4);
//        for (int i = 0; i < rightTopics.size(); i++) {
//            x = 0;
//            y = 0;
//            sum1 = sum1 + rightTopics.get(0).get_lineHeight();
//            if (sum1 <= sideLineHeight) {
//                if(count == 0)
//                {
//                    x = (this.getPoint().getX()) + this.getWidth() + ContainValue.spaceSizeHorizontal;
//                    y = (this.getPoint().getY()) + rightTopics.get(i).getHeight() + ContainValue.spaceSizeVertical;
//                    count = 1;
//                }
//                else
//                {
//                    x = rightTopics.get(i - 1).getPoint().getX();
//                    y = (rightTopics.get(i - 1).getPoint().getY()) + ContainValue.spaceSizeVertical + rightTopics.get(i).getHeight();
//                }
//                rightTopics.get(i).setPoint(new Point(x, y));
//
//            } else {
//                count = 0;
//                if(count == 0)
//                {
//                    x = (this.getPoint().getX()) + this.getWidth() + ContainValue.spaceSizeHorizontal;
//                    y = (this.getPoint().getY()) - rightTopics.get(i).getHeight() - ContainValue.spaceSizeVertical;
//                    count = 1;
//                }
//                else {
//                    x = rightTopics.get(i - 1).getPoint().getX();
//                    y = (rightTopics.get(i - 1).getPoint().getX()) - (rightTopics.get(i - 1).getHeight()) - ContainValue.spaceSizeVertical;
//                }
//                rightTopics.get(i).setPoint(new Point(x, y));
//            }
//        }
//        for (int i = 0; i < leftTopics.size(); i++) {
//            x = 0;
//            y = 0;
//            sum2 = sum2 + leftTopics.get(i).get_lineHeight();
//            if (sum2 <= sideLineHeight) {
//                if(i == 0)
//                {
//                    x = (this.getPoint().getX()) - leftTopics.get(i).getWidth() - ContainValue.spaceSizeHorizontal;
//                    y = (this.getPoint().getY()) + leftTopics.get(i).getHeight() + ContainValue.spaceSizeVertical;
//                }
//                else {
//                    x = leftTopics.get(i - 1).getPoint().getX();
//                    y = (leftTopics.get(i - 1).getPoint().getY()) + ContainValue.spaceSizeVertical + leftTopics.get(i).getHeight();
//                }
//                leftTopics.get(i).setPoint(new Point(x, y));
//            } else {
//                if(i == 0)
//                {
//                    x = (this.getPoint().getX()) - leftTopics.get(i).getWidth() - ContainValue.spaceSizeHorizontal;
//                    y = (this.getPoint().getY()) - leftTopics.get(i).getHeight() - ContainValue.spaceSizeVertical;
//                }
//                else {
//                    x = leftTopics.get(i - 1).getPoint().getX();
//                    y = (leftTopics.get(i - 1).getPoint().getX()) - leftTopics.get(i - 1).getHeight() - ContainValue.spaceSizeVertical;
//                }
//                leftTopics.get(i).setPoint(new Point(x, y));
//            }
//        }
//    }
}
