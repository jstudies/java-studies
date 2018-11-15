/*
 * Created by Dmitry Melnikov 2018.
 */

/*
 * Created by Dmitry Melnikov 2018.
 */

package edu.javacourse.stp.db;



public class FactoryDataSource {
//    public static StudentOrderDataSource getDataSource() {
//        return null;
//    }

    private static String className = "edu.javacourse.stp.db.FakeDataSource";

    private static String cName = "edu.javacourse.stp.db.reflection.FlexibleDataSource";
    private static String mName = "getAllOrders";

    public static StudentOrderDataSource getDataSource() {
        try {
            Class aClass = Class.forName(className);
            Object o = aClass.newInstance();
            StudentOrderDataSource ds = (StudentOrderDataSource) o;
            return ds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new FakeDataSource();
    }
}
