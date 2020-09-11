package test;

import com.firefly.knn.Knn;

public class Main {
    public static void main(String[] args){
        double[][] datas=new double[][]{
                {5.1,3.8,1.9,0.4},
                {4.8,3.0,1.4,0.3},
                {5.1,3.8,1.6,0.2},
                {4.6,3.2,1.4,0.2},
                {5.3,3.7,1.5,0.2},
                {5.0,3.3,1.4,0.2},
                {7.0,3.2,4.7,1.4},
                {6.4,3.2,4.5,1.5},
                {6.9,3.1,4.9,1.5},
                {5.5,2.3,4.0,1.3},
                {6.5,2.8,4.6,1.5},
                {5.7,2.8,4.5,1.3},
                {6.3,3.3,4.7,1.6}
        };
        String[] y=new String[]{
                "apple",
                "apple",
                "apple",
                "apple",
                "apple",
                "apple",
                "orange",
                "orange",
                "orange",
                "orange",
                "orange",
                "orange",
                "orange",
        };

        //归一化
        double[][] datasOne= Knn.minMaxScaling(datas);

        double[] testData=datasOne[0];
        String testY=y[0];

        double[][] trainData=copyDatas(datasOne,1,datasOne.length-1);
        String[] trainY=copyDatas(y,1,datasOne.length-1);

        String yh=Knn.getLabel(trainData,trainY,testData,(int)Math.pow(y.length,0.5));
        if(testY.equals(yh)){
            System.out.println("识别正确:"+yh);
        }else{
            System.out.println("识别错误，正确值："+testY+"，返回值："+yh);
        }
    }

    private static String[] copyDatas(String[] datas,int startRow,int endRow){
        String[] ret=new String[endRow-startRow+1];
        for(int i=startRow;i<=endRow;i++){
            ret[i-startRow]=datas[i];
        }
        return ret;
    }

    private static double[][] copyDatas(double[][] datas,int startRow,int endRow){
        double[][] ret=new double[endRow-startRow+1][];
        for(int i=startRow;i<=endRow;i++){
            ret[i-startRow]=datas[i];
        }
        return ret;
    }
}
