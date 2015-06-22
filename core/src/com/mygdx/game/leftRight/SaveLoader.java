package com.mygdx.game.leftRight;

import com.badlogic.gdx.files.FileHandle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Created by Alexander on 20.06.2015.
 */
public class SaveLoader {
    public String save(Object o)  {
        try {
            FileHandle f = new FileHandle("mob.xml");


            JAXBContext context = JAXBContext.newInstance(Mob.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(o, f.file());
            marshaller.marshal(o, System.out);
        }catch (JAXBException e){
            e.printStackTrace();
        }
        return null;
    }

    public Object load()  {
        Object o = null;
        try {
            FileHandle f = new FileHandle("mob.xml");


            JAXBContext context = JAXBContext.newInstance(Mob.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
//            unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            o = unmarshaller.unmarshal(f.file());
            ((Mob) o).createBody(LeftRight.instance.world);
            LeftRight.instance.os.put(((Mob) o));
        }catch (JAXBException e){
            e.printStackTrace();
        }
        return o;
    }


}
