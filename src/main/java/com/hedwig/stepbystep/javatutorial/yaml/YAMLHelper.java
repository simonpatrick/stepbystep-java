package com.hedwig.stepbystep.javatutorial.yaml;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/3/10.
 *
 * @version $Id$
 */


public class YAMLHelper {

    private YAMLHelper(){};
    private static  Yaml yaml = new Yaml();
    private static final Logger logger = LogManager.getLogger(YAMLHelper.class.getName());

    public static <T> T  parse(String path,Class<T> clazz){
        checkIfYaml(path);
        try {
            return yaml.loadAs(ClassLoader.getSystemResourceAsStream(path), clazz);
        }catch (Exception e){
               return parseAsList(path,clazz).get(0);
        }
    }

    public static Map parseAsMap(String path){
        return (Map)yaml.load(ClassLoader.getSystemResourceAsStream(path));
    }


    public static <T> List<T> parseAsList(String path,Class<T> clazz){
        checkIfYaml(path);
        List<T> result = Lists.newArrayList();
        List<HashMap> rawResult = Lists.newArrayList();
        try {
            rawResult = (List<HashMap>) yaml.load(ClassLoader.getSystemResourceAsStream(path));
        }catch(Exception e){
            throw new RuntimeException("please check the yaml format:it should be used as list not map");
        }
        for (HashMap o : rawResult) {
            T instance = null;
            try {
                instance = clazz.newInstance();
                BeanUtils.populate(instance,o);
                result.add(instance);
            } catch (InstantiationException e) {
                logger.error(e);
            } catch (IllegalAccessException e) {
                logger.error(e);
            } catch (InvocationTargetException e) {

                logger.error(e);
            }

        }

        return result;
    }

    private static void checkIfYaml(String path){
        if(!path.endsWith(".yaml")) throw  new RuntimeException("It is not YAML file,please check the file");
    }

}
