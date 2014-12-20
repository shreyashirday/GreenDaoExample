package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(3, "shreyashirday");
        Entity bus = schema.addEntity("Bus");

        bus.addIdProperty();

        bus.addStringProperty("route");





        new DaoGenerator().generateAll(schema, args[0]);
    }
}
