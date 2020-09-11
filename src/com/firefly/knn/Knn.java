package com.firefly.knn;

import java.util.*;

public class Knn {
    /**
     * 归一化
     * @param datas
     * @return
     */
    public static double[][] minMaxScaling(double[][] datas){
        double[][] ret=new double[datas.length][datas[0].length];

        //y的值是列
        for(int y=0;y<datas[0].length;y++){
            //统计每列的最大值和最小值
            double colMax=max(datas,y);
            double colMin=min(datas,y);

            //x的值是行
            for(int x=0;x<datas.length;x++){
                //归一化
                ret[x][y]=(datas[x][y]-colMin)/(colMax-colMin);
            }
        }
        return ret;
    }

    /**
     * 欧式距离
     * @param x1
     * @param x2
     * @return
     */
    public static double distance(double[] x1,double[] x2){
        double ret=0;
        if(x1.length==x2.length){
            for(int i=0;i<x1.length;i++){
                ret+=Math.pow((x1[i]-x2[i]),2);
            }
            //开根
            ret=Math.pow(ret,0.5);
        }else{
            //数据长度不一致
            throw new RuntimeException("Array length inconsistent");
        }
        return ret;
    }

    public static String getLabel(double[][] datas,String[] labels,double[] testData,int k){
        Map<String,Integer> countClass=new HashMap<String, Integer>();//统计分类数量

        List<DistanceItem> d=new ArrayList();//有多少行就有多少个距离
        for(int i=0;i<datas.length;i++){
            d.add(new DistanceItem(distance(datas[i],testData),labels[i]));//计算两行数据的距离
        }

        //通过集合提供的方法实现
        Collections.sort(d, new Comparator<DistanceItem>() {
            public int compare(DistanceItem o1, DistanceItem o2) {
                return (int)(o2.getVal()-o1.getVal());
            }
        });

        //统计前面K个距离每个分类的数量
        for(int i=0;i<k;i++){
            DistanceItem di=d.get(i);

            Integer val=1;
            if(countClass.containsKey(di.label)){
                val=countClass.get(di.label);
                val++;

            }
            countClass.put(di.label,val);
        }

        //获取最大数量的分类
        String ret=max(countClass);
        countClass.clear();
        d.clear();
        return ret;
    }

    private static String max(Map<String,Integer> countClass){
        String ret="";
        int max=0;
        for(Map.Entry<String, Integer> item:countClass.entrySet()){
            if(item.getValue()>max){
                max=item.getValue();
                ret=item.getKey();
            }
        }
        return ret;
    }

    /**
     * 得到某列的最大值
     * @param datas
     * @param colNum
     * @return
     */
    public static double max(double[][] datas,int colNum){
        double ret=datas[0][colNum];
        for(int i=0;i<datas.length;i++){
            if(ret<datas[i][colNum]){
                ret=datas[i][colNum];
            }
        }
        return ret;
    }

    /**
     * 得到某列的最小值
     * @param datas
     * @param colNum
     * @return
     */
    public static double min(double[][] datas,int colNum){
        double ret=datas[0][colNum];
        for(int i=0;i<datas.length;i++){
            if(ret>datas[i][colNum]){
                ret=datas[i][colNum];
            }
        }
        return ret;
    }

    public static class DistanceItem{
        private double val;
        private String label;

        public DistanceItem(){

        }

        public DistanceItem(double val, String label) {
            this.val = val;
            this.label = label;
        }

        public double getVal() {
            return val;
        }

        public void setVal(double val) {
            this.val = val;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
