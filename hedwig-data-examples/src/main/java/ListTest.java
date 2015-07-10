import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * Created by patrick on 15/7/9.
 *
 * @version $Id$
 */


public class ListTest {
    ArrayList list =Lists.newArrayList();

    public static void main(String[] args) {

        ArrayList list =Lists.newArrayList();
        list.add(3);
        list.add(2);
        ArrayList list1 =Lists.newArrayList();
        list.add(2);
        list.add(3);
        System.out.println(list.equals(list1));
    }
}
