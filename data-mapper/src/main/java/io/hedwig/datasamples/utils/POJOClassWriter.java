package io.hedwig.datasamples.utils;

import com.beust.jcommander.internal.Maps;

import java.util.Map;

/**
 * Created by patrick on 16/1/16.
 */
public class POJOClassWriter {

    private Map<String,String> members = Maps.newHashMap();
    private static final String DECLARATION ="public class _className {\n " ;
    private static final String ENDING = " \n}";
    private static final String MEMBER_DECLARATION="private _type _name ;";
    private String className;

    public static POJOClassWriter writer(){
        return new POJOClassWriter();
    }

    public POJOClassWriter member(String name){
            this.members.put(name,"String");
            return this;
    }

    public POJOClassWriter naming(String className){
        this.className =className;
        return this;
    }

    public POJOClassWriter member(String name,String type){
        this.members.put(name,type);
        return this;
    }

    public POJOClassWriter generate(){
        System.out.println(DECLARATION.replace("_className",this.className));
        for (Map.Entry<String, String> entry : members.entrySet()) {
            System.out.println(MEMBER_DECLARATION.replace("_type",entry.getValue())
                    .replace("_name", entry.getKey()));
        }

        System.out.println(ENDING);
        return this;
    }

    public POJOClassWriter cleanUp(){
        this.className ="";
        this.members.clear();
        return this;
    }

    public static void main(String[] args) {
        POJOClassWriter.writer().naming("PersonSource")
                .member("firstName").member("lastName")
                .generate()
        .cleanUp()
        .naming("PersonDest").member("givenName").member("sirName").generate();
    }
}
