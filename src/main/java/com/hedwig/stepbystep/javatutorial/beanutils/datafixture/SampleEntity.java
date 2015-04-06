package com.hedwig.stepbystep.javatutorial.beanutils.datafixture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kewu on 14-2-27.
 */
public class SampleEntity extends BaseEntity<SampleEntity>{

    private String id;
    private String name;
    private static String primaryKey ="id";
    private static String seq="id_seq";
    private static Map<String,String> rowsMap = new HashMap<String,String>();

    static {
        //System.out.println("init static");
        rowsMap.put("id","ID");
        rowsMap.put("name","name");
    }

    public SampleEntity(){
        super(primaryKey,seq,rowsMap);
       /* super.setPrimaryKenName(primaryKey);
        super.setRowMap(rowsMap);
        super.setSeqName(seq);*/
    }

    public SampleEntity(String id,String name){
        super(primaryKey,seq,rowsMap);
        this.id =id;
        this.name=name;
    }


    public SampleEntity findByPrimaryKey(String baseId) {
        System.out.println("find by primary Key");
        return null;
    }

   /* public Map<String, String> getRowMap() {
        return rowsMap;
    }

    public String getPrimaryKeyName() {
        return primaryKey;
    }

    public String getSeqName() {
        return seq;
    }*/

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        SampleEntity sample = new SampleEntity();
        System.out.println(sample);
        System.out.println(sample.getRowMap());

    }

    @Override
    public String toString() {
        return "SampleEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
