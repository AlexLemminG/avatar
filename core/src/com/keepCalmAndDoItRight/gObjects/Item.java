package com.keepCalmAndDoItRight.gObjects;

import com.keepCalmAndDoItRight.basics.Act;
import com.keepCalmAndDoItRight.basics.GObject;
import com.keepCalmAndDoItRight.basics.Level;
import com.keepCalmAndDoItRight.basics.ReactToContacts;

/**
 * Created by Alexander on 06.07.2015.
 */
public class Item extends GObject implements ReactToContacts{
    protected Unit owner;
    public Item(Level level) {
        super(level);
//        getActor().setName("Default_Item");
    }


    @Override
    public void touched(final GObject b) {
        final Item t = this;
        if(b instanceof Unit && ((Unit) b).unitType == Unit.UnitType.player){
            if(owner == null) {
                this.actions.add(new Act() {
                    @Override
                    public void act() {
                        ((Unit) b).items.putFirst(t);
                        owner = ((Unit) b);
                        level.os.removeBody(t);
                        getActor().getParent().removeActor(getActor());
                        owner.getActor().addActor(getActor());
                        setPosition(0.55f, 0.6f);
                        getActor().setColor(1,1,1, 0);
                    }
                });
            }
        }
    }

    @Override
    public void untouched(GObject b) {

    }
}
