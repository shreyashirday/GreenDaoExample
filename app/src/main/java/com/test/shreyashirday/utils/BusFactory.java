package com.test.shreyashirday.utils;

import android.content.Context;

import com.test.shreyashirday.myapplication.MyApplication;

import java.util.List;

import shreyashirday.Bus;
import shreyashirday.BusDao;

/**
 * Created by shreyashirday on 12/18/14.
 */
public class BusFactory {

    public static void insertOrUpdate(Context ctx, Bus bus){
            getBusDao(ctx).insertOrReplace(bus);
    }

    public static void clearBuses(Context ctx){
        getBusDao(ctx).deleteAll();
    }

    public  static  void deleteBusWithId(Context ctx,long id){
        getBusDao(ctx).delete(getBusForId(ctx, id));
    }

    public static List<Bus> getAllBuses(Context context){
        return  getBusDao(context).loadAll();
    }

    public static List<Bus> getBusForName(Context context,String name){
        return getBusDao(context).queryBuilder().where(BusDao.Properties.Route.eq(name)).list();
    }

    public static Bus getBusForId(Context context,long id){
        return getBusDao(context).load(id);
    }

    private static BusDao getBusDao(Context ctx){
        return ((MyApplication) ctx.getApplicationContext()).getDaoSession().getBusDao();
    }



}


