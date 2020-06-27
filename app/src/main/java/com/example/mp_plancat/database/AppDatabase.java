package com.example.mp_plancat.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mp_plancat.database.converters.DateConverters;
import com.example.mp_plancat.database.dao.AssignedGoodsDao;
import com.example.mp_plancat.database.dao.CatDao;
import com.example.mp_plancat.database.dao.CollectedCatDao;
import com.example.mp_plancat.database.dao.GameInfoDao;
import com.example.mp_plancat.database.dao.GoodsDao;
import com.example.mp_plancat.database.dao.PurchasedGoodsDao;
import com.example.mp_plancat.database.dao.TodoDao;
import com.example.mp_plancat.database.entity.AssignedGoods;
import com.example.mp_plancat.database.entity.Cat;
import com.example.mp_plancat.database.entity.CollectedCat;
import com.example.mp_plancat.database.entity.GameInfo;
import com.example.mp_plancat.database.entity.Goods;
import com.example.mp_plancat.database.entity.PurchasedGoods;
import com.example.mp_plancat.database.entity.Todo;

//@Database(entities = {Todo.class, GameInfo.class, Goods.class, PurchasedGoods.class, AssignedGoods.class, Cat.class, CollectedCat.class}, version = 1)
@Database(entities = {GameInfo.class, Goods.class, PurchasedGoods.class, AssignedGoods.class, Cat.class, CollectedCat.class}, version = 2)
@TypeConverters({DateConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    //public abstract TodoDao todoDao();

    public abstract GameInfoDao gameInfoDao();

    public abstract GoodsDao goodsDao();

    public abstract PurchasedGoodsDao purchasedGoodsDao();

    public abstract AssignedGoodsDao assignedGoodsDao();

    public abstract CatDao catDao();

    public abstract CollectedCatDao collectedCatDao();
}
