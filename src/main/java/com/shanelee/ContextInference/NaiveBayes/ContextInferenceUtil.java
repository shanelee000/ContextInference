package com.shanelee.ContextInference.NaiveBayes;

import com.shanelee.ContextInference.entity.AttributeEntity;

import java.util.*;

/**
 * 上下文分析算法类（朴素贝叶斯）
 *
 * @author ShaneLee
 * @version 1.0.0
 */
public class ContextInferenceUtil {

    public static Map<String, Double> PROBABILITY_MATRIX = new HashMap<String, Double>();   //条件概率矩阵
    public static int SIZE_OF_DATA; //训练数据数量
    public static List<AttributeEntity> attrsOfRunning = new ArrayList<AttributeEntity>();
    public static List<AttributeEntity> attrsOfWalking = new ArrayList<AttributeEntity>();
    public static List<AttributeEntity> attrsOfIdle = new ArrayList<AttributeEntity>();
    public static List<AttributeEntity> attrsOfResting = new ArrayList<AttributeEntity>();
    public static double MATRIX_OF_RUNNING;
    public static double MATRIX_OF_WALKING;
    public static double MATRIX_OF_IDLE;
    public static double MATRIX_OF_RESTING;

    /**
     * 训练数据
     * @param attributeList
     */
    public static void handleTrainingData(List<AttributeEntity> attributeList){

        SIZE_OF_DATA = attributeList.size();

        init(attributeList);

        buildLightMap(attrsOfRunning, "running");
        buildSoundMap(attrsOfRunning, "running");
        buildTemperatureMap(attrsOfRunning, "running");
        buildHumidityMap(attrsOfRunning, "running");
        buildPositionMap(attrsOfRunning, "running");
        buildMovementMap(attrsOfRunning, "running");
        buildGpsMap(attrsOfRunning, "running");

        buildLightMap(attrsOfWalking, "walking");
        buildSoundMap(attrsOfWalking, "walking");
        buildTemperatureMap(attrsOfWalking, "walking");
        buildHumidityMap(attrsOfWalking, "walking");
        buildPositionMap(attrsOfWalking, "walking");
        buildMovementMap(attrsOfWalking, "walking");
        buildGpsMap(attrsOfWalking, "walking");

        buildLightMap(attrsOfIdle, "idle");
        buildSoundMap(attrsOfIdle, "idle");
        buildTemperatureMap(attrsOfIdle, "idle");
        buildHumidityMap(attrsOfIdle, "idle");
        buildPositionMap(attrsOfIdle, "idle");
        buildMovementMap(attrsOfIdle, "idle");
        buildGpsMap(attrsOfIdle, "idle");

        buildLightMap(attrsOfResting, "resting");
        buildSoundMap(attrsOfResting, "resting");
        buildTemperatureMap(attrsOfResting, "resting");
        buildHumidityMap(attrsOfResting, "resting");
        buildPositionMap(attrsOfResting, "resting");
        buildMovementMap(attrsOfResting, "resting");
        buildGpsMap(attrsOfResting, "resting");

        MATRIX_OF_RUNNING = (double)attrsOfRunning.size() / SIZE_OF_DATA;
        MATRIX_OF_WALKING = (double)attrsOfWalking.size() / SIZE_OF_DATA;
        MATRIX_OF_IDLE = (double)attrsOfIdle.size() / SIZE_OF_DATA;
        MATRIX_OF_RESTING = (double)attrsOfResting.size() / SIZE_OF_DATA;

    }

    /**
     * 上下文分析
     * @param attr 给定的上下文
     * @return 返回上下文
     */
    public static String inferContext(AttributeEntity attr){
        double probOfRunning =
                PROBABILITY_MATRIX.get("light:" + attr.getLight() + "_running") *
                PROBABILITY_MATRIX.get("sound:" + attr.getSound() + "_running") *
                PROBABILITY_MATRIX.get("temperature:" + attr.getTemperature() + "_running") *
                PROBABILITY_MATRIX.get("humidity:" + attr.getHumidity() + "_running") *
                PROBABILITY_MATRIX.get("position:" + attr.getPosition() + "_running") *
                PROBABILITY_MATRIX.get("movement:" + attr.getMovement() + "_running") *
                PROBABILITY_MATRIX.get("gps:" + attr.getGps() + "_running") *
                MATRIX_OF_RUNNING;

        double probOfWalking =
                PROBABILITY_MATRIX.get("light:" + attr.getLight() + "_walking") *
                PROBABILITY_MATRIX.get("sound:" + attr.getSound() + "_walking") *
                PROBABILITY_MATRIX.get("temperature:" + attr.getTemperature() + "_walking") *
                PROBABILITY_MATRIX.get("humidity:" + attr.getHumidity() + "_walking") *
                PROBABILITY_MATRIX.get("position:" + attr.getPosition() + "_walking") *
                PROBABILITY_MATRIX.get("movement:" + attr.getMovement() + "_walking") *
                PROBABILITY_MATRIX.get("gps:" + attr.getGps() + "_walking") *
                MATRIX_OF_WALKING;

        double probOfIdle =
                PROBABILITY_MATRIX.get("light:" + attr.getLight() + "_idle") *
                PROBABILITY_MATRIX.get("sound:" + attr.getSound() + "_idle") *
                PROBABILITY_MATRIX.get("temperature:" + attr.getTemperature() + "_idle") *
                PROBABILITY_MATRIX.get("humidity:" + attr.getHumidity() + "_idle") *
                PROBABILITY_MATRIX.get("position:" + attr.getPosition() + "_idle") *
                PROBABILITY_MATRIX.get("movement:" + attr.getMovement() + "_idle") *
                PROBABILITY_MATRIX.get("gps:" + attr.getGps() + "_idle") *
                MATRIX_OF_IDLE;

        double probOfResting =
                PROBABILITY_MATRIX.get("light:" + attr.getLight() + "_resting") *
                PROBABILITY_MATRIX.get("sound:" + attr.getSound() + "_resting") *
                PROBABILITY_MATRIX.get("temperature:" + attr.getTemperature() + "_resting") *
                PROBABILITY_MATRIX.get("humidity:" + attr.getHumidity() + "_resting") *
                PROBABILITY_MATRIX.get("position:" + attr.getPosition() + "_resting") *
                PROBABILITY_MATRIX.get("movement:" + attr.getMovement() + "_resting") *
                PROBABILITY_MATRIX.get("gps:" + attr.getGps() + "_resting") *
                MATRIX_OF_RESTING;

        Map<String, Double> probsMap = new HashMap<String, Double>();
        probsMap.put("running", probOfRunning);
        probsMap.put("walking", probOfWalking);
        probsMap.put("idle", probOfIdle);
        probsMap.put("resting", probOfResting);

        List<Map.Entry<String, Double>> infolds = new ArrayList<Map.Entry<String, Double>>(probsMap.entrySet());
        Collections.sort(infolds, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                if (o1.getValue() < o2.getValue()) {
                    return -1;
                } else if (o1.getValue().equals(o2.getValue())) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        return infolds.get(infolds.size() - 1).getKey();
    }

    private static void init(List<AttributeEntity> attrList){

        for (AttributeEntity attr : attrList){
            if("running".equals(attr.getContext())){
                attrsOfRunning.add(attr);
            } else if("walking".equals(attr.getContext())){
                attrsOfWalking.add(attr);
            } else if("idle".equals(attr.getContext())){
                attrsOfIdle.add(attr);
            } else if("resting".equals(attr.getContext())){
                attrsOfResting.add(attr);
            }
        }
    }

    private static void buildLightMap(List<AttributeEntity> attrList, String context){

        double numOfVeryBright = 0;
        double numOfBright = 0;
        double numOfNormal = 0;
        double numOfDark = 0;
        double numOfVeryDark = 0;
        int sizeOfList = attrList.size();

        for (AttributeEntity attr : attrList) {
            if("very bright".equals(attr.getLight())){
                numOfVeryBright++;
            }
            if("bright".equals(attr.getLight())){
                numOfBright++;
            }
            if("normal".equals(attr.getLight())){
                numOfNormal++;
            }
            if("dark".equals(attr.getLight())){
                numOfDark++;
            }
            if("very dark".equals(attr.getLight())){
                numOfVeryDark++;
            }
        }

        PROBABILITY_MATRIX.put("light:very bright_" + context, numOfVeryBright / sizeOfList);
        PROBABILITY_MATRIX.put("light:bright_" + context, numOfBright / sizeOfList);
        PROBABILITY_MATRIX.put("light:normal_" + context, numOfNormal / sizeOfList);
        PROBABILITY_MATRIX.put("light:dark_" + context, numOfDark / sizeOfList);
        PROBABILITY_MATRIX.put("light:very dark_" + context, numOfVeryDark / sizeOfList);

    }

    private static void buildSoundMap(List<AttributeEntity> attrList, String context){

        double numOfVeryLoud = 0;
        double numOfLoud = 0;
        double numOfNormal = 0;
        double numOfQuiet = 0;
        double numOfVeryQuiet = 0;
        int sizeOfList = attrList.size();

        for (AttributeEntity attr : attrList) {
            if("very loud".equals(attr.getSound())){
                numOfVeryLoud++;
            }
            if("loud".equals(attr.getSound())){
                numOfLoud++;
            }
            if("normal".equals(attr.getSound())){
                numOfNormal++;
            }
            if("quiet".equals(attr.getSound())){
                numOfQuiet++;
            }
            if("very quiet".equals(attr.getSound())){
                numOfVeryQuiet++;
            }
        }

        PROBABILITY_MATRIX.put("sound:very loud_" + context, numOfVeryLoud / sizeOfList);
        PROBABILITY_MATRIX.put("sound:loud_" + context, numOfLoud / sizeOfList);
        PROBABILITY_MATRIX.put("sound:normal_" + context, numOfNormal / sizeOfList);
        PROBABILITY_MATRIX.put("sound:quiet_" + context, numOfQuiet / sizeOfList);
        PROBABILITY_MATRIX.put("sound:very quiet_" + context, numOfVeryQuiet / sizeOfList);

    }

    private static void buildTemperatureMap(List<AttributeEntity> attrList, String context){

        double numOfVeryHot = 0;
        double numOfHot = 0;
        double numOfNormal = 0;
        double numOfCold = 0;
        double numOfVeryCold = 0;
        int sizeOfList = attrList.size();

        for (AttributeEntity attr : attrList) {
            if("very hot".equals(attr.getTemperature())){
                numOfVeryHot++;
            }
            if("hot".equals(attr.getTemperature())){
                numOfHot++;
            }
            if("normal".equals(attr.getTemperature())){
                numOfNormal++;
            }
            if("cold".equals(attr.getTemperature())){
                numOfCold++;
            }
            if("very cold".equals(attr.getTemperature())){
                numOfVeryCold++;
            }
        }

        PROBABILITY_MATRIX.put("temperature:very hot_" + context, numOfVeryHot / sizeOfList);
        PROBABILITY_MATRIX.put("temperature:hot_" + context, numOfHot / sizeOfList);
        PROBABILITY_MATRIX.put("temperature:normal_" + context, numOfNormal / sizeOfList);
        PROBABILITY_MATRIX.put("temperature:cold_" + context, numOfCold / sizeOfList);
        PROBABILITY_MATRIX.put("temperature:very cold_" + context, numOfVeryCold / sizeOfList);

    }

    private static void buildHumidityMap(List<AttributeEntity> attrList, String context){

        double numOfHigh = 0;
        double numOfMedium = 0;
        double numOfLow = 0;
        int sizeOfList = attrList.size();

        for (AttributeEntity attr : attrList) {
            if("high".equals(attr.getHumidity())){
                numOfHigh++;
            }
            if("medium".equals(attr.getHumidity())){
                numOfMedium++;
            }
            if("low".equals(attr.getHumidity())){
                numOfLow++;
            }
        }

        PROBABILITY_MATRIX.put("humidity:high_" + context, numOfHigh / sizeOfList);
        PROBABILITY_MATRIX.put("humidity:medium_" + context, numOfMedium / sizeOfList);
        PROBABILITY_MATRIX.put("humidity:low_" + context, numOfLow / sizeOfList);
    }

    private static void buildPositionMap(List<AttributeEntity> attrList, String context){

        double numOfLying = 0;
        double numOfStanding = 0;
        int sizeOfList = attrList.size();

        for (AttributeEntity attr : attrList) {
            if("lying".equals(attr.getPosition())){
                numOfLying++;
            }
            if("standing".equals(attr.getPosition())){
                numOfStanding++;
            }
        }

        PROBABILITY_MATRIX.put("position:lying_" + context, numOfLying / sizeOfList);
        PROBABILITY_MATRIX.put("position:standing_" + context, numOfStanding / sizeOfList);

    }

    private static void buildMovementMap(List<AttributeEntity> attrList, String context){

        double numOfNotMoving = 0;
        double numOfMoving = 0 ;
        double numOfMovingFast = 0;
        int sizeOfList = attrList.size();

        for (AttributeEntity attr : attrList) {
            if("not moving".equals(attr.getMovement())){
                numOfNotMoving++;
            }
            if("moving".equals(attr.getMovement())){
                numOfMoving++;
            }
            if("moving fast".equals(attr.getMovement())){
                numOfMovingFast++;
            }
        }

        PROBABILITY_MATRIX.put("movement:not  moving_" + context, numOfNotMoving / sizeOfList);
        PROBABILITY_MATRIX.put("movement:moving_" + context, numOfMoving / sizeOfList);
        PROBABILITY_MATRIX.put("movement:moving fast_" + context, numOfMovingFast / sizeOfList);
    }

    private static void buildGpsMap(List<AttributeEntity> attrList, String context){

        double numOfIndoor = 0;
        double numOfOutdoor = 0;
        int sizeOfList = attrList.size();

        for (AttributeEntity attr : attrList) {
            if("indoor".equals(attr.getGps())){
                numOfIndoor++;
            }
            if("outdoor".equals(attr.getGps())){
                numOfOutdoor++;
            }
        }

        PROBABILITY_MATRIX.put("gps:indoor_" + context, numOfIndoor / sizeOfList);
        PROBABILITY_MATRIX.put("gps:outdoor_" + context, numOfOutdoor / sizeOfList);
    }

}
