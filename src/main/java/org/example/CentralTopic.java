package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CentralTopic extends Topic {

    private List<Topic> _listFloatTopic;
    private List<Relationship> _listRelationshipTopic;
    public CentralTopic(String _title, float _height, float _width, int _fontSize, Position _point) {
        super(_title, _height, _width, _fontSize, _point);
        _listFloatTopic = new ArrayList<Topic>();
        _listRelationshipTopic = new ArrayList<Relationship>();
    }

    public CentralTopic(String _title)
    {
        super(_title);
        _listFloatTopic = new ArrayList<Topic>();
        _listRelationshipTopic = new ArrayList<Relationship>();
    }

    public void moveHeadTopicRelationship(Relationship relationshipToMove, Topic toTopic)
    {
        relationshipToMove.setEnd2ID(toTopic.getUuid());
    }

    public void moveTailTopicRelationship(Relationship relationshipToMove, Topic toTopic)
    {
        relationshipToMove.setEnd1ID(toTopic.getUuid());
    }

    public void removeRelationship(Relationship ... relationshipsToMove)
    {
        for (var item: relationshipsToMove) {
            this._listRelationshipTopic = removeRelationshipItem(item, this._listRelationshipTopic);
        }
    }

    public static List<Relationship> removeRelationshipItem(Relationship element, List<Relationship> list) {
        return list.stream()
                .filter(item -> item != element)
                .collect(Collectors.toList());
    }

    public List<Relationship> getListRelationshipOfTopic(Topic topic)
    {
        List<Relationship> listRelationshipOfTopic = new ArrayList<>();
        for (var item: this._listRelationshipTopic) {
            if(item.getEnd1ID() == topic.getUuid())
            {
                listRelationshipOfTopic.add(item);
            }
        }
        return listRelationshipOfTopic;
    }


    public List<Relationship> getListRelationship() {
        return _listRelationshipTopic;
    }

    public Relationship getListRelationshipByID(Relationship relationship) {
        for (var item: _listRelationshipTopic) {
            if(item.getUuid() == relationship.getUuid())
            {
                return item;
            }
        }
        return null;
    }

    public List<Topic> getListFloatTopic() {
        return _listFloatTopic;
    }

    public void addFloatChild(Topic ... subTopics)
    {
        for (var item: subTopics) {
            _listFloatTopic.add(item);
        }
    }

    public void moveFloatingTopicToTopic(Topic floatingTopicMove, Topic newParentTopic)
    {
        newParentTopic.addChild(floatingTopicMove);
        this.deleteFloatChild(floatingTopicMove);
    }

    public void deleteFloatChild(Topic ... subTopics) {
        for (var item: subTopics) {
            this._listFloatTopic = removeElement(item, this._listFloatTopic);
        }
    }

    public void addRelationship(UUID end1ID, UUID end2ID)
    {
        this._listRelationshipTopic.add(new Relationship(end1ID, end2ID));
    }


    public void deleteRelationship(Topic topicRelationship)
    {
        this._listRelationshipTopic.remove(topicRelationship);
    }

    public void deleteListSelectTopic(Topic ... selectTopics) {
        //Delete Children
        this.deleteChild(selectTopics);
        for (var item: this.getListTopic()) {
            item.deleteChild(selectTopics);
        }
        //Delete Floating
        this.deleteFloatChild(selectTopics);
        for (var item: this._listFloatTopic) {
            item.deleteChild(selectTopics);
        }
    }
}
