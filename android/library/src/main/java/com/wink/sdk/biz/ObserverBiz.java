package com.wink.sdk.biz;

import android.app.Activity;

import com.wink.sdk.listener.Observer;
import com.wink.sdk.listener.Observerable;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2020/1/4
 * 公司：    深圳市华移科技股份有限公司
 * 作者：    yingzy
 */
public class ObserverBiz implements Observerable{

    private List<Observer> observerList = null;

    public ObserverBiz (){
        observerList = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if (!observerList.isEmpty()){
            observerList.remove(o);
        }
    }

    @Override
    public void notifyObserver(Activity activity) {
        for (Observer observer : observerList){
            observer.update(activity);
        }
    }
}
