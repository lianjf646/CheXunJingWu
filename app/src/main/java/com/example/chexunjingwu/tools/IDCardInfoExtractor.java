package com.example.chexunjingwu.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class IDCardInfoExtractor {
    private String province;  //省份
    private String city;      //城市
    private String region;    //区县
    private int year;         //年份
    private int month;        //月份
    private int day;          //日期
    private String gender;    //性别
    private Date birthday;    //出生日期
    private int age;          //年龄

    private Map<String, String> cityCodeMap = new HashMap<String, String>() {
        {
            this.put("11", "北京");
            this.put("12", "天津");
            this.put("13", "河北");
            this.put("14", "山西");
            this.put("15", "内蒙古");
            this.put("21", "辽宁");
            this.put("22", "吉林");
            this.put("23", "黑龙江");
            this.put("31", "上海");
            this.put("32", "江苏");
            this.put("33", "浙江");
            this.put("34", "安徽");
            this.put("35", "福建");
            this.put("36", "江西");
            this.put("37", "山东");
            this.put("41", "河南");
            this.put("42", "湖北");
            this.put("43", "湖南");
            this.put("44", "广东");
            this.put("45", "广西");
            this.put("46", "海南");
            this.put("50", "重庆");
            this.put("51", "四川");
            this.put("52", "贵州");
            this.put("53", "云南");
            this.put("54", "西藏");
            this.put("61", "陕西");
            this.put("62", "甘肃");
            this.put("63", "青海");
            this.put("64", "宁夏");
            this.put("65", "新疆");
            this.put("71", "台湾");
            this.put("81", "香港");
            this.put("82", "澳门");
            this.put("91", "国外");
        }
    };

    private IDCardValidator validator = null;

    /**
     * 通过构造方法初始化各个成员属性
     */
    public IDCardInfoExtractor(String idCard) {
        try {
            validator = new IDCardValidator();
            if (validator.isValidatedAllIdCard(idCard)) {
                if (idCard.length() == 15) {
                    idCard = validator.convertIdCarBy15bit(idCard);
                }
                // 获取省份
                String provinceId = idCard.substring(0, 2);
                Set<String> key = this.cityCodeMap.keySet();
                for (String id : key) {
                    if (id.equals(provinceId)) {
                        this.province = this.cityCodeMap.get(id);
                        break;
                    }
                }

                // 获取性别
                String id17 = idCard.substring(16, 17);
                if (Integer.parseInt(id17) % 2 != 0) {
                    this.gender = "男";
                } else {
                    this.gender = "女";
                }

                // 获取出生日期
                String birthday = idCard.substring(6, 14);
                Date birthDay = new SimpleDateFormat("yyyyMMdd").parse(birthday);
                this.birthday = birthDay;
                GregorianCalendar currentDay = new GregorianCalendar();
                currentDay.setTime(birthDay);
                this.year = currentDay.get(Calendar.YEAR);
                this.month = currentDay.get(Calendar.MONTH) + 1;
                this.day = currentDay.get(Calendar.DAY_OF_MONTH);

                Calendar cal = Calendar.getInstance();
                // 得到当前时间的年、月、日
                Integer yearNow = cal.get(Calendar.YEAR);
                Integer monthNow = cal.get(Calendar.MONTH) + 1;
                Integer dayNow = cal.get(Calendar.DATE);

                // 用当前年月日减去生日年月日
                Integer yearMinus = yearNow - year;
                Integer monthMinus = monthNow - month;
                Integer dayMinus = dayNow - day;
                age = yearMinus; //先大致赋值
                if (yearMinus == 0) { //出生年份为当前年份
                    age = 0;
                } else { //出生年份大于当前年份
                    if (monthMinus < 0) {//出生月份小于当前月份时，还没满周岁
                        age = age - 1;
                    }
                    if (monthMinus == 0) {//当前月份为出生月份时，判断日期
                        if (dayMinus < 0) {//出生日期小于当前月份时，没满周岁
                            age = age - 1;
                        }
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return "省份：" + this.province + ",性别：" + this.gender + ",出生日期：" + this.birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
