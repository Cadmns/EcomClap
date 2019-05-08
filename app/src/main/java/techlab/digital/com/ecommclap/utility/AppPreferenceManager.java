package techlab.digital.com.ecommclap.utility;

import android.content.Context;
import android.content.SharedPreferences;


public class AppPreferenceManager {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public AppPreferenceManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Const.TAG_LOGINDATA, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void saveString(String id) {
        editor.putString(Const.TAG_ID, id);
        editor.commit();
    }

    /*
    public void savOrderId(String id)
    {
        editor.putString(Const.TAG_ORDER_ID,id);
        editor.commit();
    }*/
    public void clearString() {
        editor.clear();
        editor.commit();
    }

    /* public String getOrderId()
     {
         return sharedPreferences.getString(Const.TAG_ORDER_ID," ");
     }*/

    public String getString() {
        return sharedPreferences.getString(Const.TAG_ID, " ");
    }
}
