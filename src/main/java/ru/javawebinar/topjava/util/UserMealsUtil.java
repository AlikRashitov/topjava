package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;


import java.util.Arrays;
import java.util.List;

import java.util.*;
import java.util.stream.Collectors;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> userMealWithExceeds = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

        userMealWithExceeds.forEach(System.out::println);
   /*     for (UserMealWithExceed uu: userMealWithExceeds) {
            System.out.println(uu.getDescription()+ " "+ uu.getCalories()+ " " + uu.isExceed());
        }*/
/*
        .toLocalDate();
        .toLocalTime();
*/
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        //    List<UserMealWithExceed> userMealWithExceed=new ArrayList<>() ;

        //

        //

        //        for (UserMeal list: mealList) {

        //            LocalTime localTime=LocalTime.of(list.getDateTime().getHour(), list.getDateTime().getMinute());

        //

        //

        //            if((!localTime.isBefore(startTime))&&(!localTime.isAfter(endTime))) userMealWithExceed.add(new UserMealWithExceed(list.getDateTime(), list.getDescription(), list.getCalories(), caloriesPerDay>list.getCalories()));

        //        }

        //

        //        return userMealWithExceed;

        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream().collect(Collectors.groupingBy(um->um.getDateTime().toLocalDate(),
            Collectors.summingInt(UserMeal::getCalories)));

    return  mealList.stream()
            .filter(um->TimeUtil.isBetween(um.getDateTime().toLocalTime(),startTime,endTime))
            .map(um->new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(),
                    caloriesSumByDate.get(um.getDateTime().toLocalDate())>caloriesPerDay))
            .collect(Collectors.toList());

    }
}
