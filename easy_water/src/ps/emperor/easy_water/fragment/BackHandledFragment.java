package ps.emperor.easy_water.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import ps.emperor.easy_water.R;
public abstract class BackHandledFragment extends Fragment { 
protected BackHandledFragment mBackHandledInterface; 
     /**
      * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
      * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
      * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
      */
     protected abstract boolean onBackPressed();
     
}